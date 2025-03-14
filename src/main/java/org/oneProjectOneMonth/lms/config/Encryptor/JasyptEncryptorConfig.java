package org.oneProjectOneMonth.lms.config.Encryptor;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptEncryptorConfig {
    private String password = "LMS-SYSTEM";

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password); // encryptor's private key
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    public String encryptMessage(String plainText) {
        var encryptor = stringEncryptor();
        return encryptor.encrypt(plainText);
    }

    public String decryptMessage(String encryptedText) {
        var encryptor = stringEncryptor();
        return encryptor.decrypt(encryptedText);
    }

    public static void main(String[] args) {
        JasyptEncryptorConfig encryptConfig = new JasyptEncryptorConfig();
        String plainText = "admin123";
        String encryptedString = encryptConfig.encryptMessage(plainText);
        System.out.println("Encrypted String : " + encryptedString);

        String encryptText = encryptedString;
        String decryptString = encryptConfig.decryptMessage(encryptText);
        System.out.println("Decrypted String : " + decryptString);

    }
}
