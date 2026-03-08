package tech.provve.skill.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Skill;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@InjectTest
class ExamRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @Inject
    ExamRepository examRepository;

    @Inject
    SkillRepository skillRepository;

    @Test
    void find_exists_foundExactly() {
        // arrange
        var skill = new Skill("s", emptyList());
        skillRepository.save(skill);

        var exam = new Exam("a", "s", "d", "1", "2");
        examRepository.save(exam);

        // act
        var found = examRepository.find(exam.name());

        // assert
        assertThat(found).isPresent()
                         .contains(exam);
    }

}