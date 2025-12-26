package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
