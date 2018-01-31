/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.manager.FtpMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class FtpAct
/*     */ {
/*  22 */   private static final Logger log = LoggerFactory.getLogger(FtpAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private FtpMng manager;
/*     */ 
/*  27 */   @RequiresPermissions({"ftp:v_list"})
/*     */   @RequestMapping({"/ftp/v_list.do"})
/*     */   public String list(Integer pageNO, HttpServletRequest request, ModelMap model) { List list = this.manager.getList();
/*  28 */     model.addAttribute("list", list);
/*  29 */     return "ftp/list"; } 
/*     */   @RequiresPermissions({"ftp:v_add"})
/*     */   @RequestMapping({"/ftp/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  35 */     return "ftp/add";
/*     */   }
/*  41 */   @RequiresPermissions({"ftp:v_edit"})
/*     */   @RequestMapping({"/ftp/v_edit.do"})
/*     */   public String edit(Integer id, HttpServletRequest request, ModelMap model) { WebErrors errors = validateEdit(id, request);
/*  42 */     if (errors.hasErrors()) {
/*  43 */       return errors.showErrorPage(model);
/*     */     }
/*  45 */     model.addAttribute("ftp", this.manager.findById(id));
/*  46 */     return "ftp/edit"; } 
/*     */   @RequiresPermissions({"ftp:o_save"})
/*     */   @RequestMapping({"/ftp/o_save.do"})
/*     */   public String save(Ftp bean, HttpServletRequest request, ModelMap model) {
/*  52 */     WebErrors errors = validateSave(bean, request);
/*  53 */     if (errors.hasErrors()) {
/*  54 */       return errors.showErrorPage(model);
/*     */     }
/*  56 */     bean = this.manager.save(bean);
/*  57 */     log.info("save Ftp id={}", bean.getId());
/*     */ 
/*  59 */     return "redirect:v_list.do";
/*     */   }
/*     */   @RequiresPermissions({"ftp:o_update"})
/*     */   @RequestMapping({"/ftp/o_update.do"})
/*     */   public String update(Ftp bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  66 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  67 */     if (errors.hasErrors()) {
/*  68 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  71 */     if (StringUtils.isBlank(bean.getPassword())) {
/*  72 */       bean.setPassword(this.manager.findById(bean.getId()).getPassword());
/*     */     }
/*  74 */     bean = this.manager.update(bean);
/*  75 */     log.info("update Ftp id={}.", bean.getId());
/*     */ 
/*  77 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ftp/o_delete.do"})
/*     */   public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  84 */     WebErrors errors = validateDelete(ids, request);
/*  85 */     if (errors.hasErrors()) {
/*  86 */       return errors.showErrorPage(model);
/*     */     }
/*  88 */     Ftp[] beans = this.manager.deleteByIds(ids);
/*  89 */     for (Ftp bean : beans) {
/*  90 */       log.info("delete Ftp id={}", bean.getId());
/*     */     }
/*     */ 
/*  93 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Ftp bean, HttpServletRequest request) {
/*  97 */     WebErrors errors = WebErrors.create(request);
/*  98 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Integer id, HttpServletRequest request) {
/* 102 */     WebErrors errors = WebErrors.create(request);
/* 103 */     if (vldExist(id, errors)) {
/* 104 */       return errors;
/*     */     }
/* 106 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
/* 110 */     WebErrors errors = WebErrors.create(request);
/* 111 */     if (vldExist(id, errors)) {
/* 112 */       return errors;
/*     */     }
/* 114 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 118 */     WebErrors errors = WebErrors.create(request);
/* 119 */     errors.ifEmpty(ids, "ids");
/* 120 */     for (Integer id : ids) {
/* 121 */       vldExist(id, errors);
/*     */     }
/* 123 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, WebErrors errors) {
/* 127 */     if (errors.ifNull(id, "id")) {
/* 128 */       return true;
/*     */     }
/* 130 */     Ftp entity = this.manager.findById(id);
/*     */ 
/* 132 */     return errors.ifNotExist(entity, Ftp.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.FtpAct
 * JD-Core Version:    0.6.0
 */