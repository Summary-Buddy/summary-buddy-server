package summarybuddy.server.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
}
