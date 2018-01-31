/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseAdspace;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Adspace extends BaseAdspace
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Adspace()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Adspace(Integer id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson() throws JSONException
/*    */   {
/* 30 */     JSONObject json = new JSONObject();
/* 31 */     json.put("id", CommonUtils.parseId(getId()));
/* 32 */     json.put("name", CommonUtils.parseStr(getName()));
/* 33 */     json.put("description", CommonUtils.parseStr(getName()));
/* 34 */     json.put("enabled", CommonUtils.parseBoolean(getEnabled()));
/* 35 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Adspace
 * JD-Core Version:    0.6.0
 */