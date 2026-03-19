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
import tech.provve.skill.db.generated.tables.records.ResultRecord;
import tech.provve.skill.domain.entity.Result;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Result.RESULT;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class ResultRepository {

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

    public Optional<Result> find(String name) {
        return dsl.select()
                  .from(RESULT)
                  .where(RESULT.EXAM_NAME.eq(name))
                  .fetchOptional(outputMapper);
    }

    public boolean exists(String examinee) {
        return nonNull(dsl.select(RESULT.EXAMINEE)
                          .from(RESULT)
                          .where(RESULT.EXAMINEE.eq(examinee))
                          .fetchOne());
    }

    public List<Result> findAllByExaminee(String examinee, String previous, int pageSize) {
        return fetchExams(previous, pageSize, RESULT.EXAMINEE.eq(examinee));
    }

    public List<Result> findAllByExamineeAndExamName(String examinee, String examName, String previous, int pageSize) {
        return fetchExams(previous, pageSize,
                          RESULT.EXAMINEE.eq(examinee),
                          RESULT.EXAM_NAME.eq(examName));
    }

    @SuppressWarnings("all")
    private List<Result> fetchExams(String previous, int pageSize, Condition... conditions) {
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
}
