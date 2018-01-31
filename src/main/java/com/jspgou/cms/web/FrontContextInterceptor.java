/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopConfig;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.manager.ShopConfigMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.cms.web.threadvariable.GroupThread;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.util.CheckMobile;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.shiro.SecurityUtils;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*     */ 
/*     */ public class FrontContextInterceptor extends HandlerInterceptorAdapter
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopConfigMng shopConfigMng;
/*     */ 
/*     */   @Autowired
/*     */   private LoginSvc loginSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
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
/*  51 */     Website web = SiteUtils.getWeb(request);
/*  52 */     ShopConfig config = this.shopConfigMng.findById(web.getId());
/*  53 */     if (config == null) {
/*  54 */       throw new IllegalStateException(
/*  55 */         "no ShopConfig found in Website id=" + web.getId());
/*     */     }
/*  57 */     request.setAttribute("_shop_config_key", config);
/*     */ 
/*  66 */     ShopMember member = null;
/*  67 */     ShopAdmin admin = null;
/*  68 */     Subject subject = SecurityUtils.getSubject();
/*  69 */     String username = "";
/*  70 */     if (subject.isAuthenticated()) {
/*  71 */       username = (String)subject.getPrincipal();
/*  72 */       member = this.shopMemberMng.getByUsername(web.getId(), username);
/*  73 */       admin = this.shopAdminMng.getByUsername(username);
/*     */     }
/*  75 */     if (admin != null) {
/*  76 */       Long userId = admin.getAdmin().getUser().getId();
/*  77 */       member = this.shopMemberMng.getByUserId(web.getId(), userId);
/*  78 */       if (member == null)
/*     */       {
/*  80 */         if (config.getRegisterAuto().booleanValue()) {
/*  81 */           member = this.shopMemberMng.join(userId, web.getId(), config.getRegisterGroup());
/*     */         }
/*     */       }
/*  84 */       AdminThread.set(admin);
/*     */     }
/*  86 */     checkEquipment(request, response);
/*  87 */     if (member != null) {
/*  88 */       List list = this.apiAccountMng.getPage(1, 1).getList();
/*  89 */       String sessionId = this.session.getSessionId(request, response);
/*  90 */       MemberThread.setSessionKey(AES128Util.encrypt(sessionId, ((ApiAccount)list.get(0)).getAesKey(), ((ApiAccount)list.get(0)).getIvKey()));
/*  91 */       MemberThread.setUserName(username);
/*  92 */       this.apiUserLoginMng.userLogin(username, sessionId);
/*  93 */       MemberThread.set(member);
/*  94 */       GroupThread.set(member.getGroup());
/*     */     }
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
/*     */     throws Exception
/*     */   {
/* 103 */     MemberThread.remove();
/* 104 */     GroupThread.remove();
/*     */   }
/*     */ 
/*     */   public void checkEquipment(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 113 */     String ua = (String)this.session.getAttribute(request, "ua");
/* 114 */     if (ua == null)
/*     */       try {
/* 116 */         String userAgent = request.getHeader("USER-AGENT").toLowerCase();
/* 117 */         if (userAgent == null) {
/* 118 */           userAgent = "";
/*     */         }
/* 120 */         if (CheckMobile.check(userAgent))
/* 121 */           ua = "mobile";
/*     */         else {
/* 123 */           ua = "pc";
/*     */         }
/*     */ 
/* 126 */         this.session.setAttribute(request, response, "ua", ua);
/*     */       } catch (Exception localException) {
/*     */       }
/* 129 */     if (StringUtils.isNotBlank(ua))
/* 130 */       request.setAttribute("ua", ua);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.FrontContextInterceptor
 * JD-Core Version:    0.6.0
 */