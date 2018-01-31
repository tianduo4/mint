/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopDictionaryType;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopDictionaryType
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopDictionaryType";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_ID = "id";
/*     */ 
/*  41 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */ 
/*     */   public BaseShopDictionaryType()
/*     */   {
/*  26 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopDictionaryType(Long id)
/*     */   {
/*  33 */     setId(id);
/*  34 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  58 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  66 */     this.id = id;
/*  67 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  85 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  92 */     if (obj == null) return false;
/*  93 */     if (!(obj instanceof ShopDictionaryType)) return false;
/*     */ 
/*  95 */     ShopDictionaryType shopDictionaryType = (ShopDictionaryType)obj;
/*  96 */     if ((getId() == null) || (shopDictionaryType.getId() == null)) return false;
/*  97 */     return getId().equals(shopDictionaryType.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 103 */     if (-2147483648 == this.hashCode) {
/* 104 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 106 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 107 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 110 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 116 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopDictionaryType
 * JD-Core Version:    0.6.0
 */