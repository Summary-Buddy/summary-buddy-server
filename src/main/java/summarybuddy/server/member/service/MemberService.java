package summarybuddy.server.member.service;

import jakarta.transaction.Transactional;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.NotFoundException;
import summarybuddy.server.common.type.error.MemberErrorType;
import summarybuddy.server.member.dto.SimpleMember;
import summarybuddy.server.member.dto.request.*;
import summarybuddy.server.member.dto.response.MemberDetailResponse;
import summarybuddy.server.member.dto.response.SimpleMemberResponse;
import summarybuddy.server.member.mapper.MemberMapper;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;
    private final EmailService emailService;

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

        Member member = getMemberById(id);

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

    public List<SimpleMemberResponse> findByUsernameLike(String username) {
        List<SimpleMember> members = memberRepository.findByUsernameLike(username);
        return members.stream().map(SimpleMemberResponse::of).toList();
    }

    @Transactional
    public void updateEmail(MemberEmailUpdateRequest request) {
        Member member = getMemberById(request.id());
        if (!member.getEmail().equals(request.email())) {
            member.updateEmail(request.email());
        }
    }

    @Transactional
    public void updatePassword(MemberPasswordUpdateRequest request) {
        Member member = getMemberById(request.id());
        validationUtil.validatePassword(request.password(), request.passwordConfirm());
        String encodedPassword = passwordEncoder.encode(request.password());
        member.updatePassword(encodedPassword);
    }

    public MemberDetailResponse findById(Long id) {
        Member member = getMemberById(id);
        return new MemberDetailResponse(member.getId(), member.getUsername(), member.getEmail());
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MemberErrorType.NOT_FOUND));
    }

    @Transactional
    public void resetPassword(MemberPasswordFindRequest request){
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundException(MemberErrorType.NOT_FOUND));
        validationUtil.validatePasswordResetRequest(request);

        String tempPassword = PasswordUtil.generateTemporaryPassword();
        member.updatePassword(passwordEncoder.encode(tempPassword));
        emailService.sendTemporaryPasswordEmail(member.getEmail(), tempPassword);
    }
}
