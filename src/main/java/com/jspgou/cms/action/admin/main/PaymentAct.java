/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PaymentAct
/*     */ {
/*  33 */   private static final Logger log = LoggerFactory.getLogger(PaymentAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*  38 */   @RequestMapping({"/payment/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { List list = this.manager.getList(SiteUtils.getWebId(request), true);
/*  39 */     model.addAttribute("list", list);
/*  40 */     return "payment/list"; }
/*     */ 
/*     */   @RequestMapping({"/payment/v_add.do"})
/*     */   public String add(String code, HttpServletRequest request, ModelMap model) {
/*  45 */     List shippingList = this.shippingMng.getList(SiteUtils.getWebId(request), false);
/*  46 */     model.addAttribute("shippingList", shippingList);
/*  47 */     return "payment/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/payment/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  53 */     WebErrors errors = validateEdit(id, request);
/*  54 */     if (errors.hasErrors()) {
/*  55 */       return errors.showErrorPage(model);
/*     */     }
/*  57 */     Payment payment = this.manager.findById(id);
/*  58 */     List shippingList = this.shippingMng.getList(SiteUtils.getWebId(request), false);
/*  59 */     model.addAttribute("shippingIds", this.manager.findById(id).getShippingIds());
/*  60 */     model.addAttribute("shippingList", shippingList);
/*  61 */     model.addAttribute("payment", payment);
/*  62 */     return "payment/edit";
/*     */   }
/*     */   @RequestMapping({"/payment/o_save.do"})
/*     */   public String save(Payment bean, long[] shippingIds, HttpServletRequest request, ModelMap model) {
/*  67 */     WebErrors errors = validateSave(bean, request);
/*  68 */     if (errors.hasErrors()) {
/*  69 */       return errors.showErrorPage(model);
/*     */     }
/*  71 */     if (bean.getIsDefault().booleanValue()) {
/*  72 */       List list = this.manager.getList(Long.valueOf(1L), true);
/*  73 */       for (int i = 0; i < list.size(); i++) {
/*  74 */         ((Payment)list.get(i)).setIsDefault(Boolean.valueOf(false));
/*  75 */         this.manager.update((Payment)list.get(i));
/*     */       }
/*     */     }
/*  78 */     bean = this.manager.save(bean);
/*  79 */     this.manager.addShipping(bean, shippingIds);
/*  80 */     log.info("save Payment, id={}", bean.getId());
/*  81 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/payment/o_update.do"})
/*     */   public String update(Payment bean, Integer pageNo, long[] shippingIds, HttpServletRequest request, ModelMap model) {
/*  87 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  88 */     if (errors.hasErrors()) {
/*  89 */       return errors.showErrorPage(model);
/*     */     }
/*  91 */     if (bean.getIsDefault().booleanValue()) {
/*  92 */       List list = this.manager.getList(Long.valueOf(1L), true);
/*  93 */       for (int i = 0; i < list.size(); i++) {
/*  94 */         ((Payment)list.get(i)).setIsDefault(Boolean.valueOf(false));
/*  95 */         this.manager.update((Payment)list.get(i));
/*     */       }
/*     */     }
/*  98 */     bean = this.manager.update(bean);
/*  99 */     this.manager.updateShipping(bean, shippingIds);
/* 100 */     log.info("update Payment, id={}.", bean.getId());
/* 101 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/payment/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 107 */     WebErrors errors = validateDelete(ids, request);
/* 108 */     if (errors.hasErrors()) {
/* 109 */       return errors.showErrorPage(model);
/*     */     }
/* 111 */     Payment[] beans = this.manager.deleteByIds(ids);
/* 112 */     for (Payment bean : beans) {
/* 113 */       log.info("delete Payment, id={}", bean.getId());
/*     */     }
/* 115 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/payment/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 121 */     this.manager.updatePriority(wids, priority);
/* 122 */     model.addAttribute("message", "global.success");
/* 123 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Payment bean, HttpServletRequest request)
/*     */   {
/* 129 */     WebErrors errors = WebErrors.create(request);
/* 130 */     Website web = SiteUtils.getWeb(request);
/* 131 */     bean.setWebsite(web);
/* 132 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request)
/*     */   {
/* 137 */     WebErrors errors = WebErrors.create(request);
/* 138 */     Website web = SiteUtils.getWeb(request);
/* 139 */     if (vldExist(id, web.getId(), errors)) {
/* 140 */       return errors;
/*     */     }
/* 142 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request)
/*     */   {
/* 147 */     WebErrors errors = WebErrors.create(request);
/* 148 */     Website web = SiteUtils.getWeb(request);
/* 149 */     if (vldExist(id, web.getId(), errors)) {
/* 150 */       return errors;
/*     */     }
/* 152 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 156 */     WebErrors errors = WebErrors.create(request);
/* 157 */     Website web = SiteUtils.getWeb(request);
/* 158 */     errors.ifEmpty(ids, "ids");
/* 159 */     for (Long id : ids) {
/* 160 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 162 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 166 */     if (errors.ifNull(id, "id")) {
/* 167 */       return true;
/*     */     }
/* 169 */     Payment entity = this.manager.findById(id);
/* 170 */     if (errors.ifNotExist(entity, Payment.class, id)) {
/* 171 */       return true;
/*     */     }
/* 173 */     if (!entity.getWebsite().getId().equals(webId)) {
/* 174 */       errors.notInWeb(Payment.class, id);
/* 175 */       return true;
/*     */     }
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   private Map<String, String> getCfg(HttpServletRequest request) {
/* 181 */     Map map = new HashMap();
/* 182 */     Enumeration e = request.getParameterNames();
/* 183 */     String param = "cfg_";
/*     */ 
/* 185 */     while (e.hasMoreElements()) {
/* 186 */       String name = (String)e.nextElement();
/* 187 */       if (name.startsWith(param)) {
/* 188 */         map.put(name.substring(param.length()), request
/* 189 */           .getParameter(name));
/*     */       }
/*     */     }
/* 192 */     return map;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.PaymentAct
 * JD-Core Version:    0.6.0
 */