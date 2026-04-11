package com.example.demo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String ALGO = "AES";

    // 🔐 Generate 16-byte key from password
    private static SecretKeySpec getKey(String password) {
        byte[] key = new byte[16];
        byte[] passwordBytes = password.getBytes();

        for (int i = 0; i < 16; i++) {
            key[i] = (i < passwordBytes.length) ? passwordBytes[i] : 0;
        }

        return new SecretKeySpec(key, ALGO);
    }

    // 🔐 Encrypt
    public static String encrypt(String data, String password) throws Exception {
        SecretKeySpec key = getKey(password);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 🔓 Decrypt
    public static String decrypt(String encryptedData, String password) throws Exception {
        SecretKeySpec key = getKey(password);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decoded = Base64.getDecoder().decode(encryptedData);

        byte[] decrypted = cipher.doFinal(decoded);

        return new String(decrypted);
    }
}