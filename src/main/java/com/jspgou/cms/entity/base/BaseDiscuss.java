/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseDiscuss
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Discuss";
/*  24 */   public static String PROP_MEMBER = "member";
/*  25 */   public static String PROP_TIME = "time";
/*  26 */   public static String PROP_PRODUCT = "product";
/*  27 */   public static String PROP_ID = "id";
/*  28 */   public static String PROP_CONTENT = "content";
/*     */ 
/*  64 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String content;
/*     */   private Date time;
/*     */   private String replay;
/*     */   private String discussType;
/*     */   private ShopMember member;
/*     */   private Product product;
/*     */ 
/*     */   public BaseDiscuss()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseDiscuss(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseDiscuss(Long id, ShopMember member, Product product, String content)
/*     */   {
/*  53 */     setId(id);
/*  54 */     setMember(member);
/*  55 */     setProduct(product);
/*  56 */     setContent(content);
/*  57 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  90 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  98 */     this.id = id;
/*  99 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 107 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 115 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/* 123 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Date time)
/*     */   {
/* 131 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public String getDiscussType()
/*     */   {
/* 136 */     return this.discussType;
/*     */   }
/*     */ 
/*     */   public void setDiscussType(String discussType) {
/* 140 */     this.discussType = discussType;
/*     */   }
/*     */ 
/*     */   public String getReplay() {
/* 144 */     return this.replay;
/*     */   }
/*     */ 
/*     */   public void setReplay(String replay) {
/* 148 */     this.replay = replay;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 155 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 163 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 171 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 179 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 186 */     if (obj == null) return false;
/* 187 */     if (!(obj instanceof Discuss)) return false;
/*     */ 
/* 189 */     Discuss discuss = (Discuss)obj;
/* 190 */     if ((getId() == null) || (discuss.getId() == null)) return false;
/* 191 */     return getId().equals(discuss.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 197 */     if (-2147483648 == this.hashCode) {
/* 198 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 200 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 201 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 204 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 210 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseDiscuss
 * JD-Core Version:    0.6.0
 */