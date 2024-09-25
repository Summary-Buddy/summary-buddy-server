package summarybuddy.server.member.mapper;

import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.repository.domain.Member;

public class MemberMapper {
	public static Member from(MemberJoinRequest request, String encodedPassword) {
		return Member.builder()
				.email(request.getEmail())
				.username(request.getUsername())
				.password(encodedPassword)
				.build();
	}
}
