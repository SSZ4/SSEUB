package com.ssafy.user.pet.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PetRequest {
	
	@ApiModelProperty(value = "반려동물 사진")
	MultipartFile petImage;
	
	@ApiModelProperty(value = "반려동물 이름", required = true)
    String petName;
	
	@ApiModelProperty(value = "반려동물 대분류 (개, 고양이, 토끼, 페럿, 기니피그, 햄스터)", required = true)
	String petType;
	
	@ApiModelProperty(value = "반려동물 품종")
	String petVariety;
	
	@ApiModelProperty(value = "반려동물 생일 (YYYY-MM-DD로 입력)")
	String petBirth;
	
	@ApiModelProperty(value = "반려동물 특이사항")
	String petInfo;
}
