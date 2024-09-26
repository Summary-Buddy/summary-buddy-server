package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.CustomUserDetails;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // username 으로 사용자를 찾음
        Member member =
                memberJpaRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "Member not found with username: " + username));

        return new CustomUserDetails(member);
    }
}
