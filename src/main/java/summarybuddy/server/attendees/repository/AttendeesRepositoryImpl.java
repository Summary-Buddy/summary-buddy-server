package summarybuddy.server.attendees.repository;

import static summarybuddy.server.attendees.repository.domain.QAttendees.attendees;
import static summarybuddy.server.member.repository.domain.QMember.member;
import static summarybuddy.server.report.repository.domain.QReport.report;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.attendees.repository.domain.Attendees;

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
}
