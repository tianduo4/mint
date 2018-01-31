/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopShipments;
/*     */ import com.jspgou.cms.manager.ShopShipmentsMng;
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
/*     */ public class ShopShipmentsAct
/*     */ {
/*  24 */   private static final Logger log = LoggerFactory.getLogger(ShopShipmentsAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopShipmentsMng manager;
/*     */ 
/*  28 */   @RequestMapping({"/shopShipments/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  29 */       CookieUtils.getPageSize(request));
/*  30 */     model.addAttribute("pagination", pagination);
/*  31 */     return "shopShipments/list"; }
/*     */ 
/*     */   @RequestMapping({"/shopShipments/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  36 */     return "shopShipments/add";
/*     */   }
/*     */   @RequestMapping({"/shopShipments/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  41 */     WebErrors errors = validateEdit(id, request);
/*  42 */     if (errors.hasErrors()) {
/*  43 */       return errors.showErrorPage(model);
/*     */     }
/*  45 */     model.addAttribute("shopShipments", this.manager.findById(id));
/*  46 */     return "shopShipments/edit";
/*     */   }
/*     */   @RequestMapping({"/shopShipments/o_save.do"})
/*     */   public String save(ShopShipments bean, HttpServletRequest request, ModelMap model) {
/*  51 */     WebErrors errors = validateSave(bean, request);
/*  52 */     if (errors.hasErrors()) {
/*  53 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  56 */     bean = this.manager.save(bean);
/*  57 */     log.info("save ShopShipments id={}", bean.getId());
/*  58 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopShipments/o_update.do"})
/*     */   public String update(ShopShipments bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  64 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  65 */     if (errors.hasErrors()) {
/*  66 */       return errors.showErrorPage(model);
/*     */     }
/*  68 */     bean = this.manager.update(bean);
/*  69 */     log.info("update ShopShipments id={}.", bean.getId());
/*  70 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopShipments/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  76 */     WebErrors errors = validateDelete(ids, request);
/*  77 */     if (errors.hasErrors()) {
/*  78 */       return errors.showErrorPage(model);
/*     */     }
/*  80 */     ShopShipments[] beans = this.manager.deleteByIds(ids);
/*  81 */     for (ShopShipments bean : beans) {
/*  82 */       log.info("delete ShopShipments id={}", bean.getId());
/*     */     }
/*  84 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopShipments bean, HttpServletRequest request) {
/*  88 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/*  91 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/*  95 */     WebErrors errors = WebErrors.create(request);
/*  96 */     Website web = SiteUtils.getWeb(request);
/*  97 */     if (vldExist(id, web.getId(), errors)) {
/*  98 */       return errors;
/*     */     }
/* 100 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 104 */     WebErrors errors = WebErrors.create(request);
/* 105 */     Website web = SiteUtils.getWeb(request);
/* 106 */     if (vldExist(id, web.getId(), errors)) {
/* 107 */       return errors;
/*     */     }
/* 109 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 113 */     WebErrors errors = WebErrors.create(request);
/* 114 */     Website web = SiteUtils.getWeb(request);
/* 115 */     errors.ifEmpty(ids, "ids");
/* 116 */     for (Long id : ids) {
/* 117 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 119 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 123 */     if (errors.ifNull(id, "id")) {
/* 124 */       return true;
/*     */     }
/* 126 */     ShopShipments entity = this.manager.findById(id);
/*     */ 
/* 128 */     return errors.ifNotExist(entity, ShopShipments.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopShipmentsAct
 * JD-Core Version:    0.6.0
 */