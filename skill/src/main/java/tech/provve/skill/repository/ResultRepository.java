package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.types.YearToSecond;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.db.generated.tables.records.ResultRecord;
import tech.provve.skill.domain.entity.Result;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Result.RESULT;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class ResultRepository extends Filtering {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Result> outputMapper = record -> new Result(
            record.get(RESULT.EXAM_NAME),
            record.get(RESULT.EXAMINEE),
            record.get(RESULT.DURATION)
                  .toDuration()
    );

    public void save(Result result) {
        dsl.insertInto(RESULT)
           .set(new ResultRecord(result.examName(),
                                 result.examinee(),
                                 YearToSecond.valueOf(result.duration())))
           .execute();
    }

    public boolean exists(String examinee) {
        return nonNull(dsl.select(RESULT.EXAMINEE)
                          .from(RESULT)
                          .where(RESULT.EXAMINEE.eq(examinee))
                          .fetchOne());
    }

    @SuppressWarnings("all")
    public List<Result> findAll(Filter filter, String examinee, String previous, int pageSize) {
        List<Condition> conditions = jooqConditions(filter.getConditions());
        conditions.add(RESULT.EXAMINEE.eq(examinee));

        var select = dsl.select()
                        .from(RESULT)
                        .where(conditions)
                        .orderBy(RESULT.EXAM_NAME)
                        .seek(previous)
                        .limit(pageSize);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

    @Override
    protected Map<String, Function<tech.provve.api.server.generated.dto.Condition, Condition>> fieldConditionMappers() {
        return Map.of(
                "exam_name", condition -> switch (condition.getOperator()) {
                    case EQ, LIKE -> RESULT.EXAM_NAME.eq(condition.getValue());
                }
        );
    }
}
