package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistrationSummaryResponse(
        @JsonProperty("no")
        Long registerNo,

        Long recruitmentNo,

        Long memberNo,

        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("date")
        LocalDate registrationDate,

        LocalDateTime createdAt
) {
}
