package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 신청(Registration) 목록 조회 시 사용되는 요약 응답 DTO입니다.
 * <p>
 * 회원별 신청 내역, 모집별 신청 목록, 관리자 전체 신청 조회 등
 * 다양한 리스트 화면에서 공통으로 사용됩니다.
 * <p>
 * 신청에 대한 핵심 정보만 포함하여
 * 빠른 조회와 화면 렌더링을 목적으로 합니다.
 *
 * @param registrationNo     신청 식별 번호
 * @param memberNo           신청한 회원 식별 번호
 * @param recruit            모집 정보
 * @param participantCount   신청 인원 수
 * @param registrationDate   신청(참여) 날짜
 * @param registrationPlace  출발지(경유지 이름)
 * @param registrationAmount 총 요금
 * @param createdAt          신청 생성 일시
 */
public record RegistrationSummaryResponse(
        @JsonProperty("no")
        Long registrationNo,

        Long memberNo,

        RecruitInfo recruit,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("date")
        LocalDate registrationDate,

        @JsonProperty("place")
        String registrationPlace,

        @JsonProperty("amount")
        BigDecimal registrationAmount,

        LocalDateTime createdAt
) {
}

