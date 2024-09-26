package summarybuddy.server.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import summarybuddy.server.member.dto.SimpleMember;

public record SimpleMemberResponse(
        @NotNull @Schema(description = "멤버 ID", example = "1") Long id,
        @NotNull @Schema(description = "멤버 이름", example = "summary_buddy123") String username) {
    public static SimpleMemberResponse of(SimpleMember member) {
        return new SimpleMemberResponse(member.id(), member.username());
    }
}
