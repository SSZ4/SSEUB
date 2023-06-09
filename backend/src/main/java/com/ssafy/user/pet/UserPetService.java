package com.ssafy.user.pet;

import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Pet;
import com.ssafy.user.pet.request.PetRequest;

@Service
public interface UserPetService {
	
	/**
	 * DB에 반려동물 정보 추가하기
	 * @param petRequest
	 * **/
	public Pet addPet(String id, PetRequest petRequest);
	
	/**
	 * 입력 받은 반려동물 정보가 유효한지 확인하기
	 * @param petRequest 반려동물 정보 DT
	 * **/
	public boolean isValidPetInfo(PetRequest petRequest);
	
	/**
	 * 반려동물 정보 수정하기
	 * @param no 반려동물 번호
	 * @param petRequest 반려동물 정보 DTO
	 * **/
	public String modifyPet(int no, PetRequest petRequest, boolean isPetDeleteImage);
	
}
