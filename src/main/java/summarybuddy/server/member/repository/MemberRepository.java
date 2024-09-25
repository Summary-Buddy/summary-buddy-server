package summarybuddy.server.member.repository;

import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
	Optional<Member> findById(Long id);

	void save(Member member);
}
