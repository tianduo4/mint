/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseCartItem
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "CartItem";
/*  24 */   public static String PROP_COUNT = "count";
/*  25 */   public static String PROP_PRODUCT = "product";
/*  26 */   public static String PROP_PRODUCT_FASH = "productFash";
/*  27 */   public static String PROP_WEBSITE = "website";
/*  28 */   public static String PROP_CART = "cart";
/*  29 */   public static String PROP_ID = "id";
/*  30 */   public static String PROP_SCORE = "score";
/*     */ 
/*  68 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer count;
/*     */   private Integer score;
/*     */   private Website website;
/*     */   private Cart cart;
/*     */   private Product product;
/*     */   private ProductFashion productFash;
/*     */   private PopularityGroup popularityGroup;
/*     */ 
/*     */   public BaseCartItem()
/*     */   {
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCartItem(Long id)
/*     */   {
/*  42 */     setId(id);
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCartItem(Long id, Website website, Cart cart, Product product, Integer count)
/*     */   {
/*  56 */     setId(id);
/*  57 */     setWebsite(website);
/*  58 */     setCart(cart);
/*  59 */     setProduct(product);
/*  60 */     setCount(count);
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  91 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  99 */     this.id = id;
/* 100 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getCount()
/*     */   {
/* 110 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(Integer count)
/*     */   {
/* 118 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 126 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 134 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 141 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 149 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Cart getCart()
/*     */   {
/* 157 */     return this.cart;
/*     */   }
/*     */ 
/*     */   public void setCart(Cart cart)
/*     */   {
/* 165 */     this.cart = cart;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 173 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 181 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public ProductFashion getProductFash()
/*     */   {
/* 189 */     return this.productFash;
/*     */   }
/*     */ 
/*     */   public void setProductFash(ProductFashion productFash)
/*     */   {
/* 197 */     this.productFash = productFash;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 204 */     if (obj == null) return false;
/* 205 */     if (!(obj instanceof CartItem)) return false;
/*     */ 
/* 207 */     CartItem cartItem = (CartItem)obj;
/* 208 */     if ((getId() == null) || (cartItem.getId() == null)) return false;
/* 209 */     return getId().equals(cartItem.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 215 */     if (-2147483648 == this.hashCode) {
/* 216 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 218 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 219 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 222 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 228 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setPopularityGroup(PopularityGroup popularityGroup) {
/* 232 */     this.popularityGroup = popularityGroup;
/*     */   }
/*     */ 
/*     */   public PopularityGroup getPopularityGroup() {
/* 236 */     return this.popularityGroup;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCartItem
 * JD-Core Version:    0.6.0
 */