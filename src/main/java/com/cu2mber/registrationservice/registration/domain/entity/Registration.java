package com.cu2mber.registrationservice.registration.domain.entity;

import com.cu2mber.registrationservice.registration.domain.vo.RegistrationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "event_registrations")
@Getter
@ToString
@NoArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_no", nullable = false)
    Long registrationNo;

    @Column(name = "recruitment_no", nullable = false)
    Long recruitmentNo;

    @Column(name = "member_no", nullable = false)
    Long memberNo;

    @Column(name = "order_id", nullable = false, unique = true)
    UUID orderId;

    @Column(name = "recruit_title", nullable = true)
    String recruitmentTitle;

    @Column(name = "participant_count", columnDefinition = "smallint", nullable = false)
    Integer registrationParticipantCount;

    @Column(name = "registration_date", nullable = false)
    LocalDate registrationDate;

    @Column(name = "registration_amount", nullable = false)
    BigDecimal registrationAmount;

    @Column(name = "registration_place", nullable = false)
    String registrationPlace;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "deleted_at", nullable = true)
    LocalDateTime deletedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    RegistrationStatus registrationStatus;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    private Registration(Long recruitmentNo, Long memberNo, UUID orderId, String recruitmentTitle, Integer participantCount, LocalDate registrationDate, BigDecimal registrationAmount, String registrationPlace, RegistrationStatus registrationStatus) {
        this.recruitmentNo = recruitmentNo;
        this.memberNo = memberNo;
        this.orderId = orderId;
        this.recruitmentTitle = recruitmentTitle;
        this.registrationParticipantCount = participantCount;
        this.registrationDate = registrationDate;
        this.registrationAmount = registrationAmount;
        this.registrationPlace = registrationPlace;
        this.registrationStatus = registrationStatus;
    }

    public static Registration ofNewRegistration(Long recruitmentNo, Long memberNo, UUID orderId, String recruitmentTitle, Integer participantCount, LocalDate registrationDate, BigDecimal registrationAmount, String registrationPlace) {
        return new Registration(recruitmentNo, memberNo, orderId, recruitmentTitle, participantCount,registrationDate, registrationAmount, registrationPlace, RegistrationStatus.COMPLETED);
    }

    public void updateStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public void cancel(Long memberNo) {

        if(!Objects.equals(this.memberNo, memberNo)) {
            throw new IllegalArgumentException("신청자가 아닙니다.");
        }

        if(RegistrationStatus.CANCELED.equals(this.registrationStatus)) {
            throw new IllegalArgumentException("이미 취소된 건 입니다.");
        }

        this.registrationStatus = RegistrationStatus.CANCELED;
        this.deletedAt = LocalDateTime.now();
    }

}
