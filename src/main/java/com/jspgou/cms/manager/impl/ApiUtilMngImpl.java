/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.Alipay;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiRecord;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiRecordMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.manager.ApiUtilMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.util.ConvertMapToJson;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ApiUtilMngImpl
/*     */   implements ApiUtilMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiRecordMng apiRecordMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   public String getJsonStrng(String message, String url, Boolean decryptionFailure, HttpServletRequest request)
/*     */   {
/*  41 */     Map map = new HashMap();
/*  42 */     String result = "00";
/*  43 */     String appId = request.getParameter("appId");
/*  44 */     String sign = request.getParameter("sign");
/*  45 */     String callback = request.getParameter("callback");
/*  46 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
/*  47 */       if (verifySign(appId, sign).booleanValue()) {
/*  48 */         String validateSign = getValidateSign(appId, request);
/*  49 */         if (verifyValidateSign(sign, validateSign).booleanValue()) {
/*  50 */           if (decryptionFailure.booleanValue())
/*     */           {
/*  52 */             result = "01";
/*  53 */             if (StringUtils.isNotBlank(message)) {
/*  54 */               map.put("pd", message);
/*     */             }
/*  56 */             if (StringUtils.isNotBlank(url))
/*  57 */               this.apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, url, sign);
/*     */           }
/*     */           else
/*     */           {
/*  61 */             result = "02";
/*     */           }
/*     */         }
/*     */         else
/*  65 */           result = "03";
/*     */       }
/*     */       else
/*     */       {
/*  69 */         result = "04";
/*     */       }
/*     */     }
/*  72 */     map.put("result", result);
/*  73 */     if (!StringUtils.isBlank(callback)) {
/*  74 */       return callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")";
/*     */     }
/*  76 */     return ConvertMapToJson.buildJsonBody(map, 0, false);
/*     */   }
/*     */ 
/*     */   public String getJsonStrng(String message, String url, HttpServletRequest request)
/*     */   {
/*  90 */     Map map = new HashMap();
/*  91 */     String result = "00";
/*  92 */     String appId = request.getParameter("appId");
/*  93 */     String sign = request.getParameter("sign");
/*  94 */     String callback = request.getParameter("callback");
/*  95 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
/*  96 */       if (verifySign(appId, sign).booleanValue()) {
/*  97 */         String validateSign = getValidateSign(appId, request);
/*  98 */         if (verifyValidateSign(sign, validateSign).booleanValue())
/*     */         {
/* 100 */           result = "01";
/* 101 */           if (StringUtils.isNotBlank(message)) {
/* 102 */             map.put("pd", message);
/*     */           }
/* 104 */           if (StringUtils.isNotBlank(url))
/* 105 */             this.apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, url, sign);
/*     */         }
/*     */         else
/*     */         {
/* 109 */           result = "03";
/*     */         }
/*     */       }
/*     */       else {
/* 113 */         result = "04";
/*     */       }
/*     */     }
/* 116 */     map.put("result", result);
/* 117 */     if (!StringUtils.isBlank(callback)) {
/* 118 */       return callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")";
/*     */     }
/* 120 */     return ConvertMapToJson.buildJsonBody(map, 0, false);
/*     */   }
/*     */ 
/*     */   public String getEncryptpass(HttpServletRequest request) throws Exception
/*     */   {
/* 125 */     String encryptPass = null;
/* 126 */     String appId = request.getParameter("appId");
/* 127 */     String sign = request.getParameter("sign");
/* 128 */     String aesPassword = request.getParameter("aesPassword");
/* 129 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(aesPassword)) && (StringUtils.isNotBlank(sign)) && 
/* 130 */       (verifySign(appId, sign).booleanValue())) {
/* 131 */       String validateSign = getValidateSign(appId, request);
/* 132 */       if (verifyValidateSign(sign, validateSign).booleanValue()) {
/* 133 */         ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
/* 134 */         String aesKey = apiAccount.getAesKey();
/* 135 */         String ivVal = apiAccount.getIvKey();
/* 136 */         encryptPass = AES128Util.decrypt(aesPassword, aesKey, ivVal);
/*     */       }
/*     */     }
/*     */ 
/* 140 */     return encryptPass;
/*     */   }
/*     */ 
/*     */   public ShopMember findbysessionKey(HttpServletRequest request) {
/* 144 */     ShopMember user = null;
/* 145 */     String appId = request.getParameter("appId");
/* 146 */     String sign = request.getParameter("sign");
/* 147 */     String sessionKey = request.getParameter("sessionKey");
/* 148 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sessionKey)) && (StringUtils.isNotBlank(sign)) && 
/* 149 */       (verifySign(appId, sign).booleanValue())) {
/* 150 */       String validateSign = getValidateSign(appId, request);
/* 151 */       if (verifyValidateSign(sign, validateSign).booleanValue()) {
/* 152 */         ApiUserLogin apiUserLogin = this.apiUserLoginMng.findBySessionKey(sessionKey);
/* 153 */         if (apiUserLogin != null) {
/* 154 */           String username = apiUserLogin.getUsername();
/* 155 */           user = this.shopMemberMng.findUsername(username);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 160 */     return user;
/*     */   }
/*     */ 
/*     */   public Boolean verify(HttpServletRequest request)
/*     */   {
/* 165 */     Boolean verify = Boolean.valueOf(false);
/* 166 */     String appId = request.getParameter("appId");
/* 167 */     String sign = request.getParameter("sign");
/* 168 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign)) && 
/* 169 */       (verifySign(appId, sign).booleanValue())) {
/* 170 */       String validateSign = getValidateSign(appId, request);
/* 171 */       if (verifyValidateSign(sign, validateSign).booleanValue()) {
/* 172 */         verify = Boolean.valueOf(true);
/*     */       }
/*     */     }
/*     */ 
/* 176 */     return verify;
/*     */   }
/*     */ 
/*     */   public Boolean verifySign(String appId, String sign)
/*     */   {
/* 182 */     Boolean verify = Boolean.valueOf(false);
/* 183 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
/* 184 */       ApiRecord apirecord = this.apiRecordMng.findBySign(sign, appId);
/* 185 */       if (apirecord == null) {
/* 186 */         verify = Boolean.valueOf(true);
/*     */       }
/*     */     }
/* 189 */     return verify;
/*     */   }
/*     */ 
/*     */   public Boolean verifyValidateSign(String sign, String validateSign)
/*     */   {
/* 194 */     Boolean verify = Boolean.valueOf(false);
/* 195 */     if ((StringUtils.isNotBlank(validateSign)) && (StringUtils.isNotBlank(sign)) && 
/* 196 */       (sign.equals(validateSign))) {
/* 197 */       verify = Boolean.valueOf(true);
/*     */     }
/*     */ 
/* 200 */     return verify;
/*     */   }
/*     */ 
/*     */   private String getValidateSign(String appId, HttpServletRequest request)
/*     */   {
/* 206 */     ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
/* 207 */     String appKey = apiAccount.getAppKey();
/* 208 */     Map param = new HashMap();
/* 209 */     Enumeration penum = request.getParameterNames();
/* 210 */     while (penum.hasMoreElements()) {
/* 211 */       String pKey = (String)penum.nextElement();
/* 212 */       String value = request.getParameter(pKey);
/*     */ 
/* 214 */       if ((!pKey.equals("sign")) && (StringUtils.isNotBlank(value))) {
/* 215 */         param.put(pKey, value);
/*     */       }
/*     */     }
/* 218 */     String validateSign = Alipay.createSign(param, appKey);
/* 219 */     return validateSign;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ApiUtilMngImpl
 * JD-Core Version:    0.6.0
 */