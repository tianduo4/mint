/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductTypeAct
/*     */ {
/*  28 */   private static final Logger log = LoggerFactory.getLogger(ProductTypeAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*  32 */   @RequestMapping({"/type/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) { List list = this.manager.getList(SiteUtils.getWebId(request));
/*  33 */     model.addAttribute("list", list);
/*  34 */     return "type/list"; }
/*     */ 
/*     */   @RequestMapping({"/type/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  39 */     return "type/add";
/*     */   }
/*     */   @RequestMapping({"/type/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  44 */     WebErrors errors = validateEdit(id, request);
/*  45 */     if (errors.hasErrors()) {
/*  46 */       return errors.showErrorPage(model);
/*     */     }
/*  48 */     model.addAttribute("productType", this.manager.findById(id));
/*  49 */     return "type/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/type/o_save.do"})
/*     */   public String save(ProductType bean, HttpServletRequest request, ModelMap model) {
/*  55 */     WebErrors errors = validateSave(bean, request);
/*  56 */     if (errors.hasErrors()) {
/*  57 */       return errors.showErrorPage(model);
/*     */     }
/*  59 */     bean = this.manager.save(bean);
/*  60 */     log.info("save ProductType. id={}", bean.getId());
/*  61 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/type/o_update.do"})
/*     */   public String update(ProductType bean, HttpServletRequest request, ModelMap model) {
/*  67 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  68 */     if (errors.hasErrors()) {
/*  69 */       return errors.showErrorPage(model);
/*     */     }
/*  71 */     bean = this.manager.update(bean);
/*  72 */     log.info("update ProductType. id={}.", bean.getId());
/*  73 */     return list(request, model);
/*     */   }
/*     */   @RequestMapping({"/type/o_delete.do"})
/*     */   public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
/*  78 */     WebErrors errors = validateDelete(ids, request);
/*  79 */     if (errors.hasErrors()) {
/*  80 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/*  84 */       ProductType[] beans = this.manager.deleteByIds(ids);
/*  85 */       for (ProductType bean : beans)
/*  86 */         log.info("delete ProductType. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/*  89 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.classification.and.other.related.data"));
/*  90 */       return errors.showErrorPage(model);
/*     */     }
/*     */     ProductType[] beans;
/*  92 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ProductType bean, HttpServletRequest request) {
/*  96 */     WebErrors errors = WebErrors.create(request);
/*  97 */     bean.setWebsite(SiteUtils.getWeb(request));
/*  98 */     return errors;
/*     */   }
/*     */ 
/*     */   public WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 102 */     WebErrors errors = WebErrors.create(request);
/* 103 */     errors.ifNull(id, "id");
/* 104 */     vldExist(id, errors);
/* 105 */     return errors;
/*     */   }
/*     */ 
/*     */   public WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 109 */     WebErrors errors = WebErrors.create(request);
/* 110 */     errors.ifNull(id, "id");
/* 111 */     vldExist(id, errors);
/* 112 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 116 */     WebErrors errors = WebErrors.create(request);
/* 117 */     errors.ifEmpty(ids, "ids");
/* 118 */     for (Long id : ids) {
/* 119 */       vldExist(id, errors);
/*     */     }
/* 121 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Long id, WebErrors errors) {
/* 125 */     if (errors.hasErrors()) {
/* 126 */       return;
/*     */     }
/* 128 */     ProductType entity = this.manager.findById(id);
/* 129 */     errors.ifNotExist(entity, ProductType.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ProductTypeAct
 * JD-Core Version:    0.6.0
 */