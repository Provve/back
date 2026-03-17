package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.db.generated.tables.records.SessionRecord;
import tech.provve.skill.domain.entity.Session;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Session.SESSION;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class SessionRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Session> outputMapper = record -> new Session(
            record.get(SESSION.OWNER),
            record.get(SESSION.EXAM_NAME),
            record.get(SESSION.CREATED)
                  .toInstant()
    );

    public void save(Session session) {
        dsl.insertInto(SESSION)
           .set(new SessionRecord(session.owner(),
                                  session.examName(),
                                  OffsetDateTime.ofInstant(session.started(), ZoneId.systemDefault())))
           .execute();
    }

    public Optional<Session> find(String name) {
        return dsl.select()
                  .from(SESSION)
                  .where(SESSION.EXAM_NAME.eq(name))
                  .fetchOptional(outputMapper);
    }

    public boolean exists(String owner) {
        return nonNull(dsl.select(SESSION.OWNER)
                          .from(SESSION)
                          .where(SESSION.OWNER.eq(owner))
                          .fetchOne());
    }
}
