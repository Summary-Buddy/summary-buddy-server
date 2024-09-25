package summarybuddy.server.member.repository;

import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);

	void save(Member member);
}
