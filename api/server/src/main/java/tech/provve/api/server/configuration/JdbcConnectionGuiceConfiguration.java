package tech.provve.api.server.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionGuiceConfiguration extends AbstractModule {

    @Provides
    Connection connection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1");

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/provve", props);
    }

}
