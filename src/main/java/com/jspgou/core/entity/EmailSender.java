/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.core.entity.base.BaseEmailSender;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class EmailSender extends BaseEmailSender
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public JSONObject convertToJson()
/*    */     throws JSONException
/*    */   {
/* 18 */     JSONObject json = new JSONObject();
/* 19 */     json.put("host", CommonUtils.parseStr(getHost()));
/* 20 */     json.put("username", CommonUtils.parseStr(getUsername()));
/* 21 */     json.put("password", CommonUtils.parseStr(getPassword()));
/* 22 */     json.put("personal", CommonUtils.parseStr(getPersonal()));
/* 23 */     json.put("encoding", CommonUtils.parseStr(getEncoding()));
/* 24 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.EmailSender
 * JD-Core Version:    0.6.0
 */