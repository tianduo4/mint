/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseOrderItem
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "OrderItem";
/*  24 */   public static String PROP_SECKILLPRICE = "seckillprice";
/*  25 */   public static String PROP_COUNT = "count";
/*  26 */   public static String PROP_PRODUCT = "product";
/*  27 */   public static String PROP_SALE_PRICE = "salePrice";
/*  28 */   public static String PROP_PRODUCT_FASH = "productFash";
/*  29 */   public static String PROP_WEBSITE = "website";
/*  30 */   public static String PROP_ORDER = "order";
/*  31 */   public static String PROP_ID = "id";
/*  32 */   public static String PROP_MEMBER_PRICE = "memberPrice";
/*  33 */   public static String PROP_PRODUCT_NAME = "productName";
/*  34 */   public static String PROP_FINAL_PRICE = "finalPrice";
/*  35 */   public static String PROP_COST_PRICE = "costPrice";
/*     */ 
/*  73 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer count;
/*     */   private Double salePrice;
/*     */   private Double memberPrice;
/*     */   private Double costPrice;
/*     */   private Double finalPrice;
/*     */   private Double seckillprice;
/*     */   private Boolean status;
/*     */   private Website website;
/*     */   private Product product;
/*     */   private Order ordeR;
/*     */   private ProductFashion productFash;
/*     */ 
/*     */   public BaseOrderItem()
/*     */   {
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrderItem(Long id)
/*     */   {
/*  47 */     setId(id);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrderItem(Long id, Website website, Product product, Order ordeR, Integer count)
/*     */   {
/*  61 */     setId(id);
/*  62 */     setWebsite(website);
/*  63 */     setProduct(product);
/*  64 */     setOrdeR(ordeR);
/*  65 */     setCount(count);
/*  66 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 110 */     this.id = id;
/* 111 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getCount()
/*     */   {
/* 118 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(Integer count)
/*     */   {
/* 126 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public Double getSalePrice()
/*     */   {
/* 134 */     return this.salePrice;
/*     */   }
/*     */ 
/*     */   public void setSalePrice(Double salePrice)
/*     */   {
/* 142 */     this.salePrice = salePrice;
/*     */   }
/*     */ 
/*     */   public Double getMemberPrice()
/*     */   {
/* 150 */     return this.memberPrice;
/*     */   }
/*     */ 
/*     */   public void setMemberPrice(Double memberPrice)
/*     */   {
/* 158 */     this.memberPrice = memberPrice;
/*     */   }
/*     */ 
/*     */   public Double getCostPrice()
/*     */   {
/* 166 */     return this.costPrice;
/*     */   }
/*     */ 
/*     */   public void setCostPrice(Double costPrice)
/*     */   {
/* 174 */     this.costPrice = costPrice;
/*     */   }
/*     */ 
/*     */   public Double getFinalPrice()
/*     */   {
/* 182 */     return this.finalPrice;
/*     */   }
/*     */ 
/*     */   public void setFinalPrice(Double finalPrice)
/*     */   {
/* 190 */     this.finalPrice = finalPrice;
/*     */   }
/*     */ 
/*     */   public Double getSeckillprice()
/*     */   {
/* 198 */     return this.seckillprice;
/*     */   }
/*     */ 
/*     */   public void setSeckillprice(Double seckillprice)
/*     */   {
/* 206 */     this.seckillprice = seckillprice;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 214 */     return this.website;
/*     */   }
/*     */ 
/*     */   public Boolean getStatus()
/*     */   {
/* 219 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Boolean status) {
/* 223 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 233 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 241 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 249 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public Order getOrdeR()
/*     */   {
/* 257 */     return this.ordeR;
/*     */   }
/*     */ 
/*     */   public void setOrdeR(Order ordeR)
/*     */   {
/* 265 */     this.ordeR = ordeR;
/*     */   }
/*     */ 
/*     */   public ProductFashion getProductFash()
/*     */   {
/* 273 */     return this.productFash;
/*     */   }
/*     */ 
/*     */   public void setProductFash(ProductFashion productFash)
/*     */   {
/* 281 */     this.productFash = productFash;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 288 */     if (obj == null) return false;
/* 289 */     if (!(obj instanceof OrderItem)) return false;
/*     */ 
/* 291 */     OrderItem orderItem = (OrderItem)obj;
/* 292 */     if ((getId() == null) || (orderItem.getId() == null)) return false;
/* 293 */     return getId().equals(orderItem.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 299 */     if (-2147483648 == this.hashCode) {
/* 300 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 302 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 303 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 306 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 312 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseOrderItem
 * JD-Core Version:    0.6.0
 */