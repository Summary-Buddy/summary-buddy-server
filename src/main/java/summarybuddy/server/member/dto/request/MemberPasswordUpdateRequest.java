package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberPasswordUpdateRequest(
        @NotNull @Schema(description = "수정할 회원 ID", example = "1") Long id,
        @NotNull @Schema(description = "수정할 비밀번호", example = "p@ssword!!") String password,
        @NotNull @Schema(description = "수정할 비밀번호 확인", example = "p@ssword!!")
                String passwordConfirm) {}
