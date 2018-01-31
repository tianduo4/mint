/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShipments;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Shipments extends BaseShipments
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Shipments()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Shipments(Long id)
/*    */   {
/* 26 */     super(id);
/*    */   }
/*    */ 
/*    */   public Shipments(Long id, Order indent, ShopAdmin shopAdmin, String waybill, String receiving, String comment)
/*    */   {
/* 46 */     super(id, 
/* 42 */       indent, 
/* 43 */       shopAdmin, 
/* 44 */       waybill, 
/* 45 */       receiving, 
/* 46 */       comment);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson()
/*    */     throws JSONException
/*    */   {
/* 54 */     JSONObject json = new JSONObject();
/* 55 */     json.put("id", CommonUtils.parseId(getId()));
/* 56 */     json.put("orderId", getIndent() == null ? "" : CommonUtils.parseLong(getIndent().getId()));
/* 57 */     json.put("code", getIndent() == null ? "" : CommonUtils.parseLong(getIndent().getCode()));
/* 58 */     json.put("receiveName", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveName()));
/* 59 */     json.put("receiveAddress", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveAddress()));
/* 60 */     json.put("receiveZip", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveZip()));
/* 61 */     json.put("receiveMobile", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveMobile()));
/* 62 */     json.put("receivePhone", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceivePhone()));
/* 63 */     json.put("username", getShopAdmin() == null ? "" : CommonUtils.parseStr(getShopAdmin().getUsername()));
/* 64 */     json.put("paymentStatus", getIndent() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getIndent().getPaymentStatus())));
/* 65 */     json.put("waybill", CommonUtils.parseStr(getWaybill()));
/* 66 */     json.put("money", CommonUtils.parseDouble(Double.valueOf(getMoney())));
/* 67 */     json.put("shippingName", CommonUtils.parseStr(getShippingName()));
/* 68 */     json.put("shippingMobile", CommonUtils.parseStr(getShippingMobile()));
/* 69 */     json.put("shipppingAddress", CommonUtils.parseStr(getShippingAddress()));
/* 70 */     json.put("isPrint", CommonUtils.parseBoolean(getIsPrint()));
/* 71 */     json.put("comment", CommonUtils.parseStr(getComment()));
/* 72 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Shipments
 * JD-Core Version:    0.6.0
 */