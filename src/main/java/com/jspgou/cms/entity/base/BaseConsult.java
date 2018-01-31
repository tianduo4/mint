/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseConsult
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Consult";
/*  20 */   public static String PROP_MEMBER = "member";
/*  21 */   public static String PROP_TIME = "time";
/*  22 */   public static String PROP_PRODUCT = "product";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_CONSULT = "consult";
/*  25 */   public static String PROP_ADMIN_REPLAY = "adminReplay";
/*     */ 
/*  59 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String consult;
/*     */   private String adminReplay;
/*     */   private Date time;
/*     */   private ShopMember member;
/*     */   private Product product;
/*     */ 
/*     */   public BaseConsult()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseConsult(Long id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseConsult(Long id, ShopMember member, Product product)
/*     */   {
/*  49 */     setId(id);
/*  50 */     setMember(member);
/*  51 */     setProduct(product);
/*  52 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  82 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  90 */     this.id = id;
/*  91 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getConsult()
/*     */   {
/* 101 */     return this.consult;
/*     */   }
/*     */ 
/*     */   public void setConsult(String consult)
/*     */   {
/* 109 */     this.consult = consult;
/*     */   }
/*     */ 
/*     */   public String getAdminReplay()
/*     */   {
/* 117 */     return this.adminReplay;
/*     */   }
/*     */ 
/*     */   public void setAdminReplay(String adminReplay)
/*     */   {
/* 125 */     this.adminReplay = adminReplay;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/* 133 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Date time)
/*     */   {
/* 141 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 149 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 157 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 165 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 173 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 180 */     if (obj == null) return false;
/* 181 */     if (!(obj instanceof Consult)) return false;
/*     */ 
/* 183 */     Consult consult = (Consult)obj;
/* 184 */     if ((getId() == null) || (consult.getId() == null)) return false;
/* 185 */     return getId().equals(consult.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 191 */     if (-2147483648 == this.hashCode) {
/* 192 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 194 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 195 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 198 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 204 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseConsult
 * JD-Core Version:    0.6.0
 */