/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseWebserviceAuth;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class WebserviceAuth extends BaseWebserviceAuth
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public boolean getEnable()
/*    */   {
/* 15 */     return super.isEnable();
/*    */   }
/*    */ 
/*    */   public WebserviceAuth()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WebserviceAuth(Integer id)
/*    */   {
/* 27 */     super(id);
/*    */   }
/*    */ 
/*    */   public WebserviceAuth(Integer id, String username, String password, String system, boolean enable)
/*    */   {
/* 45 */     super(id, 
/* 42 */       username, 
/* 43 */       password, 
/* 44 */       system, 
/* 45 */       enable);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 49 */     JSONObject json = new JSONObject();
/* 50 */     json.put("id", CommonUtils.parseId(getId()));
/* 51 */     json.put("username", CommonUtils.parseStr(getUsername()));
/* 52 */     json.put("password", "");
/* 53 */     json.put("system", CommonUtils.parseStr(getSystem()));
/* 54 */     json.put("enable", CommonUtils.parseBoolean(Boolean.valueOf(getEnable())));
/*    */ 
/* 56 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.WebserviceAuth
 * JD-Core Version:    0.6.0
 */