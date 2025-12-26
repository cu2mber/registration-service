package com.cu2mber.registrationservice.registration.controller;

import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.request.RegistrationCreateRequest;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationPendingResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationPendingResponse> prepareRegistration(@RequestBody @Valid RegistrationCreateRequest request) {

        RegistrationCreateCommand command = new RegistrationCreateCommand(
                1L,
                request.recruitmentNo(),
                request.recruitmentTitle(),
                request.participantCount(),
                request.registrationDate()
        );

        RegistrationPendingResponse response = registrationService.prepareRegistration(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{no}")
    public ResponseEntity<RegistrationResponse> getRegistration(@PathVariable("no") Long no) {
        RegistrationResponse response = new RegistrationResponse(
                1L,
                1L,
                1L,
                "모집",
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
    public ResponseEntity<PageResult<RegistrationSummaryResponse>> getRegistrationPage(@RequestParam(required = false) Long recruitNo, @PageableDefault(size = 10)Pageable pageable) {

        PageResult<RegistrationSummaryResponse> page = registrationService.getAllRegistrations(pageable);
        if(recruitNo != null) {
            page = registrationService.getRecruitRegistrations(recruitNo, pageable);
        }

        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("no")Long no) {
        return ResponseEntity.noContent().build();
    }

}
