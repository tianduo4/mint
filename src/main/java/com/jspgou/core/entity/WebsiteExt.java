/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.core.entity.base.BaseWebsiteExt;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class WebsiteExt extends BaseWebsiteExt
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public WebsiteExt()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WebsiteExt(String id)
/*    */   {
/* 21 */     super(id);
/*    */   }
/* 25 */   public static class ConfigLogin { public static String LOGIN_ERROR_INTERVAL = "login_error_interval";
/* 26 */     public static String LOGIN_ERROR_TIMES = "login_error_times";
/*    */     private Map<String, String> attr;
/*    */ 
/*    */     public static ConfigLogin create(Map<String, String> map) {
/* 31 */       ConfigLogin configLogin = new ConfigLogin();
/* 32 */       configLogin.setAttr(map);
/* 33 */       return configLogin;
/*    */     }
/*    */ 
/*    */     public Map<String, String> getAttr() {
/* 37 */       if (this.attr == null) {
/* 38 */         this.attr = new HashMap();
/*    */       }
/* 40 */       return this.attr;
/*    */     }
/*    */ 
/*    */     public void setAttr(Map<String, String> attr) {
/* 44 */       this.attr = attr;
/*    */     }
/*    */ 
/*    */     public Integer getErrorInterval() {
/* 48 */       String interval = (String)getAttr().get(LOGIN_ERROR_INTERVAL);
/* 49 */       if (NumberUtils.isDigits(interval)) {
/* 50 */         return Integer.valueOf(Integer.parseInt(interval));
/*    */       }
/*    */ 
/* 53 */       return Integer.valueOf(30);
/*    */     }
/*    */ 
/*    */     public void setErrorInterval(Integer errorInterval)
/*    */     {
/* 58 */       if (errorInterval != null){

/* 59 */         getAttr().put(LOGIN_ERROR_INTERVAL, errorInterval.toString());
              }
/*    */       else{
/* 61 */         getAttr().put(LOGIN_ERROR_INTERVAL, null);
               }
/*    */     }
/*    */ 
/*    */     public Integer getErrorTimes()
/*    */     {
/* 66 */       String times = (String)getAttr().get(LOGIN_ERROR_TIMES);
/* 67 */       if (NumberUtils.isDigits(times)) {
/* 68 */         return Integer.valueOf(Integer.parseInt(times));
/*    */       }
/*    */ 
/* 71 */       return Integer.valueOf(3);
/*    */     }
/*    */ 
/*    */     public void setErrorTimes(Integer errorTimes)
/*    */     {
/* 76 */       if (errorTimes != null){
/* 77 */         getAttr().put(LOGIN_ERROR_TIMES, errorTimes.toString());
              }
/*    */       else{
/* 79 */         getAttr().put(LOGIN_ERROR_TIMES, null);
               }
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.WebsiteExt
 * JD-Core Version:    0.6.0
 */