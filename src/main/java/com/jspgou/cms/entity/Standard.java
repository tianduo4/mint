/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseStandard;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Standard extends BaseStandard
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Standard()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Standard(Long id)
/*    */   {
/* 29 */     super(id);
/*    */   }
/*    */ 
/*    */   public Standard(Long id, StandardType type, String name)
/*    */   {
/* 43 */     super(id, 
/* 42 */       type, 
/* 43 */       name);
/*    */   }
/*    */ 
/*    */   public void addToProductFashions(ProductFashion productFashion)
/*    */   {
/* 48 */     Set set = getFashions();
/* 49 */     if (set == null) {
/* 50 */       set = new HashSet();
/* 51 */       setFashions(set);
/*    */     }
/* 53 */     set.add(productFashion);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson(Long id, String name, String field, String remark, boolean dataType, Integer priority)
/*    */     throws JSONException
/*    */   {
/* 59 */     JSONObject json = new JSONObject();
/* 60 */     json.put("itemId", CommonUtils.parseId(getId()));
/* 61 */     json.put("itemName", CommonUtils.parseStr(getName()));
/* 62 */     json.put("itemColor", CommonUtils.parseStr(getColor()));
/* 63 */     json.put("itemPriority", CommonUtils.parseInteger(getPriority()));
/*    */ 
/* 65 */     json.put("id", CommonUtils.parseId(id));
/* 66 */     json.put("name", CommonUtils.parseStr(name));
/* 67 */     json.put("field", CommonUtils.parseStr(field));
/* 68 */     json.put("remark", CommonUtils.parseStr(remark));
/* 69 */     json.put("dataType", CommonUtils.parseBoolean(Boolean.valueOf(dataType)));
/* 70 */     json.put("priority", CommonUtils.parseInteger(priority));
/* 71 */     return json;
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson1() throws JSONException
/*    */   {
/* 76 */     JSONObject json = new JSONObject();
/* 77 */     json.put("itemId", CommonUtils.parseId(getId()));
/* 78 */     json.put("itemName", CommonUtils.parseStr(getName()));
/* 79 */     json.put("itemColor", CommonUtils.parseStr(getColor()));
/* 80 */     json.put("itemPriority", CommonUtils.parseInteger(getPriority()));
/* 81 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Standard
 * JD-Core Version:    0.6.0
 */