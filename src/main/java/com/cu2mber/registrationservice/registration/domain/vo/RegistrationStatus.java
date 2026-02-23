package com.cu2mber.registrationservice.registration.domain.vo;

public enum RegistrationStatus {
    PENDING,    // 신청 생성, 결제 대기
    CONFIRMED,  // 결제 완료 → 유효한 신청
    CANCELED,   // 취소/환불 완료
    FAILED,     // 결제 실패
    COMPLETED   // 행사 종료 후 확정
}
