/*     */ package com.jspgou.common.security.rememberme;
/*     */ 
/*     */ import com.jspgou.common.security.AccountStatusException;
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.common.security.userdetails.AccountStatusUserDetailsChecker;
/*     */ import com.jspgou.common.security.userdetails.UserDetails;
/*     */ import com.jspgou.common.security.userdetails.UserDetailsChecker;
/*     */ import com.jspgou.common.security.userdetails.UserDetailsService;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.util.Assert;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public abstract class AbstractRememberMeServices
/*     */   implements RememberMeService, InitializingBean
/*     */ {
/*     */   public static final String REMEMBER_ME_COOKIE_KEY = "remember_me_cookie";
/*     */   private static final String DELIMITER = ":";
/*     */   public static final String DEFAULT_PARAMETER = "remember_me";
/*     */   public static final int TWO_WEEKS_S = 1209600;
/*  30 */   private String cookieName = "remember_me_cookie";
/*  31 */   private String parameter = "remember_me";
/*  32 */   private int tokenValiditySeconds = 1209600;
/*     */   private boolean alwaysRemember;
/*     */   private boolean alwaysRememberCookie;
/*     */   private String key;
/*  36 */   private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
/*     */   private UserDetailsService userDetailsService;
/*  39 */   protected final Logger logger = LoggerFactory.getLogger(getClass());
/*     */ 
/*     */   public final UserDetails autoLogin(HttpServletRequest request, HttpServletResponse response)
/*     */     throws CookieTheftException
/*     */   {
/*  44 */     String rememberMeCookie = extractRememberMeCookie(request);
/*     */ 
/*  46 */     if (rememberMeCookie == null) {
/*  47 */       return null;
/*     */     }
/*     */ 
/*  50 */     this.logger.debug("Remember-me cookie detected");
/*     */ 
/*  52 */     UserDetails user = null;
/*     */     try
/*     */     {
/*  56 */       String[] cookieTokens = decodeCookie(rememberMeCookie);
/*  57 */       user = processAutoLoginCookie(cookieTokens, request, response);
/*  58 */       this.userDetailsChecker.check(user);
/*     */     } catch (CookieTheftException cte) {
/*  60 */       cancelCookie(request, response);
/*  61 */       throw cte;
/*     */     } catch (UsernameNotFoundException noUser) {
/*  63 */       cancelCookie(request, response);
/*  64 */       this.logger.debug("Remember-me login was valid but corresponding user not found.", 
/*  65 */         noUser);
/*  66 */       return null;
/*     */     } catch (InvalidCookieException invalidCookie) {
/*  68 */       cancelCookie(request, response);
/*  69 */       this.logger.debug("Invalid remember-me cookie: " + 
/*  70 */         invalidCookie.getMessage());
/*  71 */       return null;
/*     */     } catch (AccountStatusException statusInvalid) {
/*  73 */       cancelCookie(request, response);
/*  74 */       this.logger.debug("Invalid UserDetails: " + statusInvalid.getMessage());
/*  75 */       return null;
/*     */     } catch (RememberMeAuthenticationException e) {
/*  77 */       cancelCookie(request, response);
/*  78 */       this.logger.debug(e.getMessage());
/*  79 */       return null;
/*     */     }
/*     */     String[] cookieTokens;
/*  81 */     this.logger.debug("Remember-me cookie accepted");
/*  82 */     return user;
/*     */   }
/*     */ 
/*     */   public final boolean loginSuccess(HttpServletRequest request, HttpServletResponse response, UserDetails user)
/*     */   {
/*  94 */     if (!rememberMeRequested(request, this.parameter)) {
/*  95 */       this.logger.debug("Remember-me login not requested.");
/*  96 */       return false;
/*     */     }
/*     */ 
/*  99 */     return onLoginSuccess(request, response, user);
/*     */   }
/*     */ 
/*     */   public final void loginFail(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 105 */     this.logger.debug("Interactive login attempt was unsuccessful.");
/* 106 */     cancelCookie(request, response);
/* 107 */     onLoginFail(request, response);
/*     */   }
/*     */ 
/*     */   public void logout(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 112 */     this.logger.debug("Remember-me logout.");
/* 113 */     cancelCookie(request, response);
/* 114 */     onLogout(request, response);
/*     */   }
/*     */ 
/*     */   protected String extractRememberMeCookie(HttpServletRequest request)
/*     */   {
/* 126 */     Cookie[] cookies = request.getCookies();
/*     */ 
/* 128 */     if ((cookies == null) || (cookies.length == 0)) {
/* 129 */       return null;
/*     */     }
/*     */ 
/* 132 */     for (int i = 0; i < cookies.length; i++) {
/* 133 */       if (this.cookieName.equals(cookies[i].getName())) {
/* 134 */         return cookies[i].getValue();
/*     */       }
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean rememberMeRequested(HttpServletRequest request, String parameter)
/*     */   {
/* 158 */     if (this.alwaysRemember) {
/* 159 */       return true;
/*     */     }
/*     */ 
/* 162 */     String paramValue = request.getParameter(parameter);
/*     */ 
/* 164 */     if ((paramValue != null) && (
/* 165 */       (paramValue.equalsIgnoreCase("true")) || 
/* 166 */       (paramValue.equalsIgnoreCase("on")) || 
/* 167 */       (paramValue.equalsIgnoreCase("yes")) || 
/* 168 */       (paramValue.equals("1")))) {
/* 169 */       return true;
/*     */     }
/*     */ 
/* 173 */     if (this.logger.isDebugEnabled()) {
/* 174 */       this.logger.debug("Did not send remember-me cookie (principal did not set parameter '" + 
/* 175 */         parameter + "')");
/*     */     }
/*     */ 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 195 */     String cookieValue = encodeCookie(tokens);
/* 196 */     Cookie cookie = new Cookie(this.cookieName, cookieValue);
/* 197 */     String ctx = request.getContextPath();
/* 198 */     cookie.setPath(StringUtils.hasText(ctx) ? ctx : "/");
/* 199 */     cookie.setMaxAge(maxAge);
/* 200 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   protected void cancelCookie(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 212 */     this.logger.debug("Cancelling cookie");
/* 213 */     Cookie cookie = new Cookie(this.cookieName, null);
/* 214 */     String ctx = request.getContextPath();
/* 215 */     cookie.setPath(StringUtils.hasText(ctx) ? ctx : "/");
/* 216 */     cookie.setMaxAge(0);
/* 217 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   protected String[] decodeCookie(String cookieValue)
/*     */     throws InvalidCookieException
/*     */   {
/* 232 */     StringBuilder sb = new StringBuilder(cookieValue.length() + 3)
/* 233 */       .append(cookieValue);
/* 234 */     for (int j = 0; j < sb.length() % 4; j++) {
/* 235 */       sb.append("=");
/*     */     }
/* 237 */     cookieValue = sb.toString();
/* 238 */     if (!Base64.isArrayByteBase64(cookieValue.getBytes())) {
/* 239 */       throw new InvalidCookieException(
/* 240 */         "Cookie token was not Base64 encoded; value was '" + 
/* 241 */         cookieValue + "'");
/*     */     }
/*     */ 
/* 244 */     String cookieAsPlainText = new String(Base64.decodeBase64(cookieValue
/* 245 */       .getBytes()));
/*     */ 
/* 247 */     return StringUtils.delimitedListToStringArray(cookieAsPlainText, 
/* 248 */       ":");
/*     */   }
/*     */ 
/*     */   protected String encodeCookie(String[] cookieTokens)
/*     */   {
/* 260 */     StringBuilder sb = new StringBuilder();
/* 261 */     for (int i = 0; i < cookieTokens.length; i++) {
/* 262 */       sb.append(cookieTokens[i]);
/*     */ 
/* 264 */       if (i < cookieTokens.length - 1) {
/* 265 */         sb.append(":");
/*     */       }
/*     */     }
/*     */ 
/* 269 */     String value = sb.toString();
/* 270 */     sb = new StringBuilder(
/* 271 */       new String(Base64.encodeBase64(value.getBytes())));
/*     */ 
/* 273 */     while (sb.charAt(sb.length() - 1) == '=') {
/* 274 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*     */ 
/* 277 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected abstract UserDetails processAutoLoginCookie(String[] paramArrayOfString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws RememberMeAuthenticationException, UsernameNotFoundException;
/*     */ 
/*     */   protected abstract boolean onLoginSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, UserDetails paramUserDetails);
/*     */ 
/*     */   protected void onLoginFail(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void onLogout(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void afterPropertiesSet()
/*     */     throws Exception
/*     */   {
/* 324 */     Assert.hasLength(this.key);
/* 325 */     Assert.hasLength(this.parameter);
/* 326 */     Assert.hasLength(this.cookieName);
/* 327 */     Assert.notNull(this.userDetailsService);
/*     */   }
/*     */ 
/*     */   protected String getCookieName() {
/* 331 */     return this.cookieName;
/*     */   }
/*     */ 
/*     */   public void setCookieName(String cookieName) {
/* 335 */     this.cookieName = cookieName;
/*     */   }
/*     */ 
/*     */   public boolean isAlwaysRemember() {
/* 339 */     return this.alwaysRemember;
/*     */   }
/*     */ 
/*     */   public void setAlwaysRemember(boolean alwaysRemember) {
/* 343 */     this.alwaysRemember = alwaysRemember;
/*     */   }
/*     */ 
/*     */   public String getParameter() {
/* 347 */     return this.parameter;
/*     */   }
/*     */ 
/*     */   public void setParameter(String parameter) {
/* 351 */     this.parameter = parameter;
/*     */   }
/*     */ 
/*     */   public UserDetailsService getUserDetailsService() {
/* 355 */     return this.userDetailsService;
/*     */   }
/*     */ 
/*     */   public void setUserDetailsService(UserDetailsService userDetailsService) {
/* 359 */     this.userDetailsService = userDetailsService;
/*     */   }
/*     */ 
/*     */   public String getKey() {
/* 363 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setKey(String key) {
/* 367 */     this.key = key;
/*     */   }
/*     */ 
/*     */   protected int getTokenValiditySeconds() {
/* 371 */     return this.tokenValiditySeconds;
/*     */   }
/*     */ 
/*     */   public void setTokenValiditySeconds(int tokenValiditySeconds) {
/* 375 */     this.tokenValiditySeconds = tokenValiditySeconds;
/*     */   }
/*     */ 
/*     */   public boolean isAlwaysRememberCookie() {
/* 379 */     return this.alwaysRememberCookie;
/*     */   }
/*     */ 
/*     */   public void setAlwaysRememberCookie(boolean alwaysRememberCookie) {
/* 383 */     this.alwaysRememberCookie = alwaysRememberCookie;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.rememberme.AbstractRememberMeServices
 * JD-Core Version:    0.6.0
 */