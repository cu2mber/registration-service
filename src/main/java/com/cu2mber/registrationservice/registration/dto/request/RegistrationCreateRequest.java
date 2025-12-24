package com.cu2mber.registrationservice.registration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RegistrationCreateRequest(

        @JsonProperty("recruitNo")
        Long recruitmentNo,
        
        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("departDate")
        LocalDate registrationDate
) {
}
