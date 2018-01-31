/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.cms.manager.DiscussMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Date;
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
/*     */ public class DiscussAct
/*     */ {
/*  32 */   private static final Logger log = LoggerFactory.getLogger(DiscussAct.class);
/*  33 */   private String product_name = "";
/*     */ 
/*     */   @Autowired
/*     */   private DiscussMng manager;
/*     */ 
/*  38 */   @RequestMapping({"/discuss/v_list.do"})
/*     */   public String list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) { String userName = RequestUtils.getQueryParam(request, "userName");
/*  39 */     userName = StringUtils.trim(userName);
/*  40 */     String productName = RequestUtils.getQueryParam(request, "productName");
/*  41 */     productName = StringUtils.trim(productName);
/*  42 */     Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, 
/*  43 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), true);
/*  44 */     if (!StringUtils.isBlank(userName)) {
/*  45 */       model.addAttribute("userName", userName);
/*     */     }
/*  47 */     if (!StringUtils.isBlank(productName)) {
/*  48 */       model.addAttribute("productName", productName);
/*     */     }
/*     */ 
/*  52 */     model.addAttribute("startTime", startTime);
/*  53 */     model.addAttribute("endTime", endTime);
/*     */ 
/*  55 */     model.addAttribute("pagination", pagination);
/*  56 */     model.addAttribute("pageNo", pageNo);
/*  57 */     return "discuss/list"; }
/*     */ 
/*     */   @RequestMapping({"/discuss/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  62 */     return "discuss/add";
/*     */   }
/*     */   @RequestMapping({"/discuss/v_edit.do"})
/*     */   public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  67 */     WebErrors errors = validateEdit(id, request);
/*  68 */     if (errors.hasErrors()) {
/*  69 */       return errors.showErrorPage(model);
/*     */     }
/*  71 */     model.addAttribute("discuss", this.manager.findById(id));
/*  72 */     model.addAttribute("pageNo", pageNo);
/*  73 */     return "discuss/edit";
/*     */   }
/*     */   @RequestMapping({"/discuss/o_save.do"})
/*     */   public String save(Discuss bean, HttpServletRequest request, ModelMap model) {
/*  78 */     WebErrors errors = validateSave(bean, request);
/*  79 */     if (errors.hasErrors()) {
/*  80 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  83 */     log.info("save Discuss id={}", bean.getId());
/*  84 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/discuss/o_update.do"})
/*     */   public String update(Discuss bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  90 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  91 */     if (errors.hasErrors()) {
/*  92 */       return errors.showErrorPage(model);
/*     */     }
/*  94 */     bean = this.manager.update(bean);
/*  95 */     log.info("update Discuss id={}.", bean.getId());
/*  96 */     return list(null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/discuss/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 102 */     WebErrors errors = validateDelete(ids, request);
/* 103 */     if (errors.hasErrors()) {
/* 104 */       return errors.showErrorPage(model);
/*     */     }
/* 106 */     Discuss[] beans = this.manager.deleteByIds(ids);
/* 107 */     for (Discuss bean : beans) {
/* 108 */       log.info("delete Discuss id={}", bean.getId());
/*     */     }
/* 110 */     return list(null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/discuss/o_visual_delete.do"})
/*     */   public String visual_delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 123 */     WebErrors errors = validateDelete(ids, request);
/* 124 */     if (errors.hasErrors()) {
/* 125 */       return errors.showErrorPage(model);
/*     */     }
/* 127 */     Discuss[] beans = this.manager.deleteByIds(ids);
/* 128 */     for (Discuss bean : beans) {
/* 129 */       log.info("delete Discuss id={}", bean.getId());
/*     */     }
/* 131 */     return get_visual_list(null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/discuss/v_visual_list.do"})
/*     */   public String get_visual_list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 146 */     String userName = RequestUtils.getQueryParam(request, "userName");
/* 147 */     userName = StringUtils.trim(userName);
/* 148 */     String productName = RequestUtils.getQueryParam(request, "productName");
/* 149 */     productName = StringUtils.trim(productName);
/*     */ 
/* 151 */     if (StringUtils.isNotBlank(productName)) {
/* 152 */       this.product_name = productName;
/*     */     }
/*     */     else {
/* 155 */       productName = this.product_name;
/*     */     }
/* 157 */     Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, 
/* 158 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), true);
/* 159 */     if (!StringUtils.isBlank(userName)) {
/* 160 */       model.addAttribute("userName", userName);
/*     */     }
/* 162 */     if (!StringUtils.isBlank(productName)) {
/* 163 */       model.addAttribute("productName", productName);
/*     */     }
/* 165 */     model.addAttribute("startTime", startTime);
/* 166 */     model.addAttribute("endTime", endTime);
/* 167 */     model.addAttribute("pagination", pagination);
/* 168 */     model.addAttribute("pageNo", pageNo);
/* 169 */     return "discuss/visual_list";
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Discuss bean, HttpServletRequest request) {
/* 173 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 176 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 180 */     WebErrors errors = WebErrors.create(request);
/* 181 */     Website web = SiteUtils.getWeb(request);
/* 182 */     if (vldExist(id, web.getId(), errors)) {
/* 183 */       return errors;
/*     */     }
/* 185 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 189 */     WebErrors errors = WebErrors.create(request);
/* 190 */     Website web = SiteUtils.getWeb(request);
/* 191 */     if (vldExist(id, web.getId(), errors)) {
/* 192 */       return errors;
/*     */     }
/* 194 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 198 */     WebErrors errors = WebErrors.create(request);
/* 199 */     Website web = SiteUtils.getWeb(request);
/* 200 */     errors.ifEmpty(ids, "ids");
/* 201 */     for (Long id : ids) {
/* 202 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 204 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 208 */     if (errors.ifNull(id, "id")) {
/* 209 */       return true;
/*     */     }
/* 211 */     Discuss entity = this.manager.findById(id);
/*     */ 
/* 213 */     return errors.ifNotExist(entity, Discuss.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.DiscussAct
 * JD-Core Version:    0.6.0
 */