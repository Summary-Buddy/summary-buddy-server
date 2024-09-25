package summarybuddy.server.member.repository;

import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findByUsername(String username);

	void save(Member member);
}
