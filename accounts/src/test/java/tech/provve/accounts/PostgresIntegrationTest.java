package tech.provve.accounts;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
public abstract class PostgresIntegrationTest {

    @Container
    public static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withInitScripts("db/migration/V1__Init.sql")
            .withUsername("postgres")
            .withPassword("1");
}
