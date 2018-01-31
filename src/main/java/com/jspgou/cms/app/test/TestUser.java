/*    */ package com.jspgou.cms.app.test;
/*    */ 
/*    */ import com.jspgou.common.util.AES128Util;
/*    */ import com.jspgou.common.util.Num62;
/*    */ import com.jspgou.common.web.HttpClientUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class TestUser
/*    */ {
/* 65 */   public static String appId = "4505818634615678";
/* 66 */   private static String appKey = "2sHKEWFCxNofq1EbwusziayFJlIfRJ8o";
/* 67 */   private static String aesKey = "S9u978Q31NGPGc5H";
/* 68 */   private static String ivKey = "X83yESM9iShLxfwS";
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 22 */     testLogin();
/*    */   }
/*    */ 
/*    */   private static String testLogin() {
/* 26 */     String url = "http:192.168.0.167:8080/newjspgou/api/user/login.jspx";
/* 27 */     StringBuffer paramBuffer = new StringBuffer();
/* 28 */     String password = "password";
/* 29 */     paramBuffer.append("username=admin");
/*    */     try {
/* 31 */       paramBuffer.append("&aesPassword=" + AES128Util.encrypt(password, aesKey, ivKey));
/*    */     } catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     }
/* 35 */     String nonce_str = RandomStringUtils.random(16, Num62.N62_CHARS);
/* 36 */     paramBuffer.append("&appId=" + appId);
/* 37 */     paramBuffer.append("&nonce_str=" + nonce_str);
/*    */ 
/* 39 */     Map param = new HashMap();
/* 40 */     String[] params = paramBuffer.toString().split("&");
/* 41 */     for (String p : params) {
/* 42 */       String[] keyValue = p.split("=");
/* 43 */       param.put(keyValue[0], keyValue[1]);
/*    */     }
/* 45 */     url = url + paramBuffer.toString();
/* 46 */     String res = HttpClientUtil.getInstance().get(url);
/* 47 */     System.out.println(res);
/*    */     try
/*    */     {
/* 50 */       JSONObject json = new JSONObject(res);
/* 51 */       String sessionKey = (String)json.get("body");
/*    */       try {
/* 53 */         String descryptKey = AES128Util.decrypt(sessionKey, aesKey, ivKey);
/* 54 */         System.out.println(descryptKey);
/*    */       } catch (Exception e) {
/* 56 */         e.printStackTrace();
/*    */       }
/*    */     } catch (JSONException e) {
/* 59 */       e.printStackTrace();
/*    */     }
/* 61 */     return res;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.test.TestUser
 * JD-Core Version:    0.6.0
 */