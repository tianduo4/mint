/*     */ package com.jspgou.common.security.encoder;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ 
/*     */ public class Md5PwdEncoder
/*     */   implements PwdEncoder
/*     */ {
/*     */   private String defaultSalt;
/*     */ 
/*     */   public String encodePassword(String rawPass)
/*     */   {
/*  15 */     return encodePassword(rawPass, this.defaultSalt);
/*     */   }
/*     */ 
/*     */   public String encodePassword(String rawPass, String salt)
/*     */   {
/*  20 */     String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
/*  21 */     MessageDigest messageDigest = getMessageDigest();
/*     */     try
/*     */     {
/*  24 */       digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*     */       byte[] digest;
/*  26 */       throw new IllegalStateException("UTF-8 not supported!");
/*     */     }
/*     */     byte[] digest;
/*  28 */     return new String(Hex.encodeHex(digest));
/*     */   }
/*     */ 
/*     */   public boolean isPasswordValid(String encPass, String rawPass)
/*     */   {
/*  33 */     return isPasswordValid(encPass, rawPass, this.defaultSalt);
/*     */   }
/*     */ 
/*     */   public boolean isPasswordValid(String encPass, String rawPass, String salt)
/*     */   {
/*  38 */     if (encPass == null) {
/*  39 */       return false;
/*     */     }
/*  41 */     String pass2 = encodePassword(rawPass, salt);
/*  42 */     return encPass.equals(pass2);
/*     */   }
/*     */ 
/*     */   protected final MessageDigest getMessageDigest() {
/*  46 */     String algorithm = "MD5";
/*     */     try {
/*  48 */       return MessageDigest.getInstance(algorithm); } catch (NoSuchAlgorithmException e) {
/*     */     }
/*  50 */     throw new IllegalArgumentException("No such algorithm [" + 
/*  51 */       algorithm + "]");
/*     */   }
/*     */ 
/*     */   protected String mergePasswordAndSalt(String password, Object salt, boolean strict)
/*     */   {
/*  76 */     if (password == null) {
/*  77 */       password = "";
/*     */     }
/*  79 */     if ((strict) && (salt != null) && (
/*  80 */       (salt.toString().lastIndexOf("{") != -1) || 
/*  81 */       (salt.toString().lastIndexOf("}") != -1))) {
/*  82 */       throw new IllegalArgumentException(
/*  83 */         "Cannot use { or } in salt.toString()");
/*     */     }
/*     */ 
/*  86 */     if ((salt == null) || ("".equals(salt))) {
/*  87 */       return password;
/*     */     }
/*  89 */     return password + "{" + salt.toString() + "}";
/*     */   }
/*     */ 
/*     */   public String getDefaultSalt()
/*     */   {
/* 104 */     return this.defaultSalt;
/*     */   }
/*     */ 
/*     */   public void setDefaultSalt(String defaultSalt)
/*     */   {
/* 113 */     this.defaultSalt = defaultSalt;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.encoder.Md5PwdEncoder
 * JD-Core Version:    0.6.0
 */