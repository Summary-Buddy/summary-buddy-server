package summarybuddy.server.attendees.repository;

import static summarybuddy.server.attendees.repository.domain.QAttendees.attendees;
import static summarybuddy.server.member.repository.domain.QMember.member;
import static summarybuddy.server.report.repository.domain.QReport.report;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.attendees.repository.domain.Attendees;
import summarybuddy.server.report.dto.SimpleReport;

@Repository
@RequiredArgsConstructor
public class AttendeesRepositoryImpl implements AttendeesRepository {
    private final JPAQueryFactory queryFactory;
    private final AttendeesJpaRepository attendeesJpaRepository;

    @Override
    public List<Attendees> findAllByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(attendees)
                .join(attendees.member, member)
                .fetchJoin()
                .join(attendees.report, report)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }

    @Override
    public void saveAll(List<Attendees> attendees) {
        attendeesJpaRepository.saveAll(attendees);
    }

    @Override
    public List<SimpleReport> findSimpleReportsByMemberId(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SimpleReport.class,
                                report.id.as("id"),
                                Expressions.dateTemplate(
                                                String.class,
                                                "DATE_FORMAT({0}, {1})",
                                                report.createdAt,
                                                ConstantImpl.create("%m%d 회의록"))
                                        .as("previewTitle"),
                                report.content.substring(0, 200).as("previewContent")))
                .from(attendees)
                .join(attendees.report, report)
                .join(attendees.member, member)
                .where(attendees.member.id.eq(memberId))
                .fetch();
    }
}
