package com.bigdata.nurim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NicknameCheckResultDto {
    @Schema(description = "사용 가능 여부")
    private Boolean availability;
}
