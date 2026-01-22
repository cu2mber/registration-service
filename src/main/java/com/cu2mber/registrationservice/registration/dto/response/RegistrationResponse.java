package com.cu2mber.registrationservice.registration.dto.response;

import com.cu2mber.registrationservice.registration.entity.Registration;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
