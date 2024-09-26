package summarybuddy.server.attendees.repository.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summarybuddy.server.member.repository.domain.Member;
import summarybuddy.server.report.repository.domain.Report;

@Entity
@Getter
@NoArgsConstructor
public class Attendees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Builder
    public Attendees(Long id, Member member, Report report) {
        this.id = id;
        this.member = member;
        this.report = report;
    }
}
