package com.cu2mber.registrationservice.registration.controller;

import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.request.RegistrationCreateRequest;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @PostMapping
    public ResponseEntity<RegistrationResponse> createRegistration(@RequestBody @Valid RegistrationCreateRequest request) {

        RegistrationCreateCommand command = new RegistrationCreateCommand(
                1L,
                request.recruitmentNo(),
                request.participantCount(),
                request.registrationDate()
        );

        RegistrationResponse response = new RegistrationResponse(
                1L,
                request.recruitmentNo(),
                1L,
                2,
                request.registrationDate(),
                LocalDateTime.now(),
                null,
                false,
                false
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{no}")
    public ResponseEntity<RegistrationResponse> getRegistration(@PathVariable("no") Long no) {
        RegistrationResponse response = new RegistrationResponse(
                1L,
                1L,
                1L,
                2,
                LocalDate.of(2026, 3, 1),
                LocalDateTime.now(),
                null,
                false,
                false
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RegistrationSummaryResponse>> getRegistrationPage(@PageableDefault(size = 10)Pageable pageable) {
        RegistrationSummaryResponse response = new RegistrationSummaryResponse(
                1L,
                1L,
                1L,
                2,
                LocalDate.of(2026, 3, 1),
                LocalDateTime.now()
        );

        return ResponseEntity.ok(new PageImpl<>(List.of(response)));
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("no")Long no) {
        return ResponseEntity.noContent().build();
    }

}
