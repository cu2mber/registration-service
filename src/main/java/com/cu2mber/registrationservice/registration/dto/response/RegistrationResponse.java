package com.cu2mber.registrationservice.registration.dto.response;

import com.cu2mber.registrationservice.registration.domain.entity.Registration;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 신청(Registration) 상세 정보를 클라이언트에 반환하기 위한 응답 DTO입니다.
 * <p>
 * 신청 단건 조회 API에서 사용되며,
 * 모집 정보, 인원 수, 금액, 상태 정보 등
 * 신청과 관련된 전체 상세 데이터를 포함합니다.
 *
 * @param registerNo           신청 식별 번호
 * @param memberNo             신청한 회원 식별 번호
 * @param recruit              모집 정보
 * @param participantCount     신청 인원 수
 * @param registrationDate     신청(참여) 날짜
 * @param registrationPlace    출발지(경유지 이름)
 * @param registrationAmount   신청 금액
 * @param createdAt            신청 생성 일시
 * @param deletedAt            신청 삭제(취소) 일시
 * @param isCanceled            신청 취소 여부
 * @param isRefunded            환불 완료 여부
 */
public record RegistrationResponse(
        @JsonProperty("no")
        Long registerNo,

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

        LocalDateTime createdAt,

        LocalDateTime deletedAt,

        Boolean isCanceled,

        Boolean isRefunded
) {
        public static RegistrationResponse from(Registration registration) {
                return new RegistrationResponse(
                        registration.getRegistrationNo(),
                        registration.getMemberNo(),
                        new RecruitInfo(
                                registration.getRecruitmentNo(),
                                registration.getRecruitmentTitle()
                        ),
                        registration.getRegistrationParticipantCount(),
                        registration.getRegistrationDate(),
                        registration.getRegistrationPlace(),
                        registration.getRegistrationAmount(),
                        registration.getCreatedAt(),
                        registration.getDeletedAt(),
                        registration.getIsCanceled(),
                        registration.getIsRefunded()
                );
        }
}
