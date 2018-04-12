/*
 Class source:
               https://www.mkyong.com/java/java-symmetric-key-cryptography-example/
 */

package com.github.maciejkrolpl.controller;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncryptionDecryption {

    private final String fileName;

    private SecretKeySpec secretKey;
    private Cipher cipher;

    FileEncryptionDecryption(String secret, int length, String algorithm, String fileName)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] key = new byte[length];
        key = fixSecret(secret, length);
        this.secretKey = new SecretKeySpec(key, algorithm);
        this.cipher = Cipher.getInstance(algorithm);
        this.fileName = fileName;
    }


    private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
        if (s.length() < length) {
            int missingLength = length - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, length).getBytes("UTF-8");
    }

    public void encryptFile(File f)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Encrypting file: " + f.getName());
        if (decrypted(f)) {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            this.writeToFile(f);
        } else {
            throw new InvalidKeyException("Cannot encrtypt encrypted file!");
        }
    }

    private boolean decrypted(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String firstLine = br.readLine();
        return (firstLine.equals("ODSZYFROWANY"));

    }

    public void decryptFile(File f)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Decrypting file: " + f.getName());
        if (!decrypted(f)) {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            this.writeToFile(f);
        } else {
            throw new InvalidKeyException("Cannot decrypt decrypted file!");
        }
    }

    private void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream in = new FileInputStream(f);
        byte[] input = new byte[(int) f.length()];
        in.read(input);

        FileOutputStream out = new FileOutputStream(f);
        byte[] output = this.cipher.doFinal(input);
        out.write(output);

        out.flush();
        out.close();
        in.close();
    }
}
