package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecruitInfo(
        @JsonProperty("no")
        Long recruitmentNo,

        @JsonProperty("title")
        String recruitmentTitle
) {
}
