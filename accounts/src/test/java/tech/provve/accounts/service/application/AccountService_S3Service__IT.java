package tech.provve.accounts.service.application;

import com.robothy.s3.jupiter.LocalS3;
import io.avaje.config.Config;
import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.FileUpload;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.UpdateAvatarRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@LocalS3(port = 19000)
@InjectTest
class AccountService_S3Service__IT extends PostgresIntegrationTest {

    @Mock
    JwsParsingService jwsParsingService;

    @Inject
    AccountService service;

    @Inject
    AccountRepository repository;

    @Inject
    S3Client s3Client;

    @Setup
    void f(BeanScopeBuilder b) {
        b.bean(Vertx.class, vertx);
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @BeforeEach
    void all() {
        s3Client.createBucket(builder -> builder.bucket(Config.get("s3.buckets.images"))
                                                .acl(BucketCannedACL.PUBLIC_READ));
    }

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

    Vertx vertx = Vertx.vertx();

    @Test
    void updateAvatar_uploadObject_anyOneCanDownload() throws IOException, InterruptedException {
        // arrange
        var login = "w";
        registerAccount(login);

        byte[] avatarBytes = new byte[]{1};
        Buffer avatarBuffer = Buffer.buffer(avatarBytes);
        String avatarPath = "a";
        vertx.fileSystem()
             .writeFileBlocking(avatarPath, avatarBuffer);

        String authToken = "a";
        var updateAvatarRequest = new UpdateAvatarRequest(stubFileUpload(avatarPath), authToken);
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", login));

        service.updateAvatar(updateAvatarRequest);
        Account updatedAccount = repository.findByLogin(login)
                                           .get();
        String avatarUrl = Config.get("s3.url") + "/" + updatedAccount.avatarUrl();
        HttpRequest downloadRequest = HttpRequest.newBuilder(URI.create(avatarUrl))
                                                 .build();
        // act
        byte[] downloadedAvatar = HttpClient.newHttpClient()
                                            .send(downloadRequest, HttpResponse.BodyHandlers.ofByteArray())
                                            .body();
        // assert
        assertThat(downloadedAvatar).containsExactly(1);
    }

    private FileUpload stubFileUpload(String uploadedFileName) {
        return new FileUpload() {
            @Override
            public String uploadedFileName() {
                return uploadedFileName;
            }

            @Override
            public String name() {
                return "";
            }

            @Override
            public String fileName() {
                return "";
            }

            @Override
            public long size() {
                return 0;
            }

            @Override
            public String contentType() {
                return "";
            }

            @Override
            public String contentTransferEncoding() {
                return "";
            }

            @Override
            public String charSet() {
                return "";
            }

            @Override
            public boolean cancel() {
                return false;
            }

            @Override
            public Future<Void> delete() {
                return null;
            }
        };
    }

    private void registerAccount(String login) {
        var account = new Account(
                login,
                "%s@%s.%s".formatted(login, login, login),
                "",
                true,
                "n",
                null,
                true
        );
        repository.save(account);
    }

}
