package com.auro.scholr.util.encryption;

import android.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class Cryptor {
    private Cipher cipher;
    private String secretKey = "TecHtreEiTSYsteM";
    private String iv = "TecHtreEiTSYsteM";
    private String CIPHER_MODE = "AES/CBC/PKCS5PADDING";//AES/CBC/PKCS5PADDING

    private SecretKey keySpec;
    private IvParameterSpec ivSpec;
    private Charset CHARSET = Charset.forName("UTF8");

    public Cryptor() throws CryptingException {

        //keySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
        //ivSpec = new IvParameterSpec(iv.getBytes(CHARSET));
        try {
            keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES"); // Create a new SecretKeySpec for the specified key data and algorithm name.
            ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)); // Create a new IvParameterSpec instance with the bytes from the specified buffer iv used as initialization vector.
            cipher = Cipher.getInstance(CIPHER_MODE);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
            throw new SecurityException(e);
        }
    }

    public final String decrypt(String input) throws CryptingException {

        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return  new String(cipher.doFinal(Base64.decode(input.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)));
        } catch (IllegalBlockSizeException|BadPaddingException|InvalidAlgorithmParameterException|InvalidKeyException e) {
            throw new SecurityException(e);
        }


    }

    /**
     * @param input Any String to be encrypted
     * @return An "AES/CFB8/NoPadding" encrypted String
     * @throws CryptingException
     */
    public final String encrypt(String input){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return new String(Base64.encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)),Base64.DEFAULT)).trim();
        } catch (InvalidKeyException|InvalidAlgorithmParameterException|IllegalBlockSizeException|BadPaddingException e) {
            throw new SecurityException(e);
        }

    }

    class CryptingException extends RuntimeException {

        private static final long serialVersionUID = 7123322995084333687L;

        public CryptingException() {
            super();
        }

        public CryptingException(String message) {
            super(message);
        }
    }
}
