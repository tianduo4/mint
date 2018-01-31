/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.CustomerService;
/*     */ import com.jspgou.cms.manager.CustomerServiceMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class CustomerServiceAct
/*     */ {
/*  27 */   private static final Logger log = LoggerFactory.getLogger(CustomerServiceAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private CustomerServiceMng manager;
/*     */ 
/*  33 */   @RequestMapping({"/customerService/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPagination(null, SimplePage.cpn(pageNo), 
/*  34 */       CookieUtils.getPageSize(request));
/*  35 */     model.addAttribute("pagination", pagination);
/*  36 */     model.addAttribute("pageNo", pageNo);
/*  37 */     return "customerService/list"; }
/*     */ 
/*     */   @RequestMapping({"/customerService/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  42 */     return "customerService/add";
/*     */   }
/*     */   @RequestMapping({"/customerService/v_edit.do"})
/*     */   public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  47 */     WebErrors errors = validateEdit(id, request);
/*  48 */     if (errors.hasErrors()) {
/*  49 */       return errors.showErrorPage(model);
/*     */     }
/*  51 */     model.addAttribute("customerService", this.manager.findById(id));
/*  52 */     model.addAttribute("pageNo", pageNo);
/*  53 */     return "customerService/edit";
/*     */   }
/*     */   @RequestMapping({"/customerService/o_save.do"})
/*     */   public String save(CustomerService bean, HttpServletRequest request, ModelMap model) {
/*  58 */     WebErrors errors = validateSave(bean, request);
/*  59 */     if (errors.hasErrors()) {
/*  60 */       return errors.showErrorPage(model);
/*     */     }
/*  62 */     bean = this.manager.save(bean);
/*  63 */     log.info("save CustomerService id={}", bean.getId());
/*  64 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerService/o_update.do"})
/*     */   public String qqUpdate(CustomerService bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  70 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  71 */     if (errors.hasErrors()) {
/*  72 */       return errors.showErrorPage(model);
/*     */     }
/*  74 */     bean = this.manager.update(bean);
/*  75 */     log.info("update CustomerService id={}.", bean.getId());
/*  76 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerService/o_delete.do"})
/*     */   public String qqDelete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  82 */     WebErrors errors = validateDelete(ids, request);
/*  83 */     if (errors.hasErrors()) {
/*  84 */       return errors.showErrorPage(model);
/*     */     }
/*  86 */     CustomerService[] beans = this.manager.deleteByIds(ids);
/*  87 */     for (CustomerService bean : beans) {
/*  88 */       log.info("delete CustomerService id={}", bean.getId());
/*     */     }
/*  90 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerService/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  96 */     this.manager.updatePriority(wids, priority);
/*  97 */     model.addAttribute("message", "global.success");
/*  98 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(CustomerService bean, HttpServletRequest request) {
/* 102 */     WebErrors errors = WebErrors.create(request);
/* 103 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 107 */     WebErrors errors = WebErrors.create(request);
/* 108 */     Website web = SiteUtils.getWeb(request);
/* 109 */     if (vldExist(id, web.getId(), errors)) {
/* 110 */       return errors;
/*     */     }
/* 112 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 116 */     WebErrors errors = WebErrors.create(request);
/* 117 */     Website web = SiteUtils.getWeb(request);
/* 118 */     if (vldExist(id, web.getId(), errors)) {
/* 119 */       return errors;
/*     */     }
/* 121 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 125 */     WebErrors errors = WebErrors.create(request);
/* 126 */     Website web = SiteUtils.getWeb(request);
/* 127 */     errors.ifEmpty(ids, "ids");
/* 128 */     for (Long id : ids) {
/* 129 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 131 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 135 */     if (errors.ifNull(id, "id")) {
/* 136 */       return true;
/*     */     }
/* 138 */     CustomerService entity = this.manager.findById(id);
/*     */ 
/* 140 */     return errors.ifNotExist(entity, CustomerService.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.CustomerServiceAct
 * JD-Core Version:    0.6.0
 */