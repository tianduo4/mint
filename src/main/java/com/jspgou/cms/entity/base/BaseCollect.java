/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Collect;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseCollect
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Collect";
/*  20 */   public static String PROP_MEMBER = "member";
/*  21 */   public static String PROP_TIME = "time";
/*  22 */   public static String PROP_PRODUCT = "product";
/*  23 */   public static String PROP_FASHION = "fashion";
/*  24 */   public static String PROP_ID = "id";
/*     */ 
/*  58 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private Date time;
/*     */   private ShopMember member;
/*     */   private Product product;
/*     */   private ProductFashion fashion;
/*     */ 
/*     */   public BaseCollect()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCollect(Integer id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCollect(Integer id, ShopMember member, Product product)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setMember(member);
/*  50 */     setProduct(product);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  80 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  88 */     this.id = id;
/*  89 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/*  99 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Date time)
/*     */   {
/* 107 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 115 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 123 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 131 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 139 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public ProductFashion getFashion()
/*     */   {
/* 147 */     return this.fashion;
/*     */   }
/*     */ 
/*     */   public void setFashion(ProductFashion fashion)
/*     */   {
/* 155 */     this.fashion = fashion;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 162 */     if (obj == null) return false;
/* 163 */     if (!(obj instanceof Collect)) return false;
/*     */ 
/* 165 */     Collect collect = (Collect)obj;
/* 166 */     if ((getId() == null) || (collect.getId() == null)) return false;
/* 167 */     return getId().equals(collect.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 173 */     if (-2147483648 == this.hashCode) {
/* 174 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 176 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 177 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 180 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 186 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCollect
 * JD-Core Version:    0.6.0
 */