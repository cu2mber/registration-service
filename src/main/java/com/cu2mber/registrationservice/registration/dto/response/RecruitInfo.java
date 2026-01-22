package com.cu2mber.registrationservice.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 모집(Recruitment)에 대한 기본 정보를 표현하는 DTO입니다.
 * <p>
 * 신청(Registration) 상세 응답 등에서
 * 중첩 객체 형태로 사용되며,
 * 모집의 식별 정보와 제목만을 포함합니다.
 *
 * @param recruitmentNo     모집(recruitment) 식별 번호
 * @param recruitmentTitle  모집 제목
 */
public record RecruitInfo(
        @JsonProperty("no")
        Long recruitmentNo,

        @JsonProperty("title")
        String recruitmentTitle
) {
}
