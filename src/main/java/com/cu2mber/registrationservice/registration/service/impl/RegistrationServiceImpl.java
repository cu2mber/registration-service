package com.cu2mber.registrationservice.registration.service.impl;

import com.cu2mber.registrationservice.common.client.RecruitClient;
import com.cu2mber.registrationservice.common.client.RecruitmentSummaryResponse;
import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.dto.PendingRegistration;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationCreateResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationPendingResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.entity.Registration;
import com.cu2mber.registrationservice.registration.repository.RegistrationRepository;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RedisTemplate<String, Object> registerTemplate;

    private final RecruitClient recruitClient;

    @Override
    public RegistrationPendingResponse prepareRegistration(RegistrationCreateCommand command) {

        /** todo 가격 책정 수정
         * CQRS 적용 - Kafka
         * 모집 글 가격 * 인원
         */
        RecruitmentSummaryResponse recruitInfo = recruitClient.getRecruit(command.recruitmentNo());
        BigDecimal amount = recruitInfo.price().multiply(BigDecimal.valueOf(command.participantCount()));

        String orderId = UUID.randomUUID().toString();

        PendingRegistration pending = new PendingRegistration(
                orderId,
                command.memberNo(),
                command.recruitmentNo(),
                command.recruitmentTitle(),
                command.participantCount(),
                amount,
                command.registrationDate()
        );

        String key = redisKey(command.memberNo(), orderId);

        HashOperations<String, String, Object> ops = registerTemplate.opsForHash();
        ops.put(key, "data", pending);
        registerTemplate.expire(key, Duration.ofMinutes(10));

        return new RegistrationPendingResponse(pending.orderId(), pending.recruitmentNo(), pending.recruitmentTitle(), pending.participantCount(), pending.registrationDate());
    }

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

        // todo: 결제 완료 이벤트

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

    private String redisKey(Long memberNo,String orderId) {
        return "registration:pending:"+memberNo+":"+ orderId;
    }
}
