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
import tech.provve.skill.domain.entity.Skill;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@InjectTest
class SkillRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @Inject
    SkillRepository skillRepository;

    @Test
    void exists_thereIsRecord_true() {
        // arrange
        var skill = new Skill("s", emptyList());
        skillRepository.save(skill);

        // act
        boolean exists = skillRepository.exists(skill.name());

        // assert
        assertTrue(exists);
    }

}