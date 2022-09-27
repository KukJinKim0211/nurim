package com.bigdata.nurim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @Schema(description = "장소 식별자")
    private int locationId;
    @Schema(description = "장소명")
    private String locationName;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "전화번호")
    private String phone;
    @Schema(description = "위도")
    private String lat;
    @Schema(description = "경도")
    private String lng;
    @Schema(description = "운영 시간")
    private String openingHours;
    @Schema(description = "서브 카테코리 이름")
    private String subCategoryName;
    @Schema(description = "메인 카테코리 이름")
    private String mainCategoryName;
    @Schema(description = "번호 카테코리 식별자")
    private String mainCategoryId;
    @Schema(description = "편의 시설 정보")
    private List<String> facilities = new ArrayList<>();
}
