/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Log;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.LogMng;
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
/*     */ public class LogAct
/*     */ {
/*  28 */   private static final Logger log = LoggerFactory.getLogger(LogAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private LogMng manager;
/*     */ 
/*  32 */   @RequestMapping({"/log/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  33 */       CookieUtils.getPageSize(request));
/*  34 */     model.addAttribute("pagination", pagination);
/*  35 */     return "log/list"; }
/*     */ 
/*     */   @RequestMapping({"/log/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  40 */     return "log/add";
/*     */   }
/*     */   @RequestMapping({"/log/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  45 */     WebErrors errors = validateEdit(id, request);
/*  46 */     if (errors.hasErrors()) {
/*  47 */       return errors.showErrorPage(model);
/*     */     }
/*  49 */     model.addAttribute("log", this.manager.findById(id));
/*  50 */     return "log/edit";
/*     */   }
/*     */   @RequestMapping({"/log/o_save.do"})
/*     */   public String save(Log bean, HttpServletRequest request, ModelMap model) {
/*  55 */     WebErrors errors = validateSave(bean, request);
/*  56 */     if (errors.hasErrors()) {
/*  57 */       return errors.showErrorPage(model);
/*     */     }
/*  59 */     bean = this.manager.save(bean);
/*  60 */     log.info("save Log id={}", bean.getId());
/*  61 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/log/o_update.do"})
/*     */   public String update(Log bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  67 */     bean = this.manager.update(bean);
/*  68 */     log.info("update Log id={}.", bean.getId());
/*  69 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/log/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateDelete(ids, request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     Log[] beans = this.manager.deleteByIds(ids);
/*  80 */     for (Log bean : beans) {
/*  81 */       log.info("delete Log id={}", bean.getId());
/*     */     }
/*  83 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Log bean, HttpServletRequest request) {
/*  87 */     WebErrors errors = WebErrors.create(request);
/*  88 */     Website web = SiteUtils.getWeb(request);
/*  89 */     bean.setSite(web);
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
/* 125 */     Log entity = this.manager.findById(id);
/*     */ 
/* 127 */     return errors.ifNotExist(entity, Log.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.LogAct
 * JD-Core Version:    0.6.0
 */