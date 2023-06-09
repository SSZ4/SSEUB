package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 모델 정의.
 */
@Entity
@Table(name="reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicInsert
public class Reservation{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "PK, AI", dataType = "int", example="0")
    int no;
	
	@Column(name="consultant_id")
	@ApiModelProperty(value = "전문가의 아이디")
    String consultantId;
	
	@Column(name="user_id")
	@ApiModelProperty(value = "유저(파트너)의 아이디")
    String userId;
	
	@Column(name="reservation_pet_no")
	@ApiModelProperty(value = "상담받을 반려동물의 번호", dataType = "int", example="0")
    int reservationPetNo;
	
	@Column(name="reservation_date")
	@ApiModelProperty(value = "상담 날짜", example = "2023-02-22 22:22:22")
	String reservationDate;
	
	@Column(name="reservation_register_time")
	@ApiModelProperty(value = "상담신청을 등록한 날짜")
	String reservationRegisterTime;
	
	@Column(name="reservation_consult_content")
	@ApiModelProperty(value = "상담을 하고 싶은 내용")
    String reservationConsultContent;
	
	@Column(name="reservation_dignosis_record")
	@ApiModelProperty(value = "진료 기록")
    String reservationDignosisRecord;
	
	@Column(name="reservation_open")
	@ApiModelProperty(value = "상담정보 공개 여부", dataType = "int", example="0")
    int reservationOpen;
	
	@Column(name="reservation_finish")
	@ApiModelProperty(value = "상담 종료 여부", dataType = "int", example="0")
    int reservationFinish;
	
	@Column(name="review_grade")
	@ApiModelProperty(value = "리뷰 점수", dataType = "int", example="0")
    int reviewGrade;
	
	@Column(name="review_comment")
	@ApiModelProperty(value = "리뷰 평가")
    String reviewComment;
	
	@Column(name="reservation_cost")
	@ApiModelProperty(value = "상담 가격", dataType = "int", example="0")
    int reservationCost;
	
	@Column(name="reservation_reason")
	@ApiModelProperty(value = "상담 가격에 대한 이유")
    String reservationReason;
}
