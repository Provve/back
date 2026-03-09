package tech.provve.statemachine.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import tech.provve.statemachine.db.generated.enums.SaveExamState;
import tech.provve.statemachine.db.generated.tables.records.SaveExamRecord;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.provve.statemachine.db.generated.tables.SaveExam.SAVE_EXAM;

@InjectTest
class SaveExamRepositoryTest {

    public static final String NAME = "n";

    @Setup
    void set(BeanScopeBuilder b) {
        MockDataProvider provider = context -> {
            DSLContext create = DSL.using(SQLDialect.POSTGRES);

            Result<SaveExamRecord> result = create.newResult(SAVE_EXAM);
            result.add(create.newRecord(SAVE_EXAM, new SaveExamRecord(NAME, SaveExamState.UNPREPARED, "", "")));

            return new MockResult[]{
                    new MockResult(1, result)
            };
        };
        Connection connection = new MockConnection(provider);

        b.bean(DSLContext.class, DSL.using(connection, SQLDialect.POSTGRES));
    }

    @Inject
    SaveExamRepository saveExamRepository;

    @Test
    void exists_thereIsRecord_true() {
        // act
        boolean exists = saveExamRepository.exists(NAME);

        // assert
        assertTrue(exists);
    }

}