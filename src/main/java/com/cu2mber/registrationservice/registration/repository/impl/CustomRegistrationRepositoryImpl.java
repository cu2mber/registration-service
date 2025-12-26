package com.cu2mber.registrationservice.registration.repository.impl;

import com.cu2mber.registrationservice.registration.dto.response.RegistrationSummaryResponse;
import com.cu2mber.registrationservice.registration.entity.QRegistration;
import com.cu2mber.registrationservice.registration.repository.CustomRegistrationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class CustomRegistrationRepositoryImpl implements CustomRegistrationRepository {

    private final JPAQueryFactory queryFactory;

    QRegistration qRegistration = QRegistration.registration;

    private JPAQuery<RegistrationSummaryResponse> queryResponse(JPAQueryFactory query) {
        return query
                .select(Projections.constructor(RegistrationSummaryResponse.class,
                        qRegistration.registrationNo,
                        qRegistration.recruitmentNo,
                        qRegistration.memberNo,
                        qRegistration.recruitmentTitle,
                        qRegistration.registrationParticipantCount,
                        qRegistration.registrationDate,
                        qRegistration.createdAt
                ))
                .from(qRegistration);
    }

    @Override
    public Page<RegistrationSummaryResponse> searchMyRegistrations(Long memberNo, Pageable pageable) {

        List<RegistrationSummaryResponse> contents = queryResponse(queryFactory)
                .where(qRegistration.memberNo.eq(memberNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> count = queryFactory.select(qRegistration.count())
                .from(qRegistration)
                .where(qRegistration.memberNo.eq(memberNo));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Page<RegistrationSummaryResponse> searchAllRegistrations(Pageable pageable) {

        List<RegistrationSummaryResponse> contents = queryResponse(queryFactory)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> count = queryFactory.select(qRegistration.count())
                .from(qRegistration);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Page<RegistrationSummaryResponse> searchRecruitRegistrations(Long recruitmentNo, Pageable pageable) {

        List<RegistrationSummaryResponse> contents = queryResponse(queryFactory)
            .where(qRegistration.recruitmentNo.eq(recruitmentNo))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        JPAQuery<Long> count = queryFactory.select(qRegistration.count())
                .from(qRegistration)
                .where(qRegistration.recruitmentNo.eq(recruitmentNo));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }
}
