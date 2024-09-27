package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.BadRequestException;
import summarybuddy.server.common.exception.UsernameAlreadyExistsException;
import summarybuddy.server.common.type.error.MemberErrorType;
import summarybuddy.server.member.dto.request.MemberPasswordFindRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;

import java.util.Optional;
import java.util.prefs.BackingStoreException;

@Service
@RequiredArgsConstructor
public class ValidationUtil {
    private final MemberRepository memberRepository;

    public void validateUsername(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(MemberErrorType.USERNAME_ALREADY_EXIST);
        }
    }

    public void validateEmail(String email) {
        // 이메일 형식 검증 로직
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new BadRequestException(MemberErrorType.INVALID_EMAIL);
        }
        // 이메일 중복 체크 로직
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException(MemberErrorType.EMAIL_ALREADY_EXIST);
        }
    }

    public void validatePassword(String password, String confirmedPassword) {
        if (password == null || password.isEmpty()) {
            throw new BadRequestException(MemberErrorType.PASSWORD_CANNOT_BE_EMPTY);
        }
        if (!password.equals(confirmedPassword)) {
            throw new BadRequestException(MemberErrorType.PASSWORD_NOT_MATCH);
        }
    }

    public void validateUpdateRequest(MemberUpdateRequest request) {
        // 이메일 또는 비밀번호가 제공되어야 함
        if (request.newEmail() == null && request.newPassword() == null) {
            throw new BadRequestException(MemberErrorType.EMAIL_OR_PASSWORD_MUST_BE_PROVIDED);
        }
        if (request.newEmail() != null) {
            validateEmail(request.newEmail());
        }
        if (request.newPassword() != null) {
            validatePassword(request.newPassword(), request.newPasswordConfirm());
        }
    }

    public void validatePasswordResetRequest(MemberPasswordFindRequest request) {
        if (request.username() == null || request.email() == null) {
            throw new BadRequestException(MemberErrorType.USERNAME_AND_EMAIL_MUST_BE_PROVIDED);
        }

        if (memberRepository.findByUsername(request.username()).isEmpty()) {
            throw new BadRequestException(MemberErrorType.NOT_FOUND);
        }

        Optional<Member> memberByUsername = memberRepository.findByUsername(request.username());
        Optional<Member> memberByEmail = memberRepository.findByEmail(request.email());

        if (memberByUsername.isEmpty() || memberByEmail.isEmpty() || !memberByUsername.get().getId().equals(memberByEmail.get().getId())) {
            throw new BadRequestException(MemberErrorType.NOT_FOUND);
        }
    }
}
