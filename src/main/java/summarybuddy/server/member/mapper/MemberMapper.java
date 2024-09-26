package summarybuddy.server.member.mapper;

import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.member.repository.domain.Role;

public class MemberMapper {
    public static Member from(MemberJoinRequest request, String encodedPassword) {
        return Member.builder()
                .email(request.email())
                .username(request.username())
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}
