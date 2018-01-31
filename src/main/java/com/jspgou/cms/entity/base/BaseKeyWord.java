/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.KeyWord;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseKeyWord
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "KeyWord";
/*  20 */   public static String PROP_KEYWORD = "keyword";
/*  21 */   public static String PROP_TIMES = "times";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  56 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String keyword;
/*     */   private Integer times;
/*     */ 
/*     */   public BaseKeyWord()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseKeyWord(Integer id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseKeyWord(Integer id, String keyword, Integer times)
/*     */   {
/*  46 */     setId(id);
/*  47 */     setKeyword(keyword);
/*  48 */     setTimes(times);
/*  49 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  74 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  82 */     this.id = id;
/*  83 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getKeyword()
/*     */   {
/*  93 */     return this.keyword;
/*     */   }
/*     */ 
/*     */   public void setKeyword(String keyword)
/*     */   {
/* 101 */     this.keyword = keyword;
/*     */   }
/*     */ 
/*     */   public Integer getTimes()
/*     */   {
/* 109 */     return this.times;
/*     */   }
/*     */ 
/*     */   public void setTimes(Integer times)
/*     */   {
/* 117 */     this.times = times;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 124 */     if (obj == null) return false;
/* 125 */     if (!(obj instanceof KeyWord)) return false;
/*     */ 
/* 127 */     KeyWord keyWord = (KeyWord)obj;
/* 128 */     if ((getId() == null) || (keyWord.getId() == null)) return false;
/* 129 */     return getId().equals(keyWord.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 135 */     if (-2147483648 == this.hashCode) {
/* 136 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 138 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 139 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 142 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 148 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseKeyWord
 * JD-Core Version:    0.6.0
 */