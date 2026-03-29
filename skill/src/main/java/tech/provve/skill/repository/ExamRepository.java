package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.server.generated.dto.Condition;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.mapper.exam.ExamJooqMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Exam.EXAM;
import static tech.provve.skill.db.generated.tables.TsExamRu.TS_EXAM_RU;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class ExamRepository extends Filtering {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Exam> outputMapper = record -> new Exam(
            record.get(EXAM.NAME),
            record.get(EXAM.SKILL_NAME),
            record.get(EXAM.DESCRIPTION),
            record.get(EXAM.PRIVATE_ARCHIVE_URL),
            record.get(EXAM.PUBLIC_ARCHIVE_URL)
    );

    public void save(Exam exam) {
        dsl.insertInto(EXAM)
           .set(ExamJooqMapper.INSTANCE.map(exam))
           .execute();
    }

    public Optional<Exam> find(String name) {
        return dsl.select()
                  .from(EXAM)
                  .where(EXAM.NAME.eq(name))
                  .fetchOptional(outputMapper);
    }

    public boolean exists(String name) {
        return nonNull(dsl.select(EXAM.NAME)
                          .from(EXAM)
                          .where(EXAM.NAME.eq(name))
                          .fetchOne());
    }

    @SuppressWarnings("all")
    public List<Exam> getAll(Filter filter, String previous, int pageSize) {
        var select = dsl.select()
                        .from(EXAM)
                        .where(jooqConditions(filter))
                        .orderBy(EXAM.NAME)
                        .seek(previous)
                        .limit(pageSize);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

    @Override
    protected Map<String, Function<Condition, org.jooq.Condition>> fieldConditionMappers() {
        return Map.of(
                "name", condition -> switch (condition.getOperator()) {
                    case EQ -> EXAM.NAME.eq(condition.getValue());
                    case LIKE -> DSL.exists(dsl.select()
                                               .from(TS_EXAM_RU)
                                               .where(DSL.field("{0} @@ plainto_tsquery('russian', {1})",
                                                                Boolean.class,
                                                                TS_EXAM_RU.TS_EXAM_NAME, DSL.inline(condition.getValue()))));
                },
                "skill_name", condition -> switch (condition.getOperator()) {
                    case EQ, LIKE -> EXAM.SKILL_NAME.eq(condition.getValue());
                },
                "description", condition -> switch (condition.getOperator()) {
                    case EQ -> EXAM.DESCRIPTION.eq(condition.getValue());
                    case LIKE -> DSL.exists(dsl.select()
                                               .from(TS_EXAM_RU)
                                               .where(DSL.field("{0} @@ plainto_tsquery('russian', {1})",
                                                                Boolean.class,
                                                                TS_EXAM_RU.DESCRIPTION, DSL.inline(condition.getValue()))));
                }
        );
    }
}
