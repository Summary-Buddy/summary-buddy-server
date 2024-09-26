package summarybuddy.server.ai.dto.response;

import java.util.List;

public record Content(List<Parts> parts, String role) {}
