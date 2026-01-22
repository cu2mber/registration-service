package com.cu2mber.registrationservice.registration.dto.response;

/**
 * 신청(Registration)에 대한 내부 처리용 요약 DTO입니다.
 * <p>
 * 외부 API 응답으로 노출되지 않으며,
 * 결제 검증, 상태 변경, 취소/환불 처리 등
 * 서비스 내부 비즈니스 로직에서 사용됩니다.
 * <p>
 * 최소한의 핵심 정보만 포함하여
 * 빠르고 효율적인 내부 처리를 목적으로 합니다.
 *
 * @param registrationNo   신청 식별 번호
 * @param recruitmentNo    모집(recruitment) 식별 번호
 * @param memberNo         신청한 회원 식별 번호
 * @param participantCount 신청 인원 수
 * @param isCanceled       신청 취소 여부
 */
public record InternalRegistrationSummaryResponse(
        Long registrationNo,

        Long recruitmentNo,

        Long memberNo,

        Integer participantCount,

        Boolean isCanceled
) {
}
