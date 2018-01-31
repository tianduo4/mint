/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.Shipments;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShipments
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Shipments";
/*  20 */   public static String PROP_RECEIVING = "receiving";
/*  21 */   public static String PROP_MONEY = "money";
/*  22 */   public static String PROP_COMMENT = "comment";
/*  23 */   public static String PROP_WAYBILL = "waybill";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_SHOP_ADMIN = "shopAdmin";
/*  26 */   public static String PROP_INDENT = "indent";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String waybill;
/*     */   private double money;
/*     */   private String receiving;
/*     */   private String comment;
/*     */   private String shippingName;
/*     */   private String shippingMobile;
/*     */   private String shippingAddress;
/*     */   private Boolean isPrint;
/*     */   private Order indent;
/*     */   private ShopAdmin shopAdmin;
/*     */ 
/*     */   public BaseShipments()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShipments(Long id)
/*     */   {
/*  38 */     setId(id);
/*  39 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShipments(Long id, Order indent, ShopAdmin shopAdmin, String waybill, String receiving, String comment)
/*     */   {
/*  53 */     setId(id);
/*  54 */     setIndent(indent);
/*  55 */     setShopAdmin(shopAdmin);
/*  56 */     setWaybill(waybill);
/*  57 */     setReceiving(receiving);
/*  58 */     setComment(comment);
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  94 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 102 */     this.id = id;
/* 103 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getWaybill()
/*     */   {
/* 113 */     return this.waybill;
/*     */   }
/*     */ 
/*     */   public void setWaybill(String waybill)
/*     */   {
/* 121 */     this.waybill = waybill;
/*     */   }
/*     */ 
/*     */   public double getMoney()
/*     */   {
/* 129 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(double money)
/*     */   {
/* 137 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public String getReceiving()
/*     */   {
/* 145 */     return this.receiving;
/*     */   }
/*     */ 
/*     */   public void setReceiving(String receiving)
/*     */   {
/* 153 */     this.receiving = receiving;
/*     */   }
/*     */ 
/*     */   public String getComment()
/*     */   {
/* 161 */     return this.comment;
/*     */   }
/*     */ 
/*     */   public void setComment(String comment)
/*     */   {
/* 169 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */   public String getShippingName()
/*     */   {
/* 174 */     return this.shippingName;
/*     */   }
/*     */ 
/*     */   public void setShippingName(String shippingName) {
/* 178 */     this.shippingName = shippingName;
/*     */   }
/*     */ 
/*     */   public String getShippingMobile() {
/* 182 */     return this.shippingMobile;
/*     */   }
/*     */ 
/*     */   public void setShippingMobile(String shippingMobile) {
/* 186 */     this.shippingMobile = shippingMobile;
/*     */   }
/*     */ 
/*     */   public String getShippingAddress() {
/* 190 */     return this.shippingAddress;
/*     */   }
/*     */ 
/*     */   public void setShippingAddress(String shippingAddress) {
/* 194 */     this.shippingAddress = shippingAddress;
/*     */   }
/*     */ 
/*     */   public Boolean getIsPrint() {
/* 198 */     return this.isPrint;
/*     */   }
/*     */ 
/*     */   public void setIsPrint(Boolean isPrint)
/*     */   {
/* 206 */     this.isPrint = isPrint;
/*     */   }
/*     */ 
/*     */   public Order getIndent()
/*     */   {
/* 214 */     return this.indent;
/*     */   }
/*     */ 
/*     */   public void setIndent(Order indent)
/*     */   {
/* 222 */     this.indent = indent;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getShopAdmin()
/*     */   {
/* 230 */     return this.shopAdmin;
/*     */   }
/*     */ 
/*     */   public void setShopAdmin(ShopAdmin shopAdmin)
/*     */   {
/* 238 */     this.shopAdmin = shopAdmin;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 245 */     if (obj == null) return false;
/* 246 */     if (!(obj instanceof Shipments)) return false;
/*     */ 
/* 248 */     Shipments shipments = (Shipments)obj;
/* 249 */     if ((getId() == null) || (shipments.getId() == null)) return false;
/* 250 */     return getId().equals(shipments.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 256 */     if (-2147483648 == this.hashCode) {
/* 257 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 259 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 260 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 263 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 269 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShipments
 * JD-Core Version:    0.6.0
 */