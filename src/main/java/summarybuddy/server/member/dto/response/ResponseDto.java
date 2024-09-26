package summarybuddy.server.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private boolean success;
    private String message;
}
