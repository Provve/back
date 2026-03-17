package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.mapper.ExamMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.Exam.EXAM;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class ExamRepository {

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
    public List<Exam> getAll() {
        var select = dsl.select()
                        .from(EXAM);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

}
