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
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Skill;

import java.util.List;

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

    @ParameterizedTest
    @CsvSource("""
            name, a
            skill_name, s
            description, d
            """)
    void getAll_equalFilterByFIELD_found(String searchField, String searchValue) {
        // arrange
        var skill = new Skill("s", emptyList());
        skillRepository.save(skill);

        var exam = new Exam("a", skill.name(), "d", "p", "pp");
        examRepository.save(exam);

        var filter = new Filter(List.of(new Condition(searchField, Condition.OperatorEnum.EQ, searchValue)));

        // act
        List<Exam> found = examRepository.getAll(filter, "", 1);

        // assert
        assertThat(exam).isIn(found);

        // cleanup
        skillRepository.delete(skill.name());
    }

    @ParameterizedTest
    @CsvSource("""
            name, настройка nftables
            description, работа освоить
            """)
    void getAll_likeFilterByFIELD_found(String searchField, String searchValue) {
        // arrange
        var skill = new Skill("Администрирование Рубунту: настройка nftables", emptyList());
        skillRepository.save(skill);

        var exam = new Exam(skill.name(), skill.name(), """
                Вам предстоить освоить X.
                В случае прохождения, мы возьмём вас на работу в компанию VidemanTechonologies.
                """, "p", "pp");
        examRepository.save(exam);

        var filter = new Filter(List.of(new Condition(searchField, Condition.OperatorEnum.LIKE, searchValue)));

        // act
        List<Exam> found = examRepository.getAll(filter, "", 1);

        // assert
        assertThat(exam).isIn(found);

        // cleanup
        skillRepository.delete(skill.name());
    }
}