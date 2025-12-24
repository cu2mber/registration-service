package com.cu2mber.registrationservice.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "recruitment-service", url = "http://localhost:8086", path = "/internal/recruits")
public interface RecruitClient {

    @GetMapping("/{no}")
    RecruitmentSummaryResponse getRecruit(@PathVariable("no") Long recruitNo);
}
