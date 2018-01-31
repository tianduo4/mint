/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.service.WeixinSvc;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.web.HttpRequestUtil;
/*     */ import com.jspgou.common.web.LoginUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.ThirdAccount;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.ThirdAccountMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.shiro.subject.PrincipalCollection;
/*     */ import org.apache.shiro.subject.SimplePrincipalCollection;
/*     */ import org.apache.shiro.util.ThreadContext;
/*     */ import org.apache.shiro.web.subject.WebSubject;
/*     */ import org.apache.shiro.web.subject.WebSubject.Builder;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ThirdLoginAct
/*     */ {
/*  56 */   private static final Logger log = LoggerFactory.getLogger(ThirdLoginAct.class);
/*     */   public static final String TPL_AUTH = "tpl.member.auth";
/*     */   public static final String TPL_BIND = "tpl.member.bind";
/*     */   public static final String UNIFORM_SINGLE_INTERFACE = "https://open.weixin.qq.com/connect/qrconnect";
/*     */   public static final String characterEncodingUTF8 = "UTF-8";
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncoder;
/*     */ 
/*     */   @Autowired
/*     */   private ThirdAccountMng accountMng;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinSvc WeixinSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @RequestMapping({"/public_auth.jspx"})
/*     */   public String auth(String openId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  67 */     Website web = SiteUtils.getWeb(request);
/*  68 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  69 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.auth", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/public_wechat_login.jspx"})
/*     */   public String weChatLogin(String code, String state, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException, JSONException {
/*  75 */     Website web = SiteUtils.getWeb(request);
/*  76 */     JSONObject json = new JSONObject();
/*     */ 
/*  78 */     if (StringUtils.isBlank(state)) {
/*  79 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*     */ 
/*  82 */     String AccessToken = this.WeixinSvc.getAccesstoken(code);
/*  83 */     json = new JSONObject(AccessToken);
/*  84 */     String oppenId = json.getString("openid");
/*  85 */     WebErrors error = validateKey(oppenId, request);
/*  86 */     if (error.hasErrors()) {
/*  87 */       return FrontHelper.showError(error, web, model, request);
/*     */     }
/*  89 */     if ("WECHAT".equals("WECHAT")) {
/*  90 */       this.session.setAttribute(request, response, "weChatId", oppenId);
/*     */     }
/*  92 */     if (StringUtils.isNotBlank(oppenId)) {
/*  93 */       oppenId = this.pwdEncoder.encodePassword(oppenId);
/*     */     }
/*  95 */     String token = json.getString("access_token");
/*     */ 
/*  97 */     String TestToken = this.WeixinSvc.testToken(token, oppenId);
/*  98 */     json = new JSONObject(TestToken);
/*     */ 
/* 100 */     String errcode = json.getString("errcode");
/* 101 */     String errmsg = json.getString("errmsg");
/* 102 */     String nickname = null;
/*     */ 
/* 104 */     if ((errcode.equals("0")) && (errmsg.equals("ok"))) {
/* 105 */       String getUserinfo = this.WeixinSvc.getUserInfo(token, oppenId);
/* 106 */       json = new JSONObject(getUserinfo);
/* 107 */       nickname = json.getString("nickname");
/*     */     }
/* 109 */     ThirdAccount account = this.accountMng.findByKey(oppenId);
/* 110 */     model.addAttribute("WECHAT", "WECHAT");
/* 111 */     if (account != null)
/*     */     {
/* 113 */       if (!account.getThirdLoginName().equals(nickname)) {
/* 114 */         account.setThirdLoginName(nickname);
/* 115 */         this.accountMng.update(account);
/*     */       }
/* 117 */       model.addAttribute("succ", Boolean.valueOf(true));
/*     */ 
/* 120 */       loginByKey(oppenId, request, response, model);
/*     */     } else {
/* 122 */       model.addAttribute("nickname", nickname);
/* 123 */       model.addAttribute("succ", Boolean.valueOf(false));
/*     */     }
/*     */ 
/* 126 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/* 128 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.auth", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/public_bind_username.jspx"})
/*     */   public String bind_username_post(String username, String password, String nickname, Boolean sina, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException
/*     */   {
/* 137 */     Website web = SiteUtils.getWeb(request);
/* 138 */     WebErrors errors = WebErrors.create(request);
/* 139 */     String source = "";
/* 140 */     if (StringUtils.isBlank(username))
/*     */     {
/* 142 */       errors.addErrorCode("error.usernameRequired");
/*     */     } else {
/* 144 */       boolean usernameExist = this.userMng.usernameExist(username);
/* 145 */       if (usernameExist)
/*     */       {
/* 147 */         errors.addErrorCode("error.usernameExist");
/*     */       }
/*     */       else {
/* 150 */         String openId = (String)this.session.getAttribute(request, "openId");
/* 151 */         String uid = (String)this.session.getAttribute(request, "uid");
/* 152 */         String wechatOpenId = (String)this.session.getAttribute(request, "weChatId");
/*     */ 
/* 154 */         if ((StringUtils.isNotBlank(openId)) || (StringUtils.isNotBlank(uid)) || (StringUtils.isNotBlank(wechatOpenId)))
/*     */         {
/* 156 */           this.shopMemberMng.register(username, password, null, Boolean.valueOf(true), null, 
/* 157 */             null, null, web.getId(), Long.valueOf(1L));
/*     */         }
/*     */ 
/* 160 */         if (StringUtils.isNotBlank(openId))
/* 161 */           source = "QQ";
/* 162 */         else if (StringUtils.isNotBlank(uid))
/* 163 */           source = "SINA";
/* 164 */         else if (StringUtils.isNotBlank(wechatOpenId)) {
/* 165 */           source = "WECHAT";
/*     */         }
/*     */ 
/* 168 */         loginByUsername(username, nickname, request, response, model);
/*     */       }
/*     */     }
/* 171 */     if (errors.hasErrors()) {
/* 172 */       errors.toModel(model);
/* 173 */       model.addAttribute("success", Boolean.valueOf(false));
/*     */     } else {
/* 175 */       model.addAttribute("success", Boolean.valueOf(true));
/*     */     }
/* 177 */     model.addAttribute("source", source);
/* 178 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 179 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.bind", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/public_auth_login.jspx"})
/*     */   public void authLogin(String key, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
/* 185 */     if (StringUtils.isNotBlank(source)) {
/* 186 */       if (source.equals("QQ"))
/* 187 */         this.session.setAttribute(request, response, "openId", key);
/* 188 */       else if (source.equals("QQWEBO"))
/* 189 */         this.session.setAttribute(request, response, "weboOpenId", key);
/* 190 */       else if (source.equals("SINA")) {
/* 191 */         this.session.setAttribute(request, response, "uid", key);
/*     */       }
/*     */     }
/* 194 */     JSONObject json = new JSONObject();
/*     */ 
/* 196 */     if (StringUtils.isNotBlank(key)) {
/* 197 */       key = this.pwdEncoder.encodePassword(key);
/*     */     }
/* 199 */     ThirdAccount account = this.accountMng.findByKey(key);
/* 200 */     if (account != null) {
/* 201 */       json.put("succ", true);
/*     */ 
/* 203 */       loginByKey(key, request, response, model);
/*     */     } else {
/* 205 */       json.put("succ", false);
/*     */     }
/* 207 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/public_bind.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String bind_get(String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 216 */     Website web = SiteUtils.getWeb(request);
/* 217 */     model.addAttribute("nickname", nickname);
/* 218 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 219 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.bind", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/sso/authenticate.jspx"})
/*     */   public void authenticate(String username, String sessionId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 230 */     User user = this.userMng.getByUsername(username);
/* 231 */     if ((user != null) && (sessionId != null)) {
/* 232 */       String userSessionId = user.getSessionId();
/* 233 */       if (StringUtils.isNotBlank(userSessionId)) {
/* 234 */         if (userSessionId.equals(sessionId))
/* 235 */           ResponseUtils.renderJson(response, "true");
/*     */       }
/*     */       else
/* 238 */         ResponseUtils.renderJson(response, "false");
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/sso/login.jspx"})
/*     */   public void loginSso(String username, String sessionId, String ssoLogout, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 249 */     User user = null;
/* 250 */     ShopAdmin admin = AdminThread.get();
/* 251 */     if (admin != null) {
/* 252 */       user = admin.getAdmin().getUser();
/*     */     }
/* 254 */     ShopMember member = MemberThread.get();
/* 255 */     if (member != null) {
/* 256 */       user = member.getMember().getUser();
/*     */     }
/* 258 */     Website site = SiteUtils.getWeb(request);
/* 259 */     if (StringUtils.isNotBlank(username)) {
/* 260 */       JSONObject object = new JSONObject();
/*     */       try {
/* 262 */         if (user == null)
/*     */         {
/* 264 */           List authenticateUrls = site.getSsoAuthenticateUrls();
/* 265 */           String success = authenticate(username, sessionId, authenticateUrls);
/* 266 */           if (success.equals("true")) {
/* 267 */             LoginUtils.loginShiro(request, response, username);
/* 268 */             user = this.userMng.getByUsername(username);
/* 269 */             if (user != null) {
/* 270 */               this.userMng.updateLoginInfo(user.getId(), null, null, sessionId);
/*     */             }
/* 272 */             object.put("result", "login");
/*     */           }
/* 274 */         } else if ((StringUtils.isNotBlank(ssoLogout)) && (ssoLogout.equals("true"))) {
/* 275 */           LoginUtils.logout();
/* 276 */           object.put("result", "logout");
/*     */         }
/*     */       }
/*     */       catch (JSONException e) {
/* 280 */         e.printStackTrace();
/*     */       }
/* 282 */       ResponseUtils.renderJson(response, object.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loginByKey(String key, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 298 */     ThirdAccount account = this.accountMng.findByKey(key);
/* 299 */     if ((StringUtils.isNotBlank(key)) && (account != null)) {
/* 300 */       String username = account.getUsername();
/* 301 */       loginShiro(request, response, username);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loginByUsername(String username, String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 314 */     String openId = (String)this.session.getAttribute(request, "openId");
/* 315 */     String uid = (String)this.session.getAttribute(request, "uid");
/* 316 */     String weChatOpenId = (String)this.session.getAttribute(request, "weChatId");
/* 317 */     if (StringUtils.isNotBlank(openId)) {
/* 318 */       loginShiro(request, response, username);
/*     */ 
/* 320 */       bind(username, nickname, openId, "QQ");
/*     */     }
/* 322 */     if (StringUtils.isNotBlank(uid)) {
/* 323 */       loginShiro(request, response, username);
/*     */ 
/* 325 */       bind(username, nickname, uid, "SINA");
/*     */     }
/* 327 */     if (StringUtils.isNotBlank(weChatOpenId)) {
/* 328 */       loginShiro(request, response, username);
/*     */ 
/* 330 */       bind(username, nickname, weChatOpenId, "WECHAT");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void bind(String username, String nickname, String openId, String source)
/*     */   {
/* 336 */     ThirdAccount account = this.accountMng.findByKey(openId);
/* 337 */     if (account == null) {
/* 338 */       account = new ThirdAccount();
/* 339 */       account.setUsername(username);
/* 340 */       account.setThirdLoginName(nickname);
/*     */ 
/* 342 */       openId = this.pwdEncoder.encodePassword(openId);
/* 343 */       account.setAccountKey(openId);
/* 344 */       account.setSource(source);
/* 345 */       this.accountMng.save(account);
/*     */     }
/*     */   }
/*     */ 
/*     */   private WebErrors validateKey(String key, HttpServletRequest request) {
/* 349 */     WebErrors errors = WebErrors.create(request);
/* 350 */     if (StringUtils.isBlank(key)) {
/* 351 */       errors.addErrorString("网站系统后台参数错误");
/* 352 */       return errors;
/*     */     }
/* 354 */     return errors;
/*     */   }
/*     */ 
/*     */   private void loginShiro(HttpServletRequest request, HttpServletResponse response, String username) {
/* 358 */     PrincipalCollection principals = new SimplePrincipalCollection(username, username);
/* 359 */     WebSubject.Builder builder = new WebSubject.Builder(request, response);
/* 360 */     builder.principals(principals);
/* 361 */     builder.authenticated(true);
/* 362 */     WebSubject subject = builder.buildWebSubject();
/* 363 */     ThreadContext.bind(subject);
/*     */   }
/*     */ 
/*     */   private String authenticate(String username, String sessionId, List<String> authenticateUrls)
/*     */   {
/* 368 */     String result = "false";
/* 369 */     for (String url : authenticateUrls) {
/* 370 */       result = authenticate(username, sessionId, url);
/* 371 */       if (result.equals("true")) {
/*     */         break;
/*     */       }
/*     */     }
/* 375 */     return result;
/*     */   }
/*     */ 
/*     */   private String authenticate(String username, String sessionId, String authenticateUrl) {
/* 379 */     Map params = new HashMap();
/* 380 */     params.put("username", username);
/* 381 */     params.put("sessionId", sessionId);
/* 382 */     String success = "false";
/*     */     try {
/* 384 */       success = HttpRequestUtil.request(authenticateUrl, params, "post", "utf-8");
/*     */     }
/*     */     catch (ClientProtocolException e) {
/* 387 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/* 390 */       e.printStackTrace();
/*     */     }
/* 392 */     return success;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.ThirdLoginAct
 * JD-Core Version:    0.6.0
 */