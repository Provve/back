package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.db.generated.tables.records.SkillRecord;
import tech.provve.skill.domain.entity.Skill;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Skill.SKILL_;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class SkillRepository {

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
    public List<Skill> getAll(String previous, int pageSize) {
        var select = dsl.select()
                        .from(SKILL_)
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

}
