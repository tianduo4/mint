/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseProductType
/*     */   implements Serializable
/*     */ {
/*  20 */   public static String REF = "ProductType";
/*  21 */   public static String PROP_LIST_IMG_HEIGHT = "listImgHeight";
/*  22 */   public static String PROP_WEBSITE = "website";
/*  23 */   public static String PROP_DETAIL_IMG_WIDTH = "detailImgWidth";
/*  24 */   public static String PROP_LIST_IMG_WIDTH = "listImgWidth";
/*  25 */   public static String PROP_MIN_IMG_HEIGHT = "minImgHeight";
/*  26 */   public static String PROP_PATH = "path";
/*  27 */   public static String PROP_DETAIL_IMG_HEIGHT = "detailImgHeight";
/*  28 */   public static String PROP_NAME = "name";
/*  29 */   public static String PROP_ALIAS = "alias";
/*  30 */   public static String PROP_PROPS = "props";
/*  31 */   public static String PROP_ID = "id";
/*  32 */   public static String PROP_MIN_IMG_WIDTH = "minImgWidth";
/*  33 */   public static String PROP_CONTENT_PREFIX = "contentPrefix";
/*  34 */   public static String PROP_REF_BRAND = "refBrand";
/*  35 */   public static String PROP_CHANNEL_PREFIX = "channelPrefix";
/*     */ 
/*  86 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String alias;
/*     */   private String channelPrefix;
/*     */   private String contentPrefix;
/*     */   private String props;
/*     */   private Integer detailImgWidth;
/*     */   private Integer detailImgHeight;
/*     */   private Integer listImgWidth;
/*     */   private Integer listImgHeight;
/*     */   private Integer minImgWidth;
/*     */   private Integer minImgHeight;
/*     */   private Website website;
/*     */   private Set<ProductTypeProperty> properties;
/*     */   private Set<Exended> exendeds;
/*     */ 
/*     */   public BaseProductType()
/*     */   {
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductType(Long id)
/*     */   {
/*  47 */     setId(id);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductType(Long id, Website website, String name, String channelPrefix, String contentPrefix, Boolean refBrand, Integer detailImgWidth, Integer detailImgHeight, Integer listImgWidth, Integer listImgHeight, Integer minImgWidth, Integer minImgHeight)
/*     */   {
/*  68 */     setId(id);
/*  69 */     setWebsite(website);
/*  70 */     setName(name);
/*  71 */     setChannelPrefix(channelPrefix);
/*  72 */     setContentPrefix(contentPrefix);
/*  73 */     setDetailImgWidth(detailImgWidth);
/*  74 */     setDetailImgHeight(detailImgHeight);
/*  75 */     setListImgWidth(listImgWidth);
/*  76 */     setListImgHeight(listImgHeight);
/*  77 */     setMinImgWidth(minImgWidth);
/*  78 */     setMinImgHeight(minImgHeight);
/*  79 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 119 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 127 */     this.id = id;
/* 128 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 138 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 146 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getAlias()
/*     */   {
/* 153 */     return this.alias;
/*     */   }
/*     */ 
/*     */   public void setAlias(String alias)
/*     */   {
/* 161 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */   public String getChannelPrefix()
/*     */   {
/* 169 */     return this.channelPrefix;
/*     */   }
/*     */ 
/*     */   public void setChannelPrefix(String channelPrefix)
/*     */   {
/* 177 */     this.channelPrefix = channelPrefix;
/*     */   }
/*     */ 
/*     */   public String getContentPrefix()
/*     */   {
/* 185 */     return this.contentPrefix;
/*     */   }
/*     */ 
/*     */   public void setContentPrefix(String contentPrefix)
/*     */   {
/* 193 */     this.contentPrefix = contentPrefix;
/*     */   }
/*     */ 
/*     */   public String getProps()
/*     */   {
/* 201 */     return this.props;
/*     */   }
/*     */ 
/*     */   public void setProps(String props)
/*     */   {
/* 209 */     this.props = props;
/*     */   }
/*     */ 
/*     */   public Integer getDetailImgWidth()
/*     */   {
/* 216 */     return this.detailImgWidth;
/*     */   }
/*     */ 
/*     */   public void setDetailImgWidth(Integer detailImgWidth)
/*     */   {
/* 224 */     this.detailImgWidth = detailImgWidth;
/*     */   }
/*     */ 
/*     */   public Integer getDetailImgHeight()
/*     */   {
/* 232 */     return this.detailImgHeight;
/*     */   }
/*     */ 
/*     */   public void setDetailImgHeight(Integer detailImgHeight)
/*     */   {
/* 240 */     this.detailImgHeight = detailImgHeight;
/*     */   }
/*     */ 
/*     */   public Integer getListImgWidth()
/*     */   {
/* 248 */     return this.listImgWidth;
/*     */   }
/*     */ 
/*     */   public void setListImgWidth(Integer listImgWidth)
/*     */   {
/* 256 */     this.listImgWidth = listImgWidth;
/*     */   }
/*     */ 
/*     */   public Integer getListImgHeight()
/*     */   {
/* 264 */     return this.listImgHeight;
/*     */   }
/*     */ 
/*     */   public void setListImgHeight(Integer listImgHeight)
/*     */   {
/* 272 */     this.listImgHeight = listImgHeight;
/*     */   }
/*     */ 
/*     */   public Integer getMinImgWidth()
/*     */   {
/* 280 */     return this.minImgWidth;
/*     */   }
/*     */ 
/*     */   public void setMinImgWidth(Integer minImgWidth)
/*     */   {
/* 288 */     this.minImgWidth = minImgWidth;
/*     */   }
/*     */ 
/*     */   public Integer getMinImgHeight()
/*     */   {
/* 296 */     return this.minImgHeight;
/*     */   }
/*     */ 
/*     */   public void setMinImgHeight(Integer minImgHeight)
/*     */   {
/* 304 */     this.minImgHeight = minImgHeight;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 312 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 320 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<ProductTypeProperty> getProperties() {
/* 324 */     return this.properties;
/*     */   }
/*     */ 
/*     */   public void setProperties(Set<ProductTypeProperty> properties)
/*     */   {
/* 329 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */   public void setExendeds(Set<Exended> exendeds) {
/* 333 */     this.exendeds = exendeds;
/*     */   }
/*     */ 
/*     */   public Set<Exended> getExendeds() {
/* 337 */     return this.exendeds;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 342 */     if (obj == null) return false;
/* 343 */     if (!(obj instanceof ProductType)) return false;
/*     */ 
/* 345 */     ProductType productType = (ProductType)obj;
/* 346 */     if ((getId() == null) || (productType.getId() == null)) return false;
/* 347 */     return getId().equals(productType.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 353 */     if (-2147483648 == this.hashCode) {
/* 354 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 356 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 357 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 360 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 366 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductType
 * JD-Core Version:    0.6.0
 */