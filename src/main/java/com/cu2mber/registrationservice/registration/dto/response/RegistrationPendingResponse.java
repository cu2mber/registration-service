package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 신청(Registration) 생성 전,
 * 결제 준비(Pending) 단계에서 반환되는 응답 DTO입니다.
 * <p>
 * 신청 가능 여부 검증이 완료된 후,
 * 실제 결제를 진행하기 위해 필요한 최소한의 정보를 제공합니다.
 * <p>
 * 반환된 {@code orderId}는 결제 승인 및 신청 생성 단계에서
 * 신청을 식별하는 기준 값으로 사용됩니다.
 *
 * @param orderId              결제 주문 식별 UUID
 * @param recruit               모집 정보
 * @param participantCount     신청 인원 수
 * @param registrationAmount   결제 예정 금액
 * @param registrationDate     신청(참여) 날짜
 */
public record RegistrationPendingResponse(
        UUID orderId,

        RecruitInfo recruit,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("amount")
        BigDecimal registrationAmount,

        @JsonProperty("departDate")
        LocalDate registrationDate
) {
}
