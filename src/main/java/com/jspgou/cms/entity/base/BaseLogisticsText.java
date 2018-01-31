/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.LogisticsText;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseLogisticsText
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "LogisticsText";
/*  20 */   public static String PROP_TEXT = "text";
/*  21 */   public static String PROP_LOGISTICS = "logistics";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  42 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String text;
/*     */   private Logistics logistics;
/*     */ 
/*     */   public BaseLogisticsText()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseLogisticsText(Long id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  62 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  70 */     this.id = id;
/*  71 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*  81 */     return this.text;
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/*  89 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public Logistics getLogistics()
/*     */   {
/*  97 */     return this.logistics;
/*     */   }
/*     */ 
/*     */   public void setLogistics(Logistics logistics)
/*     */   {
/* 105 */     this.logistics = logistics;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 112 */     if (obj == null) return false;
/* 113 */     if (!(obj instanceof LogisticsText)) return false;
/*     */ 
/* 115 */     LogisticsText logisticsText = (LogisticsText)obj;
/* 116 */     if ((getId() == null) || (logisticsText.getId() == null)) return false;
/* 117 */     return getId().equals(logisticsText.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 123 */     if (-2147483648 == this.hashCode) {
/* 124 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 126 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 127 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 130 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 136 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseLogisticsText
 * JD-Core Version:    0.6.0
 */