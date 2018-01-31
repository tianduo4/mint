/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
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
/*     */ public class AddressAct
/*     */ {
/*  28 */   private static final Logger log = LoggerFactory.getLogger(AddressAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*  32 */   @RequestMapping({"/address/v_list.do"})
/*     */   public String list(Long parentId, Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPageByParentId(parentId, SimplePage.cpn(pageNo), 
/*  33 */       CookieUtils.getPageSize(request));
/*  34 */     model.addAttribute("pagination", pagination);
/*  35 */     model.addAttribute("parentId", parentId);
/*  36 */     return "address/list"; }
/*     */ 
/*     */   @RequestMapping({"/address/v_add.do"})
/*     */   public String add(Long parentId, ModelMap model) {
/*  41 */     model.addAttribute("parentId", parentId);
/*  42 */     return "address/add";
/*     */   }
/*     */   @RequestMapping({"/address/v_edit.do"})
/*     */   public String edit(Long id, Long parentId, HttpServletRequest request, ModelMap model) {
/*  47 */     WebErrors errors = validateEdit(id, request);
/*  48 */     if (errors.hasErrors()) {
/*  49 */       return errors.showErrorPage(model);
/*     */     }
/*  51 */     model.addAttribute("address", this.manager.findById(id));
/*  52 */     model.addAttribute("parentId", parentId);
/*  53 */     return "address/edit";
/*     */   }
/*     */   @RequestMapping({"/address/o_save.do"})
/*     */   public String save(Long parentId, Address bean, HttpServletRequest request, ModelMap model) {
/*  58 */     WebErrors errors = validateSave(bean, request);
/*  59 */     if (errors.hasErrors()) {
/*  60 */       return errors.showErrorPage(model);
/*     */     }
/*  62 */     if (parentId != null) {
/*  63 */       Address address = this.manager.findById(parentId);
/*  64 */       bean.setParent(address);
/*     */     }
/*  66 */     bean = this.manager.save(bean);
/*  67 */     log.info("save Address id={}", bean.getId());
/*  68 */     model.addAttribute("parentId", parentId);
/*  69 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/address/o_update.do"})
/*     */   public String update(Long parentId, Address bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     bean = this.manager.update(bean);
/*  80 */     log.info("update Address id={}.", bean.getId());
/*  81 */     return list(parentId, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/address/o_delete.do"})
/*     */   public String delete(Long[] ids, Long parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  87 */     WebErrors errors = validateDelete(ids, request);
/*  88 */     if (errors.hasErrors()) {
/*  89 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/*  93 */       Address[] beans = this.manager.deleteByIds(ids);
/*  94 */       for (Address bean : beans)
/*  95 */         log.info("delete Address id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/*  98 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.region.sub.region"));
/*  99 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Address[] beans;
/* 101 */     return list(parentId, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/address/o_priority.do"})
/*     */   public String priority(Long[] wids, Long parentId, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 107 */     WebErrors errors = validatePriority(wids, priority, request);
/* 108 */     if (errors.hasErrors()) {
/* 109 */       return errors.showErrorPage(model);
/*     */     }
/* 111 */     this.manager.updatePriority(wids, priority);
/* 112 */     model.addAttribute("message", "global.success");
/* 113 */     return list(parentId, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 118 */     WebErrors errors = WebErrors.create(request);
/* 119 */     if (errors.ifEmpty(wids, "wids")) {
/* 120 */       return errors;
/*     */     }
/* 122 */     if (errors.ifEmpty(priority, "priority")) {
/* 123 */       return errors;
/*     */     }
/* 125 */     if (wids.length != priority.length) {
/* 126 */       errors.addErrorString("wids length not equals priority length");
/* 127 */       return errors;
/*     */     }
/* 129 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 130 */       vldExist(wids[i], errors);
/* 131 */       if (priority[i] == null) {
/* 132 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 135 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 139 */     Address entity = this.manager.findById(id);
/* 140 */     return errors.ifNotExist(entity, Address.class, id);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Address bean, HttpServletRequest request) {
/* 144 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 147 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 151 */     WebErrors errors = WebErrors.create(request);
/* 152 */     Website web = SiteUtils.getWeb(request);
/* 153 */     if (vldExist(id, web.getId(), errors)) {
/* 154 */       return errors;
/*     */     }
/* 156 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 160 */     WebErrors errors = WebErrors.create(request);
/* 161 */     Website web = SiteUtils.getWeb(request);
/* 162 */     if (vldExist(id, web.getId(), errors)) {
/* 163 */       return errors;
/*     */     }
/* 165 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 169 */     WebErrors errors = WebErrors.create(request);
/* 170 */     Website web = SiteUtils.getWeb(request);
/* 171 */     errors.ifEmpty(ids, "ids");
/* 172 */     for (Long id : ids) {
/* 173 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 175 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 179 */     if (errors.ifNull(id, "id")) {
/* 180 */       return true;
/*     */     }
/* 182 */     Address entity = this.manager.findById(id);
/*     */ 
/* 184 */     return errors.ifNotExist(entity, Address.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.AddressAct
 * JD-Core Version:    0.6.0
 */