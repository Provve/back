package tech.provve.accounts.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Constants;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.inject.Singleton;

/**
 * Сервис хеширования паролей алгоритмом Argon2, вариант d
 */
@Singleton
public class PasswordHashingService {

    private final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2d,
            64,
            Argon2Constants.DEFAULT_HASH_LENGTH
    );

    /**
     * @return hash
     */
    public String hash(String password) {
        char[] passwordArray = password.toCharArray();
        return argon2.hash(30, 65536, 1, passwordArray);
    }

    public boolean verify(String password, String previousHash) {
        var hash = hash(password);
        return previousHash.equals(hash);
    }

}
