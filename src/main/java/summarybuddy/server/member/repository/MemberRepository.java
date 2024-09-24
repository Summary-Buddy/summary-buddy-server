package summarybuddy.server.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summarybuddy.server.member.repository.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);
}
