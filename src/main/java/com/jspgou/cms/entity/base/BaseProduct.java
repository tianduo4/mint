/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExended;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ProductPicture;
/*     */ import com.jspgou.cms.entity.ProductTag;
/*     */ import com.jspgou.cms.entity.ProductText;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ShopConfig;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseProduct
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Product";
/*  24 */   public static String PROP_PRODUCT_EXT = "productExt";
/*  25 */   public static String PROP_BRAND = "brand";
/*  26 */   public static String PROP_CONFIG = "config";
/*  27 */   public static String PROP_SALE_COUNT = "saleCount";
/*  28 */   public static String PROP_SPECIAL = "special";
/*  29 */   public static String PROP_TYPE = "type";
/*  30 */   public static String PROP_SHARE_CONTENT = "shareContent";
/*  31 */   public static String PROP_RECOMMEND = "recommend";
/*  32 */   public static String PROP_NEW_PRODUCT = "newProduct";
/*  33 */   public static String PROP_VIEW_COUNT = "viewCount";
/*  34 */   public static String PROP_HOTSALE = "hotsale";
/*  35 */   public static String PROP_SCORE = "score";
/*  36 */   public static String PROP_MARKET_PRICE = "marketPrice";
/*  37 */   public static String PROP_WEBSITE = "website";
/*  38 */   public static String PROP_STOCK_COUNT = "stockCount";
/*  39 */   public static String PROP_PRODUCT_TEXT = "productText";
/*     */ 
/*  41 */   public static String PROP_ON_SALE = "status";
/*  42 */   public static String PROP_NAME = "name";
/*  43 */   public static String PROP_CATEGORY = "category";
/*  44 */   public static String PROP_SALE_PRICE = "salePrice";
/*  45 */   public static String PROP_CREATE_TIME = "createTime";
/*  46 */   public static String PROP_ID = "id";
/*  47 */   public static String PROP_COST_PRICE = "costPrice";
/*  48 */   public static String PROP_RELATED_GOODS = "relatedGoods";
/*     */ 
/* 116 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Double marketPrice;
/*     */   private Double salePrice;
/*     */   private Double costPrice;
/*     */   private Long viewCount;
/*     */   private Integer saleCount;
/*     */   private Integer stockCount;
/*     */   private Date createTime;
/*     */   private Date expireTime;
/*     */   private Boolean special;
/*     */   private Boolean recommend;
/*     */   private Boolean hotsale;
/*     */   private Boolean newProduct;
/*     */   private Integer status;
/*     */   private Boolean lackRemind;
/*     */   private Integer score;
/*     */   private String shareContent;
/*     */   private Integer alertInventory;
/*     */   private Boolean relatedGoods;
/*     */   private Double liRun;
/*     */   private ProductText productText;
/*     */   private ProductExt productExt;
/*     */   private Brand brand;
/*     */   private ShopConfig config;
/*     */   private Category category;
/*     */   private ProductType type;
/*     */   private Website website;
/*     */   private Set<ProductTag> tags;
/*     */   private Set<ProductFashion> fashions;
/*     */   private List<String> keywords;
/*     */   private List<ProductPicture> pictures;
/*     */   private Map<String, String> attr;
/*     */   private List<ProductExended> exendeds;
/*     */   private Set<PopularityGroup> popularityGroups;
/*     */ 
/*     */   public BaseProduct()
/*     */   {
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProduct(Long id)
/*     */   {
/*  60 */     setId(id);
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProduct(Long id, ShopConfig config, Category category, ProductType type, Website website, String name, Double marketPrice, Double salePrice, Double costPrice, Long viewCount, Integer saleCount, Integer stockCount, Date createTime, Boolean special, Boolean recommend, Boolean hotsale, Boolean newProduct, Boolean relatedGoods, Integer status)
/*     */   {
/*  89 */     setId(id);
/*  90 */     setConfig(config);
/*  91 */     setCategory(category);
/*  92 */     setType(type);
/*  93 */     setWebsite(website);
/*  94 */     setName(name);
/*  95 */     setMarketPrice(marketPrice);
/*  96 */     setSalePrice(salePrice);
/*  97 */     setCostPrice(costPrice);
/*  98 */     setViewCount(viewCount);
/*  99 */     setSaleCount(saleCount);
/* 100 */     setStockCount(stockCount);
/* 101 */     setCreateTime(createTime);
/* 102 */     setSpecial(special);
/* 103 */     setRecommend(recommend);
/* 104 */     setHotsale(hotsale);
/* 105 */     setNewProduct(newProduct);
/* 106 */     setRelatedGoods(relatedGoods);
/*     */ 
/* 108 */     setStatus(status);
/* 109 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Boolean getRelatedGoods()
/*     */   {
/* 139 */     return this.relatedGoods;
/*     */   }
/*     */ 
/*     */   public void setRelatedGoods(Boolean relatedGoods) {
/* 143 */     this.relatedGoods = relatedGoods;
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 180 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 188 */     this.id = id;
/* 189 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 199 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 207 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Double getMarketPrice()
/*     */   {
/* 215 */     return this.marketPrice;
/*     */   }
/*     */ 
/*     */   public void setMarketPrice(Double marketPrice)
/*     */   {
/* 223 */     this.marketPrice = marketPrice;
/*     */   }
/*     */ 
/*     */   public Double getSalePrice()
/*     */   {
/* 231 */     return this.salePrice;
/*     */   }
/*     */ 
/*     */   public void setSalePrice(Double salePrice)
/*     */   {
/* 239 */     this.salePrice = salePrice;
/*     */   }
/*     */ 
/*     */   public Double getCostPrice()
/*     */   {
/* 247 */     return this.costPrice;
/*     */   }
/*     */ 
/*     */   public void setCostPrice(Double costPrice)
/*     */   {
/* 255 */     this.costPrice = costPrice;
/*     */   }
/*     */ 
/*     */   public Long getViewCount()
/*     */   {
/* 263 */     return this.viewCount;
/*     */   }
/*     */ 
/*     */   public void setViewCount(Long viewCount)
/*     */   {
/* 271 */     this.viewCount = viewCount;
/*     */   }
/*     */ 
/*     */   public Integer getSaleCount()
/*     */   {
/* 279 */     return this.saleCount;
/*     */   }
/*     */ 
/*     */   public void setSaleCount(Integer saleCount)
/*     */   {
/* 287 */     this.saleCount = saleCount;
/*     */   }
/*     */ 
/*     */   public Integer getStockCount()
/*     */   {
/* 295 */     return this.stockCount;
/*     */   }
/*     */ 
/*     */   public void setStockCount(Integer stockCount)
/*     */   {
/* 303 */     this.stockCount = stockCount;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 311 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 319 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Boolean getSpecial()
/*     */   {
/* 327 */     return this.special;
/*     */   }
/*     */ 
/*     */   public void setSpecial(Boolean special)
/*     */   {
/* 335 */     this.special = special;
/*     */   }
/*     */ 
/*     */   public Boolean getRecommend()
/*     */   {
/* 343 */     return this.recommend;
/*     */   }
/*     */ 
/*     */   public void setRecommend(Boolean recommend)
/*     */   {
/* 351 */     this.recommend = recommend;
/*     */   }
/*     */ 
/*     */   public Boolean getHotsale()
/*     */   {
/* 359 */     return this.hotsale;
/*     */   }
/*     */ 
/*     */   public void setHotsale(Boolean hotsale)
/*     */   {
/* 367 */     this.hotsale = hotsale;
/*     */   }
/*     */ 
/*     */   public Boolean getNewProduct()
/*     */   {
/* 375 */     return this.newProduct;
/*     */   }
/*     */ 
/*     */   public void setNewProduct(Boolean newProduct)
/*     */   {
/* 383 */     this.newProduct = newProduct;
/*     */   }
/*     */ 
/*     */   public Boolean getLackRemind()
/*     */   {
/* 405 */     return this.lackRemind;
/*     */   }
/*     */ 
/*     */   public Integer getStatus()
/*     */   {
/* 411 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status) {
/* 415 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public void setLackRemind(Boolean lackRemind) {
/* 419 */     this.lackRemind = lackRemind;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 426 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 434 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public String getShareContent()
/*     */   {
/* 442 */     return this.shareContent;
/*     */   }
/*     */ 
/*     */   public void setShareContent(String shareContent)
/*     */   {
/* 450 */     this.shareContent = shareContent;
/*     */   }
/*     */ 
/*     */   public ProductText getProductText()
/*     */   {
/* 458 */     return this.productText;
/*     */   }
/*     */ 
/*     */   public void setProductText(ProductText productText)
/*     */   {
/* 466 */     this.productText = productText;
/*     */   }
/*     */ 
/*     */   public ProductExt getProductExt()
/*     */   {
/* 474 */     return this.productExt;
/*     */   }
/*     */ 
/*     */   public void setProductExt(ProductExt productExt)
/*     */   {
/* 482 */     this.productExt = productExt;
/*     */   }
/*     */ 
/*     */   public Brand getBrand()
/*     */   {
/* 490 */     return this.brand;
/*     */   }
/*     */ 
/*     */   public void setBrand(Brand brand)
/*     */   {
/* 498 */     this.brand = brand;
/*     */   }
/*     */ 
/*     */   public ShopConfig getConfig()
/*     */   {
/* 506 */     return this.config;
/*     */   }
/*     */ 
/*     */   public void setConfig(ShopConfig config)
/*     */   {
/* 514 */     this.config = config;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/* 522 */     return this.category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category category)
/*     */   {
/* 530 */     this.category = category;
/*     */   }
/*     */ 
/*     */   public ProductType getType()
/*     */   {
/* 538 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(ProductType type)
/*     */   {
/* 546 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 554 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 562 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<ProductTag> getTags()
/*     */   {
/* 570 */     return this.tags;
/*     */   }
/*     */ 
/*     */   public void setTags(Set<ProductTag> tags)
/*     */   {
/* 578 */     this.tags = tags;
/*     */   }
/*     */ 
/*     */   public Set<ProductFashion> getFashions()
/*     */   {
/* 588 */     return this.fashions;
/*     */   }
/*     */ 
/*     */   public void setFashions(Set<ProductFashion> fashions)
/*     */   {
/* 596 */     this.fashions = fashions;
/*     */   }
/*     */ 
/*     */   public List<String> getKeywords()
/*     */   {
/* 604 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(List<String> keywords)
/*     */   {
/* 612 */     this.keywords = keywords;
/*     */   }
/*     */ 
/*     */   public List<ProductPicture> getPictures()
/*     */   {
/* 619 */     return this.pictures;
/*     */   }
/*     */ 
/*     */   public void setPictures(List<ProductPicture> pictures)
/*     */   {
/* 627 */     this.pictures = pictures;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/* 635 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr)
/*     */   {
/* 643 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 650 */     if (obj == null) return false;
/* 651 */     if (!(obj instanceof Product)) return false;
/*     */ 
/* 653 */     Product product = (Product)obj;
/* 654 */     if ((getId() == null) || (product.getId() == null)) return false;
/* 655 */     return getId().equals(product.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 661 */     if (-2147483648 == this.hashCode) {
/* 662 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 664 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 665 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 668 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 674 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setAlertInventory(Integer alertInventory) {
/* 678 */     this.alertInventory = alertInventory;
/*     */   }
/*     */ 
/*     */   public Integer getAlertInventory() {
/* 682 */     return this.alertInventory;
/*     */   }
/*     */ 
/*     */   public void setExendeds(List<ProductExended> exendeds) {
/* 686 */     this.exendeds = exendeds;
/*     */   }
/*     */ 
/*     */   public List<ProductExended> getExendeds() {
/* 690 */     return this.exendeds;
/*     */   }
/*     */ 
/*     */   public void setExpireTime(Date expireTime) {
/* 694 */     this.expireTime = expireTime;
/*     */   }
/*     */ 
/*     */   public Date getExpireTime() {
/* 698 */     return this.expireTime;
/*     */   }
/*     */ 
/*     */   public void setLiRun(Double liRun) {
/* 702 */     this.liRun = liRun;
/*     */   }
/*     */ 
/*     */   public Double getLiRun() {
/* 706 */     return this.liRun;
/*     */   }
/*     */ 
/*     */   public void setPopularityGroups(Set<PopularityGroup> popularityGroups) {
/* 710 */     this.popularityGroups = popularityGroups;
/*     */   }
/*     */ 
/*     */   public Set<PopularityGroup> getPopularityGroups() {
/* 714 */     return this.popularityGroups;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProduct
 * JD-Core Version:    0.6.0
 */