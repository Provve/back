package tech.provve.accounts;

import io.avaje.inject.Bean;
import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.Factory;
import io.avaje.inject.test.Setup;
import io.avaje.inject.test.TestScope;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Factory
@TestScope
@Testcontainers
@SuppressWarnings("all")
public class PostgresIntegrationTest {

    @Container
    public static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withInitScripts("db/migration/V1__Init.sql")
            .withUsername("postgres")
            .withPassword("1");

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(Connection.class, connection());
    }

    @Bean
    Connection connection() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1");

        try {
            return DriverManager.getConnection(postgres.getJdbcUrl(), props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
