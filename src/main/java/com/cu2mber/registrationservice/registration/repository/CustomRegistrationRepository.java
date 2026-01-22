package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.registration.dto.response.InternalRegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * 신청(Registration) 도메인에 대한 커스텀 조회 Repository 인터페이스입니다.
 * <p>
 * 회원별/모집별/전체 신청 목록 조회와 같이
 * 복합 조건이 필요한 조회를 Querydsl 등으로 구현하기 위해 사용됩니다.
 * <p>
 * 또한 결제 검증 및 내부 처리 로직에서 사용하는
 * 내부 전용 조회 메서드를 포함합니다.
 */
public interface CustomRegistrationRepository {

    /**
     * 특정 회원의 신청 목록을 페이징 방식으로 조회합니다.
     *
     * @param memberNo 회원 식별 번호
     * @param pageable 페이지 요청 정보
     * @return 회원의 신청 요약 목록 (페이징 결과)
     */
    Page<RegistrationSummaryResponse> searchMyRegistrations(Long memberNo, Pageable pageable);

    /**
     * 전체 신청 목록을 페이징 방식으로 조회합니다.
     * <p>
     * 관리자 화면 등에서 사용됩니다.
     *
     * @param pageable 페이지 요청 정보
     * @return 전체 신청 요약 목록 (페이징 결과)
     */
    Page<RegistrationSummaryResponse> searchAllRegistrations(Pageable pageable);

    /**
     * 특정 모집(recruitment)에 대한 신청 목록을 페이징 방식으로 조회합니다.
     *
     * @param recruitmentNo 모집 식별 번호
     * @param pageable      페이지 요청 정보
     * @return 모집별 신청 요약 목록 (페이징 결과)
     */
    Page<RegistrationSummaryResponse> searchRecruitRegistrations(Long recruitmentNo, Pageable pageable);

    /**
     * 신청 단건을 내부 처리용 요약 정보로 조회합니다.
     * <p>
     * 결제 검증, 상태 변경, 환불 처리 등
     * 서비스 내부 로직에서만 사용되는 조회 메서드입니다.
     *
     * @param registrationNo 신청 식별 번호
     * @return 내부 처리용 신청 요약 정보 (존재하지 않으면 {@code Optional.empty()})
     */
    Optional<InternalRegistrationSummaryResponse> searchInternalRegistration(Long registrationNo);

    /**
     * 주문 ID(orderId)를 기준으로 신청 정보를 내부 처리용 요약 형태로 조회합니다.
     * <p>
     * 결제 승인 콜백, 결제 검증 등의 시나리오에서 사용됩니다.
     *
     * @param orderId 결제 주문 식별 UUID
     * @return 내부 처리용 신청 요약 정보 (존재하지 않으면 {@code Optional.empty()})
     */
    Optional<InternalRegistrationSummaryResponse> searchInternalRegistrationByOrderId(UUID orderId);
}

