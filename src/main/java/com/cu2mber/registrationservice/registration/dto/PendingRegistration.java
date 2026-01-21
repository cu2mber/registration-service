package com.cu2mber.registrationservice.registration.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PendingRegistration(
        UUID orderId,

        Long recruitmentNo,

        Long memberNo,

        String recruitmentTitle,

        Integer participantCount,

        LocalDate registrationDate,

        BigDecimal registrationAmount,

        String registrationPlace
) {}

