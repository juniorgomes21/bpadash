package br.com.bpadash.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

public class EncryptionService {

    public static String encrypt(String data) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));
        return encryptor.encrypt(data);
    }

    public static String decrypt(String encryptedData) {
        Dotenv dotenv = Dotenv.load();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(dotenv.get("ENCODE_KEY"));
        encryptor.setAlgorithm(dotenv.get("ENCODE_ALGORITHM"));
        return encryptor.decrypt(encryptedData);
    }
}
