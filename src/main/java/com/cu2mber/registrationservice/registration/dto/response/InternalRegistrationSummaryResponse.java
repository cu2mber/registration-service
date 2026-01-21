package com.cu2mber.registrationservice.registration.dto.response;

public record InternalRegistrationSummaryResponse(
        Long registerNo,

        Long recruitmentNo,

        Long memberNo,

        Integer participantCount,

        Boolean isCanceled
) {
}
