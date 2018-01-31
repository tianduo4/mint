/*     */ package com.jspgou.core.security;
/*     */ 
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.common.security.DisabledException;
/*     */ import com.jspgou.common.security.UserNotAcitveException;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.manager.AdminMng;
/*     */ import com.jspgou.core.manager.LogMng;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.octo.captcha.service.image.ImageCaptchaService;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.shiro.authc.AuthenticationException;
/*     */ import org.apache.shiro.authc.AuthenticationToken;
/*     */ import org.apache.shiro.authc.UnknownAccountException;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
/*     */ import org.apache.shiro.web.util.WebUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public class CmsAuthenticationFilter extends FormAuthenticationFilter
/*     */ {
/*  47 */   private Logger logger = LoggerFactory.getLogger(CmsAuthenticationFilter.class);
/*     */   public static final String COOKIE_ERROR_REMAINING = "_error_remaining";
/*     */   public static final String CAPTCHA_PARAM = "captcha";
/*     */   public static final String RETURN_URL = "returnUrl";
/*     */   public static final String FAILURE_URL = "failureUrl";
/*     */ 
/*     */   @Autowired
/*     */   private MemberMng memberMng;
/*     */ 
/*     */   @Autowired
/*     */   private AdminMng adminMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private ImageCaptchaService imageCaptchaService;
/*     */ 
/*     */   @Autowired
/*     */   private LogMng cmsLogMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */   private String adminPrefix;
/*     */   private String adminIndex;
/*     */   private String adminLogin;
/*     */ 
/*     */   protected boolean executeLogin(ServletRequest request, ServletResponse response)
/*     */     throws UnknownAccountException, Exception
/*     */   {
/*  64 */     AuthenticationToken token = createToken(request, response);
/*  65 */     if (token == null) {
/*  66 */       String msg = "create AuthenticationToken error";
/*  67 */       throw new IllegalStateException(msg);
/*     */     }
/*  69 */     HttpServletRequest req = (HttpServletRequest)request;
/*  70 */     HttpServletResponse res = (HttpServletResponse)response;
/*  71 */     String username = (String)token.getPrincipal();
/*  72 */     boolean adminLogin = false;
/*  73 */     if (req.getRequestURI().startsWith(req.getContextPath() + getAdminPrefix())) {
/*  74 */       adminLogin = true;
/*     */     }
/*  76 */     String failureUrl = req.getParameter("failureUrl");
/*     */ 
/*  91 */     Admin admin = this.adminMng.getByUsername(username);
/*  92 */     Member member = this.memberMng.getByUsername(username);
/*     */ 
/*  94 */     if ((admin != null) && 
/*  95 */       (isDisabled(admin))) {
/*  96 */       return onLoginFailure(token, failureUrl, adminLogin, new DisabledException(), request, response);
/*     */     }
/*     */ 
/*  99 */     if ((member != null) && 
/* 100 */       (!isActive(member))) {
/* 101 */       return onLoginFailure(token, failureUrl, adminLogin, new UserNotAcitveException(), request, response);
/*     */     }
/*     */     try
/*     */     {
/* 105 */       Subject subject = getSubject(request, response);
/* 106 */       subject.login(token);
/* 107 */       return onLoginSuccess(token, adminLogin, subject, request, response); } catch (AuthenticationException e) {
/*     */     }
/* 109 */     return onLoginFailure(token, failureUrl, adminLogin, e, request, response);
/*     */   }
/*     */ 
/*     */   public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
/*     */     throws Exception
/*     */   {
/* 115 */     boolean isAllowed = isAccessAllowed(request, response, mappedValue);
/*     */ 
/* 117 */     if ((isAllowed) && (isLoginRequest(request, response))) {
/*     */       try {
/* 119 */         issueSuccessRedirect(request, response);
/*     */       } catch (Exception e) {
/* 121 */         this.logger.error("", e);
/*     */       }
/* 123 */       return false;
/*     */     }
/* 125 */     return (isAllowed) || (onAccessDenied(request, response, mappedValue));
/*     */   }
/*     */ 
/*     */   protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
/*     */     throws Exception
/*     */   {
/* 131 */     HttpServletRequest req = (HttpServletRequest)request;
/* 132 */     HttpServletResponse res = (HttpServletResponse)response;
/* 133 */     String successUrl = req.getParameter("returnUrl");
/* 134 */     if (StringUtils.isBlank(successUrl))
/*     */     {
/* 136 */       if (req.getRequestURI().startsWith(
/* 136 */         req.getContextPath() + getAdminPrefix()))
/*     */       {
/* 138 */         successUrl = getAdminIndex();
/*     */ 
/* 140 */         WebUtils.getAndClearSavedRequest(request);
/* 141 */         WebUtils.issueRedirect(request, response, successUrl, null, true);
/* 142 */         return;
/*     */       }
/* 144 */       successUrl = getSuccessUrl();
/*     */     }
/*     */ 
/* 147 */     WebUtils.redirectToSavedRequest(req, res, successUrl);
/*     */   }
/*     */ 
/*     */   protected boolean isLoginRequest(ServletRequest req, ServletResponse resp) {
/* 151 */     return (pathsMatch(getLoginUrl(), req)) || (pathsMatch(getAdminLogin(), req));
/*     */   }
/*     */ 
/*     */   private boolean onLoginSuccess(AuthenticationToken token, boolean adminLogin, Subject subject, ServletRequest request, ServletResponse response)
/*     */     throws Exception
/*     */   {
/* 160 */     HttpServletRequest req = (HttpServletRequest)request;
/* 161 */     HttpServletResponse res = (HttpServletResponse)response;
/* 162 */     String username = (String)subject.getPrincipal();
/* 163 */     User user = this.userMng.getByUsername(username);
/* 164 */     String ip = RequestUtils.getIpAddr(req);
/* 165 */     Date now = new Timestamp(System.currentTimeMillis());
/* 166 */     String userSessionId = this.session.getSessionId((HttpServletRequest)request, (HttpServletResponse)response);
/* 167 */     this.userMng.updateLoginInfo(user.getId(), ip, now, userSessionId);
/*     */ 
/* 169 */     if (adminLogin) {
/* 170 */       this.cmsLogMng.loginSuccess(req, user);
/*     */     }
/* 172 */     this.userMng.updateLoginSuccess(user.getId(), ip);
/* 173 */     loginCookie(username, req, res);
/* 174 */     return super.onLoginSuccess(token, subject, request, response);
/*     */   }
/*     */ 
/*     */   private boolean onLoginFailure(AuthenticationToken token, String failureUrl, boolean adminLogin, AuthenticationException e, ServletRequest request, ServletResponse response)
/*     */   {
/* 182 */     String username = (String)token.getPrincipal();
/* 183 */     HttpServletRequest req = (HttpServletRequest)request;
/* 184 */     String ip = RequestUtils.getIpAddr(req);
/* 185 */     User user = this.userMng.getByUsername(username);
/* 186 */     if (user != null) {
/* 187 */       this.userMng.updateLoginError(user.getId(), ip);
/*     */     }
/*     */ 
/* 190 */     if (adminLogin) {
/* 191 */       this.cmsLogMng.loginFailure(req, "username=" + username);
/*     */     }
/* 193 */     return onLoginFailure(failureUrl, token, e, request, response);
/*     */   }
/*     */ 
/*     */   private boolean onLoginFailure(String failureUrl, AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response)
/*     */   {
/* 199 */     String className = e.getClass().getName();
/* 200 */     request.setAttribute(getFailureKeyAttribute(), className);
/* 201 */     if ((failureUrl != null) || (StringUtils.isNotBlank(failureUrl)))
/*     */       try {
/* 203 */         request.getRequestDispatcher(failureUrl).forward(request, response);
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   private void loginCookie(String username, HttpServletRequest request, HttpServletResponse response) {
/* 212 */     String domain = request.getServerName();
/* 213 */     if (domain.indexOf(".") > -1) {
/* 214 */       domain = domain.substring(domain.indexOf(".") + 1);
/*     */     }
/* 216 */     CookieUtils.addCookie(request, response, "JSESSIONID", this.session.getSessionId(request, response), null, domain, "/");
/*     */ 
/* 218 */     CookieUtils.addCookie(request, response, "sso_logout", null, Integer.valueOf(0), domain, "/");
/*     */   }
/*     */ 
/*     */   private boolean isCaptchaRequired(String username, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 223 */     Integer errorRemaining = this.userMng.errorRemaining(username);
/* 224 */     String captcha = RequestUtils.getQueryParam(request, "captcha");
/*     */ 
/* 227 */     return (!StringUtils.isBlank(captcha)) || ((errorRemaining != null) && (errorRemaining.intValue() < 0));
/*     */   }
/*     */ 
/*     */   private boolean isDisabled(Admin admin)
/*     */   {
/* 235 */     return admin.getDisabled().booleanValue();
/*     */   }
/*     */ 
/*     */   private boolean isActive(Member member)
/*     */   {
/* 243 */     Member un = this.memberMng.findById(member.getId());
/* 244 */     if (un != null)
/*     */     {
/* 246 */       return member.getActive().booleanValue();
/*     */     }
/*     */ 
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   public String getAdminPrefix()
/*     */   {
/* 279 */     return this.adminPrefix;
/*     */   }
/*     */ 
/*     */   public void setAdminPrefix(String adminPrefix) {
/* 283 */     this.adminPrefix = adminPrefix;
/*     */   }
/*     */ 
/*     */   public String getAdminIndex() {
/* 287 */     return this.adminIndex;
/*     */   }
/*     */ 
/*     */   public void setAdminIndex(String adminIndex) {
/* 291 */     this.adminIndex = adminIndex;
/*     */   }
/*     */ 
/*     */   public String getAdminLogin() {
/* 295 */     return this.adminLogin;
/*     */   }
/*     */ 
/*     */   public void setAdminLogin(String adminLogin) {
/* 299 */     this.adminLogin = adminLogin;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.CmsAuthenticationFilter
 * JD-Core Version:    0.6.0
 */