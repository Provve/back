package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.db.generated.tables.records.SkillRecord;
import tech.provve.skill.domain.entity.Skill;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Skill.SKILL_;
import static tech.provve.skill.db.generated.tables.TsSkillRu.TS_SKILL_RU;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class SkillRepository extends Filtering {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Skill> outputMapper = record -> new Skill(
            record.get(SKILL_.NAME),
            List.of(record.get(SKILL_.TAGS))
    );

    public void save(Skill skill) {
        dsl.insertInto(SKILL_)
           .set(new SkillRecord(
                   skill.name(),
                   skill.tags()
                        .toArray(new String[0])
           ))
           .execute();
    }

    public Optional<Skill> find(String name) {
        return dsl.select()
                  .from(SKILL_)
                  .where(SKILL_.NAME.eq(name))
                  .fetchOptional(outputMapper);
    }

    public boolean exists(String name) {
        return nonNull(dsl.select(SKILL_.NAME)
                          .from(SKILL_)
                          .where(SKILL_.NAME.eq(name))
                          .fetchOne());
    }

    @SuppressWarnings("all")
    public List<Skill> getAll(Filter filter, String previous, int pageSize) {
        var select = dsl.select()
                        .from(SKILL_)
                        .where(jooqConditions(filter))
                        .orderBy(SKILL_.NAME)
                        .seek(previous)
                        .limit(pageSize);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

    public void delete(String name) {
        dsl.deleteFrom(SKILL_)
           .where(SKILL_.NAME.eq(name))
           .execute();
    }

    @Override
    protected Map<String, Function<tech.provve.api.server.generated.dto.Condition, Condition>> fieldConditionMappers() {
        return Map.of(
                "examName", condition -> switch (condition.getOperator()) {
                    case EQ -> SKILL_.NAME.eq(condition.getValue());
                    case LIKE -> DSL.exists(dsl.select()
                                               .from(TS_SKILL_RU)
                                               .where(DSL.field("{0} @@ plainto_tsquery('russian', {1})",
                                                                Boolean.class,
                                                                TS_SKILL_RU.TS_SKILL_NAME, DSL.inline(condition.getValue()))));
                },
                "tags", condition -> switch (condition.getOperator()) {
                    case EQ, LIKE -> DSL.condition("{0} && {1}", SKILL_.TAGS, DSL.inline("{%s}".formatted(condition.getValue())));
                }
        );
    }
}
