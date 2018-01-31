/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.octo.captcha.service.CaptchaService;
/*     */ import com.octo.captcha.service.CaptchaServiceException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ForgotPasswordAct
/*     */ {
/*  46 */   private static final Logger log = LoggerFactory.getLogger(ForgotPasswordAct.class);
/*     */   private static final String FORGOTTEN_INPUT = "tpl.forgottenInput";
/*     */   private static final String FORGOTTEN_RESULT = "tpl.forgottenResult";
/*     */   private static final String RESET_PASSWORD_TPL = "tpl.resetPassword";
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private CaptchaService captchaService;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @RequestMapping(value={"/forgot_password.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String fogottenInput(HttpServletRequest request, ModelMap model)
/*     */   {
/*  61 */     Website web = SiteUtils.getWeb(request);
/*  62 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  63 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/*  64 */       "tpl.forgottenInput", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/forgot_password.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String fogottenSubmit(String checkcode, String username, String email, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  81 */     Website web = SiteUtils.getWeb(request);
/*  82 */     WebErrors errors = validateFogotten(checkcode, username, email, 
/*  83 */       request, response);
/*  84 */     if (errors.hasErrors()) {
/*  85 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*     */ 
/*  88 */     User user = this.userMng.getByUsername(username);
/*  89 */     MessageTemplate tpl = (MessageTemplate)web.getMessages().get(
/*  90 */       "resetPassword");
/*  91 */     EmailSender sender = web.getEmailSender();
/*  92 */     if (user == null)
/*     */     {
/*  94 */       model.addAttribute("status", Integer.valueOf(1));
/*  95 */     } else if (!user.getEmail().equalsIgnoreCase(email))
/*     */     {
/*  97 */       model.addAttribute("status", Integer.valueOf(2));
/*  98 */     } else if (!user.getEmail().equals(email))
/*     */     {
/* 100 */       model.addAttribute("status", Integer.valueOf(3));
/* 101 */     } else if (sender == null)
/*     */     {
/* 103 */       model.addAttribute("status", Integer.valueOf(4));
/* 104 */     } else if (tpl == null)
/*     */     {
/* 106 */       model.addAttribute("status", Integer.valueOf(5));
/*     */     }
/*     */     else {
/*     */       try {
/* 110 */         String base = new String(web.getUrlBuff(true));
/* 111 */         user = this.userMng.passwordForgotten(user.getId(), base, sender, tpl);
/*     */ 
/* 113 */         String emailtype = email.substring(email.indexOf("@") + 1, email.indexOf("."));
/* 114 */         model.addAttribute("emailtype", emailtype);
/* 115 */         model.addAttribute("status", Integer.valueOf(0));
/* 116 */         model.addAttribute("user", user);
/*     */       }
/*     */       catch (Exception e) {
/* 119 */         model.addAttribute("status", Integer.valueOf(100));
/* 120 */         model.addAttribute("message", e.getMessage());
/* 121 */         log.error("send email exception.", e);
/*     */       }
/*     */     }
/* 124 */     model.addAttribute("user", user);
/* 125 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 126 */     log.info("find passsword, username={} email={}", username, email);
/* 127 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/* 128 */       "tpl.forgottenResult", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/reset_password.jspx"})
/*     */   public String resetPwd(Long uid, String activationCode, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 135 */     Website web = SiteUtils.getWeb(request);
/* 136 */     WebErrors errors = validateReset(uid, activationCode, request);
/* 137 */     if (errors.hasErrors()) {
/* 138 */       return FrontHelper.showMessage((String)errors.getErrors().get(0), web, 
/* 139 */         model, request);
/*     */     }
/* 141 */     User user = this.userMng.findById(uid);
/*     */     boolean success;
/* 143 */     if (activationCode.equals(user.getResetKey())) {
/* 144 */       user = this.userMng.resetPassword(user.getId());
/* 145 */       success = true;
/*     */     } else {
/* 147 */       success = false;
/*     */     }
/* 149 */     model.addAttribute("user", user);
/* 150 */     model.addAttribute("success", Boolean.valueOf(success));
/* 151 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 152 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/* 153 */       "tpl.resetPassword", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   private WebErrors validateFogotten(String checkcode, String username, String email, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 159 */     WebErrors errors = WebErrors.create(request);
/* 160 */     String id = this.session.getSessionId(request, response);
/* 161 */     if (errors.ifOutOfLength(checkcode, "checkcode", 3, 10)) {
/* 162 */       return errors;
/*     */     }
/*     */     try
/*     */     {
/* 166 */       if (!this.captchaService.validateResponseForID(id, checkcode
/* 166 */         .toUpperCase(Locale.ENGLISH)).booleanValue())
/*     */       {
/* 167 */         errors.addErrorCode("error.checkcodeIncorrect");
/* 168 */         return errors;
/*     */       }
/*     */     } catch (CaptchaServiceException e) {
/* 171 */       errors.addErrorCode("error.checkcodeInvalid");
/* 172 */       errors.addErrorString(e.getMessage());
/* 173 */       return errors;
/*     */     }
/* 175 */     if (errors.ifNotEmail(email, "email", 100)) {
/* 176 */       return errors;
/*     */     }
/* 178 */     if (errors.ifNotUsername(username, "username", 3, 100)) {
/* 179 */       return errors;
/*     */     }
/* 181 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateReset(Long uid, String resetKey, HttpServletRequest request)
/*     */   {
/* 186 */     WebErrors errors = WebErrors.create(request);
/* 187 */     if (errors.ifNull(uid, "uid")) {
/* 188 */       return errors;
/*     */     }
/* 190 */     User user = this.userMng.findById(uid);
/* 191 */     if (errors.ifNotExist(user, User.class, uid)) {
/* 192 */       return errors;
/*     */     }
/* 194 */     if (errors.ifOutOfLength(resetKey, "resetKey", 32, 32)) {
/* 195 */       return errors;
/*     */     }
/* 197 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.ForgotPasswordAct
 * JD-Core Version:    0.6.0
 */