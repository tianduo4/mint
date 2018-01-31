/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ import com.jspgou.common.util.AES128Util;
/*    */ import com.jspgou.common.util.PayUtil;
/*    */ import com.jspgou.common.web.HttpClientUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TestApi
/*    */ {
/* 12 */   private static String base = "http://127.0.0.1:8080/newjspgou/api/admin";
/*    */ 
/* 19 */   private static String appId = "2846613546571490";
/* 20 */   private static String appKey = "7aWxvObyCkdoTagY5H8d49J2H4wvwrB0";
/* 21 */   private static String aesKey = "U4htkr4fYMYeDNmf";
/* 22 */   private static String ivKey = "CCXRVReXgeCWGTxR";
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 28 */       System.out.println(login());
/*    */ 
/* 30 */       System.out.println(AES128Util.encrypt("123456", aesKey, ivKey));
/* 31 */       System.out.println(AES128Util.encrypt("password", aesKey, ivKey));
/*    */     }
/*    */     catch (Exception e) {
/* 34 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String login() throws Exception
/*    */   {
/* 40 */     String url = base + "/user/login";
/* 41 */     Map param = new HashMap();
/* 42 */     param.put("userName", "admin");
/* 43 */     param.put("passWord", AES128Util.encrypt("123456", aesKey, ivKey));
/* 44 */     param.put("appId", appId);
/* 45 */     param.put("nonce_str", "nitdcqwetywqlkui");
/* 46 */     String validateSign = PayUtil.createSign(param, appKey);
/*    */ 
/* 48 */     Map reparam = new HashMap();
/* 49 */     reparam = param;
/* 50 */     reparam.put("sign", validateSign);
/* 51 */     HttpClientUtil.getInstance(); String res = HttpClientUtil.postParams(url, param);
/* 52 */     return res;
/*    */   }
/*    */ 
/*    */   private static String getSign() throws Exception
/*    */   {
/* 57 */     String url = base + "/user/loginOut";
/* 58 */     Map param = new HashMap();
/* 59 */     param.put("sessionKey", "73dabae808a2687bf0dac4a3380c57f0e56bca30c5e4a99dfdd01dbee70d91ad1f47d5853d72dcdfcc092cbf99805fc0");
/* 60 */     param.put("appId", appId);
/* 61 */     param.put("type", "image");
/* 62 */     param.put("nonce_str", "nitdcqwetywqlkuis");
/* 63 */     String validateSign = PayUtil.createSign(param, appKey);
/* 64 */     System.out.println(validateSign);
/*    */ 
/* 66 */     Map reparam = new HashMap();
/* 67 */     reparam = param;
/* 68 */     reparam.put("sign", validateSign);
/* 69 */     String res = "";
/* 70 */     return res;
/*    */   }
/*    */ 
/*    */   private static String loginOut()
/*    */     throws Exception
/*    */   {
/* 76 */     String url = base + "/user/loginOut";
/* 77 */     Map param = new HashMap();
/* 78 */     param.put("sessionKey", "930bc782e63c0dff7aa0507d6d75cc3522b58393a0d44b29f3448ca84659e941be14a6db6c84f456b2aa59f9861a2964");
/* 79 */     param.put("appId", appId);
/* 80 */     param.put("nonce_str", "nitdcqwetywqlkuis");
/* 81 */     String validateSign = PayUtil.createSign(param, appKey);
/* 82 */     System.out.println(validateSign);
/*    */ 
/* 84 */     Map reparam = new HashMap();
/* 85 */     reparam = param;
/* 86 */     reparam.put("sign", validateSign);
/* 87 */     HttpClientUtil.getInstance(); String res = HttpClientUtil.postParams(url, param);
/* 88 */     return res;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.TestApi
 * JD-Core Version:    0.6.0
 */