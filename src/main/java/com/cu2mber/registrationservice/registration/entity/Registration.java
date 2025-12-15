package com.cu2mber.registrationservice.registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_registration")
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

    @Column(name = "recruit_title", nullable = true)
    String recruitmentTitle;

    @Column(name = "participant_count", columnDefinition = "smallint", nullable = false)
    Integer registrationParticipantCount;

    @Column(name = "registration_date", nullable = false)
    LocalDate registrationDate;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "deleted_at", nullable = true)
    LocalDateTime deletedAt;

    @Column(name = "is_canceled", nullable = false)
    Boolean isCanceled;

    @Column(name = "is_refunded", nullable = false)
    Boolean isRefunded;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    private Registration(Long recruitmentNo, Long memberNo, String recruitmentTitle, Integer participantCount, LocalDate registrationDate, Boolean isCanceled, Boolean isRefunded) {
        this.recruitmentNo = recruitmentNo;
        this.memberNo = memberNo;
        this.recruitmentTitle = recruitmentTitle;
        this.registrationParticipantCount = participantCount;
        this.registrationDate = registrationDate;
        this.isCanceled = isCanceled;
        this.isRefunded = isRefunded;
    }

    public static Registration ofNewRegistration(Long recruitmentNo, Long memberNo, String recruitmentTitle, Integer participantCount, LocalDate registrationDate) {
        return new Registration(recruitmentNo, memberNo, recruitmentTitle, participantCount,registrationDate, false, false);
    }

    public void cancel() {
        this.isCanceled = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void refund() {
        this.isRefunded = true;
    }

}
