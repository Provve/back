package tech.provve.statemachine.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jspecify.annotations.NullMarked;
import tech.provve.statemachine.db.generated.enums.CheckSolutionState;
import tech.provve.statemachine.db.generated.tables.records.CheckSolutionRecord;
import tech.provve.statemachine.domain.entity.CheckSolution;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static tech.provve.statemachine.db.generated.tables.CheckSolution.CHECK_SOLUTION;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class CheckSolutionRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, CheckSolution> outputMapper = record -> new CheckSolution(
            record.get(CHECK_SOLUTION.NAME),
            tech.provve.statemachine.domain.value.CheckSolutionState.valueOf(record.get(CHECK_SOLUTION.STATE)
                                                                                   .toString()),
            record.get(CHECK_SOLUTION.EXAMINEE)
    );

    public void save(CheckSolution checkSolution) {
        dsl.insertInto(CHECK_SOLUTION)
           .set(new CheckSolutionRecord(
                   checkSolution.name(),
                   CheckSolutionState.lookupLiteral(checkSolution.state()
                                                                 .toString()),
                   checkSolution.examinee()
           ))
           .execute();
    }

    public void updateState(String name, tech.provve.statemachine.domain.value.CheckSolutionState state) {
        dsl.update(CHECK_SOLUTION)
           .set(CHECK_SOLUTION.STATE, CheckSolutionState.lookupLiteral(state.toString()))
           .where(CHECK_SOLUTION.NAME.eq(name))
           .execute();
    }

    @SuppressWarnings("all")
    public List<CheckSolution> list() {
        var select = dsl.select()
                        .from(CHECK_SOLUTION);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

    public boolean exists(String name) {
        var trueField = DSL.field("true", SQLDataType.BOOLEAN);
        return TRUE.equals(dsl.select()
                              .from(CHECK_SOLUTION)
                              .where(CHECK_SOLUTION.NAME.eq(name))
                              .fetchOne(set -> set.get(trueField)));
    }

    public void delete(String name) {
        dsl.deleteFrom(CHECK_SOLUTION)
           .where(CHECK_SOLUTION.NAME.eq(name))
           .execute();
    }

}
