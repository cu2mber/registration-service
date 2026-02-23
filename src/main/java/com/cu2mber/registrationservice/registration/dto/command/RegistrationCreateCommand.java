package com.cu2mber.registrationservice.registration.dto.command;

import java.util.UUID;

public record RegistrationCreateCommand(

        Long memberNo,

        Long recruitmentNo,

        String recruitTitle,

        Integer participantCount,

        String paymentKey,

        String idempotencyKey,

        UUID orderId
) {
}
