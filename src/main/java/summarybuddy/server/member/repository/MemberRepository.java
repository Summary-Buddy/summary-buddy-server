package summarybuddy.server.member.repository;

import java.util.List;
import java.util.Optional;
import summarybuddy.server.member.repository.domain.Member;

public interface MemberRepository {
    Optional<Member> findByUsername(String username);

    void save(Member member);

    List<Member> findAllByMemberId(List<Long> longs);
}
