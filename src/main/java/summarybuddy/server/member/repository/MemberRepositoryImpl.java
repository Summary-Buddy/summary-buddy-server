package summarybuddy.server.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.member.repository.domain.QMember;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public List<Member> findAllByMemberId(List<Long> memberIds) {
        return queryFactory.selectFrom(member).where(member.id.in(memberIds)).fetch();
    }
}
