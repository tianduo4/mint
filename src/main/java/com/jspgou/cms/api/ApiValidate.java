/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ import com.jspgou.cms.entity.ApiAccount;
/*    */ import com.jspgou.common.util.PayUtil;
/*    */ import com.jspgou.common.web.RequestUtils;
/*    */ import com.jspgou.core.web.WebErrors;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class ApiValidate
/*    */ {
/* 17 */   private static final Logger log = LoggerFactory.getLogger(ApiValidate.class);
/*    */ 
/*    */   public static WebErrors validateRequiredParams(WebErrors errors, Object[] params)
/*    */   {
/* 21 */     for (Object p : params)
/*    */     {
/* 23 */       if ((((p instanceof String)) && (StringUtils.isBlank((String)p))) || (p == null)) {
/* 24 */         errors.addErrorString("\"param required\"");
/*    */       }
/*    */     }
/* 27 */     return errors;
/*    */   }
/*    */ 
/*    */   public static WebErrors validateApiAccount(HttpServletRequest request, WebErrors errors, ApiAccount apiAccount)
/*    */   {
/* 32 */     if ((apiAccount == null) || (apiAccount.getDisabled())) {
/* 33 */       errors.addErrorString("\"appId not exist or appId disabled\"");
/* 34 */       log.error(RequestUtils.getIpAddr(request) + "\"appId not exist or appId disabled\"");
/*    */     }
/* 36 */     return errors;
/*    */   }
/*    */ 
/*    */   public static WebErrors validateSign(HttpServletRequest request, WebErrors errors, ApiAccount apiAccount, String sign)
/*    */   {
/* 42 */     if ((apiAccount != null) && (!apiAccount.getDisabled())) {
/* 43 */       Map param = RequestUtils.getSignMap(request);
/* 44 */       String appKey = apiAccount.getAppKey();
/* 45 */       String validateSign = PayUtil.createSign(param, appKey);
/* 46 */       if ((StringUtils.isBlank(sign)) || (!sign.equals(validateSign))) {
/* 47 */         errors.addErrorString("\"sign validate error\"");
/* 48 */         log.error(RequestUtils.getIpAddr(request) + " sign validate error");
/*    */       }
/*    */     } else {
/* 51 */       errors.addErrorString("\"appId not exist or appId disabled\"");
/* 52 */       log.error(RequestUtils.getIpAddr(request) + "\"appId not exist or appId disabled\"");
/*    */     }
/* 54 */     return errors;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.ApiValidate
 * JD-Core Version:    0.6.0
 */