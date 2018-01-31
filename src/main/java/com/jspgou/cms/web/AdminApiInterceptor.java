/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.method.HandlerMethod;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class AdminApiInterceptor extends HandlerInterceptorAdapter
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(AdminApiInterceptor.class);
/*     */   public static final String SITE_PARAM = "_site_id_param";
/*     */   public static final String SITE_COOKIE = "_site_id_cookie";
/*     */   public static final String PERMISSION_MODEL = "_permission_key";
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */   private String[] excludeUrls;
/* 390 */   private String sessionKey = "sessionKey";
/* 391 */   private String appId = "appId";
/* 392 */   private String sign = "sign";
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */ 
/*     */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*     */     throws Exception
/*     */   {
/*  50 */     String body = "\"\"";
/*  51 */     int code = 0;
/*  52 */     String message = "";
/*     */     try
/*     */     {
/*  55 */       WebErrors errors = WebErrors.create(request);
/*  56 */       String uri = getURI(request);
/*     */ 
/*  58 */       ApiAccount apiAccount = getApiAccount(request);
/*  59 */       if ((apiAccount != null) && (!apiAccount.getDisabled())) {
/*  60 */         CmsThreadVariable.setApiAccount(apiAccount);
/*     */ 
/*  62 */         if (exclude(uri)) {
/*  63 */           return true;
/*     */         }
/*     */ 
/*  66 */         ShopAdmin user = getUser(apiAccount, request);
/*     */ 
/*  68 */         Short userStatus = getStatus(apiAccount, request);
/*  69 */         CmsUtils.setAdmin(request, user);
/*     */ 
/*  71 */         CmsThreadVariable.setAdminUser(user);
/*  72 */         if (user != null)
/*     */         {
/*  74 */           CmsThreadVariable.setSite(user.getWebsite());
/*  75 */           CmsUtils.setWebsite(request, user.getWebsite());
/*     */ 
/*  77 */           if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOVERTIME)) {
/*  78 */             code = 300;
/*  79 */             message = "\"user over time\"";
/*  80 */           } else if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOUT)) {
/*  81 */             code = 302;
/*  82 */             message = "\"user not login\"";
/*     */           }
/*     */           else {
/*  85 */             if (!user.isSuper())
/*     */             {
/*  87 */               if (!hasUrlPerm(user.getWebsite(), user, uri)) {
/*  88 */                 code = 209;
/*  89 */                 message = "\"user has not perm\"";
/*     */               }
/*     */             }
/*  92 */             if (code == 0) {
/*  93 */               HandlerMethod handlerMethod = (HandlerMethod)handler;
/*  94 */               Method method = handlerMethod.getMethod();
/*  95 */               SignValidate annotation = (SignValidate)method.getAnnotation(SignValidate.class);
/*     */ 
/*  97 */               if ((annotation != null) && (annotation.need()))
/*     */               {
/*  99 */                 if (user.getAdmin().getViewonlyAdmin().booleanValue()) {
/* 100 */                   code = 209;
/* 101 */                   message = "\"user has not perm\"";
/*     */                 } else {
/* 103 */                   Object[] result = validateSign(request, errors);
/* 104 */                   if (!"0".equals(result[0])) {
/* 105 */                     code = Integer.parseInt(String.valueOf(result[0]));
/* 106 */                     message = String.valueOf(result[1]);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 114 */           code = 302;
/* 115 */           message = "\"user not login\"";
/*     */         }
/*     */       } else {
/* 118 */         code = 203;
/* 119 */         message = "\"appId not exist or appId disabled\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 123 */       code = 100;
/* 124 */       message = "\"system exception\"";
/*     */     }
/* 126 */     if (code != 0) {
/* 127 */       ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 128 */       ResponseUtils.renderApiJson(response, request, apiResponse);
/* 129 */       return false;
/*     */     }
/*     */ 
/* 132 */     refreshUserLoginActive();
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private Website getSite(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 152 */     Website site = getByParams(request, response);
/* 153 */     if (site == null) {
/* 154 */       site = getByCookie(request);
/*     */     }
/* 156 */     if (site == null) {
/* 157 */       site = getByDomain(request);
/*     */     }
/* 159 */     if (site == null) {
/* 160 */       site = getByDefault();
/*     */     }
/* 162 */     if (site == null) {
/* 163 */       throw new RuntimeException("cannot get site!");
/*     */     }
/* 165 */     return site;
/*     */   }
/*     */ 
/*     */   private Website getByParams(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 171 */     String p = request.getParameter("_site_id_param");
/* 172 */     if (!StringUtils.isBlank(p)) {
/*     */       try {
/* 174 */         Long siteId = Long.valueOf(Long.parseLong(p));
/* 175 */         Website site = this.websiteMng.findById(siteId);
/* 176 */         if (site != null)
/*     */         {
/* 178 */           CookieUtils.addCookie(request, response, "_site_id_cookie", site
/* 179 */             .getId().toString(), null, null);
/* 180 */           return site;
/*     */         }
/*     */       } catch (NumberFormatException e) {
/* 183 */         log.warn("param site id format exception", e);
/*     */       }
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   private Website getByCookie(HttpServletRequest request) {
/* 190 */     Cookie cookie = CookieUtils.getCookie(request, "_site_id_cookie");
/* 191 */     if (cookie != null) {
/* 192 */       String v = cookie.getValue();
/* 193 */       if (!StringUtils.isBlank(v)) {
/*     */         try {
/* 195 */           Long siteId = Long.valueOf(Long.parseLong(v));
/* 196 */           return this.websiteMng.findById(siteId);
/*     */         } catch (NumberFormatException e) {
/* 198 */           log.warn("cookie site id format exception", e);
/*     */         }
/*     */       }
/*     */     }
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */   private Website getByDomain(HttpServletRequest request) {
/* 206 */     String domain = request.getServerName();
/* 207 */     if (!StringUtils.isBlank(domain)) {
/* 208 */       return this.websiteMng.findByDomain(domain, true);
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   private Website getByDefault() {
/* 214 */     List list = this.websiteMng.getListFromCache();
/* 215 */     if (list.size() > 0) {
/* 216 */       return (Website)list.get(0);
/*     */     }
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   private boolean exclude(String uri)
/*     */   {
/* 223 */     if (this.excludeUrls != null) {
/* 224 */       for (String exc : this.excludeUrls) {
/* 225 */         if (exc.equals(uri)) {
/* 226 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */   private static String getURI(HttpServletRequest request)
/*     */     throws IllegalStateException
/*     */   {
/* 242 */     UrlPathHelper helper = new UrlPathHelper();
/* 243 */     String uri = helper.getOriginatingRequestUri(request);
/* 244 */     String ctxPath = helper.getOriginatingContextPath(request);
/* 245 */     int start = 0; int i = 0; int count = 2;
/* 246 */     if (!StringUtils.isBlank(ctxPath)) {
/* 247 */       count++;
/*     */     }
/* 249 */     while ((i < count) && (start != -1)) {
/* 250 */       start = uri.indexOf('/', start + 1);
/* 251 */       i++;
/*     */     }
/* 253 */     if (start <= 0) {
/* 254 */       throw new IllegalStateException(
/* 255 */         "admin access path not like '/jeeadmin/jeecms/...' pattern: " + 
/* 256 */         uri);
/*     */     }
/* 258 */     return uri.substring(start);
/*     */   }
/*     */ 
/*     */   private ApiAccount getApiAccount(HttpServletRequest request) {
/* 262 */     String appId = request.getParameter(this.appId);
/* 263 */     ApiAccount apiAccount = null;
/* 264 */     if ((StringUtils.isNotBlank(this.sessionKey)) && (StringUtils.isNotBlank(appId))) {
/* 265 */       apiAccount = this.apiAccountMng.findByAppId(appId);
/*     */     }
/* 267 */     return apiAccount;
/*     */   }
/*     */ 
/*     */   private ShopAdmin getUser(ApiAccount apiAccount, HttpServletRequest request) throws Exception
/*     */   {
/* 272 */     ShopAdmin user = null;
/* 273 */     String sessionKey = request.getParameter(this.sessionKey);
/* 274 */     String aesKey = apiAccount.getAesKey();
/* 275 */     user = this.apiUserLoginMng.findUser(sessionKey, aesKey, apiAccount.getIvKey());
/* 276 */     return user;
/*     */   }
/*     */ 
/*     */   private Short getStatus(ApiAccount apiAccount, HttpServletRequest request) {
/* 280 */     String sessionKey = request.getParameter(this.sessionKey);
/* 281 */     Short loginStatus = ApiUserLogin.USER_STATUS_LOGOUT;
/* 282 */     if (apiAccount != null) {
/* 283 */       String decryptSessionKey = "";
/* 284 */       String aesKey = apiAccount.getAesKey();
/*     */       try {
/* 286 */         decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 290 */       if (StringUtils.isNotBlank(decryptSessionKey)) {
/* 291 */         loginStatus = this.apiUserLoginMng.getUserStatus(decryptSessionKey);
/*     */       }
/*     */     }
/* 294 */     return loginStatus;
/*     */   }
/*     */ 
/*     */   private Object[] validateSign(HttpServletRequest request, WebErrors errors) {
/* 298 */     String sign = request.getParameter(this.sign);
/* 299 */     String appId = request.getParameter(this.appId);
/* 300 */     ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
/* 301 */     errors = ApiValidate.validateApiAccount(request, errors, apiAccount);
/* 302 */     int code = 0;
/* 303 */     String message = "";
/* 304 */     Object[] result = new Object[2];
/* 305 */     if (errors.hasErrors()) {
/* 306 */       code = 203;
/* 307 */       message = "\"appId not exist or appId disabled\"";
/*     */     }
/*     */     else {
/* 310 */       errors = ApiValidate.validateSign(request, errors, apiAccount, sign);
/*     */ 
/* 312 */       if (errors.hasErrors()) {
/* 313 */         code = 204;
/* 314 */         message = "\"sign validate error\"";
/*     */       }
/*     */     }
/* 317 */     result[0] = Integer.valueOf(code);
/* 318 */     result[1] = message;
/* 319 */     return result;
/*     */   }
/*     */ 
/*     */   private void refreshUserLoginActive()
/*     */   {
/* 326 */     ApiUserLogin apiUserLogin = CmsThreadVariable.getApiUserLogin();
/* 327 */     if (apiUserLogin != null) {
/* 328 */       apiUserLogin.setActiveTime(new Timestamp(System.currentTimeMillis()));
/* 329 */       this.apiUserLoginMng.update(apiUserLogin);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void userActive(HttpServletRequest request)
/*     */   {
/* 336 */     String sessionKey = request.getParameter(this.sessionKey);
/* 337 */     ApiAccount apiAccount = getApiAccount(request);
/* 338 */     Short status = getStatus(apiAccount, request);
/* 339 */     if (apiAccount != null) {
/* 340 */       String decryptSessionKey = "";
/* 341 */       String aesKey = apiAccount.getAesKey();
/*     */       try {
/* 343 */         decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 347 */       if (StringUtils.isNotBlank(decryptSessionKey)) {
/* 348 */         this.apiUserLoginMng.userActive(decryptSessionKey);
/*     */       }
/*     */     }
/* 351 */     if ((apiAccount != null) && (status.equals(ApiUserLogin.USER_STATUS_LOGIN)))
/* 352 */       this.apiUserLoginMng.userActive(sessionKey);
/*     */   }
/*     */ 
/*     */   private boolean hasUrlPerm(Website site, ShopAdmin user, String url)
/*     */   {
/* 357 */     Set perms = getUserPermission(site, user);
/* 358 */     if (perms == null) {
/* 359 */       return true;
/*     */     }
/* 361 */     Iterator it = perms.iterator();
/* 362 */     while (it.hasNext()) {
/* 363 */       String perm = (String)it.next();
/* 364 */       if ((perm.equals("/*")) || (perm.endsWith(url))) {
/* 365 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */   private Set<String> getUserPermission(Website site, ShopAdmin user) {
/* 373 */     Set perms = user.getPerms();
/* 374 */     Set userPermission = new HashSet();
/* 375 */     if (perms != null) {
/* 376 */       for (String perm : perms) {
/* 377 */         if (perm.contains(":")) {
/* 378 */           perm = perm.replace(":", "/").replace("*", "");
/*     */         }
/* 380 */         userPermission.add(perm);
/*     */       }
/*     */     }
/* 383 */     return userPermission;
/*     */   }
/*     */ 
/*     */   public void setExcludeUrls(String[] excludeUrls)
/*     */   {
/* 399 */     this.excludeUrls = excludeUrls;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.AdminApiInterceptor
 * JD-Core Version:    0.6.0
 */