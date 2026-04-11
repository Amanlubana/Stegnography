package com.example.demo.Service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class CryptoService {

    // 🔑 Generate AES key from password
    private SecretKeySpec getKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes());

        // Use first 16 bytes (AES-128)
        byte[] keyBytes = new byte[16];
        System.arraycopy(key, 0, keyBytes, 0, 16);

        return new SecretKeySpec(keyBytes, "AES");
    }

    // 🔐 Encrypt using password
    public String encrypt(String data, String password) throws Exception {
        SecretKeySpec key = getKey(password);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 🔓 Decrypt using password
    public String decrypt(String data, String password) throws Exception {
        SecretKeySpec key = getKey(password);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decoded = Base64.getDecoder().decode(data);
        return new String(cipher.doFinal(decoded));
    }
}
