package com.cu2mber.registrationservice.registration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RegistrationPrepareRequest(

        @JsonProperty("recruitNo")
        Long recruitmentNo,
        
        @JsonProperty("title")
        String recruitmentTitle,

        @JsonProperty("participant")
        Integer participantCount,

        @JsonProperty("place")
        String registrationPlace,

        @JsonProperty("departDate")
        LocalDate registrationDate
) {
}
