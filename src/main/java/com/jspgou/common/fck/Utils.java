/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class Utils
/*     */ {
/*     */   public static final String EMPTY_STRING = "";
/*     */ 
/*     */   public static Set<String> getSet(String stringList, String delimiter)
/*     */   {
/*  30 */     if (isEmpty(delimiter))
/*  31 */       throw new IllegalArgumentException(
/*  32 */         "Argument 'delimiter' shouldn't be empty!");
/*  33 */     if (isEmpty(stringList)) {
/*  34 */       return new HashSet();
/*     */     }
/*  36 */     Set set = new HashSet();
/*  37 */     StringTokenizer st = new StringTokenizer(stringList, delimiter);
/*  38 */     while (st.hasMoreTokens()) {
/*  39 */       String tmp = st.nextToken();
/*  40 */       if (isNotEmpty(tmp))
/*  41 */         set.add(tmp.toLowerCase());
/*     */     }
/*  43 */     return set;
/*     */   }
/*     */ 
/*     */   public static Set<String> getSet(String stringList)
/*     */   {
/*  58 */     return getSet(stringList, "|");
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(String str)
/*     */   {
/*  70 */     return (str == null) || (str.length() == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isNotEmpty(String str)
/*     */   {
/*  82 */     return !isEmpty(str);
/*     */   }
/*     */ 
/*     */   public static boolean isBlank(String str)
/*     */   {
/*  96 */     if (isEmpty(str)) {
/*  97 */       return true;
/*     */     }
/*  99 */     for (char c : str.toCharArray()) {
/* 100 */       if (!Character.isWhitespace(c)) {
/* 101 */         return false;
/*     */       }
/*     */     }
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isNotBlank(String str)
/*     */   {
/* 116 */     return !isBlank(str);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.Utils
 * JD-Core Version:    0.6.0
 */