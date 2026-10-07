package tech.provve.util;

import io.avaje.config.Config;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.JWTAuthHandler;
import tech.provve.api.ApiServer;

public class JWT {

    public static final JWTAuth JWT_AUTH = createJwtAuth("security.jwt.auth.secret");
    public static final JWTAuth JWT_RESET = createJwtAuth("security.jwt.reset.secret");
    public static final JWTAuthHandler JWT_RESET_HANDLER = JWTAuthHandler.create(JWT_RESET);
    public static final JWTAuthHandler JWT_TRUST_HANDLER = JWTAuthHandler.create(JWT_RESET);
    public static final JWTAuthHandler JWT_AUTH_HANDLER = JWTAuthHandler.create(JWT_AUTH);


    private static JWTAuth createJwtAuth(String secretKey) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get(secretKey))
                );
        return JWTAuth.create(ApiServer.vertx, options);
    }
}
