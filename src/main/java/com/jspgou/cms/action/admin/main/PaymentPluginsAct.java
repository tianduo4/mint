/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PaymentPluginsAct
/*     */ {
/*  27 */   private static final Logger log = LoggerFactory.getLogger(PaymentPluginsAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng manager;
/*     */ 
/*  32 */   @RequestMapping({"/plugins/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) { List<PaymentPlugins> list = this.manager.getList();
/*  33 */     Set codeSet = new HashSet();
/*  34 */     for (PaymentPlugins p : list) {
/*  35 */       codeSet.add(p.getCode());
/*     */     }
/*  37 */     model.addAttribute("list", list);
/*  38 */     model.addAttribute("codeSet", codeSet);
/*  39 */     return "plugins/list"; }
/*     */ 
/*     */   @RequestMapping({"/plugins/v_add.do"})
/*     */   public String add(String code, HttpServletRequest request, ModelMap model) {
/*  44 */     model.addAttribute("code", code);
/*  45 */     return "plugins/add_" + code;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plugins/v_edit.do"})
/*     */   public String edit(Long id, String code, HttpServletRequest request, ModelMap model) {
/*  51 */     WebErrors errors = validateEdit(id, request);
/*  52 */     if (errors.hasErrors()) {
/*  53 */       return errors.showErrorPage(model);
/*     */     }
/*  55 */     PaymentPlugins paymentPlugins = this.manager.findById(id);
/*  56 */     model.addAttribute("paymentPlugins", paymentPlugins);
/*  57 */     return "plugins/edit_" + code;
/*     */   }
/*     */   @RequestMapping({"/plugins/o_save.do"})
/*     */   public String save(PaymentPlugins bean, String platform, HttpServletRequest request, ModelMap model) {
/*  62 */     WebErrors errors = validateSave(bean, request);
/*  63 */     if (errors.hasErrors()) {
/*  64 */       return errors.showErrorPage(model);
/*     */     }
/*  66 */     bean.setPlatform(platform);
/*  67 */     bean = this.manager.save(bean);
/*  68 */     log.info("save Payment, id={}", bean.getId());
/*  69 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plugins/o_update.do"})
/*     */   public String update(PaymentPlugins bean, String platform, long[] shippingIds, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     bean.setPlatform(platform);
/*  80 */     bean = this.manager.update(bean);
/*  81 */     log.info("update Payment, id={}.", bean.getId());
/*  82 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plugins/o_delete.do"})
/*     */   public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
/*  88 */     WebErrors errors = validateDelete(ids, request);
/*  89 */     if (errors.hasErrors()) {
/*  90 */       return errors.showErrorPage(model);
/*     */     }
/*  92 */     PaymentPlugins[] beans = this.manager.deleteByIds(ids);
/*  93 */     for (PaymentPlugins bean : beans) {
/*  94 */       log.info("delete Payment, id={}", bean.getId());
/*     */     }
/*  96 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plugins/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 102 */     this.manager.updatePriority(wids, priority);
/* 103 */     model.addAttribute("message", "global.success");
/* 104 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(PaymentPlugins bean, HttpServletRequest request) {
/* 108 */     WebErrors errors = WebErrors.create(request);
/* 109 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request)
/*     */   {
/* 114 */     WebErrors errors = WebErrors.create(request);
/* 115 */     if (vldExist(id, errors)) {
/* 116 */       return errors;
/*     */     }
/* 118 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request)
/*     */   {
/* 123 */     WebErrors errors = WebErrors.create(request);
/* 124 */     if (vldExist(id, errors)) {
/* 125 */       return errors;
/*     */     }
/* 127 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 131 */     WebErrors errors = WebErrors.create(request);
/* 132 */     errors.ifEmpty(ids, "ids");
/* 133 */     for (Long id : ids) {
/* 134 */       vldExist(id, errors);
/*     */     }
/* 136 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 140 */     if (errors.ifNull(id, "id")) {
/* 141 */       return true;
/*     */     }
/* 143 */     PaymentPlugins entity = this.manager.findById(id);
/*     */ 
/* 145 */     return errors.ifNotExist(entity, Payment.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.PaymentPluginsAct
 * JD-Core Version:    0.6.0
 */