package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RegistrationResponse {

    @JsonProperty("no")
    Long registerNo;

    Long recruitmentNo;

    Long memberNo;

//    @Setter
//    @JsonProperty("recruit")
//    Long recruitmentName;

    @JsonProperty("participant")
    Integer participantCount;

    @JsonProperty("date")
    LocalDate registrationDate;

    @EqualsAndHashCode.Exclude
    LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    LocalDateTime deletedAt;

    Boolean isCanceled;

    Boolean isRefunded;
}
