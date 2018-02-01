/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class CnToSpell
/*     */ {
/*     */   private char[] chartable;
/*     */   private char[] alphatable;
/*     */   private int[] table;
/*     */ 
/*     */   public CnToSpell()
/*     */   {
/*  11 */     this.chartable = 
/*  12 */       new char[] { 
/*  13 */       '啊', 33453, '擦', '搭', 34558, '发', '噶', '哈', '哈', 
/*  14 */       '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', 
/*  15 */       '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座' };
/*     */ 
/*  18 */     this.alphatable = 
/*  19 */       new char[] { 
/*  20 */       'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
/*  21 */       'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
/*  22 */       's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
/*     */ 
/*  25 */     this.table = new int[27];
/*     */ 
/*  28 */     for (int i = 0; i < 27; i++)
/*  29 */       this.table[i] = gbValue(this.chartable[i]);
/*     */   }
/*     */ 
/*     */   public char charAlpha(char ch)
/*     */   {
/*  38 */     if ((ch >= 'a') && (ch <= 'z'))
/*     */     {
/*  40 */       return ch;
/*  41 */     }if ((ch >= 'A') && (ch <= 'Z'))
/*  42 */       return (char)(ch - 'A' + 97);
/*  43 */     if ((ch >= '0') && (ch <= '9'))
/*  44 */       return ch;
/*  45 */     int gb = gbValue(ch);
/*  46 */     if (gb < this.table[0]) {
/*  47 */       return '0';
/*     */     }
/*  49 */     for (int i = 0; i < 26; i++)
/*  50 */       if (match(i, gb))
/*     */         break;
              int i=0; //TODO
/*  52 */     if (i >= 26) {
/*  53 */       return '0';
/*     */     }
/*  55 */     return this.alphatable[i];
/*     */   }
/*     */ 
/*     */   public String getBeginCharacter(String SourceStr)
/*     */   {
/*  61 */     String Result = "";
/*  62 */     int StrLength = SourceStr.length();
/*     */     try
/*     */     {
/*  65 */       for (int i = 0; i < StrLength; i++)
/*  66 */         Result = Result + charAlpha(SourceStr.charAt(i));
/*     */     }
/*     */     catch (Exception e) {
/*  69 */       Result = "";
/*     */     }
/*  71 */     return Result;
/*     */   }
/*     */ 
/*     */   private boolean match(int i, int gb)
/*     */   {
/*  76 */     if (gb < this.table[i])
/*  77 */       return false;
/*  78 */     int j = i + 1;
/*     */ 
/*  81 */     while ((j < 26) && (this.table[j] == this.table[i])) j++;
/*  82 */     if (j == 26) {
/*  83 */       return gb <= this.table[j];
/*     */     }
/*  85 */     return gb < this.table[j];
/*     */   }
/*     */ 
/*     */   private int gbValue(char ch)
/*     */   {
/*  91 */     String str = new String();
/*  92 */     str = str + ch;
/*     */     try {
/*  94 */       byte[] bytes = str.getBytes("GB2312");
/*  95 */       if (bytes.length < 2)
/*  96 */         return 0;
/*  97 */       return (bytes[0] << 8 & 0xFF00) + (bytes[1] & 
/*  98 */         0xFF); } catch (Exception e) {
/*     */     }
/* 100 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 105 */     CnToSpell obj1 = new CnToSpell();
/* 106 */     System.out.println(obj1.getBeginCharacter("测试数据8ADGaadf"));
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.CnToSpell
 * JD-Core Version:    0.6.0
 */