package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;

@Factory
public class Email {

    @Bean
    public Mailer mailer() {
        var host = Config.get("mail.host");
        int port = Config.getInt("mail.port");
        var username = Config.get("mail.username");
        var password = Config.get("mail.password");

        return MailerBuilder
                .withSMTPServer(host, port, username, password)
                .buildMailer();
    }

}
