import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import jakarta.inject.Named;
import org.jooq.DSLContext;
import org.mockito.Mockito;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static tech.provve.statemachine.SaveExamMachine.DELAYED_EXAM_VOTE_CREATOR;

@Factory
@TestScope
public class Stubs {

    @Bean
    public DSLContext dslContext() {
        return Mockito.mock(DSLContext.class);
    }

    @Bean
    @Named(DELAYED_EXAM_VOTE_CREATOR)
    public Consumer<String> delayedExamVoteCreator() {
        return vote -> {
        };
    }

    @Bean
    public BiConsumer<String, String> validationErrorNotificationSender() {
        return (a, b) -> {
        };
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
    public S3AsyncClient s3AsyncClient() {
        return new S3AsyncClient() {
            @Override
            public String serviceName() {
                return "";
            }

            @Override
            public void close() {

            }
        };
    }

}
