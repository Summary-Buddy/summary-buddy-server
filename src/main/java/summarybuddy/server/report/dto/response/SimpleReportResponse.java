package summarybuddy.server.report.dto.response;

import summarybuddy.server.report.dto.SimpleReport;

public record SimpleReportResponse(Long id, String previewTitle, String previewContent) {
    public static SimpleReportResponse of(SimpleReport report) {
        return new SimpleReportResponse(
                report.id(), report.previewTitle(), report.previewContent());
    }
}
