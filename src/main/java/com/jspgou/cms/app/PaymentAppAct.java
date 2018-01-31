/*    */ package com.jspgou.cms.app;
/*    */ 
/*    */ import com.jspgou.cms.entity.PaymentPlugins;
/*    */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*    */ import com.jspgou.cms.web.SiteUtils;
/*    */ import com.jspgou.common.util.ConvertMapToJson;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class PaymentAppAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PaymentPluginsMng paymentMng;
/*    */ 
/*    */   @RequestMapping({"/api/paymentList.jspx"})
/*    */   public void paymentList(HttpServletRequest request, HttpServletResponse response)
/*    */     throws JSONException
/*    */   {
/* 35 */     Website web = SiteUtils.getWeb(request);
/* 36 */     Map map = new HashMap();
/* 37 */     String result = "00";
/* 38 */     String callback = request.getParameter("callback");
/* 39 */     List paylist = this.paymentMng.getList1("mobile");
/* 40 */     if (paylist != null) {
/* 41 */       if (paylist.size() > 0) {
/* 42 */         result = "01";
/* 43 */         map.put("pd", paymentListJson(paylist));
/*    */       } else {
/* 45 */         result = "02";
/*    */       }
/*    */     }
/*    */     else {
/* 49 */       result = "02";
/*    */     }
/* 51 */     map.put("result", result);
/* 52 */     if (!StringUtils.isBlank(callback))
/* 53 */       ResponseUtils.renderJson(response, callback + "(" + 
/* 54 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*    */     else
/* 56 */       ResponseUtils.renderJson(response, 
/* 57 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*    */   }
/*    */ 
/*    */   private String paymentListJson(List<PaymentPlugins> paylist)
/*    */     throws JSONException
/*    */   {
/* 63 */     JSONObject o = new JSONObject();
/* 64 */     JSONArray arr = new JSONArray();
/* 65 */     for (PaymentPlugins payment : paylist) {
/* 66 */       o.put("id", payment.getId());
/* 67 */       o.put("name", payment.getName());
/* 68 */       o.put("imgPath", payment.getImgPath());
/* 69 */       arr.put(o);
/*    */     }
/* 71 */     return arr.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.PaymentAppAct
 * JD-Core Version:    0.6.0
 */