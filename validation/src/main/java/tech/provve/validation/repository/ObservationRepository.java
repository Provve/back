package tech.provve.validation.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.validation.db.generated.tables.records.ObservationRecord;
import tech.provve.validation.domain.entity.Observation;

import java.util.Optional;

import static tech.provve.validation.db.generated.tables.Observation.OBSERVATION;


@NullMarked
@Singleton
@RequiredArgsConstructor
public class ObservationRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Observation> outputMapper = record -> new Observation(
            record.get(OBSERVATION.SESSION_OWNER),
            record.get(OBSERVATION.CHEATED)
    );

    public void save(Observation observation) {
        dsl.insertInto(OBSERVATION)
           .set(new ObservationRecord(observation.examinee(), observation.cheated()))
           .execute();
    }

    public Optional<Observation> find(String examinee) {
        return dsl.select()
                  .from(OBSERVATION)
                  .where(OBSERVATION.SESSION_OWNER.eq(examinee))
                  .fetchOptional(outputMapper);
    }
}
