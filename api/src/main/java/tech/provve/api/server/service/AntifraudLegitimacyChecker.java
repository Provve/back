package tech.provve.api.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tech.provve.api.server.generated.dto.Observation;

import java.security.*;
import java.util.Base64;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AntifraudLegitimacyChecker {

    private final PublicKey publicKey;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public boolean check(String signature, String nonce, Observation observation) {
        return verify(msg(nonce, observation), publicKey, signature);
    }

    /**
     * @return JSON of message create by scheme <code>nonce || observation</code>
     */
    @SneakyThrows
    public String msg(String nonce, Observation observation) {
        var observationJson = objectMapper.writeValueAsString(observation);
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
    public boolean verify(String message, PublicKey pubKey, String signatureBase64)
            throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        byte[] decodedSignature = Base64.getDecoder()
                                        .decode(signatureBase64);
        var verifier = Signature.getInstance("Ed25519");
        byte[] binaryMessage = message.getBytes();

        verifier.initVerify(pubKey);
        verifier.update(binaryMessage);
        return verifier.verify(decodedSignature);
    }
}
