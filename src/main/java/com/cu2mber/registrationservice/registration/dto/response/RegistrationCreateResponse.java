package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 신청(Registration) 생성 완료 후 반환되는 응답 DTO입니다.
 * <p>
 * 신청이 정상적으로 생성되었음을 클라이언트에 알리기 위해 사용되며,
 * 생성된 신청의 핵심 정보만을 포함합니다.
 * <p>
 * 주로 신청 완료 화면 또는 생성 직후 확인 용도로 사용됩니다.
 *
 * @param registerNo         생성된 신청 식별 번호
 * @param recruitmentTitle  모집 제목
 * @param participantCount  신청 인원 수
 * @param registrationDate  신청(참여) 날짜
 * @param createdAt         신청 생성 일시
 */
public record RegistrationCreateResponse(
        @JsonProperty("no")
        Long registerNo,

        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("date")
        LocalDate registrationDate,

        LocalDateTime createdAt
) {
}
