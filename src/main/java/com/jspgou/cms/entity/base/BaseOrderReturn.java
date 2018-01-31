/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.OrderReturnPicture;
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class BaseOrderReturn
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "OrderReturn";
/*  20 */   public static String PROP_SELLER_MONEY = "sellerMoney";
/*  21 */   public static String PROP_MONEY = "money";
/*  22 */   public static String PROP_SHOP_DICTIONARY = "shopDictionary";
/*  23 */   public static String PROP_ORDER = "order";
/*  24 */   public static String PROP_ALIPAY_ID = "alipayId";
/*  25 */   public static String PROP_RETURN_TYPE = "returnType";
/*  26 */   public static String PROP_CODE = "code";
/*  27 */   public static String PROP_STATUS = "status";
/*  28 */   public static String PROP_PAY_TYPE = "PayType";
/*  29 */   public static String PROP_FINISHED_TIME = "finishedTime";
/*  30 */   public static String PROP_CREATE_TIME = "createTime";
/*  31 */   public static String PROP_ID = "id";
/*  32 */   public static String PROP_REASON = "reason";
/*     */ 
/*  76 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String code;
/*     */   private String reason;
/*     */   private String alipayId;
/*     */   private Integer status;
/*     */   private Double money;
/*     */   private Double sellerMoney;
/*     */   private Integer returnType;
/*     */   private Date createTime;
/*     */   private Date finishedTime;
/*     */   private Integer payType;
/*     */   private String invoiceNo;
/*     */   private String shippingLogistics;
/*     */   private Order order;
/*     */   private ShopDictionary shopDictionary;
/*     */   private List<OrderReturnPicture> pictures;
/*     */ 
/*     */   public BaseOrderReturn()
/*     */   {
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrderReturn(Long id)
/*     */   {
/*  44 */     setId(id);
/*  45 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrderReturn(Long id, Order order, ShopDictionary shopDictionary, Integer payType, Integer status, Double money, Integer returnType, Date createTime)
/*     */   {
/*  61 */     setId(id);
/*  62 */     setOrder(order);
/*  63 */     setShopDictionary(shopDictionary);
/*  64 */     setPayType(payType);
/*  65 */     setStatus(status);
/*  66 */     setMoney(money);
/*  67 */     setReturnType(returnType);
/*  68 */     setCreateTime(createTime);
/*  69 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 111 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 119 */     this.id = id;
/* 120 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 130 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 138 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getReason()
/*     */   {
/* 146 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(String reason)
/*     */   {
/* 154 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   public String getAlipayId()
/*     */   {
/* 162 */     return this.alipayId;
/*     */   }
/*     */ 
/*     */   public void setAlipayId(String alipayId)
/*     */   {
/* 170 */     this.alipayId = alipayId;
/*     */   }
/*     */ 
/*     */   public Integer getStatus()
/*     */   {
/* 178 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status)
/*     */   {
/* 186 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Double getMoney()
/*     */   {
/* 194 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(Double money)
/*     */   {
/* 202 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public Double getSellerMoney()
/*     */   {
/* 210 */     return this.sellerMoney;
/*     */   }
/*     */ 
/*     */   public void setSellerMoney(Double sellerMoney)
/*     */   {
/* 218 */     this.sellerMoney = sellerMoney;
/*     */   }
/*     */ 
/*     */   public Integer getReturnType()
/*     */   {
/* 226 */     return this.returnType;
/*     */   }
/*     */ 
/*     */   public void setReturnType(Integer returnType)
/*     */   {
/* 234 */     this.returnType = returnType;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 242 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 250 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Date getFinishedTime()
/*     */   {
/* 258 */     return this.finishedTime;
/*     */   }
/*     */ 
/*     */   public void setFinishedTime(Date finishedTime)
/*     */   {
/* 266 */     this.finishedTime = finishedTime;
/*     */   }
/*     */ 
/*     */   public Order getOrder()
/*     */   {
/* 274 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(Order order)
/*     */   {
/* 282 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getShopDictionary()
/*     */   {
/* 290 */     return this.shopDictionary;
/*     */   }
/*     */ 
/*     */   public void setShopDictionary(ShopDictionary shopDictionary)
/*     */   {
/* 298 */     this.shopDictionary = shopDictionary;
/*     */   }
/*     */ 
/*     */   public List<OrderReturnPicture> getPictures()
/*     */   {
/* 306 */     return this.pictures;
/*     */   }
/*     */ 
/*     */   public void setPictures(List<OrderReturnPicture> pictures)
/*     */   {
/* 314 */     this.pictures = pictures;
/*     */   }
/*     */ 
/*     */   public void setPayType(Integer payType)
/*     */   {
/* 320 */     this.payType = payType;
/*     */   }
/*     */ 
/*     */   public Integer getPayType() {
/* 324 */     return this.payType;
/*     */   }
/*     */ 
/*     */   public void setInvoiceNo(String invoiceNo)
/*     */   {
/* 329 */     this.invoiceNo = invoiceNo;
/*     */   }
/*     */   public String getInvoiceNo() {
/* 332 */     return this.invoiceNo;
/*     */   }
/*     */   public void setShippingLogistics(String shippingLogistics) {
/* 335 */     this.shippingLogistics = shippingLogistics;
/*     */   }
/*     */ 
/*     */   public String getShippingLogistics() {
/* 339 */     return this.shippingLogistics;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj) {
/* 343 */     if (obj == null) return false;
/* 344 */     if (!(obj instanceof OrderReturn)) return false;
/*     */ 
/* 346 */     OrderReturn orderReturn = (OrderReturn)obj;
/* 347 */     if ((getId() == null) || (orderReturn.getId() == null)) return false;
/* 348 */     return getId().equals(orderReturn.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 354 */     if (-2147483648 == this.hashCode) {
/* 355 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 357 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 358 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 361 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 367 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseOrderReturn
 * JD-Core Version:    0.6.0
 */