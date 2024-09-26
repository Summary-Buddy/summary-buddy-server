package summarybuddy.server.ai.dto.response;

import java.util.List;

public record ContentResponse(List<Candidates> candidates, UsageMetadata usageMetadata) {}
