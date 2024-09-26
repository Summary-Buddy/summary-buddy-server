package summarybuddy.server.attendees.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summarybuddy.server.attendees.repository.AttendeesRepository;
import summarybuddy.server.attendees.repository.domain.Attendees;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.dto.response.ReportResponse;
import summarybuddy.server.report.repository.domain.Report;

@Service
@RequiredArgsConstructor
public class AttendeesService {
    private final MemberRepository memberRepository;
    private final AttendeesRepository attendeesRepository;

    public List<ReportResponse> findReportsByMemberId(Long memberId) {
        List<Attendees> attendees = attendeesRepository.findAllByMemberId(memberId);
        return attendees.stream().map(item -> ReportResponse.of(item.getReport())).toList();
    }

    public void save(Long memberId, Report report, ReportCreateRequest request) {
        request.memberIdList().addFirst(memberId);
        List<Member> members = memberRepository.findAllByMemberIds(request.memberIdList());
        List<Attendees> attendees =
                members.stream()
                        .map(member -> Attendees.builder().report(report).member(member).build())
                        .toList();
        attendeesRepository.saveAll(attendees);
    }
}
