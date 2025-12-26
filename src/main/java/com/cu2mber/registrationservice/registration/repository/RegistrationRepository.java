package com.cu2mber.registrationservice.registration.repository;

import com.cu2mber.registrationservice.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long>, CustomRegistrationRepository {

}
