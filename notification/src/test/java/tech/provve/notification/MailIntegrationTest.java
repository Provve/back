package tech.provve.notification;

import ch.martinelli.oss.testcontainers.mailpit.MailpitContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MailIntegrationTest {

    @Container
    public static MailpitContainer mailpit = new MailpitContainer();

}
