package tech.provve.statemachine.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.statemachine.db.generated.enums.SaveExamState;
import tech.provve.statemachine.db.generated.tables.records.SaveExamRecord;
import tech.provve.statemachine.domain.entity.SaveExam;

import java.util.List;

import static java.util.Objects.nonNull;
import static tech.provve.statemachine.db.generated.tables.SaveExam.SAVE_EXAM;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class SaveExamRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, SaveExam> outputMapper = record -> new SaveExam(
            record.get(SAVE_EXAM.NAME),
            tech.provve.statemachine.domain.value.SaveExamState.valueOf(record.get(SAVE_EXAM.STATE)
                                                                              .toString()),
            record.get(SAVE_EXAM.AUTHOR),
            record.get(SAVE_EXAM.DELAYED_VOTE_JSON)
    );

    public void save(SaveExam saveExam) {
        dsl.insertInto(SAVE_EXAM)
           .set(new SaveExamRecord(
                   saveExam.name(),
                   SaveExamState.lookupLiteral(saveExam.state()
                                                       .toString()),
                   saveExam.author(),
                   saveExam.delayedVoteJson()
           ))
           .execute();
    }

    public void updateState(String name, tech.provve.statemachine.domain.value.SaveExamState state) {
        dsl.update(SAVE_EXAM)
           .set(SAVE_EXAM.STATE, SaveExamState.lookupLiteral(state.toString()))
           .where(SAVE_EXAM.NAME.eq(name))
           .execute();
    }

    /**
     * @return all of saved machines in non-final state
     */
    @SuppressWarnings("all")
    public List<SaveExam> list() {
        var select = dsl.select()
                        .from(SAVE_EXAM);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

    public boolean exists(String name) {
        return nonNull(dsl.select(SAVE_EXAM.NAME)
                          .from(SAVE_EXAM)
                          .where(SAVE_EXAM.NAME.eq(name))
                          .fetchOne());
    }

    public void delete(String name) {
        dsl.deleteFrom(SAVE_EXAM)
           .where(SAVE_EXAM.NAME.eq(name))
           .execute();
    }

}
