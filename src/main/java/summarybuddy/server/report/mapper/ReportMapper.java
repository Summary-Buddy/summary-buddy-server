package summarybuddy.server.report.mapper;

import summarybuddy.server.report.repository.domain.Report;

public class ReportMapper {
    public static Report from(String content, String fileUrl) {
        return Report.builder().content(content).fileDirectory(fileUrl).build();
    }
}
