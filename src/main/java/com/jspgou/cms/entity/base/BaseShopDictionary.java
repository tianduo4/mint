/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import com.jspgou.cms.entity.ShopDictionaryType;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopDictionary
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopDictionary";
/*  24 */   public static String PROP_NAME = "name";
/*  25 */   public static String PROP_ID = "id";
/*  26 */   public static String PROP_SHOP_DICTIONARY_TYPE = "shopDictionaryType";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Integer priority;
/*     */   private ShopDictionaryType shopDictionaryType;
/*     */ 
/*     */   public BaseShopDictionary()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopDictionary(Long id)
/*     */   {
/*  38 */     setId(id);
/*  39 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopDictionary(Long id, ShopDictionaryType shopDictionaryType, String name)
/*     */   {
/*  50 */     setId(id);
/*  51 */     setShopDictionaryType(shopDictionaryType);
/*  52 */     setName(name);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  81 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  89 */     this.id = id;
/*  90 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 100 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 108 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 115 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 123 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public ShopDictionaryType getShopDictionaryType()
/*     */   {
/* 131 */     return this.shopDictionaryType;
/*     */   }
/*     */ 
/*     */   public void setShopDictionaryType(ShopDictionaryType shopDictionaryType)
/*     */   {
/* 139 */     this.shopDictionaryType = shopDictionaryType;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 146 */     if (obj == null) return false;
/* 147 */     if (!(obj instanceof ShopDictionary)) return false;
/*     */ 
/* 149 */     ShopDictionary shopDictionary = (ShopDictionary)obj;
/* 150 */     if ((getId() == null) || (shopDictionary.getId() == null)) return false;
/* 151 */     return getId().equals(shopDictionary.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 157 */     if (-2147483648 == this.hashCode) {
/* 158 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 160 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 161 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 164 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 170 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopDictionary
 * JD-Core Version:    0.6.0
 */