/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryTypeMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.core.entity.Website;
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
/*     */ public class ShopDictionaryAct
/*     */ {
/*  33 */   private static final Logger log = LoggerFactory.getLogger(ShopDictionaryAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryTypeMng shopDictionaryTypeMng;
/*     */ 
/*  38 */   @RequestMapping({"/shopDictionary/v_list.do"})
/*     */   public String list(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) { String name = RequestUtils.getQueryParam(request, "name");
/*  39 */     List typelist = this.shopDictionaryTypeMng.findAll();
/*  40 */     Pagination pagination = this.manager.getPage(name, typeId, SimplePage.cpn(pageNo), 
/*  41 */       CookieUtils.getPageSize(request));
/*  42 */     model.addAttribute("name", name);
/*  43 */     model.addAttribute("typeId", typeId);
/*  44 */     model.addAttribute("typelist", typelist);
/*  45 */     model.addAttribute("pagination", pagination);
/*  46 */     return "shopDictionary/list"; }
/*     */ 
/*     */   @RequestMapping({"/shopDictionary/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  51 */     List sdtList = this.shopDictionaryTypeMng.findAll();
/*  52 */     model.addAttribute("sdtList", sdtList);
/*  53 */     return "shopDictionary/add";
/*     */   }
/*     */   @RequestMapping({"/shopDictionary/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  58 */     WebErrors errors = validateEdit(id, request);
/*  59 */     if (errors.hasErrors()) {
/*  60 */       return errors.showErrorPage(model);
/*     */     }
/*  62 */     List sdtList = this.shopDictionaryTypeMng.findAll();
/*  63 */     model.addAttribute("sdtList", sdtList);
/*  64 */     model.addAttribute("shopDictionary", this.manager.findById(id));
/*  65 */     return "shopDictionary/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopDictionary/o_save.do"})
/*     */   public String save(ShopDictionary bean, Long shopDictionaryTypeId, HttpServletRequest request, ModelMap model) {
/*  71 */     WebErrors errors = validateSave(bean, request);
/*  72 */     if (errors.hasErrors()) {
/*  73 */       return errors.showErrorPage(model);
/*     */     }
/*  75 */     bean.setShopDictionaryType(this.shopDictionaryTypeMng.findById(shopDictionaryTypeId));
/*  76 */     bean = this.manager.save(bean);
/*  77 */     log.info("save ShopDictionary id={}", bean.getId());
/*  78 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopDictionary/o_update.do"})
/*     */   public String update(ShopDictionary bean, Long shopDictionaryTypeId, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  85 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  86 */     if (errors.hasErrors()) {
/*  87 */       return errors.showErrorPage(model);
/*     */     }
/*  89 */     bean.setShopDictionaryType(this.shopDictionaryTypeMng.findById(shopDictionaryTypeId));
/*  90 */     bean = this.manager.update(bean);
/*  91 */     log.info("update ShopDictionary id={}.", bean.getId());
/*  92 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopDictionary/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  98 */     WebErrors errors = validateDelete(ids, request);
/*  99 */     if (errors.hasErrors()) {
/* 100 */       return errors.showErrorPage(model);
/*     */     }
/* 102 */     ShopDictionary[] beans = this.manager.deleteByIds(ids);
/* 103 */     for (ShopDictionary bean : beans) {
/* 104 */       log.info("delete ShopDictionary id={}", bean.getId());
/*     */     }
/* 106 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopDictionary/o_priority.do"})
/*     */   public String priority(Integer pageNo, Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
/* 112 */     WebErrors errors = validatePriority(wids, priority, request);
/* 113 */     if (errors.hasErrors()) {
/* 114 */       return errors.showErrorPage(model);
/*     */     }
/* 116 */     this.manager.updatePriority(wids, priority);
/* 117 */     model.addAttribute("message", "global.success");
/* 118 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopDictionary bean, HttpServletRequest request) {
/* 122 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 125 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 129 */     WebErrors errors = WebErrors.create(request);
/* 130 */     Website web = SiteUtils.getWeb(request);
/* 131 */     if (vldExist(id, web.getId(), errors)) {
/* 132 */       return errors;
/*     */     }
/* 134 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 138 */     WebErrors errors = WebErrors.create(request);
/* 139 */     Website web = SiteUtils.getWeb(request);
/* 140 */     if (vldExist(id, web.getId(), errors)) {
/* 141 */       return errors;
/*     */     }
/* 143 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 147 */     WebErrors errors = WebErrors.create(request);
/* 148 */     Website web = SiteUtils.getWeb(request);
/* 149 */     errors.ifEmpty(ids, "ids");
/* 150 */     for (Long id : ids) {
/* 151 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 153 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 157 */     if (errors.ifNull(id, "id")) {
/* 158 */       return true;
/*     */     }
/* 160 */     ShopDictionary entity = this.manager.findById(id);
/*     */ 
/* 162 */     return errors.ifNotExist(entity, ShopDictionary.class, id);
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 173 */     Website web = SiteUtils.getWeb(request);
/* 174 */     WebErrors errors = WebErrors.create(request);
/* 175 */     if (errors.ifEmpty(wids, "ids")) {
/* 176 */       return errors;
/*     */     }
/* 178 */     if (errors.ifEmpty(priority, "priority")) {
/* 179 */       return errors;
/*     */     }
/* 181 */     if (wids.length != priority.length) {
/* 182 */       errors.addErrorString("ids length not equals priority length");
/* 183 */       return errors;
/*     */     }
/* 185 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 186 */       if (vldExist(wids[i], web.getId(), errors)) {
/* 187 */         return errors;
/*     */       }
/* 189 */       if (priority[i] == null) {
/* 190 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 193 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopDictionaryAct
 * JD-Core Version:    0.6.0
 */