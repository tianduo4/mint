/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShopDictionary;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ShopDictionary extends BaseShopDictionary
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopDictionary()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopDictionary(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopDictionary(Long id, ShopDictionaryType shopDictionaryType, String name)
/*    */   {
/* 39 */     super(id, 
/* 38 */       shopDictionaryType, 
/* 39 */       name);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 43 */     JSONObject json = new JSONObject();
/* 44 */     json.put("id", CommonUtils.parseId(getId()));
/* 45 */     json.put("name", CommonUtils.parseStr(getName()));
/* 46 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 47 */     json.put("typeId", getShopDictionaryType() != null ? CommonUtils.parseLong(getShopDictionaryType().getId()) : "");
/* 48 */     json.put("typeName", getShopDictionaryType() != null ? CommonUtils.parseStr(getShopDictionaryType().getName()) : "");
/* 49 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopDictionary
 * JD-Core Version:    0.6.0
 */