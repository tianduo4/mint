/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.PopularityItem;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BasePopularityItem
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "PopularityItem";
/*  18 */   public static String PROP_COUNT = "count";
/*  19 */   public static String PROP_CART = "cart";
/*  20 */   public static String PROP_ID = "id";
/*  21 */   public static String PROP_POPULARITY_GROUP = "popularityGroup";
/*     */ 
/*  55 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer count;
/*     */   private Cart cart;
/*     */   private PopularityGroup popularityGroup;
/*     */ 
/*     */   public BasePopularityItem()
/*     */   {
/*  26 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePopularityItem(Long id)
/*     */   {
/*  33 */     setId(id);
/*  34 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePopularityItem(Long id, Cart cart, Integer count)
/*     */   {
/*  45 */     setId(id);
/*  46 */     setCart(cart);
/*  47 */     setCount(count);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  76 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  84 */     this.id = id;
/*  85 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getCount()
/*     */   {
/*  95 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(Integer count)
/*     */   {
/* 103 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public Cart getCart()
/*     */   {
/* 111 */     return this.cart;
/*     */   }
/*     */ 
/*     */   public void setCart(Cart cart)
/*     */   {
/* 119 */     this.cart = cart;
/*     */   }
/*     */ 
/*     */   public PopularityGroup getPopularityGroup()
/*     */   {
/* 127 */     return this.popularityGroup;
/*     */   }
/*     */ 
/*     */   public void setPopularityGroup(PopularityGroup popularityGroup)
/*     */   {
/* 135 */     this.popularityGroup = popularityGroup;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 142 */     if (obj == null) return false;
/* 143 */     if (!(obj instanceof PopularityItem)) return false;
/*     */ 
/* 145 */     PopularityItem popularityItem = (PopularityItem)obj;
/* 146 */     if ((getId() == null) || (popularityItem.getId() == null)) return false;
/* 147 */     return getId().equals(popularityItem.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 153 */     if (-2147483648 == this.hashCode) {
/* 154 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 156 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 157 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 160 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 166 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BasePopularityItem
 * JD-Core Version:    0.6.0
 */