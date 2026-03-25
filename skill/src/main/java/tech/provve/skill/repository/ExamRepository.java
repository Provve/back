package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.server.generated.dto.Condition;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.mapper.ExamMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Exam.EXAM;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class ExamRepository extends Filtering {

    @External
    private final DSLContext dsl;

    private static final Map<String, Function<tech.provve.api.server.generated.dto.Condition, org.jooq.Condition>> FIELD_CONDITION_MAPPERS = Map.of(
            "name", condition -> switch (condition.getOperator()) {
                case EQ -> EXAM.NAME.eq(condition.getValue());
                case LIKE -> EXAM.NAME.like(condition.getValue());
            },
            "skill_name", condition -> switch (condition.getOperator()) {
                case EQ -> EXAM.SKILL_NAME.eq(condition.getValue());
                case LIKE -> EXAM.SKILL_NAME.like(condition.getValue());
            },
            "description", condition -> switch (condition.getOperator()) {
                case EQ -> EXAM.DESCRIPTION.eq(condition.getValue());
                case LIKE -> EXAM.DESCRIPTION.like(condition.getValue());
            }
    );

    private final RecordMapper<Record, Exam> outputMapper = record -> new Exam(
            record.get(EXAM.NAME),
            record.get(EXAM.SKILL_NAME),
            record.get(EXAM.DESCRIPTION),
            record.get(EXAM.PRIVATE_ARCHIVE_URL),
            record.get(EXAM.PUBLIC_ARCHIVE_URL)
    );

    public void save(Exam exam) {
        dsl.insertInto(EXAM)
           .set(ExamMapper.INSTANCE.map(exam))
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
                        .where(jooqConditions(filter.getConditions()))
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
        return FIELD_CONDITION_MAPPERS;
    }
}
