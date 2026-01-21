package com.cu2mber.registrationservice.registration.service;

import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationPrepareCommand;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationCreateResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationPendingResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import org.springframework.data.domain.Pageable;

/**
 * 신청(Registration) 도메인의 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * <p>
 * 신청 생성 전 사전 검증 및 결제 준비, 신청 생성, 조회, 목록 조회, 취소 등
 * 신청 라이프사이클 전반을 담당합니다.
 */
public interface RegistrationService {

    /**
     * 신청 생성을 위한 사전 처리 및 결제 준비를 수행합니다.
     * <p>
     * 중복 신청 여부, 모집 상태, 결제 금액 검증 등의 사전 검증을 수행하고,
     * 결제에 필요한 정보를 반환합니다.
     *
     * @param command 신청 준비에 필요한 요청 데이터
     * @return 결제 준비 및 신청 대기 상태 정보를 포함한 응답
     */
    RegistrationPendingResponse prepareRegistration(RegistrationPrepareCommand command);

    /**
     * 신청을 최종 생성합니다.
     * <p>
     * 결제 완료 이후 호출되며,
     * 결제 검증이 완료된 요청에 한해 신청을 생성합니다.
     *
     * @param command 신청 생성에 필요한 요청 데이터
     * @return 생성된 신청 정보
     */
    RegistrationCreateResponse createRegistration(RegistrationCreateCommand command);

    /**
     * 신청 단건을 조회합니다.
     * 본인 신청만 조회 가능합니다.
     *
     * @param registrationNo 조회할 신청 식별 번호
     * @param memberNo 회원 번호
     * @return 신청 상세 정보
     */
    RegistrationResponse getMyRegistration(Long registrationNo, Long memberNo);

    /**
     * 신청 단건을 조회합니다.
     * 회원 권한이 GOV,ADMIN일 경우에 조회 가능합니다.
     *
     * @param registrationNo 조회할 신청 식별 번호
     * @param role 회원 권한
     * @return 신청 상세 정보
     */
    RegistrationResponse getRegistration(Long registrationNo, String role);

    /**
     * 특정 회원이 생성한 신청 목록을 페이징 방식으로 조회합니다.
     *
     * @param memberNo 회원 식별 번호
     * @param pageable 페이지 요청 정보
     * @return 회원의 신청 요약 목록 (페이징 결과)
     */
    PageResult<RegistrationSummaryResponse> getMyRegistrations(Long memberNo, Pageable pageable);

    /**
     * 특정 모집(recruitment)에 대한 신청 목록을 페이징 방식으로 조회합니다.
     *
     * @param recruitmentNo 모집 식별 번호
     * @param role 회원 권한
     * @param pageable      페이지 요청 정보
     * @return 모집별 신청 요약 목록 (페이징 결과)
     */
    PageResult<RegistrationSummaryResponse> getRecruitRegistrations(Long recruitmentNo, String role, Pageable pageable);

    /**
     * 전체 신청 목록을 페이징 방식으로 조회합니다.
     * <p>
     * 관리자 화면에서 사용됩니다.
     *
     * @param role 회원 권한
     * @param pageable 페이지 요청 정보
     * @return 전체 신청 요약 목록 (페이징 결과)
     */
    PageResult<RegistrationSummaryResponse> getAllRegistrations(String role, Pageable pageable);

    /**
     * 신청을 취소합니다.
     * <p>
     * 취소 가능 상태 여부를 검증한 후 신청 상태를 변경하며,
     * 결제가 완료된 신청의 경우 환불 처리 로직이 포함될 수 있습니다.
     *
     * @param command 신청 취소에 필요한 요청 데이터
     */
    void cancelRegistration(RegistrationCancelCommand command);
}
