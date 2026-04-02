package tech.provve.api.server.service;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.provve.api.server.generated.dto.Observation;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@InjectTest
class AntifraudLegitimacyCheckerTest {

    @Inject
    AntifraudLegitimacyChecker antifraudLegitimacyChecker;

    @Test
    void check_signiedByProvveIssuedPrivateKey_true() throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        // arrange
        var nonce = "1";
        var observation = new Observation(true, "q");

        var ourPrivkey = "MC4CAQAwBQYDK2VwBCIEIG7xB7D8I/hZvWEbHzGseXTzG9ANQaPeW65TfM53BqOz";
        var ourPubkey = "MCowBQYDK2VwAyEA9X2DL0XqBKMEhKGzvgg0yWN3wquwa4gSUyeMvzRVnf4=";
        var keypair = genKeyPair(ourPubkey, ourPrivkey);
        String signature = sign(antifraudLegitimacyChecker.msg(nonce, observation)
                                                          .getBytes(), keypair.getPrivate());

        // act
        boolean legit = antifraudLegitimacyChecker.check(signature, nonce, observation);

        // assert
        assertTrue(legit);
    }

    @Test
    void check_signiedByHackerPrivateKey_false() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        // arrange
        var nonce = "1";
        var observation = new Observation(true, "q");

        var hackerPrivkey = "MC4CAQAwBQYDK2VwBCIEIMW8Z+8mDeBm4F7GjhlmplfrxcP8gWx9rCkEsm6l3RYl";
        var ourPubkey = "MCowBQYDK2VwAyEA9X2DL0XqBKMEhKGzvgg0yWN3wquwa4gSUyeMvzRVnf4=";
        var keypair = genKeyPair(ourPubkey, hackerPrivkey);
        String signature = sign(antifraudLegitimacyChecker.msg(nonce, observation)
                                                          .getBytes(), keypair.getPrivate());

        // act
        boolean legit = antifraudLegitimacyChecker.check(signature, nonce, observation);

        // assert
        assertFalse(legit);
    }

    static KeyPair genKeyPair(String pubBase64, String privBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
        byte[] publicKeyBytes = Base64.getDecoder()
                                      .decode(pubBase64);
        var pubSpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(pubSpec);

        byte[] priBytes = Base64.getDecoder()
                                .decode(privBase64);
        var privSpec = new PKCS8EncodedKeySpec(priBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privSpec);

        return new KeyPair(publicKey, privateKey);
    }

    static String sign(byte[] message, PrivateKey privKey) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        Signature signer = Signature.getInstance("Ed25519");
        signer.initSign(privKey);
        signer.update(message);
        byte[] signatureBytes = signer.sign();
        return Base64.getEncoder()
                     .encodeToString(signatureBytes);
    }

}