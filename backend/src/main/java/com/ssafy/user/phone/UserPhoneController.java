package com.ssafy.user.phone;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.common.util.BasicResponse;
import com.ssafy.common.util.ParameterCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/user/phone")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "User/Join" }, description = "사용자 회원가입 API")
public class UserPhoneController {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	UserPhoneService userPhoneService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@GetMapping("/auth")
	@ApiOperation(value = "휴대폰 인증 전송", notes = "사용자의 휴대폰 인증을 위해 인증 메세지를 전송한다.")
	@ApiImplicitParam(name = "userPhone", value = "휴대폰 번호", required = true)
	@ApiResponse(code = 200, response = BasicResponse.class, message = "휴대폰 인증 진행")
	public ResponseEntity<BasicResponse> phoneAuth(String userPhone) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, JsonProcessingException, URISyntaxException {
		
		
		if (parameterCheck.isValidPhone(userPhone)) {
			
			// 인증번호 생성
			String authNumber = userPhoneService.makeAuthNumber(userPhone);
			
			// SMS 전송 API 요청 보내기
			ResponseEntity<BasicResponse> apiResponse = userPhoneService.sendSMS(userPhone, "[SSUEB] 인증번호 ["+authNumber+"]를 입력해 주세요.");
			
			return apiResponse;
		}
		
		return new ResponseEntity<BasicResponse>(new BasicResponse("failure", "전화번호 형식이 잘못됐습니다. 전화번호를 다시 확인해 주세요."), HttpStatus.OK);
	}
	
	@GetMapping("/confirm")
	@ApiOperation(value = "휴대폰 인증 확인", notes = "사용자가 입력한 인증 번호가 옳은지 확인한다.")
	@ApiImplicitParams ({		
		@ApiImplicitParam(name = "authNumber", value = "인증 번호", required = true),
		@ApiImplicitParam(name = "userPhone", value = "휴대폰 번호", required = true)
	})
	@ApiResponse(code = 200, response = BasicResponse.class, message = "휴대폰 인증 번호 확인 진행")
	public ResponseEntity<BasicResponse> phoneConfirm(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "authNumber") String authNumber) {
		
		if (parameterCheck.isValidPhone(userPhone) && parameterCheck.isValidPhoneAuth(authNumber)) {
			
			// Redis에 저장해둔 휴대폰 번호를 key로 하는 value 가져오기
			ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
			String realNumber = valueOperations.get(userPhone);
			
			// 가져온 value 값과 입력 받은 인증번호가 같으면 success
			if (realNumber != null && realNumber.equals(authNumber)) {
				return new ResponseEntity<BasicResponse>(new BasicResponse("success", null), HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<BasicResponse>(new BasicResponse("failure", null), HttpStatus.OK);
	}
}
