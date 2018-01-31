/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseProductStandard;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class ProductStandard extends BaseProductStandard
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public JSONObject convertToJson()
/*    */     throws JSONException
/*    */   {
/* 21 */     JSONObject json = new JSONObject();
/* 22 */     json.put("id", CommonUtils.parseId(getId()));
/* 23 */     json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
/* 24 */     json.put("standardId", getStandard() == null ? "" : CommonUtils.parseLong(getStandard().getId()));
/* 25 */     json.put("standardName", getStandard() == null ? "" : CommonUtils.parseStr(getStandard().getName()));
/* 26 */     json.put("imgPath", CommonUtils.parseStr(getImgPath()));
/* 27 */     return json;
/*    */   }
/*    */ 
/*    */   public ProductStandard()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ProductStandard(Long id)
/*    */   {
/* 39 */     super(id);
/*    */   }
/*    */ 
/*    */   public ProductStandard(Long id, Product product, Standard standard, StandardType type, String imgPath, boolean dataType)
/*    */   {
/* 59 */     super(id, 
/* 55 */       product, 
/* 56 */       standard, 
/* 57 */       type, 
/* 58 */       imgPath, 
/* 59 */       dataType);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductStandard
 * JD-Core Version:    0.6.0
 */