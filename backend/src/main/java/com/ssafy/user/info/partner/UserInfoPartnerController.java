package com.ssafy.user.info.partner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.util.BasicResponse;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserAuthority;
import com.ssafy.user.info.partner.repository.InfoPetRepository;
import com.ssafy.user.info.partner.repository.PartnerUserRepository;
import com.ssafy.user.info.partner.repository.UserAuthorityCheckRepository;
import com.ssafy.user.info.partner.request.UserInfoPartnerRequest;
import com.ssafy.user.info.partner.response.UserInfoPartnerBasicResponse;
import com.ssafy.user.info.partner.response.UserInfoPartnerBasicResponse.UserPartnerInfoData;
import com.ssafy.user.info.partner.response.UserInfoResponse;
import com.ssafy.user.info.partner.response.UserPetResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = {"User/Info"}, description = "회원정보 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/user/info")
public class UserInfoPartnerController {
	
	@Autowired
	PartnerUserRepository partnerUserRepository;
	
	@Autowired
	InfoPetRepository infoPetRepository;
	
	@Autowired
	UserAuthorityCheckRepository userAuthorityCheckRepository;
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/partner/{id}")
	@ApiOperation(value = "반려인 회원 정보 조회", notes = "반려인 아이디를 받아 마이페이지에 보여줄 정보를 조회한다.")
	@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true)
	@ApiResponse(code = 200, response = UserInfoPartnerBasicResponse.class, message = "반려인 회원정보 조회 진행")
	public ResponseEntity<UserInfoPartnerBasicResponse> getPartnerInfo(@PathVariable String id) {
		
		// token 관련해서 어떻게 할 건지 결정 X
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			// 받아온 사용자가 반려인이 맞는지 확인
			Optional<UserAuthority> userAuthority = userAuthorityCheckRepository.findById(id);
			if (userAuthority.isPresent()) {
				if (!userAuthority.get().getAuthorityName().equals("ROLE_USER")) {
					return ResponseEntity.status(200).body(new UserInfoPartnerBasicResponse("failure", "반려인으로 가입된 사용자가 아닙니다.", null));
				}
			} else {
				return ResponseEntity.status(200).body(new UserInfoPartnerBasicResponse("failure", "존재하지 않는 사용자입니다.", null));
			}
			
			// 반환할 data를 저장할 객체 생성
			UserPartnerInfoData userPartnerInfoData = new UserPartnerInfoData();
			
			// 회원 정보 가져오기
			Optional<User> optionalUser = partnerUserRepository.findById(id);
			
			if (optionalUser.isPresent()) {
				
				User user = optionalUser.get();
				
				UserInfoResponse userInfoResponse = new UserInfoResponse();
				userInfoResponse.setId(user.getId());
				userInfoResponse.setUserName(user.getUserName());
				userInfoResponse.setUserNickname(user.getUserNickname());
				userInfoResponse.setUserPhone(user.getUserPhone());
				userInfoResponse.setUserAlertFlag(user.getUserAlertFlag());
				
				// 응답 값에 넣어주기
				userPartnerInfoData.setUserInfo(userInfoResponse);
			}
			
			// 전체 반려동물 정보 가져오기
			List<Pet> petList = infoPetRepository.findByUserId(id);
			if (petList.size() > 0) {
				List<UserPetResponse> petInfo = new ArrayList<>();
				for (Pet pet : petList) {
					
					UserPetResponse userPetResponse = new UserPetResponse();
					
					userPetResponse.setNo(pet.getNo());
					userPetResponse.setPetImage(pet.getPetImage());
					userPetResponse.setPetName(pet.getPetName());
					userPetResponse.setPetType(pet.getPetType());
					userPetResponse.setPetVariety(pet.getPetVariety());
					userPetResponse.setPetBirth(pet.getPetBirth());
					userPetResponse.setPetInfo(pet.getPetInfo());
					
					petInfo.add(userPetResponse);
				}
				
				// 응답값에 넣어주기
				userPartnerInfoData.setPetInfo(petInfo);
			}
			
			UserInfoPartnerBasicResponse UserInfoPartnerBasicResponse = new UserInfoPartnerBasicResponse("success", "회원정보 조회에 성공했습니다.", userPartnerInfoData);
			
			return ResponseEntity.status(200).body(UserInfoPartnerBasicResponse);
		}
		
		return ResponseEntity.status(200).body(new UserInfoPartnerBasicResponse("failure", "존재하지 않는 사용자입니다.", null));
	}
	
	@PostMapping("/partner")
	@ApiOperation(value = "반려인 회원 정보 수정", notes = "반려인의 회원정보를 수정한다.")
	@ApiResponse(code = 200, response = BasicResponse.class, message = "반려인 회원정보 수정 진행")
	public ResponseEntity<BasicResponse> modifyPartnerInfo(@RequestBody UserInfoPartnerRequest userInfoPartnerRequest) {
		
		System.out.println("수정될 회원 정보 "+userInfoPartnerRequest);
		
		Optional<User> user = partnerUserRepository.findById(userInfoPartnerRequest.getId());
		
		if (user.isPresent()) {
			
			User originalUser = user.get();
			
			// 비밀번호
			String userPassword = userInfoPartnerRequest.getUserPassword();
			if (userPassword != null) {
				if (parameterCheck.isEmpty(userPassword) || !parameterCheck.isValidPassword(userPassword)) {
					return ResponseEntity.status(200).body(new BasicResponse("failure", null));
				}
				userPassword = passwordEncoder.encode(userPassword);
				originalUser.setUserPassword(userPassword);
			}
			
			// 이름
			String userName = userInfoPartnerRequest.getUserName();
			if (parameterCheck.isEmpty(userName) || !parameterCheck.isValidName(userName)) {
				return ResponseEntity.status(200).body(new BasicResponse("failure", null));
			}
			originalUser.setUserName(userName);
			
			// 닉네임
			String userNickname = userInfoPartnerRequest.getUserNickname();
			if (parameterCheck.isEmpty(userName) || !parameterCheck.isValidNickname(userNickname)) {
				return ResponseEntity.status(200).body(new BasicResponse("failure", null));
			}
			originalUser.setUserNickname(userNickname);
			
			// 휴대폰 번호
			String userPhone = userInfoPartnerRequest.getUserPhone();
			if (parameterCheck.isEmpty(userName) || !parameterCheck.isValidPhone(userPhone)) {
				return ResponseEntity.status(200).body(new BasicResponse("failure", null));
			}
			originalUser.setUserPhone(userPhone);
			
			// 알람 수신 방법
			int userAlertFlag = userInfoPartnerRequest.getUserAlertFlag();
			if (!parameterCheck.isValidAlertFlag(userAlertFlag)) {
				return ResponseEntity.status(200).body(new BasicResponse("failure", null));
			}
			originalUser.setUserAlertFlag(userAlertFlag);
			
			partnerUserRepository.save(originalUser);
			return ResponseEntity.status(200).body(new BasicResponse("success", null));
		}
		
		return ResponseEntity.status(200).body(new BasicResponse("failure", null));
	}
}