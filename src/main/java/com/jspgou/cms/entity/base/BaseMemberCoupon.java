/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.cms.entity.MemberCoupon;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseMemberCoupon
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "MemberCoupon";
/*  18 */   public static String PROP_MEMBER = "member";
/*  19 */   public static String PROP_COUPON = "coupon";
/*  20 */   public static String PROP_ID = "id";
/*  21 */   public static String PROP_ISUSE = "isuse";
/*     */ 
/*  41 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Boolean isuse;
/*     */   private ShopMember member;
/*     */   private Coupon coupon;
/*     */ 
/*     */   public BaseMemberCoupon()
/*     */   {
/*  26 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseMemberCoupon(Long id)
/*     */   {
/*  33 */     setId(id);
/*  34 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  62 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  70 */     this.id = id;
/*  71 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Boolean getIsuse()
/*     */   {
/*  81 */     return this.isuse;
/*     */   }
/*     */ 
/*     */   public void setIsuse(Boolean isuse)
/*     */   {
/*  89 */     this.isuse = isuse;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/*  97 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 105 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Coupon getCoupon()
/*     */   {
/* 113 */     return this.coupon;
/*     */   }
/*     */ 
/*     */   public void setCoupon(Coupon coupon)
/*     */   {
/* 121 */     this.coupon = coupon;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 128 */     if (obj == null) return false;
/* 129 */     if (!(obj instanceof MemberCoupon)) return false;
/*     */ 
/* 131 */     MemberCoupon memberCoupon = (MemberCoupon)obj;
/* 132 */     if ((getId() == null) || (memberCoupon.getId() == null)) return false;
/* 133 */     return getId().equals(memberCoupon.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 139 */     if (-2147483648 == this.hashCode) {
/* 140 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 142 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 143 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 146 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 152 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseMemberCoupon
 * JD-Core Version:    0.6.0
 */