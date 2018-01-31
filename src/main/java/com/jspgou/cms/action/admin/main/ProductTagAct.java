/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductTag;
/*     */ import com.jspgou.cms.manager.ProductTagMng;
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
/*     */ public class ProductTagAct
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(ProductTagAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductTagMng manager;
/*     */ 
/*  30 */   @RequestMapping({"/tag/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) { List list = this.manager.getList(SiteUtils.getWebId(request));
/*  31 */     model.addAttribute("list", list);
/*  32 */     return "tag/list"; }
/*     */ 
/*     */   @RequestMapping({"/tag/o_save.do"})
/*     */   public String save(ProductTag bean, HttpServletRequest request, ModelMap model)
/*     */   {
/*  38 */     WebErrors errors = validateSave(bean, request);
/*  39 */     if (errors.hasErrors()) {
/*  40 */       return errors.showErrorPage(model);
/*     */     }
/*  42 */     bean = this.manager.save(bean);
/*  43 */     log.info("save ProductTag. id={}", bean.getId());
/*  44 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/tag/o_update_tag_names.do"})
/*     */   public String updateTagName(Long[] wids, String[] tagNames, HttpServletRequest request, ModelMap model) {
/*  50 */     WebErrors errors = validateUpdateTagNames(wids, tagNames, request);
/*  51 */     if (errors.hasErrors()) {
/*  52 */       return errors.showErrorPage(model);
/*     */     }
/*  54 */     ProductTag[] beans = this.manager.updateTagName(wids, tagNames);
/*  55 */     for (ProductTag bean : beans) {
/*  56 */       log.info("update ProductTag. id={},name={}", bean.getId(), bean
/*  57 */         .getName());
/*     */     }
/*  59 */     return "redirect:v_list.do";
/*     */   }
/*     */   @RequestMapping({"/tag/o_delete.do"})
/*     */   public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
/*  64 */     WebErrors errors = validateDelete(ids, request);
/*  65 */     if (errors.hasErrors()) {
/*  66 */       return errors.showErrorPage(model);
/*     */     }
/*  68 */     ProductTag[] beans = this.manager.deleteByIds(ids);
/*  69 */     for (ProductTag bean : beans) {
/*  70 */       log.info("delete ProductTag. id={},name={}", bean.getId(), bean
/*  71 */         .getName());
/*     */     }
/*  73 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ProductTag bean, HttpServletRequest request) {
/*  77 */     WebErrors errors = WebErrors.create(request);
/*  78 */     bean.setWebsite(SiteUtils.getWeb(request));
/*  79 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdateTagNames(Long[] wids, String[] tagNames, HttpServletRequest request)
/*     */   {
/*  84 */     WebErrors errors = WebErrors.create(request);
/*  85 */     errors.ifEmpty(wids, "wids");
/*  86 */     errors.ifEmpty(tagNames, "tagNames");
/*  87 */     if (errors.hasErrors()) {
/*  88 */       return errors;
/*     */     }
/*  90 */     if (wids.length != tagNames.length) {
/*  91 */       errors.addErrorString("wids length must equals tagNames length");
/*  92 */       return errors;
/*     */     }
/*  94 */     int i = 0; for (int len = wids.length; i < len; i++) {
/*  95 */       vldExist(wids[i], errors);
/*  96 */       if (errors.hasErrors()) {
/*  97 */         return errors;
/*     */       }
/*     */     }
/* 100 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 104 */     WebErrors errors = WebErrors.create(request);
/* 105 */     errors.ifEmpty(ids, "ids");
/* 106 */     for (Long id : ids) {
/* 107 */       vldExist(id, errors);
/*     */     }
/* 109 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Long id, WebErrors errors) {
/* 113 */     if (errors.hasErrors()) {
/* 114 */       return;
/*     */     }
/* 116 */     ProductTag entity = this.manager.findById(id);
/* 117 */     errors.ifNotExist(entity, ProductTag.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ProductTagAct
 * JD-Core Version:    0.6.0
 */