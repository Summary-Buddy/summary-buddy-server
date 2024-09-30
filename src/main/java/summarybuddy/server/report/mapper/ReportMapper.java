package summarybuddy.server.report.mapper;

import summarybuddy.server.report.repository.domain.Report;

public class ReportMapper {
    public static Report from(String content) {
        return Report.builder().content(content).build();
    }
}
