package tech.provve.skill.service;

import java.security.*;
import java.util.Base64;

public class AntifraudLegitChecker {

    public boolean legit(String signature, String message, String nonce) {
        throw new RuntimeException("impl me");
    }

    /**
     * Verifies the signature against the original message using the public key.
     *
     * @param message         The original message.
     * @param pubKey          The public key associated with the private key used for signing.
     * @param signatureBase64 The base64-encoded signature string.
     * @return True if the signature is valid, false otherwise.
     */
    private boolean verify(byte[] message, PublicKey pubKey, String signatureBase64) {
        try {
            byte[] decodedSignature = Base64.getDecoder()
                                            .decode(signatureBase64);
            Signature verifier = Signature.getInstance("Ed25519");
            verifier.initVerify(pubKey);
            verifier.update(message);
            return verifier.verify(decodedSignature);
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException _) {
            return false;
        }
    }
}
