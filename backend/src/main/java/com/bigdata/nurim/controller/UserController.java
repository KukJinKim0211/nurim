package com.bigdata.nurim.controller;

import com.bigdata.nurim.dto.*;
import com.bigdata.nurim.service.KakaoUserService;
import com.bigdata.nurim.service.NaverUserService;
import com.bigdata.nurim.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final NaverUserService naverUserService;

    @PostMapping("/kakao-login")
    public HttpEntity<?> kakaoLogin(@RequestBody HashMap<String, String> param) {
        kakaoUserService.getUserInfoByAccessToken(param.get("access_token"));
        UserDto userDto = kakaoUserService.getUserInfoByAccessToken(param.get("access_token"));
        return kakaoUserService.login(userDto);
    }
    @PostMapping("/naver-login")
    public HttpEntity<?> naverLogin(@RequestBody HashMap<String, String> param) {
        UserDto userDto = naverUserService.getUserInfoByAccessToken(param.get("access_token"));
        return naverUserService.login(userDto);
    }
    @GetMapping
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        return userService.getInfo(request);
    }
    @DeleteMapping
    public ResponseEntity<String> delete(HttpServletRequest request) {
        return userService.delete(request);
    }
    @PutMapping
    public ResponseEntity<?> modify(@RequestPart(value = "file", required = false) MultipartFile file,
                                    @RequestPart(value = "userInfo", required = false) ModifyUserInfoDto modifyUserInfoDto,
                                    HttpServletRequest request) {
        return userService.modify(modifyUserInfoDto, file,request);
    }
    @PostMapping("/nickname-check")
    public ResponseEntity<NicknameCheckResultDto> nicknameCheck(@RequestBody HashMap<String, String> param){
        return userService.nicknameCheck(param.get("nickname"));
    }
    @PostMapping("/write-moreInfo")
    public ResponseEntity<String> wirteMoreInfo(@RequestBody FirstLoginInfoDto firstLoginInfoDto,HttpServletRequest request){
        return userService.firstLogin(request,firstLoginInfoDto);
    }
}