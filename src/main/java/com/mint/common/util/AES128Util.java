package com.mint.common.util;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES128Util {
    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    private static byte[] generateKey(String aesKey)
            throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        return aesKey.getBytes();
    }

    private static AlgorithmParameters generateIV(String ivVal)
            throws Exception {
        byte[] iv = ivVal.getBytes();
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

    private static Key convertToKey(byte[] keyBytes) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        return secretKey;
    }

    public static String encrypt(String plainText, String aesKey, String ivVal) throws Exception {
        byte[] data = plainText.getBytes();
        AlgorithmParameters iv = generateIV(ivVal);
        byte[] keyBytes = generateKey(aesKey);

        Key key = convertToKey(keyBytes);
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        cipher.init(1, key, iv);
        byte[] encryptData = cipher.doFinal(data);
        return bytesToHexString(encryptData);
    }

    public static String decrypt(String encryptedStr, String aesKey, String ivVal) {
        try {
            byte[] encryptedData = hexStringToByte(encryptedStr);
            byte[] keyBytes = generateKey(aesKey);
            Key key = convertToKey(keyBytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            AlgorithmParameters iv = generateIV(ivVal);

            cipher.init(2, key, iv);

            byte[] decryptData = cipher.doFinal(encryptedData);
            return new String(decryptData);
        } catch (Exception e) {
        }
        return "";
    }

    private static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[(pos + 1)]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    private static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);

        for (int i = 0; i < bArray.length; i++) {
            String sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toLowerCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String plainTextString = "df7cdce019bc9ef9aeff6bec0be63011";
        System.out.println("明文 : " + plainTextString);
        String aesKey = "S9u978Q31NGPGc5H";
        String ivVal = "WrIppBnEpnq5oQkD";
        try {
            String encryptedData = encrypt(plainTextString, aesKey, ivVal);

            System.out.println("加密后的数据 : ");
            System.out.println(encryptedData);
            System.out.println();
            String data = decrypt(encryptedData, aesKey, ivVal);
            System.out.println("解密得到的数据 : " + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

