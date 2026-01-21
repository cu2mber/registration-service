package com.cu2mber.registrationservice.registration.controller;

import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationPrepareCommand;
import com.cu2mber.registrationservice.registration.dto.request.RegistrationPrepareRequest;
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

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationPendingResponse> prepareRegistration(@RequestBody @Valid RegistrationPrepareRequest request, @RequestHeader("X-Member-No") Long memberNo) {

        RegistrationPrepareCommand command = new RegistrationPrepareCommand(
                memberNo,
                request.recruitmentNo(),
                request.recruitmentTitle(),
                request.participantCount(),
                request.registrationDate(),
                request.registrationPlace()
        );

        RegistrationPendingResponse response = registrationService.prepareRegistration(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{no}")
    public ResponseEntity<RegistrationResponse> getRegistration(@PathVariable("no") Long no, @RequestHeader("X-Member-No") Long memberNo) {

        RegistrationResponse response = registrationService.getMyRegistration(no, memberNo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<PageResult<RegistrationSummaryResponse>> getRegistrationPage(@RequestHeader("X-Member-No") Long memberNo, @PageableDefault(size = 10)Pageable pageable) {

        PageResult<RegistrationSummaryResponse> page = registrationService.getMyRegistrations(memberNo, pageable);;

        return ResponseEntity.ok(page);
    }

    @GetMapping("/gov")
    public ResponseEntity<PageResult<RegistrationSummaryResponse>> getRegistrationGovPage(@RequestParam Long recruitNo, @RequestHeader("X-Role") String role, @PageableDefault(size = 10)Pageable pageable) {

        PageResult<RegistrationSummaryResponse> page = registrationService.getRecruitRegistrations(recruitNo, role,pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/admin")
    public ResponseEntity<PageResult<RegistrationSummaryResponse>> getRegistrationAdminPage(@RequestHeader("X-Role") String role, @PageableDefault(size = 10)Pageable pageable) {

        PageResult<RegistrationSummaryResponse> page = registrationService.getAllRegistrations(role, pageable);

        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("no")Long no, @RequestHeader("X-Member-No") Long memberNo) {

        RegistrationCancelCommand command = new RegistrationCancelCommand(no, memberNo);
        registrationService.cancelRegistration(command);
        return ResponseEntity.noContent().build();
    }
}
