/*     */ package com.jspgou.common.ueditor;
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
/*  31 */     if (isEmpty(delimiter))
/*  32 */       throw new IllegalArgumentException(
/*  33 */         "Argument 'delimiter' shouldn't be empty!");
/*  34 */     if (isEmpty(stringList)) {
/*  35 */       return new HashSet();
/*     */     }
/*  37 */     Set set = new HashSet();
/*  38 */     StringTokenizer st = new StringTokenizer(stringList, delimiter);
/*  39 */     while (st.hasMoreTokens()) {
/*  40 */       String tmp = st.nextToken();
/*  41 */       if (isNotEmpty(tmp))
/*  42 */         set.add(tmp.toLowerCase());
/*     */     }
/*  44 */     return set;
/*     */   }
/*     */ 
/*     */   public static Set<String> getSet(String stringList)
/*     */   {
/*  59 */     return getSet(stringList, "|");
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(String str)
/*     */   {
/*  71 */     return (str == null) || (str.length() == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isNotEmpty(String str)
/*     */   {
/*  83 */     return !isEmpty(str);
/*     */   }
/*     */ 
/*     */   public static boolean isBlank(String str)
/*     */   {
/*  97 */     if (isEmpty(str)) {
/*  98 */       return true;
/*     */     }
/* 100 */     for (char c : str.toCharArray()) {
/* 101 */       if (!Character.isWhitespace(c)) {
/* 102 */         return false;
/*     */       }
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isNotBlank(String str)
/*     */   {
/* 117 */     return !isBlank(str);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.ueditor.Utils
 * JD-Core Version:    0.6.0
 */