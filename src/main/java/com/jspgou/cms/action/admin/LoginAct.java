/*     */ package com.jspgou.cms.action.admin;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.security.BadCredentialsException;
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.security.UserNotInWebsiteException;
/*     */ import com.jspgou.core.web.WebErrors;
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
/*     */ public class LoginAct
/*     */ {
/*  31 */   private static final Logger log = LoggerFactory.getLogger(LoginAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private LoginSvc loginSvc;
/*     */ 
/*  35 */   @RequestMapping(value={"/index.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String index(ModelMap model) { ShopAdmin admin = AdminThread.get();
/*  36 */     if (admin != null) {
/*  37 */       model.addAttribute("admin", admin);
/*  38 */       return "index";
/*     */     }
/*  40 */     return "login";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/login.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String input(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  48 */     return "login";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/login.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String submit(String username, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  59 */     WebErrors errors = validateSubmit(username, request, response);
/*  60 */     if (!errors.hasErrors()) {
/*  61 */       Website web = SiteUtils.getWeb(request);
/*     */       try {
/*  63 */         this.loginSvc.adminLogin(request, response, web, username, password);
/*  64 */         log.info("admin '{}' login success.", username);
/*  65 */         return "redirect:index.do";
/*     */       } catch (UsernameNotFoundException e) {
/*  67 */         errors.addError(e.getMessage());
/*  68 */         log.info(e.getMessage());
/*     */       }
/*     */       catch (BadCredentialsException e)
/*     */       {
/*  73 */         errors.addError(e.getMessage());
/*  74 */         log.info(e.getMessage());
/*     */       } catch (UserNotInWebsiteException e) {
/*  76 */         errors.addError(e.getMessage());
/*  77 */         log.info(e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  83 */     errors.toModel(model);
/*     */ 
/*  85 */     return "login";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logout.do"})
/*     */   public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/*  91 */     this.loginSvc.logout(request, response);
/*  92 */     return "redirect:index.do";
/*     */   }
/*     */ 
/*     */   private WebErrors validateSubmit(String username, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  98 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 104 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.LoginAct
 * JD-Core Version:    0.6.0
 */