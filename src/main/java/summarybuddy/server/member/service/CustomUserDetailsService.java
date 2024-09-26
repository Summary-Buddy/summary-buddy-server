package summarybuddy.server.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import summarybuddy.server.common.exception.NotFoundException;
import summarybuddy.server.common.type.error.MemberErrorType;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.CustomUserDetails;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberJpaRepository;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) {
        // username 으로 사용자를 찾음
        Member member =
                memberJpaRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(MemberErrorType.NOT_FOUND));

        return new CustomUserDetails(member);
    }
}
