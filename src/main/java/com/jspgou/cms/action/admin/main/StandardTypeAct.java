/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class StandardTypeAct
/*     */ {
/*  37 */   private static final Logger log = LoggerFactory.getLogger(StandardTypeAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*  41 */   @RequestMapping({"/standardType/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  42 */       CookieUtils.getPageSize(request));
/*  43 */     model.addAttribute("pagination", pagination);
/*  44 */     return "standardType/list"; }
/*     */ 
/*     */   @RequestMapping({"/standardType/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  49 */     return "standardType/add";
/*     */   }
/*     */   @RequestMapping({"/standardType/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  54 */     Website web = SiteUtils.getWeb(request);
/*  55 */     WebErrors errors = validateEdit(id, request);
/*  56 */     if (errors.hasErrors()) {
/*  57 */       return errors.showErrorPage(model);
/*     */     }
/*  59 */     StandardType bean = this.manager.findById(id);
/*  60 */     List standards = this.standardMng.findByTypeId(id);
/*  61 */     model.addAttribute("standardType", bean);
/*  62 */     model.addAttribute("standards", standards);
/*  63 */     return "standardType/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/o_save.do"})
/*     */   public String save(StandardType bean, String[] itemName, String[] itemColor, Integer[] itemPriority, HttpServletRequest request, ModelMap model)
/*     */   {
/*  70 */     WebErrors errors = validateSave(bean, request);
/*  71 */     if (errors.hasErrors()) {
/*  72 */       return errors.showErrorPage(model);
/*     */     }
/*  74 */     bean = this.manager.save(bean);
/*  75 */     addStandard(bean, itemName, itemColor, itemPriority);
/*  76 */     log.info("save StandardType id={}", bean.getId());
/*  77 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/o_update.do"})
/*     */   public String update(StandardType bean, Long[] itemId, String[] itemName, String[] itemColor, Integer[] itemPriority, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  84 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  85 */     if (errors.hasErrors()) {
/*  86 */       return errors.showErrorPage(model);
/*     */     }
/*  88 */     bean = this.manager.update(bean);
/*     */ 
/*  90 */     updateStandard(bean, itemId, itemName, itemColor, itemPriority);
/*  91 */     log.info("update StandardType id={}.", bean.getId());
/*  92 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/o_priority.do"})
/*     */   public String priority(Integer pageNo, Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
/*  98 */     WebErrors errors = validatePriority(wids, priority, request);
/*  99 */     if (errors.hasErrors()) {
/* 100 */       return errors.showErrorPage(model);
/*     */     }
/* 102 */     this.manager.updatePriority(wids, priority);
/* 103 */     model.addAttribute("message", "global.success");
/* 104 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 112 */     WebErrors errors = validateDelete(ids, request);
/* 113 */     if (errors.hasErrors()) {
/* 114 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 118 */       StandardType[] beans = this.manager.deleteByIds(ids);
/* 119 */       for (StandardType bean : beans)
/* 120 */         log.info("delete StandardType id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 123 */       errors.addErrorString(this.productMng.getTipFile("Please.remove.dele"));
/* 124 */       return errors.showErrorPage(model);
/*     */     }
/*     */     StandardType[] beans;
/* 126 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private void addStandard(StandardType bean, String[] itemName, String[] itemColor, Integer[] itemPriority)
/*     */   {
/* 131 */     if (itemName != null) {
/* 132 */       int i = 0;
                for (int len = itemName.length; i < len; i++)
/* 133 */         if (!StringUtils.isBlank(itemName[i])) {
/* 134 */           Standard item = new Standard();
/* 135 */           item.setName(itemName[i]);
/* 136 */           item.setColor(itemColor[i]);
/* 137 */           item.setPriority(itemPriority[i]);
/* 138 */           item.setType(bean);
/* 139 */           this.standardMng.save(item);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/v_check_field.do"})
/*     */   public String checkUsername(String field, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 148 */     if ((StringUtils.isBlank(field)) || (this.manager.getByField(field) != null))
/* 149 */       ResponseUtils.renderJson(response, "false");
/*     */     else {
/* 151 */       ResponseUtils.renderJson(response, "true");
/*     */     }
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   private void updateStandard(StandardType bean, Long[] itemId, String[] itemName, String[] itemColor, Integer[] itemPriority) {
/* 157 */     Set<Standard> set = bean.getStandardSet();
/* 158 */     if (itemId != null) {
/* 159 */       for (Standard s : set) {
/* 160 */         if (!Arrays.asList(itemId).contains(s.getId()))
/* 161 */           this.standardMng.deleteById(s.getId());
/*     */       }
/*     */     }
/*     */     else {
/* 165 */       for (Standard s : set) {
/* 166 */         this.standardMng.deleteById(s.getId());
/*     */       }
/*     */     }
/*     */ 
/* 170 */     if (itemName != null) {
/* 171 */       int i = 0; for (int len = itemName.length; i < len; i++)
/* 172 */         if (!StringUtils.isBlank(itemName[i]))
/* 173 */           if ((itemId != null) && (i < itemId.length)) {
/* 174 */             Standard item = this.standardMng.findById(itemId[i]);
/* 175 */             item.setName(itemName[i]);
/* 176 */             item.setColor(itemColor[i]);
/* 177 */             item.setPriority(itemPriority[i]);
/* 178 */             item.setType(bean);
/* 179 */             this.standardMng.update(item);
/*     */           } else {
/* 181 */             Standard item = new Standard();
/* 182 */             item.setName(itemName[i]);
/* 183 */             item.setColor(itemColor[i]);
/* 184 */             item.setPriority(itemPriority[i]);
/* 185 */             item.setType(bean);
/* 186 */             this.standardMng.save(item);
/*     */           }
/*     */     }
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(StandardType bean, HttpServletRequest request)
/*     */   {
/* 194 */     WebErrors errors = WebErrors.create(request);
/* 195 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 199 */     WebErrors errors = WebErrors.create(request);
/* 200 */     Website web = SiteUtils.getWeb(request);
/* 201 */     if (vldExist(id, web.getId(), errors)) {
/* 202 */       return errors;
/*     */     }
/* 204 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 208 */     WebErrors errors = WebErrors.create(request);
/* 209 */     Website web = SiteUtils.getWeb(request);
/* 210 */     if (vldExist(id, web.getId(), errors)) {
/* 211 */       return errors;
/*     */     }
/* 213 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 217 */     WebErrors errors = WebErrors.create(request);
/* 218 */     Website web = SiteUtils.getWeb(request);
/* 219 */     errors.ifEmpty(ids, "ids");
/* 220 */     for (Long id : ids) {
/* 221 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 223 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 227 */     if (errors.ifNull(id, "id")) {
/* 228 */       return true;
/*     */     }
/* 230 */     StandardType entity = this.manager.findById(id);
/*     */ 
/* 232 */     return errors.ifNotExist(entity, StandardType.class, id);
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 239 */     Website web = SiteUtils.getWeb(request);
/* 240 */     WebErrors errors = WebErrors.create(request);
/* 241 */     if (errors.ifEmpty(wids, "ids")) {
/* 242 */       return errors;
/*     */     }
/* 244 */     if (errors.ifEmpty(priority, "priority")) {
/* 245 */       return errors;
/*     */     }
/* 247 */     if (wids.length != priority.length) {
/* 248 */       errors.addErrorString("ids length not equals priority length");
/* 249 */       return errors;
/*     */     }
/* 251 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 252 */       if (vldExist(wids[i], web.getId(), errors)) {
/* 253 */         return errors;
/*     */       }
/* 255 */       if (priority[i] == null) {
/* 256 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 259 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.StandardTypeAct
 * JD-Core Version:    0.6.0
 */