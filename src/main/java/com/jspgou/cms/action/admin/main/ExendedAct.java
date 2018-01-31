/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.ExendedItem;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.manager.ExendedItemMng;
/*     */ import com.jspgou.cms.manager.ExendedMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ExendedAct
/*     */ {
/*  38 */   private static final Logger log = LoggerFactory.getLogger(ExendedAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ExendedMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ExendedItemMng exendedItemMng;
/*     */ 
/*  42 */   @RequestMapping({"/exended/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  43 */     model.addAttribute("pagination", pagination);
/*  44 */     return "exended/list"; }
/*     */ 
/*     */   @RequestMapping({"/exended/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  49 */     Website web = SiteUtils.getWeb(request);
/*  50 */     List ptList = this.productTypeMng.getList(web.getId());
/*  51 */     model.addAttribute("ptList", ptList);
/*  52 */     return "exended/add";
/*     */   }
/*     */   @RequestMapping({"/exended/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  57 */     Website web = SiteUtils.getWeb(request);
/*  58 */     WebErrors errors = validateEdit(id, request);
/*  59 */     if (errors.hasErrors()) {
/*  60 */       return errors.showErrorPage(model);
/*     */     }
/*  62 */     Long[] typeIds = this.manager.findById(id).getProductTypeIds();
/*  63 */     List list = new ArrayList();
/*  64 */     list.addAll(this.manager.findById(id).getItems());
/*  65 */     List ptList = this.productTypeMng.getList(web.getId());
/*  66 */     model.addAttribute("ptList", ptList);
/*  67 */     model.addAttribute("list", list);
/*  68 */     model.addAttribute("typeIds", typeIds);
/*  69 */     model.addAttribute("exended", this.manager.findById(id));
/*  70 */     return "exended/edit";
/*     */   }
/*     */   @RequestMapping({"/exended/o_save.do"})
/*     */   public String save(Exended bean, Long[] typeIds, String[] itemName, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateSave(bean, request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     bean = this.manager.save(bean, typeIds);
/*  80 */     addExendedItems(bean, itemName);
/*  81 */     log.info("save exended id={}", bean.getId());
/*  82 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exended/o_update.do"})
/*     */   public String update(Exended bean, Long[] typeIds, Long[] itemId, String[] itemName, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  88 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  89 */     if (errors.hasErrors()) {
/*  90 */       return errors.showErrorPage(model);
/*     */     }
/*  92 */     bean = this.manager.update(bean, typeIds);
/*  93 */     updateExendedItems(bean, itemId, itemName);
/*  94 */     log.info("update Exended id={}.", bean.getId());
/*  95 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exended/o_priority.do"})
/*     */   public String priority(Integer pageNo, Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
/* 101 */     WebErrors errors = validatePriority(wids, priority, request);
/* 102 */     if (errors.hasErrors()) {
/* 103 */       return errors.showErrorPage(model);
/*     */     }
/* 105 */     this.manager.updatePriority(wids, priority);
/* 106 */     model.addAttribute("message", "global.success");
/* 107 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exended/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 113 */     WebErrors errors = validateDelete(ids, request);
/* 114 */     if (errors.hasErrors()) {
/* 115 */       return errors.showErrorPage(model);
/*     */     }
/* 117 */     Exended[] beans = this.manager.deleteByIds(ids);
/* 118 */     for (Exended bean : beans) {
/* 119 */       log.info("delete Exended id={}", bean.getId());
/*     */     }
/* 121 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private void addExendedItems(Exended bean, String[] itemName)
/*     */   {
/* 127 */     if (itemName != null) {
/* 128 */       int i = 0; for (int len = itemName.length; i < len; i++)
/* 129 */         if (!StringUtils.isBlank(itemName[i])) {
/* 130 */           ExendedItem item = new ExendedItem();
/* 131 */           item.setName(itemName[i]);
/* 132 */           item.setExended(bean);
/* 133 */           this.exendedItemMng.save(item);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateExendedItems(Exended bean, Long[] itemId, String[] itemName)
/*     */   {
/* 141 */     Set<ExendedItem> set = bean.getItems();
/* 142 */     if (itemId != null) {
/* 143 */       for (ExendedItem e : set) {
/* 144 */         if (!Arrays.asList(itemId).contains(e.getId()))
/* 145 */           this.exendedItemMng.deleteById(e.getId());
/*     */       }
/*     */     }
/*     */     else {
/* 149 */       for (ExendedItem e : set) {
/* 150 */         this.exendedItemMng.deleteById(e.getId());
/*     */       }
/*     */     }
/*     */ 
/* 154 */     if (itemName != null) {
/* 155 */       int i = 0; for (int len = itemName.length; i < len; i++)
/* 156 */         if (!StringUtils.isBlank(itemName[i]))
/* 157 */           if ((itemId != null) && (i < itemId.length)) {
/* 158 */             ExendedItem item = this.exendedItemMng.findById(itemId[i]);
/* 159 */             item.setName(itemName[i]);
/* 160 */             item.setExended(bean);
/* 161 */             this.exendedItemMng.update(item);
/*     */           } else {
/* 163 */             ExendedItem item = new ExendedItem();
/* 164 */             item.setName(itemName[i]);
/* 165 */             item.setExended(bean);
/* 166 */             this.exendedItemMng.save(item);
/*     */           }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Long[] fetchProductTypeIds(Collection<ProductType> productTypes)
/*     */   {
/* 180 */     Long[] ids = new Long[productTypes.size()];
/* 181 */     int i = 0;
/* 182 */     for (ProductType sdt : productTypes) {
/* 183 */       ids[(i++)] = sdt.getId();
/*     */     }
/* 185 */     return ids;
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Exended bean, HttpServletRequest request)
/*     */   {
/* 190 */     WebErrors errors = WebErrors.create(request);
/* 191 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 195 */     WebErrors errors = WebErrors.create(request);
/* 196 */     Website web = SiteUtils.getWeb(request);
/* 197 */     if (vldExist(id, web.getId(), errors)) {
/* 198 */       return errors;
/*     */     }
/* 200 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 204 */     WebErrors errors = WebErrors.create(request);
/* 205 */     Website web = SiteUtils.getWeb(request);
/* 206 */     if (vldExist(id, web.getId(), errors)) {
/* 207 */       return errors;
/*     */     }
/* 209 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 213 */     WebErrors errors = WebErrors.create(request);
/* 214 */     Website web = SiteUtils.getWeb(request);
/* 215 */     errors.ifEmpty(ids, "ids");
/* 216 */     for (Long id : ids) {
/* 217 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 219 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 223 */     if (errors.ifNull(id, "id")) {
/* 224 */       return true;
/*     */     }
/* 226 */     Exended entity = this.manager.findById(id);
/*     */ 
/* 228 */     return errors.ifNotExist(entity, Exended.class, id);
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 235 */     Website web = SiteUtils.getWeb(request);
/* 236 */     WebErrors errors = WebErrors.create(request);
/* 237 */     if (errors.ifEmpty(wids, "ids")) {
/* 238 */       return errors;
/*     */     }
/* 240 */     if (errors.ifEmpty(priority, "priority")) {
/* 241 */       return errors;
/*     */     }
/* 243 */     if (wids.length != priority.length) {
/* 244 */       errors.addErrorString("ids length not equals priority length");
/* 245 */       return errors;
/*     */     }
/* 247 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 248 */       if (vldExist(wids[i], web.getId(), errors)) {
/* 249 */         return errors;
/*     */       }
/* 251 */       if (priority[i] == null) {
/* 252 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 255 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ExendedAct
 * JD-Core Version:    0.6.0
 */