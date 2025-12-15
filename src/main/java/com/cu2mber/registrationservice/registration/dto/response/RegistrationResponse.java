package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistrationResponse(
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

        @EqualsAndHashCode.Exclude
        LocalDateTime createdAt,

        @EqualsAndHashCode.Exclude
        LocalDateTime deletedAt,

        Boolean isCanceled,

        Boolean isRefunded
) {


}
