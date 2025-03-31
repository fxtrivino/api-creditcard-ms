package com.bci.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.bci.exception.InvalidDecryptDataException;
import com.bci.exception.InvalidEncryptDataException;
import com.bci.exception.InvalidGenerationKeyAESException;

import java.util.Base64;

public class EncryptionUtil {
    private static final String AES = "AES";
    private static final SecretKey SECRET_KEY = generateKey();

    private EncryptionUtil() {}
    
    private static SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (Exception e) {
            throw new InvalidGenerationKeyAESException("Error generando la clave AES " + e.getMessage());
        }
    }

    public static String encrypt(String data) {
	    	try {
	        Cipher cipher = Cipher.getInstance(AES);
	        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    	} catch (Exception e) {
            throw new InvalidEncryptDataException("Error en encriptacion " + e.getMessage());
        } 
    }

    public static String decrypt(String encryptedData) {
	    try {
	        Cipher cipher = Cipher.getInstance(AES);
	        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
    	} catch (Exception e) {
            throw new InvalidDecryptDataException("Error en desencriptacion " + e.getMessage());
        }
    }
}

