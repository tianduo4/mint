/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopConfig;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.entity.ShopScore.ScoreTypes;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.octo.captcha.service.CaptchaService;
/*     */ import com.octo.captcha.service.CaptchaServiceException;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class RegisterAct
/*     */ {
/*  57 */   private static final Logger log = LoggerFactory.getLogger(RegisterAct.class);
/*     */   private static final String REGISTER = "tpl.register";
/*     */   private static final String REGISTER_RESULT = "tpl.registerResult";
/*     */   private static final String REGISTER_TREATY = "tpl.registerTreaty";
/*     */   private static final String REGISTER_ACTIVE_STATUS = "tpl.registerActiveStatus";
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private MemberMng memberMng;
/*     */ 
/*     */   @Autowired
/*     */   private CaptchaService captchaService;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng webserviceMng;
/*     */ 
/*     */   @RequestMapping(value={"/register.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String registerInput(HttpServletRequest request, ModelMap model)
/*     */   {
/*  65 */     Website web = com.jspgou.core.web.SiteUtils.getWeb(request);
/*  66 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  67 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.register", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/register.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String registerSubmit(String checkcode, String username, String email, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  74 */     Website web = com.jspgou.core.web.SiteUtils.getWeb(request);
/*  75 */     ShopConfig config = com.jspgou.cms.web.SiteUtils.getConfig(request);
/*  76 */     WebErrors errors = validate(checkcode, username, email, password, request, response);
/*  77 */     if (errors.hasErrors()) {
/*  78 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*  80 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  81 */     EmailSender sender = web.getEmailSender();
/*  82 */     MessageTemplate tpl = (MessageTemplate)web.getMessages().get("resetPassword");
/*     */ 
/*  84 */     if (sender == null)
/*     */     {
/*  86 */       model.addAttribute("status", Integer.valueOf(2));
/*  87 */     } else if (tpl == null)
/*     */     {
/*  89 */       model.addAttribute("status", Integer.valueOf(3));
/*     */     }
/*     */     else {
/*     */       try {
/*  93 */         String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
/*  94 */         String base = new String(web.getUrlBuff(true));
/*  95 */         this.userMng.senderActiveEmail(username, base, email, uuid, sender, tpl);
/*  96 */         ShopMember member = this.shopMemberMng.register(username, password, email, Boolean.valueOf(false), uuid, 
/*  97 */           request.getRemoteAddr(), Boolean.valueOf(false), web.getId(), config.getRegisterGroup().getId());
/*     */ 
/*  99 */         this.webserviceMng.callWebService("false", username, password, email, null, "addUser");
/*     */ 
/* 101 */         String emailtype = email.substring(email.indexOf("@") + 1, email.indexOf("."));
/* 102 */         model.addAttribute("emailtype", emailtype);
/* 103 */         model.addAttribute("member", member);
/* 104 */         model.addAttribute("status", Integer.valueOf(1));
/* 105 */         log.info("register member '{}'", member.getUsername());
/*     */       }
/*     */       catch (Exception e) {
/* 108 */         model.addAttribute("status", Integer.valueOf(4));
/* 109 */         model.addAttribute("message", e.getMessage());
/* 110 */         log.error("send email exception {}.", e.getMessage());
/*     */       }
/*     */     }
/* 113 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.registerResult", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/active.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String active(String userName, String activationCode, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
/*     */   {
/* 120 */     Website web = com.jspgou.core.web.SiteUtils.getWeb(request);
/* 121 */     String userName1 = new String(userName.getBytes("ISO_8859_1"), "GBK");
/* 122 */     Member bean = this.memberMng.getByUsername(web.getId(), userName1);
/* 123 */     long l = System.currentTimeMillis();
/* 124 */     l -= 86400000L;
/* 125 */     Date date = new Date();
/* 126 */     date.setTime(l);
/* 127 */     if ((StringUtils.isBlank(String.valueOf(userName))) || (StringUtils.isBlank(activationCode))) {
/* 128 */       model.addAttribute("status", Integer.valueOf(2));
/* 129 */     } else if (bean == null) {
/* 130 */       model.addAttribute("status", Integer.valueOf(3));
/* 131 */     } else if (bean.getActive().booleanValue()) {
/* 132 */       model.addAttribute("status", Integer.valueOf(4));
/* 133 */     } else if (!bean.getActivationCode().equals(activationCode)) {
/* 134 */       model.addAttribute("status", Integer.valueOf(5));
/* 135 */     } else if (bean.getCreateTime().compareTo(date) < 0) {
/* 136 */       model.addAttribute("status", Integer.valueOf(6));
/*     */     } else {
/* 138 */       bean.setActive(Boolean.valueOf(true));
/* 139 */       this.memberMng.update(bean);
/*     */ 
/* 141 */       this.shopMemberMng.updateScore(this.shopMemberMng.findById(bean.getId()), 
/* 142 */         com.jspgou.core.web.SiteUtils.getWeb(request).getGlobal().getActiveScore());
/* 143 */       ShopScore shopScore = new ShopScore();
/* 144 */       shopScore.setMember(this.shopMemberMng.findById(bean.getId()));
/* 145 */       shopScore.setName("邮箱验证送积分");
/* 146 */       shopScore.setScoreTime(new Date());
/* 147 */       shopScore.setStatus(true);
/* 148 */       shopScore.setUseStatus(false);
/* 149 */       shopScore.setScoreType(Integer.valueOf(ShopScore.ScoreTypes.EMAIL_VALIDATE.getValue()));
/* 150 */       this.shopScoreMng.save(shopScore);
/* 151 */       model.addAttribute("status", Integer.valueOf(1));
/* 152 */       model.addAttribute("member", bean);
/*     */     }
/* 154 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 155 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.registerActiveStatus", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/reactive.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void reactive(Long userId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
/*     */   {
/* 162 */     Website web = com.jspgou.core.web.SiteUtils.getWeb(request);
/* 163 */     JSONObject json = new JSONObject();
/* 164 */     Member bean = this.memberMng.findById(userId);
/* 165 */     if (bean.getActive().booleanValue()) {
/*     */       try {
/* 167 */         json.put("data", 1);
/*     */       } catch (JSONException e) {
/* 169 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     else {
/* 173 */       String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
/* 174 */       bean.setActivationCode(uuid);
/* 175 */       bean.setCreateTime(new Date());
/* 176 */       this.memberMng.update(bean);
/* 177 */       String base = new String(web.getUrlBuff(true));
/* 178 */       EmailSender sender = web.getEmailSender();
/* 179 */       Map messages = web.getMessages();
/* 180 */       MessageTemplate tpl = (MessageTemplate)messages.get("resetPassword");
/*     */       try {
/* 182 */         this.userMng.senderActiveEmail(bean.getUsername(), base, bean.getEmail(), uuid, sender, tpl);
/*     */         try {
/* 184 */           json.put("data", 2);
/*     */         } catch (JSONException e) {
/* 186 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/*     */         try {
/* 191 */           json.put("data", 3);
/*     */         } catch (JSONException e1) {
/* 193 */           e1.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 198 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/treaty.jspx"})
/*     */   public String treaty(HttpServletRequest request, ModelMap model) {
/* 204 */     Website web = com.jspgou.core.web.SiteUtils.getWeb(request);
/* 205 */     model.addAttribute("global", com.jspgou.core.web.SiteUtils.getWeb(request).getGlobal());
/* 206 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 207 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/* 208 */       "tpl.registerTreaty", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/username_unique.jspx"})
/*     */   public void checkUsername(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 215 */     String username = RequestUtils.getQueryParam(request, "username");
/*     */ 
/* 217 */     if (StringUtils.isBlank(username)) {
/* 218 */       ResponseUtils.renderJson(response, "false");
/* 219 */       return;
/*     */     }
/*     */ 
/* 222 */     if (this.userMng.usernameExist(username)) {
/* 223 */       ResponseUtils.renderJson(response, "false");
/* 224 */       return;
/*     */     }
/* 226 */     ResponseUtils.renderJson(response, "true");
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/email_unique.jspx"})
/*     */   public void checkEmail(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 233 */     String email = RequestUtils.getQueryParam(request, "email");
/*     */ 
/* 235 */     if (StringUtils.isBlank(email)) {
/* 236 */       ResponseUtils.renderJson(response, "false");
/* 237 */       return;
/*     */     }
/*     */ 
/* 240 */     if (this.userMng.emailExist(email)) {
/* 241 */       ResponseUtils.renderJson(response, "false");
/* 242 */       return;
/*     */     }
/* 244 */     ResponseUtils.renderJson(response, "true");
/*     */   }
/*     */ 
/*     */   private WebErrors validate(String checkcode, String username, String email, String password, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 249 */     WebErrors errors = WebErrors.create(request);
/* 250 */     String id = this.session.getSessionId(request, response);
/* 251 */     if (errors.ifOutOfLength(checkcode, "checkcode", 3, 10)) {
/* 252 */       return errors;
/*     */     }
/* 254 */     if (errors.ifOutOfLength(password, "password", 3, 32)) {
/* 255 */       return errors;
/*     */     }
/*     */     try
/*     */     {
/* 259 */       if (!this.captchaService.validateResponseForID(id, checkcode
/* 259 */         .toUpperCase(Locale.ENGLISH)).booleanValue())
/*     */       {
/* 260 */         errors.addErrorCode("error.checkcodeIncorrect");
/* 261 */         return errors;
/*     */       }
/*     */     } catch (CaptchaServiceException e) {
/* 264 */       errors.addErrorCode("error.checkcodeInvalid");
/* 265 */       errors.addErrorString(e.getMessage());
/* 266 */       return errors;
/*     */     }
/* 268 */     if (errors.ifNotEmail(email, "email", 100)) {
/* 269 */       return errors;
/*     */     }
/* 271 */     if (this.userMng.emailExist(email)) {
/* 272 */       errors.addErrorCode("error.emailExist");
/* 273 */       return errors;
/*     */     }
/* 275 */     if (errors.ifNotUsername(username, "username", 3, 100)) {
/* 276 */       return errors;
/*     */     }
/* 278 */     if (this.userMng.usernameExist(username)) {
/* 279 */       errors.addErrorCode("error.usernameExist");
/* 280 */       return errors;
/*     */     }
/* 282 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.RegisterAct
 * JD-Core Version:    0.6.0
 */