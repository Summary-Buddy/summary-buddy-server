package summarybuddy.server.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.NotFoundException;
import summarybuddy.server.common.type.error.MemberErrorType;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.mapper.MemberMapper;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;

    @Transactional
    public void save(MemberJoinRequest request) {
        // 사용자 이름 검증
        validationUtil.validateUsername(request.username());

        // 비밀번호 검증
        validationUtil.validatePassword(request.password(), request.passwordConfirm());

        Member member = MemberMapper.from(request, passwordEncoder.encode(request.password()));
        memberRepository.save(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequest request) {
        validationUtil.validateUpdateRequest(request);

        Member member =
                memberRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(MemberErrorType.NOT_FOUND));

        // 비밀번호 변경
        if (request.newPassword() != null) {
            validationUtil.validatePassword(request.newPassword(), request.newPasswordConfirm());
            String encodedPassword = passwordEncoder.encode(request.newPassword());
            member.updatePassword(encodedPassword);
        }

        // 이메일 변경
        if (request.newEmail() != null && !member.getEmail().equals(request.newEmail())) {
            member.updateEmail(request.newEmail());
        }
    }

    public void checkUsername(String username) {
        validationUtil.validateUsername(username);
    }
}
