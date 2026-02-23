package com.cu2mber.registrationservice.registration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RegistrationPrepareRequest(

        @JsonProperty("recruitNo")
        Long recruitmentNo,

        @JsonProperty("participant")
        Integer participantCount,

        // todo: 경유지 번호 or 이름 선택
        @JsonProperty("place")
        String registrationPlace,

        @JsonProperty("departDate")
        LocalDate registrationDate
) {
}
