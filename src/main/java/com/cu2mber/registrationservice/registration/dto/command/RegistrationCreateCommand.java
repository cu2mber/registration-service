package com.cu2mber.registrationservice.registration.dto.command;

import java.util.UUID;

public record RegistrationCreateCommand(

        Long memberNo,

        UUID orderId
//
//        Long recruitmentNo,
//
//        String recruitmentTitle,
//
//        Integer participantCount,
//
//        LocalDate registrationDate,
//
//        String registrationPlace
) {
}
