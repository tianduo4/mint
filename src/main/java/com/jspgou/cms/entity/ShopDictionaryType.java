/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShopDictionaryType;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ShopDictionaryType extends BaseShopDictionaryType
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopDictionaryType()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 31 */     JSONObject json = new JSONObject();
/* 32 */     json.put("id", CommonUtils.parseId(getId()));
/* 33 */     json.put("name", CommonUtils.parseStr(getName()));
/* 34 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopDictionaryType
 * JD-Core Version:    0.6.0
 */