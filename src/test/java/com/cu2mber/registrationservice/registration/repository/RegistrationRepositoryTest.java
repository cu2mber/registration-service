package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.common.config.QuerydslConfig;
import com.cu2mber.registrationservice.registration.dto.response.InternalRegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.domain.entity.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@Import(QuerydslConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class RegistrationRepositoryTest {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    TestEntityManager entityManager;

    Registration registration1;
    @BeforeEach
    void setUp() {
        registration1 = Registration.ofNewRegistration(1L, 1L, UUID.randomUUID(), "김해 금관가야 축제 모집", 20, LocalDate.now(), BigDecimal.valueOf(5000L), "남해 군청");
        Registration registration2 = Registration.ofNewRegistration(1L, 2L, UUID.randomUUID(), "김해 금관가야 축제 모집", 20, LocalDate.now(), BigDecimal.valueOf(1000L), "부산 남구청");
        Registration registration3 = Registration.ofNewRegistration(2L, 1L, UUID.randomUUID(), "부산 불꽃 축제 모집", 20, LocalDate.now(), BigDecimal.valueOf(5500L), "남해 군청");
        entityManager.persistAndFlush(registration1);
        entityManager.persistAndFlush(registration2);
        entityManager.persistAndFlush(registration3);
        entityManager.clear();
    }

    @Test
    @DisplayName("신청 내역 조회 - 본인")
    void searchMyRegistrations() {
        Page<RegistrationSummaryResponse> results = registrationRepository.searchMyRegistrations(1L, Pageable.ofSize(10));

        assertFalse(results.getContent().isEmpty());
        assertEquals(results.getTotalElements(), 2);

        var first = results.getContent().get(0);
        var second = results.getContent().get(1);

        assertAll(
                () -> assertEquals("김해 금관가야 축제 모집", first.recruit().recruitmentTitle()),
                () -> assertEquals("남해 군청", first.registrationPlace()),
                () -> assertEquals("부산 불꽃 축제 모집", second.recruit().recruitmentTitle()),
                () -> assertEquals("남해 군청", second.registrationPlace())
        );
    }

    @Test
    @DisplayName("신청 내역 조회 - all")
    void searchAllRegistrations() {
        Page<RegistrationSummaryResponse> results = registrationRepository.searchAllRegistrations(Pageable.ofSize(10));

        assertFalse(results.getContent().isEmpty());
        assertEquals(results.getTotalElements(), 3);

        var first = results.getContent().get(0);
        var second = results.getContent().get(1);
        var third = results.getContent().get(2);

        assertAll(
                () -> assertEquals(1L, first.recruit().recruitmentNo()),
                () -> assertEquals("김해 금관가야 축제 모집", first.recruit().recruitmentTitle()),
                () -> assertEquals("남해 군청", first.registrationPlace()),
                () -> assertEquals(1L,second.recruit().recruitmentNo()),
                () -> assertEquals("김해 금관가야 축제 모집", second.recruit().recruitmentTitle()),
                () -> assertEquals("부산 남구청", second.registrationPlace()),
                () -> assertEquals(2L, third.recruit().recruitmentNo()),
                () -> assertEquals("부산 불꽃 축제 모집", third.recruit().recruitmentTitle()),
                () -> assertEquals("남해 군청", third.registrationPlace())
        );
    }

    @Test
    @DisplayName("신청 내역 조회 - 지자체")
    void searchRecruitRegistrations() {
        Page<RegistrationSummaryResponse> results = registrationRepository.searchRecruitRegistrations(1L, Pageable.ofSize(10));

        assertFalse(results.getContent().isEmpty());
        assertEquals(results.getTotalElements(), 2);

        var first = results.getContent().get(0);
        var second = results.getContent().get(1);

        assertAll(
                () -> assertEquals(1L, first.recruit().recruitmentNo()),
                () -> assertEquals("김해 금관가야 축제 모집", first.recruit().recruitmentTitle()),
                () -> assertEquals("남해 군청", first.registrationPlace()),
                () -> assertEquals(1L,second.recruit().recruitmentNo()),
                () -> assertEquals("김해 금관가야 축제 모집", second.recruit().recruitmentTitle()),
                () -> assertEquals("부산 남구청", second.registrationPlace())
        );
    }

    @Test
    @DisplayName("내부 API - 신청 내역 조회(id)")
    void searchInternalRegistration() {
        Optional<InternalRegistrationSummaryResponse> result = registrationRepository.searchInternalRegistration(registration1.getRegistrationNo());

        assertTrue(result.isPresent());

        assertAll(
                () -> assertEquals(1L, result.get().recruitmentNo()),
                () -> assertEquals(1L, result.get().memberNo()),
                () -> assertEquals(20, result.get().participantCount()),
                () -> assertEquals(false, result.get().isCanceled())
        );
    }

    @Test
    @DisplayName("내부 API - 신청 내역 조회(orderId)")
    void searchInternalRegistrationByOrderId() {
        Optional<InternalRegistrationSummaryResponse> result = registrationRepository.searchInternalRegistrationByOrderId(registration1.getOrderId());

        assertTrue(result.isPresent());

        assertAll(
                () -> assertEquals(1L, result.get().recruitmentNo()),
                () -> assertEquals(1L, result.get().memberNo()),
                () -> assertEquals(20, result.get().participantCount()),
                () -> assertEquals(false, result.get().isCanceled())
        );
    }
}