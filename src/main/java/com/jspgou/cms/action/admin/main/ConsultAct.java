/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.manager.ConsultMng;
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
/*     */ public class ConsultAct
/*     */ {
/*  32 */   private static final Logger log = LoggerFactory.getLogger(ConsultAct.class);
/*  33 */   private String product_name = "";
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng manager;
/*     */ 
/*  38 */   @RequestMapping({"/consult/v_list.do"})
/*     */   public String list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) { String userName = RequestUtils.getQueryParam(request, "userName");
/*  39 */     userName = StringUtils.trim(userName);
/*  40 */     String productName = RequestUtils.getQueryParam(request, "productName");
/*  41 */     productName = StringUtils.trim(productName);
/*  42 */     Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime, 
/*  43 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), Boolean.valueOf(true));
/*     */ 
/*  45 */     if (!StringUtils.isBlank(userName)) {
/*  46 */       model.addAttribute("userName", userName);
/*     */     }
/*  48 */     if (!StringUtils.isBlank(productName)) {
/*  49 */       model.addAttribute("productName", productName);
/*     */     }
/*     */ 
/*  52 */     model.addAttribute("startTime", startTime);
/*  53 */     model.addAttribute("endTime", endTime);
/*     */ 
/*  55 */     model.addAttribute("pagination", pagination);
/*  56 */     model.addAttribute("pageNo", pageNo);
/*  57 */     return "consult/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/v_visualize_list.do"})
/*     */   public String get_visualize_list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  72 */     String userName = RequestUtils.getQueryParam(request, "userName");
/*  73 */     userName = StringUtils.trim(userName);
/*  74 */     String productName = RequestUtils.getQueryParam(request, "productName");
/*  75 */     productName = StringUtils.trim(productName);
/*     */ 
/*  77 */     if (StringUtils.isNotBlank(productName)) {
/*  78 */       this.product_name = productName;
/*     */     }
/*     */     else {
/*  81 */       productName = this.product_name;
/*     */     }
/*  83 */     Pagination pagination = this.manager.getVisiblePage(userName, productName, startTime, endTime, 
/*  84 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  85 */     if (!StringUtils.isBlank(userName)) {
/*  86 */       model.addAttribute("userName", userName);
/*     */     }
/*  88 */     if (!StringUtils.isBlank(productName)) {
/*  89 */       model.addAttribute("productName", productName);
/*     */     }
/*  91 */     model.addAttribute("startTime", startTime);
/*  92 */     model.addAttribute("endTime", endTime);
/*  93 */     model.addAttribute("pagination", pagination);
/*  94 */     model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
/*     */ 
/*  99 */     return "consult/visualize_list";
/*     */   }
/*     */   @RequestMapping({"/consult/v_add.do"})
/*     */   public String add(ModelMap model) {
/* 104 */     return "consult/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/v_edit.do"})
/*     */   public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 110 */     WebErrors errors = validateEdit(id, request);
/* 111 */     if (errors.hasErrors()) {
/* 112 */       return errors.showErrorPage(model);
/*     */     }
/* 114 */     model.addAttribute("consult", this.manager.findById(id));
/* 115 */     model.addAttribute("pageNo", pageNo);
/* 116 */     return "consult/edit";
/*     */   }
/*     */   @RequestMapping({"/consult/o_save.do"})
/*     */   public String save(Consult bean, HttpServletRequest request, ModelMap model) {
/* 121 */     WebErrors errors = validateSave(bean, request);
/* 122 */     if (errors.hasErrors()) {
/* 123 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/* 126 */     log.info("save Consult id={}", bean.getId());
/* 127 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/o_update.do"})
/*     */   public String update(Long id, String adminReplay, HttpServletRequest request, ModelMap model, Integer pageNo) {
/* 133 */     Consult bean = this.manager.findById(id);
/* 134 */     bean.setAdminReplay(adminReplay);
/* 135 */     this.manager.update(bean);
/* 136 */     model.addAttribute("pageNo", pageNo);
/* 137 */     log.info("update Consult id={}.", bean.getId());
/* 138 */     return list(null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 144 */     WebErrors errors = validateDelete(ids, request);
/* 145 */     if (errors.hasErrors()) {
/* 146 */       return errors.showErrorPage(model);
/*     */     }
/* 148 */     Consult[] beans = this.manager.deleteByIds(ids);
/* 149 */     for (Consult bean : beans) {
/* 150 */       log.info("delete Consult id={}", bean.getId());
/*     */     }
/* 152 */     return list(null, null, Integer.valueOf(SimplePage.cpn(pageNo)), request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/o_visual_delete.do"})
/*     */   public String visual_delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 158 */     WebErrors errors = validateDelete(ids, request);
/* 159 */     if (errors.hasErrors()) {
/* 160 */       return errors.showErrorPage(model);
/*     */     }
/* 162 */     Consult[] beans = this.manager.deleteByIds(ids);
/* 163 */     for (Consult bean : beans) {
/* 164 */       log.info("delete Consult id={}", bean.getId());
/*     */     }
/* 166 */     return get_visualize_list(null, null, Integer.valueOf(SimplePage.cpn(pageNo)), request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/o_visualize_update.do"})
/*     */   public String updateVisual(String id, HttpServletRequest request, ModelMap model, String pageNo) {
/* 172 */     Consult bean = this.manager.findById(Long.valueOf(Long.parseLong(id)));
/* 173 */     String adminReplay = request.getParameter("adminReplay" + id);
/* 174 */     if (StringUtils.isNotBlank(adminReplay))
/* 175 */       bean.setAdminReplay(adminReplay);
/*     */     else {
/* 177 */       bean.setAdminReplay(null);
/*     */     }
/* 179 */     this.manager.update(bean);
/* 180 */     model.addAttribute("pageNo", Integer.valueOf(SimplePage.cpn(Integer.valueOf(Integer.parseInt(pageNo)))));
/* 181 */     log.info("update Consult id={}.", bean.getId());
/* 182 */     return get_visualize_list(null, null, Integer.valueOf(SimplePage.cpn(Integer.valueOf(Integer.parseInt(pageNo)))), request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Consult bean, HttpServletRequest request)
/*     */   {
/* 187 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 190 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 194 */     WebErrors errors = WebErrors.create(request);
/* 195 */     Website web = SiteUtils.getWeb(request);
/* 196 */     if (vldExist(id, web.getId(), errors)) {
/* 197 */       return errors;
/*     */     }
/* 199 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 203 */     WebErrors errors = WebErrors.create(request);
/* 204 */     Website web = SiteUtils.getWeb(request);
/* 205 */     if (vldExist(id, web.getId(), errors)) {
/* 206 */       return errors;
/*     */     }
/* 208 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 212 */     WebErrors errors = WebErrors.create(request);
/* 213 */     Website web = SiteUtils.getWeb(request);
/* 214 */     errors.ifEmpty(ids, "ids");
/* 215 */     for (Long id : ids) {
/* 216 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 218 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 222 */     if (errors.ifNull(id, "id")) {
/* 223 */       return true;
/*     */     }
/* 225 */     Consult entity = this.manager.findById(id);
/*     */ 
/* 227 */     return errors.ifNotExist(entity, Consult.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ConsultAct
 * JD-Core Version:    0.6.0
 */