/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseOrder
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Order";
/*  20 */   public static String PROP_IP = "ip";
/*  21 */   public static String PROP_MEMBER = "member";
/*  22 */   public static String PROP_COMMENTS = "comments";
/*  23 */   public static String PROP_WEBSITE = "website";
/*  24 */   public static String PROP_RETURN_REASON = "returnReason";
/*  25 */   public static String PROP_FREIGHT = "freight";
/*  26 */   public static String PROP_CODE = "code";
/*  27 */   public static String PROP_PAYMENT = "payment";
/*  28 */   public static String PROP_PRODUCT_PRICE = "productPrice";
/*  29 */   public static String PROP_COUPON_PRICE = "couponPrice";
/*  30 */   public static String PROP_STATUS = "status";
/*  31 */   public static String PROP_SHIPPING_TIME = "shippingTime";
/*  32 */   public static String PROP_FINISHED_TIME = "finishedTime";
/*  33 */   public static String PROP_WEIGHT = "weight";
/*  34 */   public static String PROP_CREATE_TIME = "createTime";
/*  35 */   public static String PROP_ID = "id";
/*  36 */   public static String PROP_SHOP_DIRECTORY = "shopDirectory";
/*  37 */   public static String PROP_SHIPPING = "shipping";
/*  38 */   public static String PROP_PRODUCT_NAME = "productName";
/*  39 */   public static String PROP_LAST_MODIFIED = "lastModified";
/*  40 */   public static String PROP_SCORE = "score";
/*  41 */   public static String PROP_TOTAL = "total";
/*     */ 
/*  95 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Long code;
/*     */   private String comments;
/*     */   private String ip;
/*     */   private Date createTime;
/*     */   private Date shippingTime;
/*     */   private Date finishedTime;
/*     */   private Date lastModified;
/*     */   private Double productPrice;
/*     */   private Double freight;
/*     */   private Double total;
/*     */   private Integer score;
/*     */   private Double weight;
/*     */   private Double couponPrice;
/*     */   private String productName;
/*     */   private Integer paymentStatus;
/*     */   private Integer shippingStatus;
/*     */   private Integer status;
/*     */   private String receiveName;
/*     */   private String receiveAddress;
/*     */   private String receiveZip;
/*     */   private String receivePhone;
/*     */   private String receiveMobile;
/*     */   private String tradeNo;
/*     */   private String paymentCode;
/*     */   private Boolean delStatus;
/*     */   private Website website;
/*     */   private ShopMember member;
/*     */   private Payment payment;
/*     */   private Shipping shipping;
/*     */   private OrderReturn returnOrder;
/*     */   private Set<OrderItem> items;
/*     */ 
/*     */   public BaseOrder()
/*     */   {
/*  46 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrder(Long id)
/*     */   {
/*  53 */     setId(id);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseOrder(Long id, Website website, ShopMember member, Payment payment, Shipping shipping, Shipping shopDirectory, long code, String ip, Date createTime, Date lastModified, Double total, Integer score, Double weight)
/*     */   {
/*  75 */     setId(id);
/*  76 */     setWebsite(website);
/*  77 */     setMember(member);
/*  78 */     setPayment(payment);
/*  79 */     setShipping(shipping);
/*     */ 
/*  81 */     setCode(Long.valueOf(code));
/*  82 */     setIp(ip);
/*  83 */     setCreateTime(createTime);
/*  84 */     setLastModified(lastModified);
/*  85 */     setTotal(total);
/*  86 */     setScore(score);
/*  87 */     setWeight(weight);
/*  88 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 148 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 156 */     this.id = id;
/* 157 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Long getCode()
/*     */   {
/* 164 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(Long code)
/*     */   {
/* 172 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getComments()
/*     */   {
/* 179 */     return this.comments;
/*     */   }
/*     */ 
/*     */   public void setComments(String comments)
/*     */   {
/* 187 */     this.comments = comments;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/* 195 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip)
/*     */   {
/* 203 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 211 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 219 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Date getShippingTime()
/*     */   {
/* 226 */     return this.shippingTime;
/*     */   }
/*     */ 
/*     */   public void setShippingTime(Date shippingTime)
/*     */   {
/* 234 */     this.shippingTime = shippingTime;
/*     */   }
/*     */ 
/*     */   public Date getFinishedTime()
/*     */   {
/* 242 */     return this.finishedTime;
/*     */   }
/*     */ 
/*     */   public void setFinishedTime(Date finishedTime)
/*     */   {
/* 250 */     this.finishedTime = finishedTime;
/*     */   }
/*     */ 
/*     */   public Date getLastModified()
/*     */   {
/* 258 */     return this.lastModified;
/*     */   }
/*     */ 
/*     */   public void setLastModified(Date lastModified)
/*     */   {
/* 266 */     this.lastModified = lastModified;
/*     */   }
/*     */ 
/*     */   public Double getProductPrice()
/*     */   {
/* 274 */     return this.productPrice;
/*     */   }
/*     */ 
/*     */   public void setProductPrice(Double productPrice)
/*     */   {
/* 282 */     this.productPrice = productPrice;
/*     */   }
/*     */ 
/*     */   public Double getFreight()
/*     */   {
/* 290 */     return this.freight;
/*     */   }
/*     */ 
/*     */   public void setFreight(Double freight)
/*     */   {
/* 298 */     this.freight = freight;
/*     */   }
/*     */ 
/*     */   public Double getTotal()
/*     */   {
/* 306 */     return this.total;
/*     */   }
/*     */ 
/*     */   public void setTotal(Double total)
/*     */   {
/* 314 */     this.total = total;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 322 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 330 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Double getWeight()
/*     */   {
/* 338 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(Double weight)
/*     */   {
/* 346 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */   public Double getCouponPrice()
/*     */   {
/* 353 */     return this.couponPrice;
/*     */   }
/*     */ 
/*     */   public void setCouponPrice(Double couponPrice)
/*     */   {
/* 361 */     this.couponPrice = couponPrice;
/*     */   }
/*     */ 
/*     */   public String getProductName()
/*     */   {
/* 369 */     return this.productName;
/*     */   }
/*     */ 
/*     */   public void setProductName(String productName)
/*     */   {
/* 377 */     this.productName = productName;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 384 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 392 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 400 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 408 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Payment getPayment()
/*     */   {
/* 416 */     return this.payment;
/*     */   }
/*     */ 
/*     */   public void setPayment(Payment payment)
/*     */   {
/* 424 */     this.payment = payment;
/*     */   }
/*     */ 
/*     */   public Shipping getShipping()
/*     */   {
/* 432 */     return this.shipping;
/*     */   }
/*     */ 
/*     */   public void setShipping(Shipping shipping)
/*     */   {
/* 440 */     this.shipping = shipping;
/*     */   }
/*     */ 
/*     */   public Set<OrderItem> getItems()
/*     */   {
/* 447 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setItems(Set<OrderItem> items)
/*     */   {
/* 455 */     this.items = items;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 460 */     if (obj == null) return false;
/* 461 */     if (!(obj instanceof Order)) return false;
/*     */ 
/* 463 */     Order order = (Order)obj;
/* 464 */     if ((getId() == null) || (order.getId() == null)) return false;
/* 465 */     return getId().equals(order.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 471 */     if (-2147483648 == this.hashCode) {
/* 472 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 474 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 475 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 478 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 484 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setPaymentStatus(Integer paymentStatus) {
/* 488 */     this.paymentStatus = paymentStatus;
/*     */   }
/*     */ 
/*     */   public Integer getPaymentStatus() {
/* 492 */     return this.paymentStatus;
/*     */   }
/*     */ 
/*     */   public void setShippingStatus(Integer shippingStatus) {
/* 496 */     this.shippingStatus = shippingStatus;
/*     */   }
/*     */ 
/*     */   public Integer getShippingStatus() {
/* 500 */     return this.shippingStatus;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status) {
/* 504 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Integer getStatus() {
/* 508 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setReturnOrder(OrderReturn returnOrder) {
/* 512 */     this.returnOrder = returnOrder;
/*     */   }
/*     */ 
/*     */   public OrderReturn getReturnOrder() {
/* 516 */     return this.returnOrder;
/*     */   }
/*     */ 
/*     */   public void setReceiveName(String receiveName) {
/* 520 */     this.receiveName = receiveName;
/*     */   }
/*     */ 
/*     */   public String getReceiveName() {
/* 524 */     return this.receiveName;
/*     */   }
/*     */ 
/*     */   public void setReceiveAddress(String receiveAddress) {
/* 528 */     this.receiveAddress = receiveAddress;
/*     */   }
/*     */ 
/*     */   public String getReceiveAddress() {
/* 532 */     return this.receiveAddress;
/*     */   }
/*     */ 
/*     */   public void setReceiveZip(String receiveZip) {
/* 536 */     this.receiveZip = receiveZip;
/*     */   }
/*     */ 
/*     */   public String getReceiveZip() {
/* 540 */     return this.receiveZip;
/*     */   }
/*     */ 
/*     */   public void setReceivePhone(String receivePhone) {
/* 544 */     this.receivePhone = receivePhone;
/*     */   }
/*     */ 
/*     */   public String getReceivePhone() {
/* 548 */     return this.receivePhone;
/*     */   }
/*     */ 
/*     */   public void setReceiveMobile(String receiveMobile) {
/* 552 */     this.receiveMobile = receiveMobile;
/*     */   }
/*     */ 
/*     */   public String getReceiveMobile() {
/* 556 */     return this.receiveMobile;
/*     */   }
/*     */ 
/*     */   public void setTradeNo(String tradeNo) {
/* 560 */     this.tradeNo = tradeNo;
/*     */   }
/*     */ 
/*     */   public String getTradeNo() {
/* 564 */     return this.tradeNo;
/*     */   }
/*     */ 
/*     */   public void setPaymentCode(String paymentCode) {
/* 568 */     this.paymentCode = paymentCode;
/*     */   }
/*     */ 
/*     */   public String getPaymentCode() {
/* 572 */     return this.paymentCode;
/*     */   }
/*     */ 
/*     */   public void setDelStatus(Boolean delStatus) {
/* 576 */     this.delStatus = delStatus;
/*     */   }
/*     */   public Boolean getDelStatus() {
/* 579 */     return this.delStatus = this.delStatus;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseOrder
 * JD-Core Version:    0.6.0
 */