package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.registration.domain.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, Long>, CustomRegistrationRepository {

    Boolean existsByOrderId(UUID orderId);

}
