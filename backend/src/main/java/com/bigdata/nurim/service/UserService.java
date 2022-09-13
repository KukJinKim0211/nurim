package com.bigdata.nurim.service;

import com.bigdata.nurim.dto.LoginDto;
import com.bigdata.nurim.dto.TokenDto;
import com.bigdata.nurim.dto.UserDto;
import com.bigdata.nurim.entity.LoginType;
import com.bigdata.nurim.entity.User;
import com.bigdata.nurim.repository.UserRepository;
import com.bigdata.nurim.security.JwtFilter;
import com.bigdata.nurim.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public ResponseEntity<String> signup(UserDto userDto, LoginType loginType) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userDto.toEntity(loginType);
        userRepository.save(user);
        return new ResponseEntity<>("가입 성공", HttpStatus.OK);
    }

    public ResponseEntity<String> checkEmail(String email) {
        if (userRepository.findByEmail(email).orElse(null) != null) {
            log.info("이미존재하는 이메일");
            return new ResponseEntity<>("이미 존재하는 이메일입니다.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("사용가능한 이메일입니다.", HttpStatus.OK);
    }

    public ResponseEntity<TokenDto> login(LoginDto loginDto) {
        //  LoginDto의 userName,Password를 받아서 UsernamePasswordAuthenticationToken 객체를 생성한다
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        // authenticationToken 을 이용해서 Authentication 객체를 생성하려고 authenticate메서드가 실행될때
        // CustomUserDetailsService 의 loadUserByUsername 메서드가 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 생성된 Authentication 객체를 SecurityContextHolder에 저장하고,
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 그 인증정보를 기반으로 토큰을 생성한다
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        log.debug(jwt);
        // 생성한 토큰을 Response 헤더에 넣어주고,
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<UserDto> getInfo(HttpServletRequest request) {
        String token = request.getHeader("jwt-token");
        if (!tokenProvider.validateToken(token)) {
            return new ResponseEntity<>(new UserDto(), HttpStatus.NO_CONTENT);
        }

        String userEmail = String.valueOf(tokenProvider.getPayload(token).get("sub"));
        log.info(String.valueOf(tokenProvider.getPayload(token)));
        User findUser = userRepository.findByEmail(userEmail).get();

        UserDto userDto = findUser.toDto();

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}