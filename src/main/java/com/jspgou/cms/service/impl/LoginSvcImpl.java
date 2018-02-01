/*     */ package com.jspgou.cms.service.impl;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopConfig;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.manager.ShopConfigMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.service.ShoppingSvc;
/*     */ import com.jspgou.common.security.BadCredentialsException;
/*     */ import com.jspgou.common.security.UserNotAcitveException;
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.security.rememberme.CookieTheftException;
/*     */ import com.jspgou.common.security.rememberme.RememberMeService;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.security.UserNotInWebsiteException;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class LoginSvcImpl
/*     */   implements LoginSvc
/*     */ {
/*  48 */   private static final Logger log = LoggerFactory.getLogger(LoginSvcImpl.class);
/*     */   private ShopMemberMng shopMemberMng;
/*     */   private ShopAdminMng shopAdminMng;
/*     */   private UserMng userMng;
/*     */   private MemberMng memberMng;
/*     */   private ShopConfigMng shopConfigMng;
/*     */   private PwdEncoder pwdEncoder;
/*     */   private RememberMeService rememberMeService;
/*     */   private ShoppingSvc shoppingSvc;
/*     */   private SessionProvider session;
/*     */ 
/*     */   public ShopMember memberLogin(HttpServletRequest request, HttpServletResponse response, Website web, String username, String password)
/*     */     throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException, UserNotAcitveException
/*     */   {
/*  54 */     Long webId = web.getId();
/*     */ 
/*  56 */     logout(request, response);
/*  57 */     User user = login(username, password);
/*  58 */     ShopMember member = this.shopMemberMng.getByUserId(webId, user.getId());
/*  59 */     if (member == null) {
/*  60 */       ShopConfig config = this.shopConfigMng.findById(webId);
/*  61 */       if (config.getRegisterAuto().booleanValue())
/*     */       {
/*  63 */         member = this.shopMemberMng.join(user, webId, config.getRegisterGroup());
/*     */       }
/*  65 */       else throw new UserNotInWebsiteException("user '" + 
/*  66 */           user.getUsername() + "' not in Website '" + webId + 
/*  67 */           "'");
/*     */ 
/*     */     }
/*  70 */     else if (!member.getMember().getActive().booleanValue()) {
/*  71 */       throw new UserNotAcitveException("user '" + 
/*  72 */         user.getUsername() + "' not Active '" + webId + 
/*  73 */         "'");
/*     */     }
/*     */ 
/*  77 */     ShopAdmin admin = this.shopAdminMng.getByUserId(user.getId(), webId);
/*  78 */     if (admin != null) {
/*  79 */       this.session.setAttribute(request, response, "_admin_id_key", admin.getId());
/*     */     }
/*     */ 
/*  82 */     this.userMng.updateLoginInfo(user.getId(), request.getRemoteAddr());
/*  83 */     this.rememberMeService.loginSuccess(request, response, member.getMember());
/*  84 */     this.session.setAttribute(request, response, "_user_id_key", user.getId());
/*  85 */     this.session.setAttribute(request, response, "_member_id_key", member.getId());
/*  86 */     addUsernameCookie(member.getUsername(), null, null, request, response);
/*  87 */     this.shoppingSvc.addCookie(member, request, response);
/*  88 */     return member;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember(HttpServletRequest request, HttpServletResponse response, Website web)
/*     */   {
/*  93 */     ShopMember member = getMemberFromSession(request, response, web);
/*  94 */     return member != null ? member : getMemberFromCookie(request, response, 
/*  95 */       web);
/*     */   }
/*     */ 
/*     */   private ShopMember getMemberFromCookie(HttpServletRequest request, HttpServletResponse response, Website web)
/*     */   {
    /*     */     Member coreMember;
/*     */     try
/*     */     {
/* 102 */        coreMember = (Member)this.rememberMeService
/* 103 */         .autoLogin(request, response);
/* 104 */       if (coreMember == null)
/* 105 */         return null;
/*     */     }
/*     */     catch (CookieTheftException e) {
/* 108 */       log.warn("remember me cookie theft: {}", e.getMessage());
/* 109 */       return null;
/*     */     }
/* 112 */     if (coreMember == null) {
/* 113 */       return null;
/*     */     }
/* 115 */     Long webId = web.getId();
/* 116 */     Long userId = coreMember.getUser().getId();
/* 117 */     ShopMember member = null;
/*     */ 
/* 119 */     boolean change = false;
/*     */ 
/* 121 */     if (!coreMember.getWebsite().getId().equals(webId)) {
/* 122 */       coreMember = this.memberMng.getByUserId(webId, userId);
/* 123 */       change = true;
/*     */     }
/* 125 */     if (coreMember == null)
/*     */     {
/* 127 */       ShopConfig config = this.shopConfigMng.findById(webId);
/* 128 */       if (config.getRegisterAuto().booleanValue()) {
/* 129 */         member = this.shopMemberMng.join(userId, webId, config
/* 130 */           .getRegisterGroup());
/* 131 */         log.debug("shop member auto login. username= {}", member
/* 132 */           .getUsername());
/*     */       } else {
/* 134 */         log.debug("shop member not allow auto login.");
/*     */       }
/*     */     } else {
/* 137 */       member = this.shopMemberMng.findById(coreMember.getId());
/*     */ 
/* 139 */       if (member == null) {
/* 140 */         throw new IllegalStateException(
/* 141 */           "This is JspGou's BUG, ShopMember here should not be null.");
/*     */       }
/*     */     }
/* 144 */     if (member != null) {
/* 145 */       this.userMng.updateLoginInfo(userId, request.getRemoteAddr());
/* 146 */       this.session.setAttribute(request, response, "_user_id_key", member
/* 147 */         .getMember().getUser().getId());
/* 148 */       this.session.setAttribute(request, response, "_member_id_key", member.getMember().getId());
/*     */ 
/* 151 */       addUsernameCookie(member.getUsername(), null, null, request, response);
/*     */     }
/*     */ 
/* 158 */     return member;
/*     */   }
/*     */ 
/*     */   private ShopMember getMemberFromSession(HttpServletRequest request, HttpServletResponse response, Website web)
/*     */   {
/* 163 */     Long memberId = (Long)this.session.getAttribute(request, "_member_id_key");
/* 164 */     ShopMember member = null;
/* 165 */     Long webId = web.getId();
/* 166 */     if (memberId != null) {
/* 167 */       member = this.shopMemberMng.findById(memberId);
/*     */ 
/* 169 */       if ((member != null) && (member.getWebsite().getId().equals(webId))) {
/* 170 */         return member;
/*     */       }
/*     */     }
/* 173 */     Long userId = (Long)this.session.getAttribute(request, "_user_id_key");
/* 174 */     if (userId != null) {
/* 175 */       member = this.shopMemberMng.getByUserId(webId, userId);
/* 176 */       if (member == null) {
/* 177 */         ShopConfig config = this.shopConfigMng.findById(webId);
/*     */ 
/* 179 */         if (config.getRegisterAuto().booleanValue()) {
/* 180 */           member = this.shopMemberMng.join(userId, webId, config
/* 181 */             .getRegisterGroup());
/*     */         }
/*     */       }
/* 184 */       if (member != null) {
/* 185 */         this.session.setAttribute(request, response, "_member_id_key", member.getId());
/*     */       }
/*     */     }
/* 188 */     return member;
/*     */   }
/*     */ 
/*     */   private void addUsernameCookie(String username, String firstname, String lastname, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 194 */     StringBuilder un = new StringBuilder(
/* 195 */       new String(Base64.encodeBase64(username.getBytes())));
/* 196 */     while (un.charAt(un.length() - 1) == '=') {
/* 197 */       un.deleteCharAt(un.length() - 1);
/*     */     }
/* 199 */     response.addCookie(createCookie("username", un.toString(), request));
/*     */ 
/* 201 */     StringBuilder fn = new StringBuilder();
/* 202 */     if (!StringUtils.isBlank(firstname)) {
/* 203 */       fn.append(firstname);
/*     */     }
/* 205 */     fn.append(":");
/* 206 */     if (!StringUtils.isBlank(lastname)) {
/* 207 */       fn.append(lastname);
/*     */     }
/* 209 */     String value = fn.toString();
/* 210 */     fn = new StringBuilder(
/* 211 */       new String(Base64.encodeBase64(value.getBytes())));
/* 212 */     while (fn.charAt(fn.length() - 1) == '=') {
/* 213 */       fn.deleteCharAt(fn.length() - 1);
/*     */     }
/* 215 */     Cookie c = createCookie("fullname", fn.toString(), request);
/* 216 */     c.setMaxAge(2147483647);
/* 217 */     response.addCookie(c);
/*     */   }
/*     */ 
/*     */   private void deleteUsernameCookie(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 222 */     Cookie username = createCookie("username", null, request);
/* 223 */     username.setMaxAge(0);
/* 224 */     Cookie fullname = createCookie("fullname", null, request);
/* 225 */     fullname.setMaxAge(0);
/* 226 */     response.addCookie(username);
/* 227 */     response.addCookie(fullname);
/*     */   }
/*     */ 
/*     */   private Cookie createCookie(String name, String value, HttpServletRequest request)
/*     */   {
/* 232 */     Cookie cookie = new Cookie(name, value);
/* 233 */     String ctx = request.getContextPath();
/* 234 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/* 235 */     return cookie;
/*     */   }
/*     */ 
/*     */   public void logout(HttpServletRequest request, HttpServletResponse response) {
/* 239 */     this.rememberMeService.logout(request, response);
/* 240 */     this.session.logout(request, response);
/* 241 */     clearCookie(request, response);
/*     */   }
/*     */ 
/*     */   public void clearCookie(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 246 */     this.shoppingSvc.clearCookie(request, response);
/* 247 */     deleteUsernameCookie(request, response);
/*     */   }
/*     */ 
/*     */   public ShopAdmin adminLogin(HttpServletRequest request, HttpServletResponse response, Website web, String username, String password)
/*     */     throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException
/*     */   {
/* 254 */     Long webId = web.getId();
/*     */ 
/* 256 */     logout(request, response);
/* 257 */     User user = login(username, password);
/* 258 */     ShopAdmin admin = this.shopAdminMng.getByUserId(user.getId(), webId);
/* 259 */     if (admin == null) {
/* 260 */       throw new UserNotInWebsiteException("user '" + user.getUsername() + 
/* 261 */         "' not in Website '" + webId + "'");
/*     */     }
/* 263 */     ShopMember member = this.shopMemberMng.getByUserId(webId, user.getId());
/* 264 */     if (member != null) {
/* 265 */       this.session.setAttribute(request, response, "_member_id_key", member.getId());
/*     */     }
/* 267 */     this.userMng.updateLoginInfo(user.getId(), request.getRemoteAddr());
/* 268 */     this.session.setAttribute(request, response, "_user_id_key", user
/* 269 */       .getId());
/* 270 */     this.session.setAttribute(request, response, "_admin_id_key", admin
/* 271 */       .getId());
/* 272 */     addUsernameCookie(admin.getUsername(), null, null, request, response);
/* 273 */     return admin;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getAdmin(HttpServletRequest request, HttpServletResponse response, Website web)
/*     */   {
/* 278 */     Long webId = web.getId();
/* 279 */     Long adminId = (Long)this.session.getAttribute(request, 
/* 280 */       "_admin_id_key");
/*     */ 
/* 283 */     if (adminId != null) {
/* 284 */       ShopAdmin admin = this.shopAdminMng.findById(adminId);
/* 285 */       if ((admin != null) && (admin.getWebsite().getId().equals(webId)))
/*     */       {
/* 287 */         return admin;
/*     */       }
/*     */ 
/* 290 */       Long userId = admin.getAdmin().getUser().getId();
/* 291 */       admin = this.shopAdminMng.getByUserId(userId, webId);
/* 292 */       return admin;
/*     */     }
/*     */ 
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */   private User login(String username, String password) throws UsernameNotFoundException, BadCredentialsException
/*     */   {
/* 300 */     User user = this.userMng.getByUsername(username);
/* 301 */     if (user == null) {
/* 302 */       throw new UsernameNotFoundException("username not found: " + 
/* 303 */         username);
/*     */     }
/* 305 */     if (!this.pwdEncoder.isPasswordValid(user.getPassword(), password)) {
/* 306 */       throw new BadCredentialsException("password invalid!");
/*     */     }
/* 308 */     return user;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setShopMemberMng(ShopMemberMng shopMemberMng)
/*     */   {
/* 323 */     this.shopMemberMng = shopMemberMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setShopConfigMng(ShopConfigMng shopConfigMng) {
/* 328 */     this.shopConfigMng = shopConfigMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setShopAdminMng(ShopAdminMng shopAdminMng) {
/* 333 */     this.shopAdminMng = shopAdminMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setUserMng(UserMng userMng) {
/* 338 */     this.userMng = userMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setPwdEncoder(PwdEncoder pwdEncoder) {
/* 343 */     this.pwdEncoder = pwdEncoder;
/*     */   }
/*     */   @Autowired
/*     */   public void setRememberMeService(RememberMeService rememberMeService) {
/* 348 */     this.rememberMeService = rememberMeService;
/*     */   }
/*     */   @Autowired
/*     */   public void setMemberMng(MemberMng memberMng) {
/* 353 */     this.memberMng = memberMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setSessionProvider(SessionProvider session) {
/* 358 */     this.session = session;
/*     */   }
/*     */   @Autowired
/*     */   public void setShoppingSvc(ShoppingSvc shoppingSvc) {
/* 363 */     this.shoppingSvc = shoppingSvc;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.impl.LoginSvcImpl
 * JD-Core Version:    0.6.0
 */