/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.GiftExchange;
/*     */ import com.jspgou.cms.manager.GiftExchangeMng;
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
/*     */ public class GiftExchangeAct
/*     */ {
/*  24 */   private static final Logger log = LoggerFactory.getLogger(GiftExchangeAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private GiftExchangeMng manager;
/*     */ 
/*  28 */   @RequestMapping({"/exchange/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  29 */       CookieUtils.getPageSize(request));
/*  30 */     model.addAttribute("pagination", pagination);
/*  31 */     return "exchange/list"; }
/*     */ 
/*     */   @RequestMapping({"/exchange/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  36 */     return "exchange/add";
/*     */   }
/*     */   @RequestMapping({"/exchange/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  41 */     WebErrors errors = validateEdit(id, request);
/*  42 */     if (errors.hasErrors()) {
/*  43 */       return errors.showErrorPage(model);
/*     */     }
/*  45 */     model.addAttribute("giftExchange", this.manager.findById(id));
/*  46 */     return "exchange/edit";
/*     */   }
/*     */   @RequestMapping({"/exchange/o_save.do"})
/*     */   public String save(GiftExchange bean, HttpServletRequest request, ModelMap model) {
/*  51 */     WebErrors errors = validateSave(bean, request);
/*  52 */     if (errors.hasErrors()) {
/*  53 */       return errors.showErrorPage(model);
/*     */     }
/*  55 */     bean = this.manager.save(bean);
/*  56 */     log.info("save GiftExchange id={}", bean.getId());
/*  57 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exchange/o_update.do"})
/*     */   public String update(Long id, String waybill, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  63 */     GiftExchange bean = this.manager.findById(id);
/*  64 */     bean.setWaybill(waybill);
/*  65 */     bean.setStatus(Integer.valueOf(2));
/*  66 */     this.manager.update(bean);
/*  67 */     log.info("update GiftExchange id={}.", bean.getId());
/*  68 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exchange/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  74 */     WebErrors errors = validateDelete(ids, request);
/*  75 */     if (errors.hasErrors()) {
/*  76 */       return errors.showErrorPage(model);
/*     */     }
/*  78 */     GiftExchange[] beans = this.manager.deleteByIds(ids);
/*  79 */     for (GiftExchange bean : beans) {
/*  80 */       log.info("delete GiftExchange id={}", bean.getId());
/*     */     }
/*  82 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(GiftExchange bean, HttpServletRequest request) {
/*  86 */     WebErrors errors = WebErrors.create(request);
/*  87 */     Website web = SiteUtils.getWeb(request);
/*  88 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/*  92 */     WebErrors errors = WebErrors.create(request);
/*  93 */     Website web = SiteUtils.getWeb(request);
/*  94 */     if (vldExist(id, web.getId(), errors)) {
/*  95 */       return errors;
/*     */     }
/*  97 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 101 */     WebErrors errors = WebErrors.create(request);
/* 102 */     Website web = SiteUtils.getWeb(request);
/* 103 */     if (vldExist(id, web.getId(), errors)) {
/* 104 */       return errors;
/*     */     }
/* 106 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 110 */     WebErrors errors = WebErrors.create(request);
/* 111 */     Website web = SiteUtils.getWeb(request);
/* 112 */     errors.ifEmpty(ids, "ids");
/* 113 */     for (Long id : ids) {
/* 114 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 116 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 120 */     if (errors.ifNull(id, "id")) {
/* 121 */       return true;
/*     */     }
/* 123 */     GiftExchange entity = this.manager.findById(id);
/*     */ 
/* 125 */     return errors.ifNotExist(entity, GiftExchange.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.GiftExchangeAct
 * JD-Core Version:    0.6.0
 */