package com.cu2mber.registrationservice.registration.service;

import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationCreateResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegistrationService {

    RegistrationCreateResponse createRegistration(RegistrationCreateCommand command);

    RegistrationResponse getRegistration(Long registrationNo);

    Page<RegistrationSummaryResponse> getMyRegistrations(Long memberNo, Pageable pageable);

    Page<RegistrationSummaryResponse> getAllRegistrations(Pageable pageable);

    void cancelRegistration(RegistrationCancelCommand command);
}
