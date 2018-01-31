/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.Key;
/*     */ import java.security.Security;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ 
/*     */ public class AES128Util
/*     */ {
/*     */   public static final String KEY_ALGORITHM = "AES";
/*     */   public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
/*     */ 
/*     */   private static byte[] generateKey(String aesKey)
/*     */     throws Exception
/*     */   {
/*  26 */     Security.addProvider(new BouncyCastleProvider());
/*     */ 
/*  36 */     return aesKey.getBytes();
/*     */   }
/*     */ 
/*     */   private static AlgorithmParameters generateIV(String ivVal)
/*     */     throws Exception
/*     */   {
/*  45 */     byte[] iv = ivVal.getBytes();
/*  46 */     AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
/*  47 */     params.init(new IvParameterSpec(iv));
/*  48 */     return params;
/*     */   }
/*     */ 
/*     */   private static Key convertToKey(byte[] keyBytes) throws Exception
/*     */   {
/*  53 */     SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
/*  54 */     return secretKey;
/*     */   }
/*     */ 
/*     */   public static String encrypt(String plainText, String aesKey, String ivVal) throws Exception
/*     */   {
/*  59 */     byte[] data = plainText.getBytes();
/*  60 */     AlgorithmParameters iv = generateIV(ivVal);
/*  61 */     byte[] keyBytes = generateKey(aesKey);
/*     */ 
/*  63 */     Key key = convertToKey(keyBytes);
/*  64 */     Security.addProvider(new BouncyCastleProvider());
/*  65 */     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
/*     */ 
/*  67 */     cipher.init(1, key, iv);
/*  68 */     byte[] encryptData = cipher.doFinal(data);
/*  69 */     return bytesToHexString(encryptData);
/*     */   }
/*     */ 
/*     */   public static String decrypt(String encryptedStr, String aesKey, String ivVal)
/*     */   {
/*     */     try {
/*  75 */       byte[] encryptedData = hexStringToByte(encryptedStr);
/*  76 */       byte[] keyBytes = generateKey(aesKey);
/*  77 */       Key key = convertToKey(keyBytes);
/*  78 */       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
/*  79 */       AlgorithmParameters iv = generateIV(ivVal);
/*     */ 
/*  81 */       cipher.init(2, key, iv);
/*     */ 
/*  83 */       byte[] decryptData = cipher.doFinal(encryptedData);
/*  84 */       return new String(decryptData);
/*     */     } catch (Exception e) {
/*     */     }
/*  87 */     return "";
/*     */   }
/*     */ 
/*     */   private static byte[] hexStringToByte(String hex)
/*     */   {
/*  99 */     int len = hex.length() / 2;
/* 100 */     byte[] result = new byte[len];
/* 101 */     char[] achar = hex.toCharArray();
/* 102 */     for (int i = 0; i < len; i++) {
/* 103 */       int pos = i * 2;
/* 104 */       result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[(pos + 1)]));
/*     */     }
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */   private static byte toByte(char c) {
/* 110 */     byte b = (byte)"0123456789abcdef".indexOf(c);
/* 111 */     return b;
/*     */   }
/*     */ 
/*     */   private static final String bytesToHexString(byte[] bArray)
/*     */   {
/* 121 */     StringBuffer sb = new StringBuffer(bArray.length);
/*     */ 
/* 123 */     for (int i = 0; i < bArray.length; i++) {
/* 124 */       String sTemp = Integer.toHexString(0xFF & bArray[i]);
/* 125 */       if (sTemp.length() < 2)
/* 126 */         sb.append(0);
/* 127 */       sb.append(sTemp.toLowerCase());
/*     */     }
/* 129 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 134 */     String plainTextString = "df7cdce019bc9ef9aeff6bec0be63011";
/* 135 */     System.out.println("明文 : " + plainTextString);
/* 136 */     String aesKey = "S9u978Q31NGPGc5H";
/* 137 */     String ivVal = "WrIppBnEpnq5oQkD";
/*     */     try
/*     */     {
/* 140 */       String encryptedData = encrypt(plainTextString, aesKey, ivVal);
/*     */ 
/* 142 */       System.out.println("加密后的数据 : ");
/* 143 */       System.out.println(encryptedData);
/* 144 */       System.out.println();
/* 145 */       String data = decrypt(encryptedData, aesKey, ivVal);
/* 146 */       System.out.println("解密得到的数据 : " + data);
/*     */     } catch (Exception e) {
/* 148 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.AES128Util
 * JD-Core Version:    0.6.0
 */