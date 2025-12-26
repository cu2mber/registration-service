package com.cu2mber.registrationservice.registration.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingRegistration(
        String orderId,
        Long recruitmentNo,
        Long memberNo,
        String recruitmentTitle,
        Integer participantCount,
        BigDecimal amount,
        LocalDate registrationDate
) {}

