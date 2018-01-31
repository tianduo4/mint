/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseProductTypeProperty;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class ProductTypeProperty extends BaseProductTypeProperty
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ProductTypeProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ProductTypeProperty(Long id)
/*    */   {
/* 27 */     super(id);
/*    */   }
/*    */ 
/*    */   public ProductTypeProperty(Long id, ProductType propertyType, String propertyName, String field, Integer isStart, Integer isNotNull, Integer sort, Integer dataType, Boolean single, Boolean category, Boolean custom)
/*    */   {
/* 57 */     super(id, 
/* 48 */       propertyType, 
/* 49 */       propertyName, 
/* 50 */       field, 
/* 51 */       isStart, 
/* 52 */       isNotNull, 
/* 53 */       sort, 
/* 54 */       dataType, 
/* 55 */       single, 
/* 56 */       category, 
/* 57 */       custom);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson(Long typeId, String name)
/*    */     throws JSONException
/*    */   {
/* 63 */     JSONObject json = new JSONObject();
/* 64 */     json.put("id", CommonUtils.parseId(getId()));
/* 65 */     json.put("field", CommonUtils.parseStr(getField()));
/* 66 */     json.put("dataType", CommonUtils.parseInteger(getDataType()));
/* 67 */     json.put("propertyName", CommonUtils.parseStr(getPropertyName()));
/* 68 */     json.put("sort", CommonUtils.parseInteger(getSort()));
/* 69 */     json.put("single", CommonUtils.parseBoolean(getSingle()));
/* 70 */     json.put("name", CommonUtils.parseStr(name));
/* 71 */     json.put("typeId", CommonUtils.parseLong(typeId));
/* 72 */     if ((getDataType() != null) && ((getDataType().intValue() == 6) || (getDataType().intValue() == 7) || (getDataType().intValue() == 8)))
/* 73 */       json.put("optValue", CommonUtils.parseStr(getOptValue()).split(","));
/*    */     else {
/* 75 */       json.put("optValue", new JSONArray());
/*    */     }
/* 77 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductTypeProperty
 * JD-Core Version:    0.6.0
 */