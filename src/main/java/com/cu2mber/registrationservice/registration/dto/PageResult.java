package com.cu2mber.registrationservice.registration.dto;

import java.util.List;

/**
 * 페이징 처리된 결과를 클라이언트에 반환하기 위한 공통 응답 객체입니다.
 * <p>
 * 조회된 데이터 목록과 전체 데이터 개수, 전체 페이지 수를 포함하며,
 * 다양한 도메인의 페이징 응답에서 재사용할 수 있도록 제네릭 타입으로 정의되어 있습니다.
 *
 * @param contents      현재 페이지에 포함된 데이터 목록
 * @param totalContent  전체 데이터 개수
 * @param totalPage     전체 페이지 수
 * @param <T>           페이지 내 요소 타입
 */
public record PageResult<T>(
        List<T> contents,

        long totalContent,

        int totalPage
) {
}
