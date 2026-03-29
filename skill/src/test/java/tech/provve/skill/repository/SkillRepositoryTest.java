package tech.provve.skill.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tech.provve.api.server.generated.dto.Condition;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.Skill;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
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

        // cleanup
        skillRepository.delete(skill.name());
    }

    @Test
    void getAll_equalFilterByName_found() {
        // arrange
        var skill = new Skill("s", emptyList());
        skillRepository.save(skill);

        // act
        List<Skill> found = skillRepository.getAll(new Filter(List.of(new Condition("name", Condition.OperatorEnum.EQ, skill.name()))), "", 1);

        // assert
        assertThat(skill).isIn(found);

        // cleanup
        skillRepository.delete(skill.name());
    }

    @ParameterizedTest
    @CsvSource("""
            'a, b, c'
            'b, c, a'
            """)
    void getAll_equalFilterByTags_found(String tagsFromFrontend) {
        // arrange
        var tags = List.of("a", "b", "c");
        var skill = new Skill("s", tags);
        skillRepository.save(skill);

        // act
        List<Skill> found = skillRepository.getAll(new Filter(List.of(new Condition("tags", Condition.OperatorEnum.EQ, tagsFromFrontend))), "", 1);

        // assert
        assertThat(found).isNotEmpty();

        // cleanup
        skillRepository.delete(skill.name());
    }

    @Test
    void getAll_likeFilterByName_found() {
        // arrange
        var skill = new Skill("Администрирование Рубунту: настройка nftables", emptyList());
        skillRepository.save(skill);

        // act
        List<Skill> found = skillRepository.getAll(new Filter(List.of(new Condition("name", Condition.OperatorEnum.LIKE, "настройка nftables"))), "", 1);

        // assert
        assertThat(skill).isIn(found);

        // cleanup
        skillRepository.delete(skill.name());
    }

}