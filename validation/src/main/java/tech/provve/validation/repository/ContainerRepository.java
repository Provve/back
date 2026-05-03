package tech.provve.validation.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.validation.domain.value.ContainerView;
import tech.provve.validation.mapper.ContainerJooqMapper;

import java.util.Optional;

import static tech.provve.validation.db.generated.tables.Container.CONTAINER;


@NullMarked
@Singleton
@RequiredArgsConstructor
public class ContainerRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, ContainerView> outputMapper = record -> new ContainerView(
            record.get(CONTAINER.EXAMINEE),
            record.get(CONTAINER.EXAM_NAME),
            record.get(CONTAINER.CONTAINER_ID)
    );

    public void save(ContainerView containerView) {
        dsl.insertInto(CONTAINER)
           .set(ContainerJooqMapper.INST.map(containerView))
           .execute();
    }

    public void delete(String examinee) {
        dsl.deleteFrom(CONTAINER)
           .where(CONTAINER.EXAMINEE.eq(examinee))
           .execute();
    }

    public Optional<ContainerView> find(String examinee) {
        return dsl.select()
                  .from(CONTAINER)
                  .where(CONTAINER.EXAMINEE.eq(examinee))
                  .fetchOptional(outputMapper);
    }
}
