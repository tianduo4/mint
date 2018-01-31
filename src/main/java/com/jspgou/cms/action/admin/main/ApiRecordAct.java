/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiRecord;
/*     */ import com.jspgou.cms.manager.ApiRecordMng;
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
/*     */ public class ApiRecordAct
/*     */ {
/*  24 */   private static final Logger log = LoggerFactory.getLogger(ApiRecordAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ApiRecordMng manager;
/*     */ 
/*  28 */   @RequestMapping({"/apiRecord/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  29 */       CookieUtils.getPageSize(request));
/*  30 */     model.addAttribute("pagination", pagination);
/*  31 */     return "apiRecord/list"; }
/*     */ 
/*     */   @RequestMapping({"/apiRecord/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  36 */     return "apiRecord/add";
/*     */   }
/*     */   @RequestMapping({"/apiRecord/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  41 */     WebErrors errors = validateEdit(id, request);
/*  42 */     if (errors.hasErrors()) {
/*  43 */       return errors.showErrorPage(model);
/*     */     }
/*  45 */     model.addAttribute("apiRecord", this.manager.findById(id));
/*  46 */     return "apiRecord/edit";
/*     */   }
/*     */   @RequestMapping({"/apiRecord/o_save.do"})
/*     */   public String save(ApiRecord bean, HttpServletRequest request, ModelMap model) {
/*  51 */     WebErrors errors = validateSave(bean, request);
/*  52 */     if (errors.hasErrors()) {
/*  53 */       return errors.showErrorPage(model);
/*     */     }
/*  55 */     bean = this.manager.save(bean);
/*  56 */     log.info("save ApiRecord id={}", bean.getId());
/*  57 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/apiRecord/o_update.do"})
/*     */   public String update(ApiRecord bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  63 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  64 */     if (errors.hasErrors()) {
/*  65 */       return errors.showErrorPage(model);
/*     */     }
/*  67 */     bean = this.manager.update(bean);
/*  68 */     log.info("update ApiRecord id={}.", bean.getId());
/*  69 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/apiRecord/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateDelete(ids, request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     ApiRecord[] beans = this.manager.deleteByIds(ids);
/*  80 */     for (ApiRecord bean : beans) {
/*  81 */       log.info("delete ApiRecord id={}", bean.getId());
/*     */     }
/*  83 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ApiRecord bean, HttpServletRequest request) {
/*  87 */     WebErrors errors = WebErrors.create(request);
/*  88 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/*  90 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/*  94 */     WebErrors errors = WebErrors.create(request);
/*  95 */     Website web = SiteUtils.getWeb(request);
/*  96 */     if (vldExist(id, web.getId(), errors)) {
/*  97 */       return errors;
/*     */     }
/*  99 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 103 */     WebErrors errors = WebErrors.create(request);
/* 104 */     Website web = SiteUtils.getWeb(request);
/* 105 */     if (vldExist(id, web.getId(), errors)) {
/* 106 */       return errors;
/*     */     }
/* 108 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 112 */     WebErrors errors = WebErrors.create(request);
/* 113 */     Website web = SiteUtils.getWeb(request);
/* 114 */     errors.ifEmpty(ids, "ids");
/* 115 */     for (Long id : ids) {
/* 116 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 118 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 122 */     if (errors.ifNull(id, "id")) {
/* 123 */       return true;
/*     */     }
/* 125 */     ApiRecord entity = this.manager.findById(id);
/*     */ 
/* 127 */     return errors.ifNotExist(entity, ApiRecord.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ApiRecordAct
 * JD-Core Version:    0.6.0
 */