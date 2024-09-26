package summarybuddy.server.report.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import summarybuddy.server.ai.GoogleClient;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.mapper.ReportMapper;
import summarybuddy.server.report.repository.ReportRepository;
import summarybuddy.server.report.repository.domain.Report;
import summarybuddy.server.storage.GcsClient;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final GcsClient gcsClient;
    private final GoogleClient googleClient;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public Report save(Long memberId, MultipartFile file, ReportCreateRequest request) {
        String audioUrl = gcsClient.createAudioUrl(file);
        request.memberIdList().addFirst(memberId);
        List<Member> members = memberRepository.findAllByMemberIds(request.memberIdList());
        List<String> participants = members.stream().map(Member::getUsername).toList();

        String result = googleClient.speechToText(file, audioUrl);
        String summary = googleClient.getSummary(result, participants);
        Report report = ReportMapper.from(summary, audioUrl); // pdf file url로 변경 필요
        return reportRepository.save(report);
    }
}
