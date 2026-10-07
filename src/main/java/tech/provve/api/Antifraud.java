package tech.provve.api;

import io.avaje.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import lombok.SneakyThrows;
import tech.provve.api.generated.dto.Observation;
import tech.provve.util.Jackson;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Antifraud {
    public static final PublicKey PUBLIC_KEY = createPublicKey();
    public static final JWTAuth JWT_AUTH = createJwtAuth();

    public static PublicKey createPublicKey() {
        String key = io.avaje.config.Config.get("antifraud.legit-check.pubkey");
        try {
            byte[] decodedKey = Base64.getDecoder()
                                      .decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Ed25519 public key", e);
        }
    }

    @SneakyThrows
    public static boolean check(String signature, String nonce, Observation observation) {
        return verify(msg(nonce, observation), PUBLIC_KEY, signature);
    }

    /**
     * @return JSON of message create by scheme <code>nonce || observation</code>
     */
    @SneakyThrows
    public static String msg(String nonce, Observation observation) {
        var observationJson = Jackson.json.writeValueAsString(observation);
        return nonce + observationJson;
    }

    /**
     * Verifies the signature against the original message using the public key.
     *
     * @param message         The original message.
     * @param pubKey          The public key associated with the private key used for signing.
     * @param signatureBase64 The base64-encoded signature string.
     * @return True if the signature is valid, false otherwise.
     */
    public static boolean verify(String message, PublicKey pubKey, String signatureBase64)
            throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        byte[] decodedSignature = Base64.getDecoder()
                                        .decode(signatureBase64);
        var verifier = Signature.getInstance("Ed25519");
        byte[] binaryMessage = message.getBytes();

        verifier.initVerify(pubKey);
        verifier.update(binaryMessage);
        return verifier.verify(decodedSignature);
    }

    public static JWTAuth createJwtAuth() {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("antifraud.trust-token.secret"))
                );
        return JWTAuth.create(ApiServer.vertx, options);
    }

    /**
     * @return JWT token for trusted Antifraud
     */
    public static String trust(String examinee) {
        int expirationSeconds = Config.getInt("antifraud.trust-token.expires-in-seconds");
        return JWT_AUTH.generateToken(
                new JsonObject(),
                new JWTOptions().setSubject(examinee)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }
}
