package summarybuddy.server.attendees.repository.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendees is a Querydsl query type for Attendees
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendees extends EntityPathBase<Attendees> {

    private static final long serialVersionUID = 786503873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendees attendees = new QAttendees("attendees");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final summarybuddy.server.member.repository.domain.QMember member;

    public final summarybuddy.server.report.repository.domain.QReport report;

    public QAttendees(String variable) {
        this(Attendees.class, forVariable(variable), INITS);
    }

    public QAttendees(Path<? extends Attendees> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendees(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendees(PathMetadata metadata, PathInits inits) {
        this(Attendees.class, metadata, inits);
    }

    public QAttendees(Class<? extends Attendees> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new summarybuddy.server.member.repository.domain.QMember(forProperty("member")) : null;
        this.report = inits.isInitialized("report") ? new summarybuddy.server.report.repository.domain.QReport(forProperty("report")) : null;
    }

}

