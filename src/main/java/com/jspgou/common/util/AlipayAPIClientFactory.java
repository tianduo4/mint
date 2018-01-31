/*    */ package com.jspgou.common.util;
/*    */ 
/*    */ import com.alipay.api.AlipayClient;
/*    */ import com.alipay.api.DefaultAlipayClient;
/*    */ 
/*    */ public class AlipayAPIClientFactory
/*    */ {
/*    */   private static AlipayClient alipayClient;
/*    */ 
/*    */   public static AlipayClient getAlipayClient(String url, String appId, String privateKey, String publicKey, String charset)
/*    */   {
/* 26 */     if (alipayClient == null) {
/* 27 */       alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", charset, publicKey);
/*    */     }
/* 29 */     return alipayClient;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.AlipayAPIClientFactory
 * JD-Core Version:    0.6.0
 */