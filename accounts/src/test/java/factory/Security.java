package factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import tech.provve.accounts.service.JwtIssuingService;

@Factory
@TestScope
public class Security {

    @Bean
    public JwtIssuingService stub() {
        return new JwtIssuingService() {
            @Override
            public String issueAuth(String login, boolean premium) {
                return "";
            }

            @Override
            public String issueReset(String login) {
                return "";
            }
        };
    }

}
