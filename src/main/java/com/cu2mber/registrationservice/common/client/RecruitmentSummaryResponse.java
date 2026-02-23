package com.cu2mber.registrationservice.common.client;

import java.math.BigDecimal;

public record RecruitmentSummaryResponse(
        Long recruitmentNo,
        String recruitmentStatus,
        Long memberLocalNo,
        Long eventNo,
        String recruitmentTitle,
        Long price
) {
}
