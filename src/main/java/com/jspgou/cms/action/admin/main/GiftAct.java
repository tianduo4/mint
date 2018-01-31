/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import com.jspgou.cms.manager.GiftMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
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
/*     */ public class GiftAct
/*     */ {
/*  28 */   private static final Logger log = LoggerFactory.getLogger(GiftAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private GiftMng manager;
/*     */ 
/*  33 */   @RequestMapping({"/gift/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPageGift(SimplePage.cpn(pageNo), 
/*  34 */       CookieUtils.getPageSize(request));
/*  35 */     model.addAttribute("pagination", pagination);
/*  36 */     return "gift/list"; }
/*     */ 
/*     */   @RequestMapping({"/gift/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  41 */     return "gift/add";
/*     */   }
/*     */   @RequestMapping({"/gift/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  46 */     WebErrors errors = validateEdit(id, request);
/*  47 */     if (errors.hasErrors()) {
/*  48 */       return errors.showErrorPage(model);
/*     */     }
/*  50 */     model.addAttribute("gift", this.manager.findById(id));
/*  51 */     return "gift/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/gift/o_save.do"})
/*     */   public String save(Gift bean, HttpServletRequest request, ModelMap model) {
/*  57 */     bean = this.manager.save(bean);
/*  58 */     log.info("save brand. id={}", bean.getId());
/*  59 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/gift/o_update.do"})
/*     */   public String update(Gift gift, String text, Long[] typeIds, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  65 */     this.manager.updateByUpdater(gift);
/*  66 */     log.info("update brand. id={}.", gift.getId());
/*  67 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/gift/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  73 */     WebErrors errors = validateDelete(ids, request);
/*  74 */     if (errors.hasErrors()) {
/*  75 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/*  79 */       Gift[] beans = this.manager.deleteByIds(ids);
/*  80 */       for (Gift bean : beans)
/*  81 */         log.info("delete brand. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/*  84 */       errors.addErrorString(this.productMng.getTipFile("Pleasedtrofgiftin"));
/*  85 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Gift[] beans;
/*  87 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request)
/*     */   {
/* 104 */     WebErrors errors = WebErrors.create(request);
/* 105 */     errors.ifNull(id, "id");
/* 106 */     vldExist(id, errors);
/* 107 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request)
/*     */   {
/* 112 */     WebErrors errors = WebErrors.create(request);
/* 113 */     errors.ifEmpty(ids, "ids");
/* 114 */     for (Long id : ids) {
/* 115 */       vldExist(id, errors);
/*     */     }
/* 117 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 122 */     WebErrors errors = WebErrors.create(request);
/* 123 */     if (errors.ifEmpty(wids, "wids")) {
/* 124 */       return errors;
/*     */     }
/* 126 */     if (errors.ifEmpty(priority, "priority")) {
/* 127 */       return errors;
/*     */     }
/* 129 */     if (wids.length != priority.length) {
/* 130 */       errors.addErrorString("wids length not equals priority length");
/* 131 */       return errors;
/*     */     }
/* 133 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 134 */       vldExist(wids[i], errors);
/* 135 */       if (priority[i] == null) {
/* 136 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 139 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 143 */     Gift entity = this.manager.findById(id);
/* 144 */     return errors.ifNotExist(entity, Brand.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.GiftAct
 * JD-Core Version:    0.6.0
 */