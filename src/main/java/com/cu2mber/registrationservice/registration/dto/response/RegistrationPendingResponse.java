package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RegistrationPendingResponse(
        String orderId,

        Long recruitNo,

        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("date")
        LocalDate registrationDate
) {
}
