package com.cu2mber.registrationservice.registration.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 결제 완료 전까지 임시로 저장되는 신청(Registration) 대기 상태 객체입니다.
 * <p>
 * 신청 사전 검증이 완료된 후,
 * 결제 진행 단계에서 필요한 정보를 Redis 등에 일정 시간 동안 저장하기 위해 사용됩니다.
 * <p>
 * {@code orderId}를 기준으로 조회되며,
 * 결제 승인 완료 시 이 정보를 기반으로
 * 실제 신청(Registration)을 생성합니다.
 * <p>
 * 저장된 데이터는 TTL(Time To Live)을 가지며,
 * 일정 시간이 지나면 자동으로 만료됩니다.
 * (예: 결제 미완료 시 10분 후 만료)
 *
 * @param orderId               결제 주문 식별 UUID
 * @param recruitmentNo         모집(recruitment) 식별 번호
 * @param memberNo              신청한 회원 식별 번호
 * @param recruitmentTitle      모집 제목
 * @param participantCount      신청 인원 수
 * @param registrationDate      신청(참여) 날짜
 * @param registrationAmount    결제 예정 금액
 * @param registrationPlace     신청 장소
 */
public record PendingRegistration(
        UUID orderId,

        Long recruitmentNo,

        Long memberNo,

        String recruitmentTitle,

        Integer participantCount,

        LocalDate registrationDate,

        BigDecimal registrationAmount,

        String registrationPlace
) {}

