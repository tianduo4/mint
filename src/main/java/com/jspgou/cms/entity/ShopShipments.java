/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShopShipments;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class ShopShipments extends BaseShopShipments
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopShipments()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopShipments(Long id)
/*    */   {
/* 21 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson()
/*    */     throws JSONException
/*    */   {
/* 27 */     JSONObject json = new JSONObject();
/* 28 */     json.put("id", CommonUtils.parseLong(getId()));
/* 29 */     json.put("name", CommonUtils.parseStr(getName()));
/* 30 */     json.put("mobile", CommonUtils.parseStr(getMobile()));
/* 31 */     json.put("address", CommonUtils.parseStr(getAddress()));
/* 32 */     json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
/* 33 */     return json;
/*    */   }
/*    */ 
/*    */   public ShopShipments(Long id, String name, String mobile, String address, Boolean isDefault)
/*    */   {
/* 50 */     super(id, 
/* 47 */       name, 
/* 48 */       mobile, 
/* 49 */       address, 
/* 50 */       isDefault);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopShipments
 * JD-Core Version:    0.6.0
 */