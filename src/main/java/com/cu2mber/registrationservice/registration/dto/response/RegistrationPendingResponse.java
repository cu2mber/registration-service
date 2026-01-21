package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegistrationPendingResponse(
        UUID orderId,

        Long recruitNo,

        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("amount")
        BigDecimal registrationAmount,

        @JsonProperty("departDate")
        LocalDate registrationDate
) {
}
