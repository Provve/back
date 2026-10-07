package tech.provve.accounts;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Constants;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Сервис хеширования паролей алгоритмом Argon2, вариант d
 */

public class PasswordHashing {

    private static final Argon2 ARGON_2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2d,
            64,
            Argon2Constants.DEFAULT_HASH_LENGTH
    );

    /**
     * @return hash
     */
    public static String hash(String password) {
        char[] passwordArray = password.toCharArray();
        return ARGON_2.hash(30, 65536, 1, passwordArray);
    }

    public static boolean verify(String password, String previousHash) {
        return ARGON_2.verify(previousHash, password.toCharArray());
    }

}
