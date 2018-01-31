/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMoney;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseShopMoney
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopMoney";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_STATUS = "status";
/*  22 */   public static String PROP_MEMBER = "member";
/*  23 */   public static String PROP_MONEY = "money";
/*  24 */   public static String PROP_CREATE_TIME = "createTime";
/*  25 */   public static String PROP_ID = "id";
/*  26 */   public static String PROP_REMARK = "remark";
/*     */ 
/*  64 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Double money;
/*     */   private boolean status;
/*     */   private Date createTime;
/*     */   private String remark;
/*     */   private ShopMember member;
/*     */ 
/*     */   public BaseShopMoney()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMoney(Long id)
/*     */   {
/*  38 */     setId(id);
/*  39 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMoney(Long id, String name, Double money, boolean status, Date createTime)
/*     */   {
/*  52 */     setId(id);
/*  53 */     setName(name);
/*  54 */     setMoney(money);
/*  55 */     setStatus(status);
/*  56 */     setCreateTime(createTime);
/*  57 */     initialize();
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
/*     */   public Double getMoney()
/*     */   {
/* 123 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(Double money)
/*     */   {
/* 131 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public boolean isStatus()
/*     */   {
/* 139 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(boolean status)
/*     */   {
/* 147 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 155 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 163 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public String getRemark()
/*     */   {
/* 171 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark)
/*     */   {
/* 179 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 187 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 195 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 202 */     if (obj == null) return false;
/* 203 */     if (!(obj instanceof ShopMoney)) return false;
/*     */ 
/* 205 */     ShopMoney shopMoney = (ShopMoney)obj;
/* 206 */     if ((getId() == null) || (shopMoney.getId() == null)) return false;
/* 207 */     return getId().equals(shopMoney.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 213 */     if (-2147483648 == this.hashCode) {
/* 214 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 216 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 217 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 220 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 226 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopMoney
 * JD-Core Version:    0.6.0
 */