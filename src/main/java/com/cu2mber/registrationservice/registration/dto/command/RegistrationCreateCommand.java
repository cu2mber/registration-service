package com.cu2mber.registrationservice.registration.dto.command;

import java.time.LocalDate;

public record RegistrationCreateCommand(

        Long memberNo,

        Long recruitmentNo,

        Integer participantCount,

        LocalDate registrationDate
) {
}
