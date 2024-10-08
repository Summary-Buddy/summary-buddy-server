package summarybuddy.server.member.repository;

import static summarybuddy.server.member.repository.domain.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.member.dto.SimpleMember;
import summarybuddy.server.member.repository.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JPAQueryFactory queryFactory;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public List<Member> findAllByMemberIds(List<Long> memberIds) {
        return queryFactory.selectFrom(member).where(member.id.in(memberIds)).fetch();
    }

    @Override
    public List<SimpleMember> findByUsernameLike(String username) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SimpleMember.class,
                                member.id.as("id"),
                                member.username.as("username")))
                .from(member)
                .where(member.username.contains(username))
                .fetch();
    }

    @Override
    public void deleteById(Long id) {
        memberJpaRepository.deleteById(id);
    }
}
