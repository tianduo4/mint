/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.manager.ApiRecordMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.web.LoginUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class LoginController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiRecordMng apiRecordMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncodee;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */ 
/*     */   @RequestMapping(value={"/user/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void login(String userName, String passWord, String appId, String nonce_str, String sign, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  56 */     String body = "\"\"";
/*  57 */     String message = "\"\"";
/*  58 */     int code = 0;
/*     */     try {
/*  60 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  62 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { appId, 
/*  63 */         nonce_str, sign, userName, passWord });
/*  64 */       if (errors.hasErrors()) {
/*  65 */         code = 202;
/*  66 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/*  69 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/*  71 */         errors = ApiValidate.validateSign(request, errors, apiAccount, sign);
/*  72 */         if (errors.hasErrors()) {
/*  73 */           code = 204;
/*  74 */           message = "\"sign validate error\"";
/*     */         } else {
/*  76 */           ShopAdmin user = this.shopAdminMng.getByUsername(userName);
/*  77 */           if (user != null) {
/*  78 */             String encryptPass = AES128Util.decrypt(passWord, apiAccount.getAesKey(), apiAccount.getIvKey());
/*     */ 
/*  80 */             if (this.pwdEncodee.isPasswordValid(user.getAdmin().getUser().getPassword(), encryptPass))
/*     */             {
/*  82 */               String sessionKey = this.session.getSessionId(request, response);
/*     */ 
/*  84 */               this.apiUserLoginMng.userLogin(userName, sessionKey);
/*     */ 
/*  86 */               LoginUtils.loginShiro(request, response, userName);
/*     */ 
/*  89 */               this.apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), 
/*  90 */                 appId, "/user/login", sign);
/*  91 */               code = 200;
/*  92 */               message = "\"success\"";
/*  93 */               JSONObject json = new JSONObject();
/*  94 */               json.put("sessionKey", AES128Util.encrypt(sessionKey, apiAccount.getAesKey(), apiAccount.getIvKey()));
/*  95 */               if (user.isSuper())
/*  96 */                 json.put("permission", "*");
/*     */               else {
/*  98 */                 json.put("permission", StringUtils.join(user.getPerms().toArray(), ","));
/*     */               }
/* 100 */               body = json.toString();
/*     */             }
/*     */             else {
/* 103 */               code = 306;
/* 104 */               message = "\"username or password error\"";
/*     */             }
/*     */           }
/*     */           else {
/* 108 */             code = 306;
/* 109 */             message = "\"username or password error\"";
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/* 115 */       code = 100;
/* 116 */       message = "\"system exception\"";
/*     */     }
/* 118 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 119 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/user/loginOut"})
/*     */   public void loginOut(String sessionKey, String appId, String nonce_str, String sign, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 134 */     String body = "\"\"";
/* 135 */     String message = "\"\"";
/* 136 */     int code = 0;
/*     */     try {
/* 138 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 140 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { appId, 
/* 141 */         nonce_str, sign, sessionKey });
/* 142 */       if (errors.hasErrors()) {
/* 143 */         code = 202;
/* 144 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 147 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 149 */         errors = ApiValidate.validateSign(request, errors, apiAccount, sign);
/* 150 */         if (errors.hasErrors()) {
/* 151 */           code = 204;
/* 152 */           message = "\"sign validate error\"";
/*     */         } else {
/* 154 */           ShopAdmin user = this.apiUserLoginMng.findUser(sessionKey, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 155 */           if (user != null) {
/* 156 */             this.apiUserLoginMng.userLogout(user.getUsername());
/* 157 */             LoginUtils.logout();
/*     */           }
/* 159 */           code = 200;
/* 160 */           message = "\"success\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 164 */       e.printStackTrace();
/* 165 */       code = 100;
/* 166 */       message = "\"system exception\"";
/*     */     }
/* 168 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 169 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/user/getLoginUser"})
/*     */   public void getLoginUser(String sessionKey, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 180 */     String body = "\"\"";
/* 181 */     int code = 200;
/* 182 */     String message = "\"success\"";
/*     */     try {
/* 184 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 186 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { sessionKey });
/* 187 */       if (errors.hasErrors()) {
/* 188 */         code = 202;
/* 189 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 192 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/* 193 */         ShopAdmin user = this.apiUserLoginMng.findUser(sessionKey, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 194 */         if (user != null) {
/* 195 */           JSONObject json = new JSONObject();
/* 196 */           if (user.isSuper())
/* 197 */             json.put("permission", "*");
/*     */           else {
/* 199 */             json.put("permission", StringUtils.join(user.getPerms().toArray(), ","));
/*     */           }
/* 201 */           body = json.toString();
/*     */         } else {
/* 203 */           code = 302;
/* 204 */           message = "\"user not login\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 208 */       e.printStackTrace();
/* 209 */       code = 100;
/* 210 */       message = "\"system exception\"";
/*     */     }
/* 212 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 213 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.LoginController
 * JD-Core Version:    0.6.0
 */