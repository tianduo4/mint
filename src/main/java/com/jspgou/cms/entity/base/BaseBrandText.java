/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.BrandText;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseBrandText
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "BrandText";
/*  20 */   public static String PROP_BRAND = "brand";
/*  21 */   public static String PROP_TEXT = "text";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  42 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String text;
/*     */   private Brand brand;
/*     */ 
/*     */   public BaseBrandText()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseBrandText(Long id)
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
/*     */   public Brand getBrand()
/*     */   {
/*  97 */     return this.brand;
/*     */   }
/*     */ 
/*     */   public void setBrand(Brand brand)
/*     */   {
/* 105 */     this.brand = brand;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 112 */     if (obj == null) return false;
/* 113 */     if (!(obj instanceof BrandText)) return false;
/*     */ 
/* 115 */     BrandText brandText = (BrandText)obj;
/* 116 */     if ((getId() == null) || (brandText.getId() == null)) return false;
/* 117 */     return getId().equals(brandText.getId());
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
 * Qualified Name:     com.jspgou.cms.entity.base.BaseBrandText
 * JD-Core Version:    0.6.0
 */