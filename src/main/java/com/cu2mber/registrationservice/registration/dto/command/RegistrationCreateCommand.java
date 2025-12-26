package com.cu2mber.registrationservice.registration.dto.command;

import java.time.LocalDate;

public record RegistrationCreateCommand(

        Long memberNo,

        Long recruitmentNo,
        
        String recruitmentTitle,

        Integer participantCount,

        LocalDate registrationDate
) {
}
