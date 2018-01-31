/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopMemberGroup
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopMemberGroup";
/*  24 */   public static String PROP_WEBSITE = "website";
/*  25 */   public static String PROP_DISCOUNT = "discount";
/*  26 */   public static String PROP_SCORE = "score";
/*  27 */   public static String PROP_NAME = "name";
/*  28 */   public static String PROP_ID = "id";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Integer score;
/*     */   private Integer discount;
/*     */   private Website website;
/*     */ 
/*     */   public BaseShopMemberGroup()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMemberGroup(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMemberGroup(Long id, Website website, String name, Integer score, Integer discount)
/*     */   {
/*  54 */     setId(id);
/*  55 */     setWebsite(website);
/*  56 */     setName(name);
/*  57 */     setScore(score);
/*  58 */     setDiscount(discount);
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
/*     */   public String getName()
/*     */   {
/* 107 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 115 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 123 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 131 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Integer getDiscount()
/*     */   {
/* 139 */     return this.discount;
/*     */   }
/*     */ 
/*     */   public void setDiscount(Integer discount)
/*     */   {
/* 147 */     this.discount = discount;
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
/*     */   public boolean equals(Object obj)
/*     */   {
/* 170 */     if (obj == null) return false;
/* 171 */     if (!(obj instanceof ShopMemberGroup)) return false;
/*     */ 
/* 173 */     ShopMemberGroup shopMemberGroup = (ShopMemberGroup)obj;
/* 174 */     if ((getId() == null) || (shopMemberGroup.getId() == null)) return false;
/* 175 */     return getId().equals(shopMemberGroup.getId());
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
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopMemberGroup
 * JD-Core Version:    0.6.0
 */