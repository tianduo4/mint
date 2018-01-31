/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseProductExt
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ProductExt";
/*  24 */   public static String PROP_SECKILLPRICE = "seckillprice";
/*  25 */   public static String PROP_DESCRIPTION = "description";
/*  26 */   public static String PROP_PRODUCT = "product";
/*  27 */   public static String PROP_DETAIL_IMG = "detailImg";
/*  28 */   public static String PROP_MTITLE = "mtitle";
/*  29 */   public static String PROP_MIN_IMG = "minImg";
/*  30 */   public static String PROP_MDESCRIPTION = "mdescription";
/*  31 */   public static String PROP_LIST_IMG = "listImg";
/*  32 */   public static String PROP_UNIT = "unit";
/*  33 */   public static String PROP_PRODUCT_IMG_DESC = "productImgDesc";
/*  34 */   public static String PROP_PRODUCT_IMGS = "productImgs";
/*  35 */   public static String PROP_TIME_LIMIT = "timeLimit";
/*  36 */   public static String PROP_WEIGHT = "weight";
/*  37 */   public static String PROP_PRODUCT_PROPERTY = "productProperty";
/*  38 */   public static String PROP_ID = "id";
/*  39 */   public static String PROP_ISLIMIT_TIME = "islimitTime";
/*  40 */   public static String PROP_MKEYWORDS = "mkeywords";
/*     */ 
/*  74 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Long code;
/*     */   private String mtitle;
/*     */   private String mkeywords;
/*     */   private String mdescription;
/*     */   private String detailImg;
/*     */   private String listImg;
/*     */   private String minImg;
/*     */   private String coverImg;
/*     */   private Integer weight;
/*     */   private String unit;
/*     */   private Boolean islimitTime;
/*     */   private Date timeLimit;
/*     */   private Double seckillprice;
/*     */   private String productImgs;
/*     */   private String productImgDesc;
/*     */   private Product product;
/*     */ 
/*     */   public BaseProductExt()
/*     */   {
/*  45 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductExt(Long id)
/*     */   {
/*  52 */     setId(id);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductExt(Long id, Integer weight, String unit)
/*     */   {
/*  64 */     setId(id);
/*  65 */     setWeight(weight);
/*  66 */     setUnit(unit);
/*  67 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 108 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 116 */     this.id = id;
/* 117 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Long getCode()
/*     */   {
/* 124 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(Long code)
/*     */   {
/* 132 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getMtitle()
/*     */   {
/* 140 */     return this.mtitle;
/*     */   }
/*     */ 
/*     */   public void setMtitle(String mtitle)
/*     */   {
/* 148 */     this.mtitle = mtitle;
/*     */   }
/*     */ 
/*     */   public String getMkeywords()
/*     */   {
/* 156 */     return this.mkeywords;
/*     */   }
/*     */ 
/*     */   public void setMkeywords(String mkeywords)
/*     */   {
/* 164 */     this.mkeywords = mkeywords;
/*     */   }
/*     */ 
/*     */   public String getMdescription()
/*     */   {
/* 172 */     return this.mdescription;
/*     */   }
/*     */ 
/*     */   public void setMdescription(String mdescription)
/*     */   {
/* 180 */     this.mdescription = mdescription;
/*     */   }
/*     */ 
/*     */   public String getDetailImg()
/*     */   {
/* 188 */     return this.detailImg;
/*     */   }
/*     */ 
/*     */   public void setDetailImg(String detailImg)
/*     */   {
/* 196 */     this.detailImg = detailImg;
/*     */   }
/*     */ 
/*     */   public String getListImg()
/*     */   {
/* 204 */     return this.listImg;
/*     */   }
/*     */ 
/*     */   public void setListImg(String listImg)
/*     */   {
/* 212 */     this.listImg = listImg;
/*     */   }
/*     */ 
/*     */   public String getMinImg()
/*     */   {
/* 220 */     return this.minImg;
/*     */   }
/*     */ 
/*     */   public void setMinImg(String minImg)
/*     */   {
/* 228 */     this.minImg = minImg;
/*     */   }
/*     */ 
/*     */   public String getCoverImg()
/*     */   {
/* 235 */     return this.coverImg;
/*     */   }
/*     */ 
/*     */   public void setCoverImg(String coverImg)
/*     */   {
/* 243 */     this.coverImg = coverImg;
/*     */   }
/*     */ 
/*     */   public Integer getWeight()
/*     */   {
/* 251 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(Integer weight)
/*     */   {
/* 259 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */   public String getUnit()
/*     */   {
/* 267 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String unit)
/*     */   {
/* 275 */     this.unit = unit;
/*     */   }
/*     */ 
/*     */   public Boolean getIslimitTime()
/*     */   {
/* 283 */     return this.islimitTime;
/*     */   }
/*     */ 
/*     */   public void setIslimitTime(Boolean islimitTime)
/*     */   {
/* 291 */     this.islimitTime = islimitTime;
/*     */   }
/*     */ 
/*     */   public Date getTimeLimit()
/*     */   {
/* 299 */     return this.timeLimit;
/*     */   }
/*     */ 
/*     */   public void setTimeLimit(Date timeLimit)
/*     */   {
/* 307 */     this.timeLimit = timeLimit;
/*     */   }
/*     */ 
/*     */   public Double getSeckillprice()
/*     */   {
/* 315 */     return this.seckillprice;
/*     */   }
/*     */ 
/*     */   public void setSeckillprice(Double seckillprice)
/*     */   {
/* 323 */     this.seckillprice = seckillprice;
/*     */   }
/*     */ 
/*     */   public String getProductImgs()
/*     */   {
/* 331 */     return this.productImgs;
/*     */   }
/*     */ 
/*     */   public void setProductImgs(String productImgs)
/*     */   {
/* 339 */     this.productImgs = productImgs;
/*     */   }
/*     */ 
/*     */   public String getProductImgDesc()
/*     */   {
/* 347 */     return this.productImgDesc;
/*     */   }
/*     */ 
/*     */   public void setProductImgDesc(String productImgDesc)
/*     */   {
/* 355 */     this.productImgDesc = productImgDesc;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 363 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 371 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 378 */     if (obj == null) return false;
/* 379 */     if (!(obj instanceof ProductExt)) return false;
/*     */ 
/* 381 */     ProductExt productExt = (ProductExt)obj;
/* 382 */     if ((getId() == null) || (productExt.getId() == null)) return false;
/* 383 */     return getId().equals(productExt.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 389 */     if (-2147483648 == this.hashCode) {
/* 390 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 392 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 393 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 396 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 402 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductExt
 * JD-Core Version:    0.6.0
 */