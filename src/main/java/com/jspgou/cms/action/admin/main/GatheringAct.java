/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gathering;
/*     */ import com.jspgou.cms.manager.GatheringMng;
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
/*     */ public class GatheringAct
/*     */ {
/*  28 */   private static final Logger log = LoggerFactory.getLogger(GatheringAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private GatheringMng manager;
/*     */ 
/*  32 */   @RequestMapping({"/Gathering/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  33 */       CookieUtils.getPageSize(request));
/*  34 */     model.addAttribute("pagination", pagination);
/*  35 */     return "Gathering/list"; }
/*     */ 
/*     */   @RequestMapping({"/Gathering/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  40 */     return "Gathering/add";
/*     */   }
/*     */   @RequestMapping({"/Gathering/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  45 */     WebErrors errors = validateEdit(id, request);
/*  46 */     if (errors.hasErrors()) {
/*  47 */       return errors.showErrorPage(model);
/*     */     }
/*  49 */     model.addAttribute("gathering", this.manager.findById(id));
/*  50 */     model.addAttribute("order", this.manager.findById(id).getIndent());
/*  51 */     return "Gathering/edit";
/*     */   }
/*     */   @RequestMapping({"/Gathering/o_save.do"})
/*     */   public String save(Gathering bean, HttpServletRequest request, ModelMap model) {
/*  56 */     WebErrors errors = validateSave(bean, request);
/*  57 */     if (errors.hasErrors()) {
/*  58 */       return errors.showErrorPage(model);
/*     */     }
/*  60 */     bean = this.manager.save(bean);
/*  61 */     log.info("save Gathering id={}", bean.getId());
/*  62 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Gathering/o_update.do"})
/*     */   public String update(Gathering bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  68 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  69 */     if (errors.hasErrors()) {
/*  70 */       return errors.showErrorPage(model);
/*     */     }
/*  72 */     bean = this.manager.update(bean);
/*  73 */     log.info("update Gathering id={}.", bean.getId());
/*  74 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Gathering/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  80 */     WebErrors errors = validateDelete(ids, request);
/*  81 */     if (errors.hasErrors()) {
/*  82 */       return errors.showErrorPage(model);
/*     */     }
/*  84 */     Gathering[] beans = this.manager.deleteByIds(ids);
/*  85 */     for (Gathering bean : beans) {
/*  86 */       log.info("delete Gathering id={}", bean.getId());
/*     */     }
/*  88 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Gathering bean, HttpServletRequest request) {
/*  92 */     WebErrors errors = WebErrors.create(request);
/*  93 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/*  97 */     WebErrors errors = WebErrors.create(request);
/*  98 */     Website web = SiteUtils.getWeb(request);
/*  99 */     if (vldExist(id, web.getId(), errors)) {
/* 100 */       return errors;
/*     */     }
/* 102 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 106 */     WebErrors errors = WebErrors.create(request);
/* 107 */     Website web = SiteUtils.getWeb(request);
/* 108 */     if (vldExist(id, web.getId(), errors)) {
/* 109 */       return errors;
/*     */     }
/* 111 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 115 */     WebErrors errors = WebErrors.create(request);
/* 116 */     Website web = SiteUtils.getWeb(request);
/* 117 */     errors.ifEmpty(ids, "ids");
/* 118 */     for (Long id : ids) {
/* 119 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 121 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 125 */     if (errors.ifNull(id, "id")) {
/* 126 */       return true;
/*     */     }
/* 128 */     Gathering entity = this.manager.findById(id);
/*     */ 
/* 130 */     return errors.ifNotExist(entity, Gathering.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.GatheringAct
 * JD-Core Version:    0.6.0
 */