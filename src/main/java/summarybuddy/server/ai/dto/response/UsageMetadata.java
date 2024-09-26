package summarybuddy.server.ai.dto.response;

public record UsageMetadata(
        Integer promptTokenCount, Integer candidatesTokenCount, Integer totalTokenCount) {}
