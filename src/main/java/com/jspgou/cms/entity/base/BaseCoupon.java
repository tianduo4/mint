/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseCoupon
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Coupon";
/*  20 */   public static String PROP_COUPON_COUNT = "couponCount";
/*  21 */   public static String PROP_WEBSITE = "website";
/*  22 */   public static String PROP_COUPON_TIME = "couponTime";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_COUPON_NAME = "couponName";
/*  25 */   public static String PROP_ISUSING = "isusing";
/*  26 */   public static String PROP_LEAST_PRICE = "leastPrice";
/*  27 */   public static String PROP_COUPON_END_TIME = "couponEndTime";
/*  28 */   public static String PROP_COUPON_PRICE = "couponPrice";
/*  29 */   public static String PROP_COUPON_PICTURE = "couponPicture";
/*     */ 
/*  78 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Category category;
/*     */   private String couponName;
/*     */   private Date couponTime;
/*     */   private Date couponEndTime;
/*     */   private String couponPicture;
/*     */   private BigDecimal couponPrice;
/*     */   private BigDecimal leastPrice;
/*     */   private Boolean isusing;
/*     */   private Integer couponCount;
/*     */   private String comments;
/*     */   private Website website;
/*     */ 
/*     */   public BaseCoupon()
/*     */   {
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCoupon(Long id)
/*     */   {
/*  42 */     setId(id);
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCoupon(Long id, Website website, String couponName, Date couponTime, Date couponEndTime, String couponPicture, BigDecimal couponPrice, BigDecimal leastPrice, Boolean isusing, Integer couponCount)
/*     */   {
/*  61 */     setId(id);
/*  62 */     setWebsite(website);
/*  63 */     setCouponName(couponName);
/*  64 */     setCouponTime(couponTime);
/*  65 */     setCouponEndTime(couponEndTime);
/*  66 */     setCouponPicture(couponPicture);
/*  67 */     setCouponPrice(couponPrice);
/*  68 */     setLeastPrice(leastPrice);
/*  69 */     setIsusing(isusing);
/*  70 */     setCouponCount(couponCount);
/*  71 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 106 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 114 */     this.id = id;
/* 115 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/* 121 */     return this.category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category category) {
/* 125 */     this.category = category;
/*     */   }
/*     */ 
/*     */   public String getCouponName()
/*     */   {
/* 132 */     return this.couponName;
/*     */   }
/*     */ 
/*     */   public void setCouponName(String couponName)
/*     */   {
/* 140 */     this.couponName = couponName;
/*     */   }
/*     */ 
/*     */   public Date getCouponTime()
/*     */   {
/* 148 */     return this.couponTime;
/*     */   }
/*     */ 
/*     */   public void setCouponTime(Date couponTime)
/*     */   {
/* 156 */     this.couponTime = couponTime;
/*     */   }
/*     */ 
/*     */   public Date getCouponEndTime()
/*     */   {
/* 164 */     return this.couponEndTime;
/*     */   }
/*     */ 
/*     */   public void setCouponEndTime(Date couponEndTime)
/*     */   {
/* 172 */     this.couponEndTime = couponEndTime;
/*     */   }
/*     */ 
/*     */   public String getCouponPicture()
/*     */   {
/* 180 */     return this.couponPicture;
/*     */   }
/*     */ 
/*     */   public void setCouponPicture(String couponPicture)
/*     */   {
/* 188 */     this.couponPicture = couponPicture;
/*     */   }
/*     */ 
/*     */   public BigDecimal getCouponPrice()
/*     */   {
/* 196 */     return this.couponPrice;
/*     */   }
/*     */ 
/*     */   public void setCouponPrice(BigDecimal couponPrice)
/*     */   {
/* 204 */     this.couponPrice = couponPrice;
/*     */   }
/*     */ 
/*     */   public BigDecimal getLeastPrice()
/*     */   {
/* 212 */     return this.leastPrice;
/*     */   }
/*     */ 
/*     */   public void setLeastPrice(BigDecimal leastPrice)
/*     */   {
/* 220 */     this.leastPrice = leastPrice;
/*     */   }
/*     */ 
/*     */   public Boolean getIsusing()
/*     */   {
/* 228 */     return this.isusing;
/*     */   }
/*     */ 
/*     */   public void setIsusing(Boolean isusing)
/*     */   {
/* 236 */     this.isusing = isusing;
/*     */   }
/*     */ 
/*     */   public Integer getCouponCount()
/*     */   {
/* 244 */     return this.couponCount;
/*     */   }
/*     */ 
/*     */   public void setCouponCount(Integer couponCount)
/*     */   {
/* 252 */     this.couponCount = couponCount;
/*     */   }
/*     */ 
/*     */   public String getComments() {
/* 256 */     return this.comments;
/*     */   }
/*     */ 
/*     */   public void setComments(String comments) {
/* 260 */     this.comments = comments;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 269 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 277 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 284 */     if (obj == null) return false;
/* 285 */     if (!(obj instanceof Coupon)) return false;
/*     */ 
/* 287 */     Coupon coupon = (Coupon)obj;
/* 288 */     if ((getId() == null) || (coupon.getId() == null)) return false;
/* 289 */     return getId().equals(coupon.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 295 */     if (-2147483648 == this.hashCode) {
/* 296 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 298 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 299 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 302 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 308 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCoupon
 * JD-Core Version:    0.6.0
 */