/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BasePaymentPlugins;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class PaymentPlugins extends BasePaymentPlugins
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public PaymentPlugins()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PaymentPlugins(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public PaymentPlugins(Long id, String name, String code, Integer priority, Boolean disabled)
/*    */   {
/* 43 */     super(id, 
/* 40 */       name, 
/* 41 */       code, 
/* 42 */       priority, 
/* 43 */       disabled);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 47 */     JSONObject json = new JSONObject();
/* 48 */     json.put("id", CommonUtils.parseId(getId()));
/* 49 */     json.put("name", CommonUtils.parseStr(getName()));
/* 50 */     json.put("description", CommonUtils.parseStr(getDescription()));
/* 51 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 52 */     json.put("imgPath", CommonUtils.parseStr(getImgPath()));
/* 53 */     json.put("partner", CommonUtils.parseStr(getPartner()));
/* 54 */     json.put("sellerEmail", CommonUtils.parseStr(getSellerEmail()));
/* 55 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/* 56 */     json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
/* 57 */     json.put("sellerKey", CommonUtils.parseStr(getSellerKey()));
/* 58 */     json.put("code", CommonUtils.parseStr(getCode()));
/*    */ 
/* 60 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.PaymentPlugins
 * JD-Core Version:    0.6.0
 */