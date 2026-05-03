package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.JWTAuthHandler;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Factory
public class Security {

    public static final String JWT_PROVIDER_AUTH = "auth";
    public static final String JWT_HANDLER_AUTH = "auth-handler";

    public static final String JWT_PROVIDER_RESET = "reset";
    public static final String JWT_HANDLER_RESET = "reset-handler";

    public static final String JWT_PROVIDER_TRUST = "trust";
    public static final String JWT_HANDLER_TRUST = "trust-handler";

    @Bean
    @Named(JWT_HANDLER_AUTH)
    public JWTAuthHandler jwtAuthHandler(@Named(JWT_PROVIDER_AUTH) JWTAuth jwtAuth) {
        return JWTAuthHandler.create(jwtAuth);
    }

    @Bean
    @Named(JWT_PROVIDER_AUTH)
    public JWTAuth jwtAuth(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.auth.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

    @Bean
    @Named(JWT_HANDLER_TRUST)
    public JWTAuthHandler jwtTrustHandler(@Named(JWT_PROVIDER_TRUST) JWTAuth jwtReset) {
        return JWTAuthHandler.create(jwtReset);
    }

    @Bean
    @Named(JWT_PROVIDER_TRUST)
    public JWTAuth jwtTrust(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("antifraud.trust-token.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

    @Bean
    @Named(JWT_HANDLER_RESET)
    public JWTAuthHandler jwtResetHandler(@Named(JWT_PROVIDER_RESET) JWTAuth jwtReset) {
        return JWTAuthHandler.create(jwtReset);
    }

    @Bean
    @Named(JWT_PROVIDER_RESET)
    public JWTAuth jwtReset(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.reset.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

    @Bean
    @SuppressWarnings("all")
    public Provider<PublicKey> publicKeyProvider() {
        // Avaje-inject увидит только создание этого фабричного объекта.
        // Ошибки компиляции не возникнет, так как метод create() не вызывается здесь.
        String key = Config.get("antifraud.legit-check.pubkey");
        final Ed25519PublicKeyFactory factory = new Ed25519PublicKeyFactory(key);

        return new Provider<>() {
            @Override
            @SneakyThrows
            public PublicKey get() {
                return factory.create();
            }
        };
    }


    public static class Ed25519PublicKeyFactory {

        /**
         * Ключ в формате base64 без PEM-заголовков.
         */
        private final String base64PublicKey;

        public Ed25519PublicKeyFactory(String base64PublicKey) {
            this.base64PublicKey = base64PublicKey;
        }

        /**
         * Метод создает и возвращает PublicKey.
         * Логика здесь будет выполняться в рантайме, что обходит проблему компиляции avaje-inject.
         * Без этого падает ошибка this is a preview feature, причиной которой импорт java.security.DEREncodable; в генерируремом модуле Avaje.
         */
        @SneakyThrows
        public PublicKey create() {
            byte[] decodedKey = Base64.getDecoder()
                                      .decode(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);

            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");

            return keyFactory.generatePublic(keySpec);
        }
    }
}
