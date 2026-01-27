package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Factory
public class JdbcConnection {

    @Bean
    public Connection connection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1");

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/provve", props);
    }

}
