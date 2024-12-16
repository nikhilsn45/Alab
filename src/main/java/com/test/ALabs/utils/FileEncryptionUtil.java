package com.test.ALabs.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class FileEncryptionUtil {
    private static final String ALGORITHM = "AES";

    public static void encryptFile(File inputFile, File outputFile, String passcode) throws Exception {

        File oFile = new File(outputFile.getPath());
        File Parent = oFile.getParentFile();
        if(!Parent.exists()){
            Parent.mkdirs();
        }
        byte[] keyBytes = Arrays.copyOf(passcode.getBytes(StandardCharsets.UTF_8),16);


        SecretKey key = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] inputBytes = new byte[(int) inputFile.length()];
                fis.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);
                fos.write(outputBytes);
            }
    }

    public static void decryptFile(File inputFile, File outputFile, String passcode) throws Exception {

        byte[] keyBytes = Arrays.copyOf(passcode.getBytes(StandardCharsets.UTF_8),16);

        SecretKey key = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            fis.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            fos.write(outputBytes);
        }
    }
}