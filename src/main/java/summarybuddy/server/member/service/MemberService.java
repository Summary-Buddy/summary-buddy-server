package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
import summarybuddy.server.member.mapper.MemberMapper;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void save(MemberJoinRequest request) {
		if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Username already exists");
		} else if (!request.emailValidation()) {
			throw new RuntimeException("Email is not valid");
		} else if (!request.checkPassword()) {
			throw new RuntimeException("Passwords do not match");
		} else {
			Member member = MemberMapper.from(request, passwordEncoder.encode(request.getPassword()));
			memberRepository.save(member);
		}
	}

	public boolean usernameExists(String username) {
		return memberRepository.findByUsername(username).isPresent();
	}

	public void updateMember(Long memberId, MemberUpdateRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Member not found"));

		// 비밀번호 변경
		if (request.getNewPassword() != null) {
			if (!request.isPasswordConfirmed()) {
				throw new RuntimeException("Passwords do not match");
			}
			String encodedPassword = passwordEncoder.encode(request.getNewPassword());
			member.updatePassword(encodedPassword);
		}

		// 이메일 변경
		if (request.getNewEmail() != null && !member.getEmail().equals(request.getNewEmail())) {
			if (!request.isEmailValid()) {
				throw new RuntimeException("Email is not valid");
			}
			// 이메일 중복 체크
			if (memberRepository.findByEmail(request.getNewEmail()).isPresent()) {
				throw new RuntimeException("Email already exists");
			}
			member.updateEmail(request.getNewEmail());
		}

		memberRepository.save(member);

		// 업데이트 성공 로그
		System.out.println("Member updated: " + member.getEmail());
	}
}
