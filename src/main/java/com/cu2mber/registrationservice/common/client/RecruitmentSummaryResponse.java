package com.cu2mber.registrationservice.common.client;

import java.math.BigDecimal;

public record RecruitmentSummaryResponse(
        Long recruitmentNo,
        String recruitmentStatus,
        Integer localNo,
        Long eventNo,
        String recruitmentTitle,
        BigDecimal price
) {
}
