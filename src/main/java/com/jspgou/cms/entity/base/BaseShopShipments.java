/*    */ package com.jspgou.cms.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseShopShipments
/*    */   implements Serializable
/*    */ {
/*  7 */   public static String REF = "ShopShipments";
/*  8 */   public static String PROP_ID = "id";
/*  9 */   public static String PROP_NAME = "name";
/* 10 */   public static String PROP_MOBILE = "mobile";
/* 11 */   public static String PROP_ADDRESS = "address";
/*    */ 
/* 40 */   private int hashCode = -2147483648;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String mobile;
/*    */   private String address;
/*    */   private Boolean isDefault;
/*    */ 
/*    */   public BaseShopShipments()
/*    */   {
/* 15 */     initialize();
/*    */   }
/*    */   public BaseShopShipments(Long id) {
/* 18 */     setId(id);
/* 19 */     initialize();
/*    */   }
/*    */ 
/*    */   public BaseShopShipments(Long id, String name, String mobile, String address, Boolean isDefault)
/*    */   {
/* 28 */     setId(id);
/* 29 */     setName(name);
/* 30 */     setMobile(mobile);
/* 31 */     setAddress(address);
/* 32 */     setIsDefault(isDefault);
/*    */ 
/* 34 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 49 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 52 */     this.id = id;
/* 53 */     this.hashCode = -2147483648;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 57 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 61 */     this.name = name;
/*    */   }
/*    */   public String getMobile() {
/* 64 */     return this.mobile;
/*    */   }
/*    */ 
/*    */   public void setMobile(String mobile) {
/* 68 */     this.mobile = mobile;
/*    */   }
/*    */ 
/*    */   public String getAddress() {
/* 72 */     return this.address;
/*    */   }
/*    */   public void setAddress(String address) {
/* 75 */     this.address = address;
/*    */   }
/*    */   public Boolean getIsDefault() {
/* 78 */     return this.isDefault;
/*    */   }
/*    */   public void setIsDefault(Boolean isDefault) {
/* 81 */     this.isDefault = isDefault;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopShipments
 * JD-Core Version:    0.6.0
 */