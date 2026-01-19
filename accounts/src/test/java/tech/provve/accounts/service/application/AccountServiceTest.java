package tech.provve.accounts.service.application;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.provve.accounts.configuration.AccountsGuiceConfiguration;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(GuiceExtension.class)
@IncludeModule(AccountsGuiceConfiguration.class)
@IncludeModule(AccountServiceTest.TestConnection.class)
class AccountServiceTest {

    @Inject
    AccountService service;

    @Test
    void registerUser_givenEmail_falsePersonalDataConsent_exception() {
        // arrange
        var registerRequest = new RegisterUserRequest(
                "test_login",
                "test@example.com",
                "hashed_password",
                false,
                "Test User"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.registerUser(registerRequest));
    }

    @Test
    void registerUser_givenEmptyLogin_exception() {
        // arrange
        var registerRequest = new RegisterUserRequest(
                null,
                null,
                "hashed_password",
                false,
                "Test User"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.registerUser(registerRequest));
    }

    static class TestConnection extends AbstractModule {

        @Provides
        Connection connection() {
            return new Connection() {
                @Override
                public Statement createStatement() throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql) throws SQLException {
                    return null;
                }

                @Override
                public CallableStatement prepareCall(String sql) throws SQLException {
                    return null;
                }

                @Override
                public String nativeSQL(String sql) throws SQLException {
                    return "";
                }

                @Override
                public boolean getAutoCommit() throws SQLException {
                    return false;
                }

                @Override
                public void setAutoCommit(boolean autoCommit) throws SQLException {

                }

                @Override
                public void commit() throws SQLException {

                }

                @Override
                public void rollback() throws SQLException {

                }

                @Override
                public void close() throws SQLException {

                }

                @Override
                public boolean isClosed() throws SQLException {
                    return false;
                }

                @Override
                public DatabaseMetaData getMetaData() throws SQLException {
                    return null;
                }

                @Override
                public boolean isReadOnly() throws SQLException {
                    return false;
                }

                @Override
                public void setReadOnly(boolean readOnly) throws SQLException {

                }

                @Override
                public String getCatalog() throws SQLException {
                    return "";
                }

                @Override
                public void setCatalog(String catalog) throws SQLException {

                }

                @Override
                public int getTransactionIsolation() throws SQLException {
                    return 0;
                }

                @Override
                public void setTransactionIsolation(int level) throws SQLException {

                }

                @Override
                public SQLWarning getWarnings() throws SQLException {
                    return null;
                }

                @Override
                public void clearWarnings() throws SQLException {

                }

                @Override
                public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                    return null;
                }

                @Override
                public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                    return null;
                }

                @Override
                public Map<String, Class<?>> getTypeMap() throws SQLException {
                    return Map.of();
                }

                @Override
                public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

                }

                @Override
                public int getHoldability() throws SQLException {
                    return 0;
                }

                @Override
                public void setHoldability(int holdability) throws SQLException {

                }

                @Override
                public Savepoint setSavepoint() throws SQLException {
                    return null;
                }

                @Override
                public Savepoint setSavepoint(String name) throws SQLException {
                    return null;
                }

                @Override
                public void rollback(Savepoint savepoint) throws SQLException {

                }

                @Override
                public void releaseSavepoint(Savepoint savepoint) throws SQLException {

                }

                @Override
                public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                    return null;
                }

                @Override
                public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
                    return null;
                }

                @Override
                public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
                    return null;
                }

                @Override
                public Clob createClob() throws SQLException {
                    return null;
                }

                @Override
                public Blob createBlob() throws SQLException {
                    return null;
                }

                @Override
                public NClob createNClob() throws SQLException {
                    return null;
                }

                @Override
                public SQLXML createSQLXML() throws SQLException {
                    return null;
                }

                @Override
                public boolean isValid(int timeout) throws SQLException {
                    return false;
                }

                @Override
                public void setClientInfo(String name, String value) throws SQLClientInfoException {

                }

                @Override
                public String getClientInfo(String name) throws SQLException {
                    return "";
                }

                @Override
                public Properties getClientInfo() throws SQLException {
                    return null;
                }

                @Override
                public void setClientInfo(Properties properties) throws SQLClientInfoException {

                }

                @Override
                public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
                    return null;
                }

                @Override
                public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
                    return null;
                }

                @Override
                public String getSchema() throws SQLException {
                    return "";
                }

                @Override
                public void setSchema(String schema) throws SQLException {

                }

                @Override
                public void abort(Executor executor) throws SQLException {

                }

                @Override
                public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

                }

                @Override
                public int getNetworkTimeout() throws SQLException {
                    return 0;
                }

                @Override
                public <T> T unwrap(Class<T> iface) throws SQLException {
                    return null;
                }

                @Override
                public boolean isWrapperFor(Class<?> iface) throws SQLException {
                    return false;
                }
            }; // в этом тесте не используется
        }

    }

}