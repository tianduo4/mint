/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Num62
/*     */ {
/*  11 */   public static final char[] N62_CHARS = { '0', '1', '2', '3', '4', '5', '6', 
/*  12 */     '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
/*  13 */     'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 
/*  14 */     'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
/*  15 */     'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
/*  16 */     'x', 'y', 'z' };
/*     */ 
/*  20 */   public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6', 
/*  21 */     '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
/*  22 */     'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
/*  23 */     'x', 'y', 'z' };
/*     */   public static final int LONG_N36_LEN = 13;
/*     */   public static final int LONG_N62_LEN = 11;
/*     */ 
/*     */   private static StringBuilder longToNBuf(long l, char[] chars)
/*     */   {
/*  41 */     int upgrade = chars.length;
/*  42 */     StringBuilder result = new StringBuilder();
/*     */ 
/*  44 */     while (l > 0L) {
/*  45 */       int last = (int)(l % upgrade);
/*  46 */       result.append(chars[last]);
/*  47 */       l /= upgrade;
/*     */     }
/*  49 */     return result;
/*     */   }
/*     */ 
/*     */   public static String longToN62(long l)
/*     */   {
/*  59 */     return longToNBuf(l, N62_CHARS).reverse().toString();
/*     */   }
/*     */ 
/*     */   public static String longToN36(long l)
/*     */   {
/*  69 */     return longToNBuf(l, N36_CHARS).reverse().toString();
/*     */   }
/*     */ 
/*     */   public static String longToN62(long l, int length)
/*     */   {
/*  81 */     StringBuilder sb = longToNBuf(l, N62_CHARS);
/*  82 */     for (int i = sb.length(); i < length; i++) {
/*  83 */       sb.append('0');
/*     */     }
/*  85 */     return sb.reverse().toString();
/*     */   }
/*     */ 
/*     */   public static String longToN36(long l, int length)
/*     */   {
/*  97 */     StringBuilder sb = longToNBuf(l, N36_CHARS);
/*  98 */     for (int i = sb.length(); i < length; i++) {
/*  99 */       sb.append('0');
/*     */     }
/* 101 */     return sb.reverse().toString();
/*     */   }
/*     */ 
/*     */   public static long n62ToLong(String n62)
/*     */   {
/* 111 */     return nToLong(n62, N62_CHARS);
/*     */   }
/*     */ 
/*     */   public static long n36ToLong(String n36)
/*     */   {
/* 121 */     return nToLong(n36, N36_CHARS);
/*     */   }
/*     */ 
/*     */   private static long nToLong(String s, char[] chars) {
/* 125 */     char[] nc = s.toCharArray();
/* 126 */     long result = 0L;
/* 127 */     long pow = 1L;
/* 128 */     for (int i = nc.length - 1; i >= 0; pow *= chars.length) {
/* 129 */       int n = findNIndex(nc[i], chars);
/* 130 */       result += n * pow;
/*     */ 
/* 128 */       i--;
/*     */     }
/*     */ 
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */   private static int findNIndex(char c, char[] chars) {
/* 136 */     for (int i = 0; i < chars.length; i++) {
/* 137 */       if (c == chars[i]) {
/* 138 */         return i;
/*     */       }
/*     */     }
/* 141 */     throw new RuntimeException("N62(N36)非法字符：" + c);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 145 */     System.out.println(longToN62(9223372036854775807L));
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.Num62
 * JD-Core Version:    0.6.0
 */