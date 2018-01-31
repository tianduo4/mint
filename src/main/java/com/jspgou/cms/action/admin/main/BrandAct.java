/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.util.CnToSpell;
/*     */ import com.jspgou.common.util.StrUtils;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.List;
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
/*     */ public class BrandAct
/*     */ {
/*  37 */   private static final Logger log = LoggerFactory.getLogger(BrandAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*  42 */   @RequestMapping({"/brand/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { String name = RequestUtils.getQueryParam(request, "name");
/*  43 */     String brandtype = RequestUtils.getQueryParam(request, "brandtype");
/*  44 */     List list = this.manager.getAllList();
/*  45 */     Pagination pagination = this.manager.getPage(name, brandtype, SimplePage.cpn(pageNo), 
/*  46 */       CookieUtils.getPageSize(request));
/*     */ 
/*  48 */     List categoryList = this.categoryMng.getListForParent(
/*  49 */       SiteUtils.getWebId(request), null);
/*  50 */     model.addAttribute("categoryList", categoryList);
/*     */ 
/*  52 */     model.addAttribute("list", list);
/*  53 */     model.addAttribute("name", name);
/*  54 */     model.addAttribute("brandtype", brandtype);
/*  55 */     model.addAttribute("pagination", pagination);
/*  56 */     return "brand/list"; }
/*     */ 
/*     */   @RequestMapping({"/brand/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  61 */     List list = this.categoryMng.getListForParent(
/*  62 */       SiteUtils.getWebId(request), null);
/*  63 */     model.addAttribute("list", list);
/*  64 */     return "brand/add";
/*     */   }
/*     */   @RequestMapping({"/brand/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  69 */     if (id != null) {
/*  70 */       WebErrors errors = validateEdit(id, request);
/*  71 */       if (errors.hasErrors()) {
/*  72 */         return errors.showErrorPage(model);
/*     */       }
/*  74 */       List list = this.categoryMng.getListForParent(
/*  75 */         SiteUtils.getWebId(request), null);
/*  76 */       model.addAttribute("list", list);
/*     */     } else {
/*  78 */       return "brand/list";
/*     */     }
/*     */ 
/*  81 */     model.addAttribute("brand", this.manager.findById(id));
/*  82 */     return "brand/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/o_save.do"})
/*     */   public String save(Brand bean, String text, Integer brandtypeId, HttpServletRequest request, ModelMap model) {
/*  88 */     WebErrors errors = validateSave(bean, request);
/*  89 */     if (errors.hasErrors()) {
/*  90 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  93 */     String name = bean.getName();
/*  94 */     CnToSpell cts = new CnToSpell();
/*  95 */     bean.setFirstCharacter(cts.getBeginCharacter(name).substring(0, 1));
/*  96 */     bean.setCategory(this.categoryMng.findById(brandtypeId));
/*  97 */     bean = this.manager.save(bean, text);
/*  98 */     log.info("save brand. id={}", bean.getId());
/*  99 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/o_update.do"})
/*     */   public String update(Brand bean, String text, Integer brandtypeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 105 */     WebErrors errors = validateUpdate(bean, request);
/* 106 */     if (errors.hasErrors()) {
/* 107 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/* 110 */     String name = bean.getName();
/* 111 */     CnToSpell cts = new CnToSpell();
/* 112 */     bean.setFirstCharacter(cts.getBeginCharacter(name).substring(0, 1));
/* 113 */     bean.setCategory(this.categoryMng.findById(brandtypeId));
/* 114 */     bean = this.manager.update(bean, text);
/* 115 */     log.info("update brand. id={}.", bean.getId());
/* 116 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 122 */     WebErrors errors = validateDelete(ids, request);
/* 123 */     if (errors.hasErrors()) {
/* 124 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 128 */       Brand[] beans = this.manager.deleteByIds(ids);
/* 129 */       for (Brand bean : beans)
/* 130 */         log.info("delete brand. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 133 */       errors.addErrorString(this.productMng.getTipFile("Pleaseselectedoperation"));
/* 134 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Brand[] beans;
/* 136 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 142 */     WebErrors errors = validatePriority(wids, priority, request);
/* 143 */     if (errors.hasErrors()) {
/* 144 */       return errors.showErrorPage(model);
/*     */     }
/* 146 */     this.manager.updatePriority(wids, priority);
/* 147 */     model.addAttribute("message", "global.success");
/* 148 */     return list(pageNo, request, model);
/*     */   }
/*     */   @RequestMapping({"/brand/v_check_brandName.do"})
/*     */   public void checkUsername(String name, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/* 154 */       name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 157 */       e.printStackTrace();
/*     */     }
/*     */     String pass;
/*     */     String pass;
/* 160 */     if (StringUtils.isBlank(name))
/* 161 */       pass = "false";
/*     */     else {
/* 163 */       pass = this.manager.brandNameNotExist(name) ? "true" : "false";
/*     */     }
/* 165 */     ResponseUtils.renderJson(response, pass);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Brand bean, HttpServletRequest request) {
/* 169 */     WebErrors errors = WebErrors.create(request);
/* 170 */     bean.setWebUrl(StrUtils.handelUrl(bean.getWebUrl()));
/* 171 */     bean.setWebsite(SiteUtils.getWeb(request));
/* 172 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 176 */     WebErrors errors = WebErrors.create(request);
/* 177 */     errors.ifNull(id, "id");
/* 178 */     vldExist(id, errors);
/* 179 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Brand bean, HttpServletRequest request) {
/* 183 */     WebErrors errors = WebErrors.create(request);
/* 184 */     Long id = bean.getId();
/* 185 */     bean.setWebUrl(StrUtils.handelUrl(bean.getWebUrl()));
/* 186 */     errors.ifNull(id, "id");
/* 187 */     vldExist(id, errors);
/* 188 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 192 */     WebErrors errors = WebErrors.create(request);
/* 193 */     errors.ifEmpty(ids, "ids");
/* 194 */     for (Long id : ids) {
/* 195 */       vldExist(id, errors);
/*     */     }
/* 197 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 202 */     WebErrors errors = WebErrors.create(request);
/* 203 */     if (errors.ifEmpty(wids, "wids")) {
/* 204 */       return errors;
/*     */     }
/* 206 */     if (errors.ifEmpty(priority, "priority")) {
/* 207 */       return errors;
/*     */     }
/* 209 */     if (wids.length != priority.length) {
/* 210 */       errors.addErrorString("wids length not equals priority length");
/* 211 */       return errors;
/*     */     }
/* 213 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 214 */       vldExist(wids[i], errors);
/* 215 */       if (priority[i] == null) {
/* 216 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 219 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 223 */     Brand entity = this.manager.findById(id);
/* 224 */     return errors.ifNotExist(entity, Brand.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.BrandAct
 * JD-Core Version:    0.6.0
 */