/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.common.security.BadCredentialsException;
/*     */ import com.jspgou.common.security.UserNotAcitveException;
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.security.UserNotInWebsiteException;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class LoginAct
/*     */ {
/*  51 */   private static final Logger log = LoggerFactory.getLogger(LoginAct.class);
/*     */   private static final String LOGIN_INPUT = "tpl.loginInput";
/*     */   public static final String TPL_INDEX = "tpl.index";
/*     */ 
/*     */   @Autowired
/*     */   private LoginSvc loginSvc;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @RequestMapping(value={"/login.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String loginInput(String returnUrl, String message, HttpServletRequest request, ModelMap model)
/*     */   {
/*  60 */     Website web = SiteUtils.getWeb(request);
/*  61 */     if (!StringUtils.isBlank(returnUrl)) {
/*  62 */       model.addAttribute("returnUrl", returnUrl);
/*  63 */       if (!StringUtils.isBlank(message)) {
/*  64 */         model.addAttribute("message", message);
/*     */       }
/*     */     }
/*     */ 
/*  68 */     model.addAttribute("global", this.globalMng.get().getConfigAttr());
/*  69 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  70 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/*  71 */       "tpl.loginInput", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/login.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String loginSubmit(String username, String password, String returnUrl, String redirectUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  79 */     Website web = SiteUtils.getWeb(request);
/*  80 */     WebErrors errors = WebErrors.create(request);
/*     */     try
/*     */     {
/*  83 */       ShopMember member = this.loginSvc.memberLogin(request, response, web, username, password);
/*  84 */       if (member == null) {
/*  85 */         return "redirect:/";
/*     */       }
/*  87 */       log.info("member '{}' login success.", username);
/*  88 */       if (!StringUtils.isBlank(returnUrl))
/*  89 */         return "redirect:" + returnUrl;
/*  90 */       if (!StringUtils.isBlank(redirectUrl)) {
/*  91 */         return "redirect:" + redirectUrl;
/*     */       }
/*     */ 
/*  94 */       String sessionKey = this.session.getSessionId(request, response);
/*  95 */       model.addAttribute("member", member);
/*  96 */       ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/*  98 */       return web.getTemplate("index", MessageResolver.getMessage(request, 
/*  99 */         "tpl.index", new Object[0]), request);
/*     */     }
/*     */     catch (UsernameNotFoundException e) {
/* 102 */       errors.addErrorCode("error.usernameNotExist");
/* 103 */       log.info(e.getMessage());
/*     */     } catch (BadCredentialsException e) {
/* 105 */       errors.addErrorCode("error.passwordInvalid");
/* 106 */       log.info(e.getMessage());
/*     */     } catch (UserNotInWebsiteException e) {
/* 108 */       errors.addErrorCode("error.usernameNotInWebsite");
/* 109 */       log.info(e.getMessage());
/*     */     } catch (UserNotAcitveException e) {
/* 111 */       errors.addErrorCode("error.usernameNotActivated");
/* 112 */       log.info(e.getMessage());
/*     */     } catch (Exception e) {
/* 114 */       errors.addErrorCode("error.appIderror");
/* 115 */       log.info(e.getMessage());
/*     */     }
/* 117 */     errors.toModel(model);
/* 118 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 119 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.loginInput", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public Integer errorRemaining(String username) {
/* 123 */     if (StringUtils.isBlank(username)) {
/* 124 */       return null;
/*     */     }
/* 126 */     User user = this.userMng.getByUsername(username);
/* 127 */     if (user == null) {
/* 128 */       return null;
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logout.jspx"})
/*     */   public String logout(String redirectUrl, String username, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 137 */     this.apiUserLoginMng.userLogout(username);
/* 138 */     this.loginSvc.logout(request, response);
/* 139 */     if (!StringUtils.isBlank(redirectUrl)) {
/* 140 */       return "redirect:" + redirectUrl;
/*     */     }
/* 142 */     return "redirect:/";
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.LoginAct
 * JD-Core Version:    0.6.0
 */