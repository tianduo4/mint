/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartGift;
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseCartGift
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "CartGift";
/*  24 */   public static String PROP_COUNT = "count";
/*  25 */   public static String PROP_GIFT = "gift";
/*  26 */   public static String PROP_WEBSITE = "website";
/*  27 */   public static String PROP_CART = "cart";
/*  28 */   public static String PROP_ID = "id";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer count;
/*     */   private Website website;
/*     */   private Cart cart;
/*     */   private Gift gift;
/*     */ 
/*     */   public BaseCartGift()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCartGift(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCartGift(Long id, Website website, Cart cart, Gift gift, Integer count)
/*     */   {
/*  54 */     setId(id);
/*  55 */     setWebsite(website);
/*  56 */     setCart(cart);
/*  57 */     setGift(gift);
/*  58 */     setCount(count);
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  88 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  96 */     this.id = id;
/*  97 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getCount()
/*     */   {
/* 107 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(Integer count)
/*     */   {
/* 115 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 123 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 131 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Cart getCart()
/*     */   {
/* 139 */     return this.cart;
/*     */   }
/*     */ 
/*     */   public void setCart(Cart cart)
/*     */   {
/* 147 */     this.cart = cart;
/*     */   }
/*     */ 
/*     */   public Gift getGift()
/*     */   {
/* 155 */     return this.gift;
/*     */   }
/*     */ 
/*     */   public void setGift(Gift gift)
/*     */   {
/* 163 */     this.gift = gift;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 170 */     if (obj == null) return false;
/* 171 */     if (!(obj instanceof CartGift)) return false;
/*     */ 
/* 173 */     CartGift cartGift = (CartGift)obj;
/* 174 */     if ((getId() == null) || (cartGift.getId() == null)) return false;
/* 175 */     return getId().equals(cartGift.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 181 */     if (-2147483648 == this.hashCode) {
/* 182 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 184 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 185 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 188 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 194 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCartGift
 * JD-Core Version:    0.6.0
 */