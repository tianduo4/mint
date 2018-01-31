/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.entity.WebserviceAuth;
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.cms.manager.WebserviceAuthMng;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.page.SimplePage;
/*    */ import com.jspgou.common.security.encoder.Md5PwdEncoder;
/*    */ import com.jspgou.common.web.CookieUtils;
/*    */ import com.jspgou.core.web.WebErrors;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class WebserviceAuthAct
/*    */ {
/* 25 */   private static final Logger log = LoggerFactory.getLogger(WebserviceAuthAct.class);
/*    */ 
/*    */   @Autowired
/*    */   private WebserviceAuthMng manager;
/*    */ 
/*    */   @Autowired
/*    */   private Md5PwdEncoder pwdEncoder;
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/* 29 */   @RequestMapping({"/webserviceAuth/v_list.do"})
/*    */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/* 30 */       CookieUtils.getPageSize(request));
/* 31 */     model.addAttribute("pagination", pagination);
/* 32 */     return "webserviceAuth/list"; }
/*    */ 
/*    */   @RequestMapping({"/webserviceAuth/v_add.do"})
/*    */   public String add(ModelMap model) {
/* 37 */     return "webserviceAuth/add";
/*    */   }
/*    */   @RequestMapping({"/webserviceAuth/v_edit.do"})
/*    */   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
/* 42 */     model.addAttribute("WebserviceAuth", this.manager.findById(id));
/* 43 */     return "webserviceAuth/edit";
/*    */   }
/*    */   @RequestMapping({"/webserviceAuth/o_save.do"})
/*    */   public String save(WebserviceAuth bean, HttpServletRequest request, ModelMap model) {
/* 48 */     bean.setPassword(this.pwdEncoder.encodePassword(bean.getPassword()));
/* 49 */     bean = this.manager.save(bean);
/* 50 */     log.info("save WebserviceAuth id={}", bean.getId());
/* 51 */     return "redirect:v_list.do";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webserviceAuth/o_update.do"})
/*    */   public String update(Integer id, String username, String password, String system, Boolean enable, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 57 */     WebserviceAuth bean = this.manager.update(id, username, password, system, enable);
/* 58 */     log.info("update WebserviceAuth id={}.", bean.getId());
/* 59 */     return list(pageNo, request, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webserviceAuth/o_delete.do"})
/*    */   public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 65 */     WebErrors errors = validateDelete(ids, request);
/* 66 */     if (errors.hasErrors()) {
/* 67 */       return errors.showErrorPage(model);
/*    */     }
/*    */     try
/*    */     {
/* 71 */       bean = this.manager.deleteByIds(ids);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */       WebserviceAuth[] bean;
/* 73 */       errors.addErrorString(this.productMng.getTipFile("Please.and.user.operation"));
/* 74 */       return errors.showErrorPage(model);
/*    */     }
/*    */     WebserviceAuth[] bean;
/* 76 */     return list(pageNo, request, model);
/*    */   }
/*    */ 
/*    */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 80 */     WebErrors errors = WebErrors.create(request);
/* 81 */     errors.ifEmpty(ids, "ids");
/* 82 */     for (Integer id : ids) {
/* 83 */       vldExist(id, errors);
/*    */     }
/* 85 */     return errors;
/*    */   }
/*    */ 
/*    */   private boolean vldExist(Integer id, WebErrors errors) {
/* 89 */     WebserviceAuth entity = this.manager.findById(id);
/* 90 */     return errors.ifNotExist(entity, WebserviceAuth.class, id);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.WebserviceAuthAct
 * JD-Core Version:    0.6.0
 */