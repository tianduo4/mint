/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartGift;
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseCart
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Cart";
/*  24 */   public static String PROP_MEMBER = "member";
/*  25 */   public static String PROP_WEBSITE = "website";
/*  26 */   public static String PROP_ADDRESS = "address";
/*  27 */   public static String PROP_TOTAL_ITEMS = "totalItems";
/*  28 */   public static String PROP_ID = "id";
/*  29 */   public static String PROP_SHIPPING = "shipping";
/*  30 */   public static String PROP_TOTAL_GIFTS = "totalGifts";
/*  31 */   public static String PROP_PAYMENT = "payment";
/*     */ 
/*  65 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer totalItems;
/*     */   private Integer totalGifts;
/*     */   private Member member;
/*     */   private Website website;
/*     */   private Set<CartItem> items;
/*     */   private Set<CartGift> gifts;
/*     */ 
/*     */   public BaseCart()
/*     */   {
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCart(Long id)
/*     */   {
/*  43 */     setId(id);
/*  44 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCart(Long id, Website website, Integer totalItems)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setWebsite(website);
/*  57 */     setTotalItems(totalItems);
/*  58 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 100 */     this.id = id;
/* 101 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getTotalItems()
/*     */   {
/* 108 */     return this.totalItems;
/*     */   }
/*     */ 
/*     */   public void setTotalItems(Integer totalItems)
/*     */   {
/* 116 */     this.totalItems = totalItems;
/*     */   }
/*     */ 
/*     */   public Integer getTotalGifts()
/*     */   {
/* 124 */     return this.totalGifts;
/*     */   }
/*     */ 
/*     */   public void setTotalGifts(Integer totalGifts)
/*     */   {
/* 132 */     this.totalGifts = totalGifts;
/*     */   }
/*     */ 
/*     */   public Member getMember()
/*     */   {
/* 139 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member)
/*     */   {
/* 147 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 155 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 163 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<CartItem> getItems()
/*     */   {
/* 170 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setItems(Set<CartItem> items)
/*     */   {
/* 178 */     this.items = items;
/*     */   }
/*     */ 
/*     */   public Set<CartGift> getGifts()
/*     */   {
/* 186 */     return this.gifts;
/*     */   }
/*     */ 
/*     */   public void setGifts(Set<CartGift> gifts)
/*     */   {
/* 194 */     this.gifts = gifts;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 201 */     if (obj == null) return false;
/* 202 */     if (!(obj instanceof Cart)) return false;
/*     */ 
/* 204 */     Cart cart = (Cart)obj;
/* 205 */     if ((getId() == null) || (cart.getId() == null)) return false;
/* 206 */     return getId().equals(cart.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 212 */     if (-2147483648 == this.hashCode) {
/* 213 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 215 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 216 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 219 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 225 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCart
 * JD-Core Version:    0.6.0
 */