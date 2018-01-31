/*     */ package com.jspgou.plug.weixin.action.admin;
/*     */ 
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMessageMng;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class WeixinMessageAct
/*     */ {
/*  25 */   private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMessageMng manager;
/*     */ 
/*  33 */   @RequiresPermissions({"weixinMessage:v_list"})
/*     */   @RequestMapping({"/weixinMessage/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Website site = SiteUtils.getWeb(request);
/*  34 */     Pagination p = this.manager.getPage(site.getId(), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*     */ 
/*  36 */     model.addAttribute("pagination", p);
/*  37 */     model.addAttribute("pageNo", pageNo);
/*  38 */     return "weixinMessage/list"; }
/*     */ 
/*     */   @RequiresPermissions({"weixinMessage:v_default_set"})
/*     */   @RequestMapping({"/weixinMessage/v_default_set.do"})
/*     */   public String setDefault(HttpServletRequest request, ModelMap model)
/*     */   {
/*  46 */     WeixinMessage defaultMsg = this.manager.getWelcome(SiteUtils.getWebId(request));
/*  47 */     model.addAttribute("sessionId", request.getSession().getId());
/*  48 */     if (defaultMsg == null) {
/*  49 */       return "weixinMessage/adddefault";
/*     */     }
/*  51 */     model.addAttribute("menu", defaultMsg);
/*  52 */     return "weixinMessage/editdefault";
/*     */   }
/*     */   @RequiresPermissions({"weixinMessage:o_default_save"})
/*     */   @RequestMapping({"/weixinMessage/o_default_save.do"})
/*     */   public String saveDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) {
/*  59 */     bean.setSite(SiteUtils.getWeb(request));
/*  60 */     bean.setWelcome(Boolean.valueOf(true));
/*  61 */     this.manager.save(bean);
/*  62 */     return setDefault(request, model);
/*     */   }
/*  68 */   @RequiresPermissions({"weixinMessage:o_default_update"})
/*     */   @RequestMapping({"/weixinMessage/o_default_update.do"})
/*     */   public String updateDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) { this.manager.update(bean);
/*  69 */     return setDefault(request, model); } 
/*     */   @RequiresPermissions({"weixinMessage:v_add"})
/*     */   @RequestMapping({"/weixinMessage/v_add.do"})
/*     */   public String add(Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  75 */     model.addAttribute("sessionId", request.getSession().getId());
/*  76 */     model.addAttribute("pageNo", pageNo);
/*  77 */     return "weixinMessage/add";
/*     */   }
/*  83 */   @RequiresPermissions({"weixinMessage:v_edit"})
/*     */   @RequestMapping({"/weixinMessage/v_edit.do"})
/*     */   public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) { WeixinMessage entity = this.manager.findById(id);
/*  84 */     model.addAttribute("pageNo", pageNo);
/*  85 */     model.addAttribute("menu", entity);
/*  86 */     model.addAttribute("sessionId", request.getSession().getId());
/*  87 */     return "weixinMessage/edit"; } 
/*     */   @RequiresPermissions({"weixinMessage:o_save"})
/*     */   @RequestMapping({"/weixinMessage/o_save.do"})
/*     */   public String save(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  93 */     Website site = SiteUtils.getWeb(request);
/*  94 */     bean.setSite(site);
/*  95 */     bean.setWelcome(Boolean.valueOf(false));
/*  96 */     bean.setType(Integer.valueOf(0));
/*  97 */     this.manager.save(bean);
/*  98 */     return list(pageNo, request, model);
/*     */   }
/* 104 */   @RequiresPermissions({"weixinMessage:o_update"})
/*     */   @RequestMapping({"/weixinMessage/o_update.do"})
/*     */   public String update(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) { this.manager.update(bean);
/* 105 */     return list(pageNo, request, model); }
/*     */ 
/*     */   @RequiresPermissions({"weixinMessage:o_delete"})
/*     */   @RequestMapping({"/weixinMessage/o_delete.do"})
/*     */   public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 112 */     WebErrors errors = validateDelete(ids, request);
/* 113 */     if (errors.hasErrors()) {
/* 114 */       return errors.showErrorPage(model);
/*     */     }
/* 116 */     WeixinMessage[] beans = this.manager.deleteByIds(ids);
/* 117 */     for (WeixinMessage bean : beans) {
/* 118 */       log.info("delete WeixinMessage id={}", bean.getId());
/*     */     }
/* 120 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 124 */     WebErrors errors = WebErrors.create(request);
/* 125 */     if (errors.ifEmpty(ids, "ids")) {
/* 126 */       return errors;
/*     */     }
/* 128 */     for (Integer id : ids) {
/* 129 */       vldExist(id, errors);
/*     */     }
/* 131 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, WebErrors errors) {
/* 135 */     if (errors.ifNull(id, "id")) {
/* 136 */       return true;
/*     */     }
/* 138 */     WeixinMessage entity = this.manager.findById(id);
/*     */ 
/* 140 */     return errors.ifNotExist(entity, WeixinMenu.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.action.admin.WeixinMessageAct
 * JD-Core Version:    0.6.0
 */