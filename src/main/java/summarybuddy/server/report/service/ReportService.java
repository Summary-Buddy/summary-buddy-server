package summarybuddy.server.report.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import java.io.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import summarybuddy.server.ai.GoogleClient;
import summarybuddy.server.common.exception.InternalServerException;
import summarybuddy.server.common.exception.NotFoundException;
import summarybuddy.server.common.type.error.CommonErrorType;
import summarybuddy.server.common.type.error.ReportErrorType;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.dto.request.ReportPdfCreateRequest;
import summarybuddy.server.report.dto.response.ReportPdfCreateResponse;
import summarybuddy.server.report.dto.response.ReportResponse;
import summarybuddy.server.report.mapper.ReportMapper;
import summarybuddy.server.report.repository.ReportRepository;
import summarybuddy.server.report.repository.domain.Report;
import summarybuddy.server.storage.FFmpegClient;
import summarybuddy.server.storage.GcsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final GcsClient gcsClient;
    private final FFmpegClient ffmpegClient;
    private final GoogleClient googleClient;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public Report save(Long memberId, MultipartFile file, ReportCreateRequest request) {
        try {
            InputStream input = ffmpegClient.convertInputStreamToMp3(file.getInputStream());
            String audioUrl = gcsClient.createAudioUrl(input);
            request.memberIdList().addFirst(memberId);
            List<Member> members = memberRepository.findAllByMemberIds(request.memberIdList());
            List<String> participants = members.stream().map(Member::getUsername).toList();

            String result = googleClient.speechToText(file, audioUrl);
            String summary = googleClient.getSummary(result, participants);
            Report report = ReportMapper.from(summary);
            return reportRepository.save(report);
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
        }
        throw new InternalServerException(ReportErrorType.NOT_FOUND);
    }

    public ReportResponse findById(Long reportId) {
        Report report = getReportById(reportId);
        return ReportResponse.of(report);
    }

    @Transactional
    public ReportPdfCreateResponse createPdfUrl(ReportPdfCreateRequest request) {
        Report report = getReportById(request.reportId());
        if (checkFileExist(report.getFileDirectory())) {
            return new ReportPdfCreateResponse(gcsClient.getUrl(report.getFileDirectory()));
        }
        try {
            createPdfFile(report);
            String pdfDirectory = gcsClient.createPdfDirectory(new FileInputStream("temp.pdf"));

            report.updateFileDirectory(pdfDirectory);
            return new ReportPdfCreateResponse(gcsClient.getUrl(pdfDirectory));
        } catch (Exception e) {
            log.info("EXCEPTION: {}", e.getMessage());
            throw new InternalServerException(CommonErrorType.INTERNAL_SERVER);
        }
    }

    private Report getReportById(Long reportId) {
        return reportRepository
                .findById(reportId)
                .orElseThrow(() -> new NotFoundException(ReportErrorType.NOT_FOUND));
    }

    private static void createPdfFile(Report report) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        FileOutputStream out = new FileOutputStream("temp.pdf");
        PdfWriter.getInstance(document, out);
        document.addLanguage("ko-KR");
        document.open();
        File fontFile = new File("src/main/resources/static/PretendardVariable.ttf");
        BaseFont unicode =
                BaseFont.createFont(
                        fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(unicode, 18);
        document.add(new Paragraph(report.getContent(), font));
        document.close();
    }

    private static boolean checkFileExist(String directory) {
        return directory != null;
    }
}
