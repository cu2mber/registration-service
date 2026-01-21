package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.registration.dto.response.InternalRegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomRegistrationRepository {

    Page<RegistrationSummaryResponse> searchMyRegistrations(Long memberNo, Pageable pageable);

    Page<RegistrationSummaryResponse> searchAllRegistrations(Pageable pageable);

    Page<RegistrationSummaryResponse> searchRecruitRegistrations(Long recruitmentNo, Pageable pageable);

    Optional<InternalRegistrationSummaryResponse> findInternalRegistration(Long registrationNo);

    Optional<InternalRegistrationSummaryResponse> findInternalRegistrationByOrderId(UUID orderId);

}
