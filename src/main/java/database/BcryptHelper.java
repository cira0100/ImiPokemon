package database;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BcryptHelper {
	private static int difficulty=10;

    public static String hashPasword(String password){
        String passwordH=BCrypt.withDefaults().hashToString(10, password.toCharArray());
        return passwordH;
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash");

        password_verified = BCrypt.verifyer().verify(password_plaintext.toCharArray(), stored_hash).verified;

        return password_verified;
    }
}
