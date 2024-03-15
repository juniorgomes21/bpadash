package br.com.bpadash.api.adm.AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {

    private static final String X_X_X = ""; // Chave fixa

//    public static void main(String[] args) throws Exception {
//        // Carregar a chave fixa
//
//
//        SecretKey secretKey = generateAESKey();
//
//        // Codificar a chave em Base64 para armazenamento
//        String encodedKey = encodeAESKey(secretKey);
//        System.out.println("Chave fixa em Base64: " + encodedKey);
//    }

    public static void main(String[] args) throws Exception {
        // Gerar um vetor de inicialização (IV) aleatório
        byte[] iv = generateAESIV();
        System.out.println("IV aleatório em Base64: " + encodeAESIV(iv));
    }

    public static byte[] generateAESIV() {
        byte[] iv = new byte[16]; // IV de 16 bytes para AES
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }

    public static String encodeAESIV(byte[] iv) {
        return Base64.getEncoder().encodeToString(iv);
    }

    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String encodeAESKey(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey loadAESKey() {
        byte[] decodedKey = Base64.getDecoder().decode(X_X_X);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String encryptAES(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAES(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
