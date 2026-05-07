package tech.provve.skill.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.server.generated.dto.Condition;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Result;
import tech.provve.skill.domain.entity.Skill;

import java.time.Duration;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;


@InjectTest
class ResultRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @Inject
    ExamRepository examRepository;

    @Inject
    SkillRepository skillRepository;

    @Inject
    ResultRepository resultRepository;

    @Inject
    AccountRepository accountRepository;

    @Test
    void getAllForExaminee_equalFilterByExamName_found() {
        // arrange
        var skill = new Skill("s", emptyList());
        skillRepository.save(skill);

        var exam = new Exam("a", skill.name(), "d", "p", "pp");
        examRepository.save(exam);

        var account = new Account("u", "", "", false, "", "", "", false);
        accountRepository.save(account);

        var result = new Result(exam.name(), account.login(), Duration.ofHours(1));
        resultRepository.save(result);

        var filter = new Filter(List.of(new Condition("exam_name", Condition.OperatorEnum.EQ, exam.name())));

        // act
        List<Result> found = resultRepository.getAllForExaminee(filter, account.login(), "", 1);

        // assert
        assertThat(result).isIn(found);

        // cleanup
        skillRepository.delete(skill.name());
    }

}