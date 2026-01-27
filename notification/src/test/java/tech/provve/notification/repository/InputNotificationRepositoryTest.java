package tech.provve.notification.repository;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.provve.notification.PostgresIntegrationTest;
import tech.provve.notification.domain.entity.InputNotification;
import tech.provve.notification.domain.value.NotificationLevel;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@InjectTest
class InputNotificationRepositoryTest extends PostgresIntegrationTest {

    @Inject
    NotificationRepository repository;

    @Test
    void save_forUnexistingAccount_exception() {
        // arrange
        var notification = new InputNotification(
                "whitelight",
                NotificationLevel.INFO,
                "You have received a trillion"
        );

        // act assert
        assertThatThrownBy(() -> repository.save(notification))
                .hasMessageContaining("is not present in table");
    }
}