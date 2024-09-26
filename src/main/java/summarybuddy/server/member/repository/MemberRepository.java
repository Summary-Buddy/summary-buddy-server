package summarybuddy.server.member.repository;

import java.util.List;
import java.util.Optional;
import summarybuddy.server.member.repository.domain.Member;

public interface MemberRepository {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    void save(Member member);

    List<Member> findAllByMemberIds(List<Long> memberIds);

    Optional<Member> findById(Long id);
}
