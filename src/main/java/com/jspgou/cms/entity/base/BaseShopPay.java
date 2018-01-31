/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopPay;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopPay
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopPay";
/*  20 */   public static String PROP_BANK_NUM = "bankNum";
/*  21 */   public static String PROP_BANKID = "bankid";
/*  22 */   public static String PROP_ADDRESS = "address";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_BANKKEY = "bankkey";
/*     */ 
/*  44 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String address;
/*     */   private String bankNum;
/*     */   private String bankid;
/*     */   private String bankkey;
/*     */ 
/*     */   public BaseShopPay()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopPay(Integer id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  64 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  72 */     this.id = id;
/*  73 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/*  83 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address)
/*     */   {
/*  91 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getBankNum()
/*     */   {
/*  99 */     return this.bankNum;
/*     */   }
/*     */ 
/*     */   public void setBankNum(String bankNum)
/*     */   {
/* 107 */     this.bankNum = bankNum;
/*     */   }
/*     */ 
/*     */   public String getBankid()
/*     */   {
/* 115 */     return this.bankid;
/*     */   }
/*     */ 
/*     */   public void setBankid(String bankid)
/*     */   {
/* 123 */     this.bankid = bankid;
/*     */   }
/*     */ 
/*     */   public String getBankkey()
/*     */   {
/* 131 */     return this.bankkey;
/*     */   }
/*     */ 
/*     */   public void setBankkey(String bankkey)
/*     */   {
/* 139 */     this.bankkey = bankkey;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 146 */     if (obj == null) return false;
/* 147 */     if (!(obj instanceof ShopPay)) return false;
/*     */ 
/* 149 */     ShopPay shopPay = (ShopPay)obj;
/* 150 */     if ((getId() == null) || (shopPay.getId() == null)) return false;
/* 151 */     return getId().equals(shopPay.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 157 */     if (-2147483648 == this.hashCode) {
/* 158 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 160 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 161 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 164 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 170 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopPay
 * JD-Core Version:    0.6.0
 */