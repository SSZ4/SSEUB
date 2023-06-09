package com.ssafy.reservation.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ssafy.db.entity.Attach;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.pet.response.ReservationPet;
import com.ssafy.reservation.pet.response.ReservationPetFinish;

public interface ReservationService {
	//예약 생성
	Reservation createReservation(Reservation reservation) throws SQLException;
	//no에 해당하는 예약 정보를 가져옴
	Reservation readReservation(int no) throws SQLException;
	//no에 해당하는 예약 삭제
	void deleteReservation(int no) throws SQLException;
	//no에 해당하는 예약의 finish flag를 1로 바꿔 끝낸다
	void finishReservation(int no) throws SQLException;
	//작성된 진단기록을 no에 해당하는 예약에 넣는다
	void writeDignosis(int no, String reservationDignosisRecord) throws SQLException;
	//작성된 리뷰을 no에 해당하는 예약에 넣는다.
	void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen) throws SQLException;
	
	//attach테이블에 파일 경로를 저장한다
	Attach createAttach(int reservationNo, String attachFile) throws SQLException;
	
	
	
	//파트너(유저의) 확정된 예약 정보를 가져온다
	List<ReservationPet> getPartnerConfirmedReservation(String userId) throws SQLException;
	//전문가의 확정된 예약 정보를 가져온다
	List<ReservationPet> getConsultantConfirmedReservation(String consultantId) throws SQLException;
	
	//파트너(유저의) 확정되지 않은 예약 정보를 가져온다
	List<ReservationPet> getPartnerUnconfirmedReservation(String userId) throws SQLException;
	//전문가가 견적을 신청할 수 있는 확정되지 않은 예약 정보를 다 가져온다
	List<ReservationPet> getAllUnconfirmedReservation() throws SQLException;
	
	//공개된 상담 기록을 가져온다 -> 정보 획득
	List<ReservationPetFinish> getOpenReservation() throws SQLException;
	//유저가 지금까지 받았던 상담 기록을 가져온다
	List<ReservationPetFinish> getPartnerPastReservation(String userId) throws SQLException;
	//전문가가 지금까지 해온 상담 기록을 가져온다
	List<ReservationPetFinish> getConsultantPastReservation(String consultantId) throws SQLException;
	
	
	
	//해당 유저의 예약 예정 시간들
	List<String> getDateValidation(String userId) throws SQLException;
	
}
