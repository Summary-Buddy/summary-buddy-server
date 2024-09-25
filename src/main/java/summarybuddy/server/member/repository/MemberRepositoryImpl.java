package summarybuddy.server.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public Optional<Member> findByUsername(String username) {
		return memberJpaRepository.findByUsername(username);
	}

	@Override
	public Optional<Member> findByEmail(String email) { return memberJpaRepository.findByEmail(email); }

	@Override
	public Optional<Member> findById(Long id) { return memberJpaRepository.findById(id); }

	@Override
	public void save(Member member) {
		memberJpaRepository.save(member);
	}
}
