package com.cu2mber.registrationservice.registration.controller;

import com.cu2mber.registrationservice.registration.dto.response.InternalRegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/internal/registrations")
@RequiredArgsConstructor
public class InternalRegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/{no}")
    public ResponseEntity<InternalRegistrationSummaryResponse> getRegistration(@PathVariable("no") Long no) {
        InternalRegistrationSummaryResponse response = registrationService.getInternalRegistration(no);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{no}")
    public ResponseEntity<InternalRegistrationSummaryResponse> getRegistration(@PathVariable("no") UUID orderId) {
        InternalRegistrationSummaryResponse response = registrationService.getInternalRegistrationByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
}
