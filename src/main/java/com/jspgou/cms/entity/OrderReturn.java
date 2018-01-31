/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseOrderReturn;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class OrderReturn extends BaseOrderReturn
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public OrderReturn()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OrderReturn(Long id)
/*     */   {
/*  30 */     super(id);
/*     */   }
/*     */ 
/*     */   public OrderReturn(Long id, Order order, ShopDictionary shopDictionary, Integer payType, Integer status, Double money, Integer returnType, Date createTime)
/*     */   {
/*  54 */     super(id, 
/*  48 */       order, 
/*  49 */       shopDictionary, 
/*  50 */       payType, 
/*  51 */       status, 
/*  52 */       money, 
/*  53 */       returnType, 
/*  54 */       createTime);
/*     */   }
/*     */ 
/*     */   public void addToPictures(String path, String desc)
/*     */   {
/*  65 */     List list = getPictures();
/*  66 */     if (list == null) {
/*  67 */       list = new ArrayList();
/*  68 */       setPictures(list);
/*     */     }
/*  70 */     OrderReturnPicture cp = new OrderReturnPicture();
/*  71 */     cp.setImgPath(path);
/*  72 */     cp.setDescription(desc);
/*  73 */     list.add(cp);
/*     */   }
/*     */ 
/*     */   public OrderReturnStatus getOrderReturnStatus()
/*     */   {
/*  82 */     Integer status = getReturnType();
/*  83 */     if (status != null) {
/*  84 */       return OrderReturnStatus.valueOf(status.intValue());
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson()
/*     */     throws JSONException
/*     */   {
/* 131 */     JSONObject json = new JSONObject();
/* 132 */     json.put("id", CommonUtils.parseId(getId()));
/* 133 */     json.put("code", CommonUtils.parseStr(getCode()));
/* 134 */     json.put("orderId", getOrder() == null ? "" : CommonUtils.parseLong(getOrder().getId()));
/* 135 */     json.put("reason", CommonUtils.parseStr(getReason()));
/* 136 */     json.put("createTime", CommonUtils.parseDate(getCreateTime(), DateUtils.COMMON_FORMAT_STR));
/* 137 */     json.put("status", CommonUtils.parseInteger(getStatus()));
/* 138 */     json.put("money", CommonUtils.parseDouble(getMoney()));
/* 139 */     json.put("returnType", CommonUtils.parseInteger(getReturnType()));
/* 140 */     json.put("orderCode", getOrder() == null ? "" : CommonUtils.parseLong(getOrder().getCode()));
/* 141 */     json.put("username", getOrder().getMember().getUsername() == null ? "" : CommonUtils.parseStr(getOrder().getMember().getUsername()));
/* 142 */     json.put("orderTime", getOrder() == null ? "" : CommonUtils.parseDate(getOrder().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
/* 143 */     return json;
/*     */   }
/*     */ 
/*     */   public static enum OrderReturnStatus
/*     */   {
/* 102 */     SELLER_NODELIVERY_RETURN(1), 
/*     */ 
/* 106 */     SELLER_DELIVERY_RETURN(2);
/*     */ 
/*     */     private int value;
/*     */ 
/* 110 */     private OrderReturnStatus(int value) { this.value = value;
/*     */     }
/*     */ 
/*     */     public int getValue()
/*     */     {
/* 116 */       return this.value;
/*     */     }
/*     */ 
/*     */     public static OrderReturnStatus valueOf(int value) {
/* 120 */       for (OrderReturnStatus s : values()) {
/* 121 */         if (s.getValue() == value) {
/* 122 */           return s;
/*     */         }
/*     */       }
/* 125 */       throw new IllegalArgumentException("Connot find value " + value + 
/* 126 */         " in eunu OrderStatus.");
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.OrderReturn
 * JD-Core Version:    0.6.0
 */