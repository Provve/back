package tech.provve.notification;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import org.mockito.Mockito;
import tech.provve.notification.repository.NotificationRepository;

@Factory
@TestScope
public class Stubs {

    @Bean
    NotificationRepository notificationRepository() {
        return Mockito.mock(NotificationRepository.class);
    }

}
