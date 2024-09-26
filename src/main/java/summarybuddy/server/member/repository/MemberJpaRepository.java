package summarybuddy.server.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import summarybuddy.server.member.repository.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
