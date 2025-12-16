package com.cu2mber.registrationservice.registration.service.impl;

import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationCreateResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.entity.Registration;
import com.cu2mber.registrationservice.registration.repository.RegistrationRepository;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public RegistrationCreateResponse createRegistration(RegistrationCreateCommand command) {

        Registration registration = Registration.ofNewRegistration(
                command.recruitmentNo(),
                command.memberNo(),
                command.recruitmentTitle(),
                command.participantCount(),
                command.registrationDate()
        );

        registrationRepository.save(registration);

        return new RegistrationCreateResponse(
                registration.getRegistrationNo(),
                registration.getRecruitmentTitle(),
                registration.getRegistrationParticipantCount(),
                registration.getRegistrationDate(),
                registration.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationResponse getRegistration(Long registrationNo) {
        Registration registration = registrationRepository.findById(registrationNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 내역입니다."));
        return new RegistrationResponse(
                registration.getRegistrationNo(),
                registration.getRecruitmentNo(),
                registration.getMemberNo(),
                registration.getRecruitmentTitle(),
                registration.getRegistrationParticipantCount(),
                registration.getRegistrationDate(),
                registration.getCreatedAt(),
                registration.getDeletedAt(),
                registration.getIsCanceled(),
                registration.getIsRefunded());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<RegistrationSummaryResponse> getMyRegistrations(Long memberNo, Pageable pageable) {
        Page<RegistrationSummaryResponse> registrations = registrationRepository.searchMyRegistrations(memberNo, pageable);
        List<RegistrationSummaryResponse> registrationList = registrations.getContent();

        return new PageResult<>(registrationList, registrations.getTotalElements(), registrations.getTotalPages());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<RegistrationSummaryResponse> getRecruitRegistrations(Long recruitmentNo, Pageable pageable) {
        Page<RegistrationSummaryResponse> registrations = registrationRepository.searchRecruitRegistrations(recruitmentNo, pageable);
        List<RegistrationSummaryResponse> registrationList = registrations.getContent();

        return new PageResult<>(registrationList, registrations.getTotalElements(), registrations.getTotalPages());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<RegistrationSummaryResponse> getAllRegistrations(Pageable pageable) {
        Page<RegistrationSummaryResponse> registrations = registrationRepository.searchAllRegistrations(pageable);
        List<RegistrationSummaryResponse> registrationList = registrations.getContent();

        return new PageResult<>(registrationList, registrations.getTotalElements(), registrations.getTotalPages());
    }

    @Override
    public void cancelRegistration(RegistrationCancelCommand command) {
        Registration registration = registrationRepository.findById(command.registrationNo())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 내역입니다."));

        registration.cancel();
    }
}
