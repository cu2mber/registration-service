package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRegistrationRepository {

    Page<RegistrationSummaryResponse> searchMyRegistrations(Long memberNo, Pageable pageable);

    Page<RegistrationSummaryResponse> searchAllRegistrations(Pageable pageable);

    Page<RegistrationSummaryResponse> searchRecruitRegistrations(Long recruitmentNo, Pageable pageable);

}
