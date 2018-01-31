/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseOrder;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Order extends BaseOrder
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public int calWeight()
/*     */   {
/*  28 */     int weight = 0;
/*  29 */     for (OrderItem item : getItems()) {
/*  30 */       weight += item.getProduct().getWeight().intValue();
/*     */     }
/*  32 */     return weight;
/*     */   }
/*     */ 
/*     */   public Double getWeightForFreight()
/*     */   {
/*  41 */     Double weight = Double.valueOf(0.0D);
/*  42 */     for (OrderItem item : getItems()) {
/*  43 */       weight = Double.valueOf(weight.doubleValue() + item.getWeightForFreight());
/*     */     }
/*  45 */     return weight;
/*     */   }
/*     */ 
/*     */   public int getCountForFreight()
/*     */   {
/*  54 */     int count = 0;
/*  55 */     for (OrderItem item : getItems()) {
/*  56 */       count += item.getCountForFreigt();
/*     */     }
/*  58 */     return count;
/*     */   }
/*     */ 
/*     */   public void addToItems(OrderItem item) {
/*  62 */     Set items = getItems();
/*  63 */     if (items == null) {
/*  64 */       items = new LinkedHashSet();
/*  65 */       setItems(items);
/*     */     }
/*  67 */     item.setOrdeR(this);
/*  68 */     items.add(item);
/*     */   }
/*     */ 
/*     */   public void init() {
/*  72 */     Date now = new Timestamp(System.currentTimeMillis());
/*  73 */     if (getCreateTime() == null) {
/*  74 */       setCreateTime(now);
/*     */     }
/*  76 */     if (getLastModified() == null)
/*  77 */       setLastModified(now);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson() throws JSONException
/*     */   {
/*  82 */     JSONObject json = new JSONObject();
/*  83 */     json.put("id", CommonUtils.parseId(getId()));
/*  84 */     json.put("userName", CommonUtils.parseStr(getMember().getUsername()));
/*  85 */     json.put("receiveName", CommonUtils.parseStr(getReceiveName()));
/*  86 */     json.put("total", CommonUtils.parseDouble(getTotal()));
/*  87 */     json.put("freight", CommonUtils.parseDouble(getFreight()));
/*  88 */     json.put("createTime", CommonUtils.parseDate(getCreateTime(), DateUtils.COMMON_FORMAT_STR));
/*  89 */     json.put("shippingName", getShipping() == null ? "" : CommonUtils.parseStr(getShipping().getName()));
/*     */ 
/*  91 */     json.put("shippingId", getShipping() == null ? "" : CommonUtils.parseLong(getShipping().getId()));
/*  92 */     json.put("paymentId", getPayment() == null ? "" : CommonUtils.parseLong(getPayment().getId()));
/*     */ 
/*  94 */     json.put("paymentName", getPayment() == null ? "" : CommonUtils.parseStr(getPayment().getName()));
/*  95 */     json.put("orderStatus", CommonUtils.parseInteger(getStatus()));
/*  96 */     json.put("paymentStatus", CommonUtils.parseInteger(getPaymentStatus()));
/*  97 */     json.put("shippingStatus", CommonUtils.parseInteger(getShippingStatus()));
/*  98 */     Set order = getItems();
/*  99 */     JSONArray jsons = new JSONArray();
/* 100 */     for (OrderItem item : order) {
/* 101 */       JSONObject obj = new JSONObject();
/*     */ 
/* 103 */       obj.put("productCode", CommonUtils.parseLong(item.getProduct().getProductExt().getCode()));
/* 104 */       obj.put("coverImg", CommonUtils.parseStr(item.getProduct().getProductExt().getCoverImg()));
/* 105 */       obj.put("productName", CommonUtils.parseStr(item.getProduct().getName()));
/* 106 */       obj.put("itemWeight", CommonUtils.parseInteger(item.getProduct().getWeight()));
/* 107 */       obj.put("itemId", CommonUtils.parseId(item.getId()));
/* 108 */       obj.put("itemPrice", CommonUtils.parseDouble(item.getSalePrice()));
/* 109 */       obj.put("itemCount", CommonUtils.parseInteger(item.getCount()));
/* 110 */       jsons.put(obj);
/*     */     }
/* 112 */     json.put("product", jsons);
/* 113 */     json.put("code", CommonUtils.parseLong(getCode()));
/* 114 */     json.put("receiveMobile", CommonUtils.parseStr(getReceiveMobile()));
/* 115 */     json.put("receivePhone", CommonUtils.parseStr(getReceivePhone()));
/* 116 */     json.put("receiveZip", CommonUtils.parseStr(getReceiveZip()));
/* 117 */     json.put("receiveAddress", CommonUtils.parseStr(getReceiveAddress()));
/* 118 */     json.put("comments", CommonUtils.parseStr(getComments()));
/*     */ 
/* 121 */     json.put("returnCode", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getCode()));
/* 122 */     json.put("returnTime", getReturnOrder() == null ? "" : CommonUtils.parseDate(getReturnOrder().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
/* 123 */     json.put("returnType", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getReturnType())));
/* 124 */     json.put("status", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getStatus())));
/* 125 */     json.put("money", getReturnOrder() == null ? "" : CommonUtils.parseDouble(getReturnOrder().getMoney()));
/* 126 */     json.put("reason", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getReason()));
/* 127 */     json.put("invoiceNo", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getInvoiceNo()));
/* 128 */     json.put("shippingLogistics", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getShippingLogistics()));
/* 129 */     json.put("returnPayType", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getPayType())));
/* 130 */     json.put("returnReasonTypeName", (getReturnOrder() != null) && (getReturnOrder().getShopDictionary() != null) ? CommonUtils.parseStr(getReturnOrder().getShopDictionary().getName()) : "");
/*     */ 
/* 132 */     return json;
/*     */   }
/*     */ 
/*     */   public Order()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Order(Long id)
/*     */   {
/* 144 */     super(id);
/*     */   }
/*     */ 
/*     */   public Order(Long id, Website website, ShopMember member, Payment payment, Shipping shipping, Shipping shopDirectory, Long code, String ip, Date createTime, Date lastModified, Double total, Integer score, Double weight, Integer status)
/*     */   {
/* 179 */     super(id, 
/* 168 */       website, 
/* 169 */       member, 
/* 170 */       payment, 
/* 171 */       shipping, 
/* 172 */       shopDirectory, 
/* 173 */       code.longValue(), 
/* 174 */       ip, 
/* 175 */       createTime, 
/* 176 */       lastModified, 
/* 177 */       total, 
/* 178 */       score, 
/* 179 */       weight);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Order
 * JD-Core Version:    0.6.0
 */