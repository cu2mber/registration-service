package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

