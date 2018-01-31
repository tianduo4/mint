/*     */ package com.jspgou.common.security.rememberme;
/*     */ 
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.common.security.userdetails.UserDetails;
/*     */ import com.jspgou.common.security.userdetails.UserDetailsService;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.springframework.dao.DataAccessException;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class TokenBasedRememberMeServices extends AbstractRememberMeServices
/*     */ {
/*     */   protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response)
/*     */     throws DataAccessException, UsernameNotFoundException, InvalidCookieException
/*     */   {
/*  84 */     if (cookieTokens.length != 4) {
/*  85 */       throw new InvalidCookieException("Cookie token did not contain 4 tokens, but contained '" + 
/*  87 */         Arrays.asList(cookieTokens) + "'");
/*     */     }
               long tokenExpiryTime;
/*     */       Long userId;
/*     */     try
/*     */     {
/*  93 */       tokenExpiryTime = new Long(cookieTokens[1]).longValue();
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/*  95 */       throw new InvalidCookieException(
/*  96 */         "Cookie token[1] did not contain a valid number (contained '" + 
/*  97 */         cookieTokens[1] + "')");
/*     */     }
/* 100 */     if (isTokenExpired(tokenExpiryTime))
/* 101 */       throw new InvalidCookieException(
/* 102 */         "Cookie token[1] has expired (expired on '" + 
/* 103 */         new Date(tokenExpiryTime) + 
/* 104 */         "'; current time is '" + new Date() + "')");
/*     */     try
/*     */     {
/* 107 */       userId = new Long(cookieTokens[3]);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {

/* 109 */       throw new InvalidCookieException(
/* 110 */         "Cookie token[3] did not contain a valid number (contained '" + 
/* 111 */         cookieTokens[3] + "')");
/*     */     }
/* 117 */     UserDetails user = getUserDetailsService().loadUser(userId, 
/* 118 */       cookieTokens[0]);
/*     */ 
/* 128 */     String expectedTokenSignature = makeTokenSignature(tokenExpiryTime, 
/* 129 */       user.getUsername(), user.getPassword(), user.getId());
/*     */ 
/* 131 */     if (!expectedTokenSignature.equals(cookieTokens[2])) {
/* 132 */       throw new InvalidCookieException(
/* 133 */         "Cookie token[2] contained signature '" + cookieTokens[2] + 
/* 134 */         "' but expected '" + expectedTokenSignature + "'");
/*     */     }
/* 136 */     return user;
/*     */   }
/*     */ 
/*     */   protected String makeTokenSignature(long tokenExpiryTime, String username, String password, Long id)
/*     */   {
/* 145 */     return DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + 
/* 146 */       password + ":" + getKey() + ":" + id);
/*     */   }
/*     */ 
/*     */   protected boolean isTokenExpired(long tokenExpiryTime) {
/* 150 */     return tokenExpiryTime < System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public boolean onLoginSuccess(HttpServletRequest request, HttpServletResponse response, UserDetails user)
/*     */   {
/* 157 */     String username = user.getUsername();
/* 158 */     String password = user.getPassword();
/*     */ 
/* 163 */     if ((!StringUtils.hasLength(username)) || 
/* 164 */       (!StringUtils.hasLength(password))) {
/* 165 */       return false;
/*     */     }
/*     */ 
/* 168 */     int tokenLifetime = calculateLoginLifetime(request, user);
/* 169 */     long expiryTime = System.currentTimeMillis();
/*     */ 
/* 171 */     expiryTime += 1000L * (tokenLifetime < 0 ? 1209600 : tokenLifetime);
/*     */ 
/* 173 */     String signatureValue = makeTokenSignature(expiryTime, username, 
/* 174 */       password, user.getId());
/*     */ 
/* 176 */     setCookie(new String[] { username, Long.toString(expiryTime), 
/* 177 */       signatureValue, user.getId().toString() }, tokenLifetime, 
/* 178 */       request, response);
/*     */ 
/* 180 */     if (this.logger.isDebugEnabled()) {
/* 181 */       this.logger.debug("Added remember-me cookie for user '" + username + 
/* 182 */         "', expiry: '" + new Date(expiryTime) + "'");
/*     */     }
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   protected int calculateLoginLifetime(HttpServletRequest request, UserDetails user)
/*     */   {
/* 208 */     return getTokenValiditySeconds();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.rememberme.TokenBasedRememberMeServices
 * JD-Core Version:    0.6.0
 */