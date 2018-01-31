/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseProductFashion
/*     */   implements Serializable
/*     */ {
/*  20 */   public static String REF = "ProductFashion";
/*  21 */   public static String PROP_DEFAULT = "default";
/*  22 */   public static String PROP_STANDARD = "standard";
/*  23 */   public static String PROP_SALE_COUNT = "saleCount";
/*  24 */   public static String PROP_MARKET_PRICE = "marketPrice";
/*  25 */   public static String PROP_PRODUCT_CODE = "productCode";
/*  26 */   public static String PROP_STOCK_COUNT = "stockCount";
/*  27 */   public static String PROP_PRODUCT_ID = "productId";
/*  28 */   public static String PROP_ON_SALE = "onSale";
/*  29 */   public static String PROP_SALE_PRICE = "salePrice";
/*  30 */   public static String PROP_STANDARD_SECOND = "standardSecond";
/*  31 */   public static String PROP_FASHION_STYLE = "fashionStyle";
/*  32 */   public static String PROP_CREATE_TIME = "createTime";
/*  33 */   public static String PROP_FASHION_PIC = "fashionPic";
/*  34 */   public static String PROP_FASHION_SIZE = "fashionSize";
/*  35 */   public static String PROP_ID = "id";
/*  36 */   public static String PROP_COST_PRICE = "costPrice";
/*  37 */   public static String PROP_FASHION_COLOR = "fashionColor";
/*     */ 
/*  69 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String fashionStyle;
/*     */   private String productCode;
/*     */   private Integer saleCount;
/*     */   private Integer stockCount;
/*     */   private Integer onSale;
/*     */   private Date createTime;
/*     */   private Double marketPrice;
/*     */   private Double salePrice;
/*     */   private Double costPrice;
/*     */   private Integer lackRemind;
/*     */   private String fashionPic;
/*     */   private String fashionColor;
/*     */   private String fashionSize;
/*     */   private Boolean isDefault;
/*     */   private String nature;
/*     */   private String attitude;
/*     */   private Product productId;
/*     */   private Set<CartItem> cartItems;
/*     */   private Set<Standard> standards;
/*     */ 
/*     */   public BaseProductFashion()
/*     */   {
/*  42 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductFashion(Long id)
/*     */   {
/*  49 */     setId(id);
/*  50 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductFashion(Long id, Boolean isDefault)
/*     */   {
/*  60 */     setId(id);
/*  61 */     setIsDefault(isDefault);
/*  62 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 109 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 117 */     this.id = id;
/* 118 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getFashionStyle()
/*     */   {
/* 128 */     return this.fashionStyle;
/*     */   }
/*     */ 
/*     */   public void setFashionStyle(String fashionStyle)
/*     */   {
/* 136 */     this.fashionStyle = fashionStyle;
/*     */   }
/*     */ 
/*     */   public String getProductCode()
/*     */   {
/* 144 */     return this.productCode;
/*     */   }
/*     */ 
/*     */   public void setProductCode(String productCode)
/*     */   {
/* 152 */     this.productCode = productCode;
/*     */   }
/*     */ 
/*     */   public Integer getSaleCount()
/*     */   {
/* 160 */     return this.saleCount;
/*     */   }
/*     */ 
/*     */   public void setSaleCount(Integer saleCount)
/*     */   {
/* 168 */     this.saleCount = saleCount;
/*     */   }
/*     */ 
/*     */   public Integer getStockCount()
/*     */   {
/* 176 */     return this.stockCount;
/*     */   }
/*     */ 
/*     */   public void setStockCount(Integer stockCount)
/*     */   {
/* 184 */     this.stockCount = stockCount;
/*     */   }
/*     */ 
/*     */   public Integer getOnSale()
/*     */   {
/* 192 */     return this.onSale;
/*     */   }
/*     */ 
/*     */   public void setOnSale(Integer onSale)
/*     */   {
/* 200 */     this.onSale = onSale;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 208 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 216 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Double getMarketPrice()
/*     */   {
/* 224 */     return this.marketPrice;
/*     */   }
/*     */ 
/*     */   public void setMarketPrice(Double marketPrice)
/*     */   {
/* 232 */     this.marketPrice = marketPrice;
/*     */   }
/*     */ 
/*     */   public Double getSalePrice()
/*     */   {
/* 240 */     return this.salePrice;
/*     */   }
/*     */ 
/*     */   public void setSalePrice(Double salePrice)
/*     */   {
/* 248 */     this.salePrice = salePrice;
/*     */   }
/*     */ 
/*     */   public Double getCostPrice()
/*     */   {
/* 256 */     return this.costPrice;
/*     */   }
/*     */ 
/*     */   public void setCostPrice(Double costPrice)
/*     */   {
/* 264 */     this.costPrice = costPrice;
/*     */   }
/*     */ 
/*     */   public Integer getLackRemind()
/*     */   {
/* 272 */     return this.lackRemind;
/*     */   }
/*     */ 
/*     */   public void setLackRemind(Integer lackRemind)
/*     */   {
/* 280 */     this.lackRemind = lackRemind;
/*     */   }
/*     */ 
/*     */   public String getFashionPic()
/*     */   {
/* 288 */     return this.fashionPic;
/*     */   }
/*     */ 
/*     */   public void setFashionPic(String fashionPic)
/*     */   {
/* 296 */     this.fashionPic = fashionPic;
/*     */   }
/*     */ 
/*     */   public String getFashionColor()
/*     */   {
/* 304 */     return this.fashionColor;
/*     */   }
/*     */ 
/*     */   public void setFashionColor(String fashionColor)
/*     */   {
/* 312 */     this.fashionColor = fashionColor;
/*     */   }
/*     */ 
/*     */   public String getFashionSize()
/*     */   {
/* 320 */     return this.fashionSize;
/*     */   }
/*     */ 
/*     */   public void setFashionSize(String fashionSize)
/*     */   {
/* 328 */     this.fashionSize = fashionSize;
/*     */   }
/*     */ 
/*     */   public Boolean getIsDefault()
/*     */   {
/* 336 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(Boolean isDefault)
/*     */   {
/* 344 */     this.isDefault = isDefault;
/*     */   }
/*     */ 
/*     */   public Product getProductId()
/*     */   {
/* 352 */     return this.productId;
/*     */   }
/*     */ 
/*     */   public void setProductId(Product productId)
/*     */   {
/* 360 */     this.productId = productId;
/*     */   }
/*     */ 
/*     */   public Set<CartItem> getCartItems()
/*     */   {
/* 370 */     return this.cartItems;
/*     */   }
/*     */ 
/*     */   public void setCartItems(Set<CartItem> cartItems)
/*     */   {
/* 378 */     this.cartItems = cartItems;
/*     */   }
/*     */ 
/*     */   public void setStandards(Set<Standard> standards)
/*     */   {
/* 384 */     this.standards = standards;
/*     */   }
/*     */ 
/*     */   public Set<Standard> getStandards() {
/* 388 */     return this.standards;
/*     */   }
/*     */ 
/*     */   public void setNature(String nature) {
/* 392 */     this.nature = nature;
/*     */   }
/*     */ 
/*     */   public String getNature() {
/* 396 */     return this.nature;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 401 */     if (obj == null) return false;
/* 402 */     if (!(obj instanceof ProductFashion)) return false;
/*     */ 
/* 404 */     ProductFashion productFashion = (ProductFashion)obj;
/* 405 */     if ((getId() == null) || (productFashion.getId() == null)) return false;
/* 406 */     return getId().equals(productFashion.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 412 */     if (-2147483648 == this.hashCode) {
/* 413 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 415 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 416 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 419 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 425 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setAttitude(String attitude) {
/* 429 */     this.attitude = attitude;
/*     */   }
/*     */ 
/*     */   public String getAttitude() {
/* 433 */     return this.attitude;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductFashion
 * JD-Core Version:    0.6.0
 */