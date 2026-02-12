import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import org.mockito.Mockito;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import software.amazon.awssdk.services.s3.S3Client;

@Factory
@TestScope
public class Stubs {

    @Bean
    public Mailer mailer() {
        return MailerBuilder.withSMTPServerHost("t")
                            .buildMailer();
    }

    @Bean
    public S3Client s3Client() {
        return new S3Client() {
            @Override
            public String serviceName() {
                return "";
            }

            @Override
            public void close() {

            }
        };
    }

    @Bean
    public Vertx vertx() {
        return Mockito.mock(Vertx.class);
    }

    @Bean
    public JWTAuth auth() {
        return Mockito.mock(JWTAuth.class);
    }

}
