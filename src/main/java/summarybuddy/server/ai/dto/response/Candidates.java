package summarybuddy.server.ai.dto.response;

import java.util.List;

public record Candidates(
        Content content, String finishReason, Integer index, List<SafetyRatings> safetyRatings) {}
