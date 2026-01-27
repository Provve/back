package tech.provve.accounts.service;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.Primary;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.TestScope;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@TestScope
@Factory
@InjectTest
class JwtIssuing_JwsParsingService_IntegrationTest {

    private static final String JWS_SECRET = "AxSOx5szljMmgLbnbhZ94rUz9CZcDqub68yPQtZhOzwlwfFe6ULpXx0eCIjv0mKgjB8JBBbXEBumi2XjhCwEeCnXPIjebrJfme5pHvp1EKcfYy37BLc8T4Lj5ljths0CcFeuntR3rUM0J2pIEPZz5mGvrOe65oqBThEzy8Gl3ROHVMdCIxHlp1104sOdeZB7Gv2ZUR1Env9CU0N92cQz2QPBYSZRnCaTlsyWXfrAdHZmNIcOtcT2OQ8nouMIz1cT";

    @Bean
    @Named("reset")
    public JWTAuth jwtReset() {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(JWS_SECRET)
                );
        return JWTAuth.create(Vertx.vertx(), options);
    }

    @Bean
    @Primary
    JwtIssuingService jwtIssuingService(@Named("reset") JWTAuth jwtReset) {
        return new JwtIssuingServiceImpl(null, jwtReset);
    }

    @Bean
    JwsParsingService jwsParsingService() {
        return new JwsParsingServiceImpl();
    }


    @Inject
    JwtIssuingService jwtIssuingService;

    @Inject
    JwsParsingService jwsParsingService;

    @Test
    void issueReset_valueGivenTo_parseReset_verifiedAndParsed() {
        // arrange
        var login = "whitelight";
        var resetToken = jwtIssuingService.issueReset(login);

        // act assert
        assertThatNoException()
                .isThrownBy(() -> jwsParsingService.parseReset(resetToken));
    }

    @Test
    void issueReset_valueGivenTo_parseReset_subjectTheSame() {
        // arrange
        var login = "whitelight";
        var resetToken = jwtIssuingService.issueReset(login);

        // act
        var parsed = jwsParsingService.parseReset(resetToken);
        var subject = ((String) parsed.get("sub"));

        // assert
        assertThat(subject)
                .isEqualTo(login);
    }

}