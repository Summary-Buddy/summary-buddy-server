package summarybuddy.server.attendees.dto;

import java.util.List;

public record AttendeesAndReportIds(List<Long> attendeesIds, List<Long> reportIds) {}
