package com.cu2mber.registrationservice.registration.service.impl;

import com.cu2mber.registrationservice.common.client.RecruitClient;
import com.cu2mber.registrationservice.common.client.RecruitmentSummaryResponse;
import com.cu2mber.registrationservice.registration.dto.PageResult;
import com.cu2mber.registrationservice.registration.domain.PendingRegistration;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCancelCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationCreateCommand;
import com.cu2mber.registrationservice.registration.dto.command.RegistrationPrepareCommand;
import com.cu2mber.registrationservice.registration.dto.response.*;
import com.cu2mber.registrationservice.registration.domain.entity.Registration;
import com.cu2mber.registrationservice.registration.exception.RegistrationErrorCode;
import com.cu2mber.registrationservice.registration.exception.RegistrationException;
import com.cu2mber.registrationservice.registration.repository.RegistrationRepository;
import com.cu2mber.registrationservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RedisTemplate<String, Object> registerTemplate;

    private final RecruitClient recruitClient;


    @Override
    public RegistrationPendingResponse prepareRegistration(RegistrationPrepareCommand command) {

        /** todo 가격 책정 수정
         * 문제: 트래픽 발생 가능성 큼
         * CQRS 적용 - Kafka
         * 모집 글 가격 * 인원
         */
        RecruitmentSummaryResponse recruitInfo = recruitClient.getRecruit(command.recruitmentNo());
        if(!recruitInfo.recruitmentStatus().equals("OPEN")) {
            throw new IllegalArgumentException("신청 할 수 없습니다.");
        }

        BigDecimal amount =  BigDecimal.valueOf(recruitInfo.price()).multiply(BigDecimal.valueOf(command.participantCount()));

        UUID orderId = UUID.randomUUID();

        PendingRegistration pending = new PendingRegistration(
                orderId,
                command.memberNo(),
                command.recruitmentNo(),
                recruitInfo.recruitmentTitle(),
                command.participantCount(),
                command.registrationDate(),
                amount,
                command.registrationPlace()
        );

        String key = redisKey(command.memberNo(), orderId);

        HashOperations<String, String, Object> ops = registerTemplate.opsForHash();
        ops.put(key, "data", pending);
        registerTemplate.expire(key, Duration.ofMinutes(10));

        return new RegistrationPendingResponse(
                pending.orderId(),
                new RecruitInfo(pending.recruitmentNo(), pending.recruitmentTitle()),
                pending.participantCount(),
                amount,
                pending.registrationDate()
        );
    }

    @Override
    public RegistrationCreateResponse createRegistration(RegistrationCreateCommand command) {

        /** todo: redis orderId로 조회
         *
         */
        String key = redisKey(command.memberNo(), command.orderId());
        HashOperations<String, String, Object> ops = registerTemplate.opsForHash();
        PendingRegistration pending = (PendingRegistration) ops.get(key, "data");

        if (pending == null) {
            log.warn("신청 내역을 찾을 수 없습니다, key={}", key);
            return null;
        }

        if (registrationRepository.existsByOrderId(command.orderId())) {
            return null;
        }

        Registration registration = Registration.ofNewRegistration(
                pending.recruitmentNo(),
                pending.memberNo(),
                pending.orderId(),
                pending.recruitmentTitle(),
                pending.participantCount(),
                pending.registrationDate(),
                pending.registrationAmount(),
                pending.registrationPlace()
        );

        registrationRepository.save(registration);
        registerTemplate.delete(key);

        return new RegistrationCreateResponse(
                registration.getRegistrationNo(),
                registration.getRecruitmentTitle(),
                registration.getRegistrationParticipantCount(),
                registration.getRegistrationDate(),
                registration.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationResponse getMyRegistration(Long registrationNo, Long memberNo) {

        Registration registration = registrationRepository.findById(registrationNo)
                .orElseThrow(() -> new RegistrationException(RegistrationErrorCode.NOT_FOUND));

        if(!registration.getMemberNo().equals(memberNo)) {
            throw new RegistrationException(RegistrationErrorCode.FORBIDDEN);
        }

        return RegistrationResponse.from(registration);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationResponse getRegistration(Long registrationNo, String role) {

        if(!role.equals("ROLE_GOV") && !role.equals("ROLE_ADMIN")) {
            throw new RegistrationException(RegistrationErrorCode.FORBIDDEN);
        }

        Registration registration = registrationRepository.findById(registrationNo)
                .orElseThrow(() -> new RegistrationException(RegistrationErrorCode.NOT_FOUND));

        return RegistrationResponse.from(registration);
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
    public PageResult<RegistrationSummaryResponse> getRecruitRegistrations(Long recruitmentNo, String role, Pageable pageable) {

        if(!role.equals("ROLE_GOV")) {
            throw new RegistrationException(RegistrationErrorCode.FORBIDDEN);
        }

        Page<RegistrationSummaryResponse> registrations = registrationRepository.searchRecruitRegistrations(recruitmentNo, pageable);
        List<RegistrationSummaryResponse> registrationList = registrations.getContent();

        return new PageResult<>(registrationList, registrations.getTotalElements(), registrations.getTotalPages());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<RegistrationSummaryResponse> getAllRegistrations(String role, Pageable pageable) {

        if(!role.equals("ROLE_ADMIN")) {
            throw new RegistrationException(RegistrationErrorCode.FORBIDDEN);
        }

        Page<RegistrationSummaryResponse> registrations = registrationRepository.searchAllRegistrations(pageable);
        List<RegistrationSummaryResponse> registrationList = registrations.getContent();

        return new PageResult<>(registrationList, registrations.getTotalElements(), registrations.getTotalPages());
    }

    @Override
    public void cancelRegistration(RegistrationCancelCommand command) {
        Registration registration = registrationRepository.findById(command.registrationNo())
                .orElseThrow(() -> new RegistrationException(RegistrationErrorCode.NOT_FOUND));

        registration.cancel(command.memberNo());
        // todo: 이벤트 발생
    }

    @Override
    public InternalRegistrationSummaryResponse getInternalRegistration(Long registrationNo) {
        return registrationRepository.searchInternalRegistration(registrationNo)
                .orElseThrow(() -> new RegistrationException(RegistrationErrorCode.NOT_FOUND));
    }

    @Override
    public InternalRegistrationSummaryResponse getInternalRegistrationByOrderId(UUID orderId) {
        return registrationRepository.searchInternalRegistrationByOrderId(orderId)
                .orElseThrow(() -> new RegistrationException(RegistrationErrorCode.NOT_FOUND));
    }

    private String redisKey(Long memberNo, UUID orderId) {
        return "registration:pending:"+memberNo+":"+ orderId;
    }
}
