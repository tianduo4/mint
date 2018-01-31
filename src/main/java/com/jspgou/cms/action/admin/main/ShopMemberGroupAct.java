/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
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
/*     */ public class ShopMemberGroupAct
/*     */ {
/*  27 */   private static final Logger log = LoggerFactory.getLogger(ShopMemberGroupAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng manager;
/*     */ 
/*  32 */   @RequestMapping({"/group/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { List list = this.manager.getList(
/*  33 */       SiteUtils.getWebId(request));
/*  34 */     model.addAttribute("list", list);
/*  35 */     return "group/list"; }
/*     */ 
/*     */   @RequestMapping({"/group/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  40 */     return "group/add";
/*     */   }
/*     */   @RequestMapping({"/group/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  45 */     WebErrors errors = validateEdit(id, request);
/*  46 */     if (errors.hasErrors()) {
/*  47 */       return errors.showErrorPage(model);
/*     */     }
/*  49 */     model.addAttribute("group", this.manager.findById(id));
/*  50 */     return "group/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/group/o_save.do"})
/*     */   public String save(ShopMemberGroup bean, HttpServletRequest request, ModelMap model) {
/*  56 */     WebErrors errors = validateSave(bean, request);
/*  57 */     if (errors.hasErrors()) {
/*  58 */       return errors.showErrorPage(model);
/*     */     }
/*  60 */     bean = this.manager.save(bean);
/*  61 */     log.info("save ShopMemberGroup. id={}", bean.getId());
/*  62 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/group/o_update.do"})
/*     */   public String update(ShopMemberGroup bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  68 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  69 */     if (errors.hasErrors()) {
/*  70 */       return errors.showErrorPage(model);
/*     */     }
/*  72 */     bean = this.manager.update(bean);
/*  73 */     log.info("update ShopMemberGroup. id={}.", bean.getId());
/*  74 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/group/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  80 */     WebErrors errors = validateDelete(ids, request);
/*  81 */     if (errors.hasErrors()) {
/*  82 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/*  86 */       ShopMemberGroup[] beans = this.manager.deleteByIds(ids);
/*  87 */       for (ShopMemberGroup bean : beans)
/*  88 */         log.info("delete ShopMemberGroup. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/*  91 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.member.then.delete.the.operation"));
/*  92 */       return errors.showErrorPage(model);
/*     */     }
/*     */     ShopMemberGroup[] beans;
/*  94 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopMemberGroup bean, HttpServletRequest request)
/*     */   {
/*  99 */     WebErrors errors = WebErrors.create(request);
/* 100 */     bean.setWebsite(SiteUtils.getWeb(request));
/* 101 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 105 */     WebErrors errors = WebErrors.create(request);
/* 106 */     errors.ifNull(id, "id");
/* 107 */     vldExist(id, errors);
/* 108 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 112 */     WebErrors errors = WebErrors.create(request);
/* 113 */     errors.ifNull(id, "id");
/* 114 */     vldExist(id, errors);
/* 115 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 119 */     WebErrors errors = WebErrors.create(request);
/* 120 */     errors.ifEmpty(ids, "ids");
/* 121 */     for (Long id : ids) {
/* 122 */       vldExist(id, errors);
/*     */     }
/* 124 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Long id, WebErrors errors) {
/* 128 */     if (errors.hasErrors()) {
/* 129 */       return;
/*     */     }
/* 131 */     ShopMemberGroup entity = this.manager.findById(id);
/* 132 */     errors.ifNotExist(entity, ShopMemberGroup.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopMemberGroupAct
 * JD-Core Version:    0.6.0
 */