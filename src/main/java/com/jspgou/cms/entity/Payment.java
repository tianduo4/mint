/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BasePayment;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Payment extends BasePayment
/*    */ {
/*    */   public void addToShippings(Shipping shipping)
/*    */   {
/* 20 */     if (shipping == null) {
/* 21 */       return;
/*    */     }
/* 23 */     Set set = getShippings();
/* 24 */     if (set == null) {
/* 25 */       set = new HashSet();
/* 26 */       setShippings(set);
/*    */     }
/* 28 */     set.add(shipping);
/*    */   }
/*    */ 
/*    */   public Long[] getShippingIds() {
/* 32 */     Set shippings = getShippings();
/* 33 */     return Shipping.fetchIds(shippings);
/*    */   }
/*    */ 
/*    */   public Payment()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Payment(Long id)
/*    */   {
/* 47 */     super(id);
/*    */   }
/*    */ 
/*    */   public Payment(Long id, Website website, String name, Integer priority, Boolean disabled)
/*    */   {
/* 56 */     super(id, website, name, priority, disabled);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */     throws JSONException
/*    */   {
/* 62 */     JSONObject json = new JSONObject();
/* 63 */     json.put("id", CommonUtils.parseId(getId()));
/* 64 */     json.put("name", CommonUtils.parseStr(getName()));
/* 65 */     json.put("description", CommonUtils.parseStr(getDescription()));
/* 66 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/* 67 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/* 68 */     json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
/* 69 */     json.put("introduce", CommonUtils.parseStr(getIntroduce()));
/* 70 */     json.put("type", getType());
/* 71 */     json.put("shippingIds", getShippingIds() == null ? "" : getShippingIds());
/* 72 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Payment
 * JD-Core Version:    0.6.0
 */