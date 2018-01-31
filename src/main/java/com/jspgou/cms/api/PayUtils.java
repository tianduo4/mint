/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ import com.alipay.api.AlipayApiException;
/*    */ import com.alipay.api.AlipayClient;
/*    */ import com.alipay.api.DefaultAlipayClient;
/*    */ import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
/*    */ import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
/*    */ import com.jspgou.cms.entity.OrderReturn;
/*    */ import com.jspgou.cms.entity.PaymentPlugins;
/*    */ import java.io.PrintStream;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class PayUtils
/*    */ {
/*    */   public static JSONObject alipayReturn(PaymentPlugins paymentPlugins, OrderReturn orderReturn)
/*    */     throws AlipayApiException
/*    */   {
/* 18 */     String appId = paymentPlugins.getPartner();
/* 19 */     String privatekey = paymentPlugins.getSellerKey();
/* 20 */     String pubkey = paymentPlugins.getPublicKey();
/*    */ 
/* 22 */     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privatekey, "json", "UTF-8", pubkey, "RSA2");
/* 23 */     AlipayFundTransToaccountTransferRequest req = new AlipayFundTransToaccountTransferRequest();
/*    */ 
/* 31 */     req.setBizContent("{\"out_biz_no\":" + 
/* 32 */       orderReturn.getCode() + "," + 
/* 33 */       "\"payee_type\":\"ALIPAY_LOGONID\"," + 
/* 34 */       "\"payee_account\":" + orderReturn.getAlipayId() + "," + 
/* 35 */       "\"amount\":" + orderReturn.getMoney() + "," + 
/* 36 */       "\"payer_show_name\":\"金磊科技\"," + 
/* 37 */       "\"payee_real_name\":\"\"," + 
/* 38 */       "\"remark\":" + orderReturn + "订单退款" + 
/* 39 */       "}");
/* 40 */     AlipayFundTransToaccountTransferResponse response = (AlipayFundTransToaccountTransferResponse)alipayClient.execute(req);
/* 41 */     System.out.println("支付宝AlipayFundTransToaccountTransfer返回参数:" + response.getBody());
/* 42 */     JSONObject json = new JSONObject();
/* 43 */     json.put("status", Boolean.valueOf(false));
/* 44 */     JSONObject body = new JSONObject();
/* 45 */     if (StringUtils.isNotEmpty(response.getBody())) {
/* 46 */       body = JSONObject.fromObject(response.getBody());
/* 47 */       if ("10000".equals(body.get("code"))) {
/* 48 */         json.put("status", Boolean.valueOf(true));
/* 49 */         json.put("msg", "");
/*    */       } else {
/* 51 */         body = JSONObject.fromObject(body.get("alipay_fund_trans_toaccount_transfer_response"));
/* 52 */         json.put("msg", body.get("sub_msg"));
/*    */       }
/*    */     } else {
/* 55 */       json.put("msg", "");
/*    */     }
/* 57 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.PayUtils
 * JD-Core Version:    0.6.0
 */