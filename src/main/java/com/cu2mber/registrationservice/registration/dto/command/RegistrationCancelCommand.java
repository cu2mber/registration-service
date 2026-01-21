package com.cu2mber.registrationservice.registration.dto.command;

public record RegistrationCancelCommand(

        Long registrationNo,

        Long memberNo
) {
}
