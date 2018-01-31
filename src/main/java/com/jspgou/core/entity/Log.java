/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.core.entity.base.BaseLog;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Log extends BaseLog
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int LOGIN_SUCCESS = 1;
/*    */   public static final int LOGIN_FAILURE = 2;
/*    */   public static final int OPERATING = 3;
/*    */   public static final String LOGIN_SUCCESS_TITLE = "login success";
/*    */   public static final String LOGIN_FAILURE_TITLE = "login failure";
/*    */ 
/*    */   public Log()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Log(Long id)
/*    */   {
/* 30 */     super(id);
/*    */   }
/*    */ 
/*    */   public Log(Long id, Integer category, Date time)
/*    */   {
/* 44 */     super(id, 
/* 43 */       category, 
/* 44 */       time);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 50 */     JSONObject json = new JSONObject();
/* 51 */     json.put("id", getId());
/* 52 */     json.put("username", getUser() != null ? CommonUtils.parseStr(getUser().getUsername()) : "");
/* 53 */     json.put("ip", CommonUtils.parseStr(getIp()));
/* 54 */     json.put("url", CommonUtils.parseStr(getUrl()));
/* 55 */     json.put("title", CommonUtils.parseStr(getTitle()));
/* 56 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 57 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Log
 * JD-Core Version:    0.6.0
 */