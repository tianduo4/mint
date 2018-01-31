/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopConfig;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopConfig";
/*  24 */   public static String PROP_REGISTER_AUTO = "registerAuto";
/*  25 */   public static String PROP_FAVORITE_SIZE = "favoriteSize";
/*  26 */   public static String PROP_WEBSITE = "website";
/*  27 */   public static String PROP_REGISTER_GROUP = "registerGroup";
/*  28 */   public static String PROP_ID = "id";
/*  29 */   public static String PROP_MARKET_PRICE_NAME = "marketPriceName";
/*  30 */   public static String PROP_SHOP_PRICE_NAME = "shopPriceName";
/*     */ 
/*  70 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String shopPriceName;
/*     */   private String marketPriceName;
/*     */   private Integer favoriteSize;
/*     */   private Boolean registerAuto;
/*     */   private Website website;
/*     */   private ShopMemberGroup registerGroup;
/*     */ 
/*     */   public BaseShopConfig()
/*     */   {
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopConfig(Long id)
/*     */   {
/*  42 */     setId(id);
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopConfig(Long id, ShopMemberGroup registerGroup, String shopPriceName, String marketPriceName, Integer favoriteSize, Boolean registerAuto)
/*     */   {
/*  57 */     setId(id);
/*  58 */     setRegisterGroup(registerGroup);
/*  59 */     setShopPriceName(shopPriceName);
/*  60 */     setMarketPriceName(marketPriceName);
/*  61 */     setFavoriteSize(favoriteSize);
/*  62 */     setRegisterAuto(registerAuto);
/*  63 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  96 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 104 */     this.id = id;
/* 105 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getShopPriceName()
/*     */   {
/* 115 */     return this.shopPriceName;
/*     */   }
/*     */ 
/*     */   public void setShopPriceName(String shopPriceName)
/*     */   {
/* 123 */     this.shopPriceName = shopPriceName;
/*     */   }
/*     */ 
/*     */   public String getMarketPriceName()
/*     */   {
/* 131 */     return this.marketPriceName;
/*     */   }
/*     */ 
/*     */   public void setMarketPriceName(String marketPriceName)
/*     */   {
/* 139 */     this.marketPriceName = marketPriceName;
/*     */   }
/*     */ 
/*     */   public Integer getFavoriteSize()
/*     */   {
/* 147 */     return this.favoriteSize;
/*     */   }
/*     */ 
/*     */   public void setFavoriteSize(Integer favoriteSize)
/*     */   {
/* 155 */     this.favoriteSize = favoriteSize;
/*     */   }
/*     */ 
/*     */   public Boolean getRegisterAuto()
/*     */   {
/* 163 */     return this.registerAuto;
/*     */   }
/*     */ 
/*     */   public void setRegisterAuto(Boolean registerAuto)
/*     */   {
/* 171 */     this.registerAuto = registerAuto;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 179 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 187 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public ShopMemberGroup getRegisterGroup()
/*     */   {
/* 195 */     return this.registerGroup;
/*     */   }
/*     */ 
/*     */   public void setRegisterGroup(ShopMemberGroup registerGroup)
/*     */   {
/* 203 */     this.registerGroup = registerGroup;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 210 */     if (obj == null) return false;
/* 211 */     if (!(obj instanceof ShopConfig)) return false;
/*     */ 
/* 213 */     ShopConfig shopConfig = (ShopConfig)obj;
/* 214 */     if ((getId() == null) || (shopConfig.getId() == null)) return false;
/* 215 */     return getId().equals(shopConfig.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 221 */     if (-2147483648 == this.hashCode) {
/* 222 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 224 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 225 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 228 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 234 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopConfig
 * JD-Core Version:    0.6.0
 */