/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
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
/*     */ public class ShippingAct
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(ShippingAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng logisticsMng;
/*     */ 
/*  34 */   @RequestMapping({"/shipping/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { List list = this.manager.getList(SiteUtils.getWebId(request), true);
/*  35 */     model.addAttribute("list", list);
/*  36 */     return "shipping/list"; }
/*     */ 
/*     */   @RequestMapping({"/shipping/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  41 */     List list = this.logisticsMng.getAllList();
/*  42 */     model.addAttribute("list", list);
/*  43 */     return "shipping/add";
/*     */   }
/*     */   @RequestMapping({"/shipping/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  48 */     WebErrors errors = validateEdit(id, request);
/*  49 */     if (errors.hasErrors()) {
/*  50 */       return errors.showErrorPage(model);
/*     */     }
/*  52 */     model.addAttribute("shipping", this.manager.findById(id));
/*  53 */     List list = this.logisticsMng.getAllList();
/*  54 */     model.addAttribute("list", list);
/*  55 */     return "shipping/edit";
/*     */   }
/*     */   @RequestMapping({"/shipping/o_save.do"})
/*     */   public String save(Shipping bean, Long logisticsId, HttpServletRequest request, ModelMap model) {
/*  60 */     WebErrors errors = validateSave(bean, request);
/*  61 */     if (errors.hasErrors()) {
/*  62 */       return errors.showErrorPage(model);
/*     */     }
/*  64 */     if (bean.getIsDefault().booleanValue()) {
/*  65 */       List list = this.manager.getList(Long.valueOf(1L), true);
/*  66 */       for (int i = 0; i < list.size(); i++) {
/*  67 */         ((Shipping)list.get(i)).setIsDefault(Boolean.valueOf(false));
/*  68 */         this.manager.update((Shipping)list.get(i));
/*     */       }
/*     */     }
/*  71 */     bean.setLogistics(this.logisticsMng.findById(logisticsId));
/*  72 */     bean = this.manager.save(bean);
/*  73 */     log.info("save Shipping. id={}", bean.getId());
/*  74 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipping/o_update.do"})
/*     */   public String update(Shipping bean, Long logisticsId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  80 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  81 */     if (errors.hasErrors()) {
/*  82 */       return errors.showErrorPage(model);
/*     */     }
/*  84 */     if (bean.getIsDefault().booleanValue()) {
/*  85 */       List list = this.manager.getList(Long.valueOf(1L), true);
/*  86 */       for (int i = 0; i < list.size(); i++) {
/*  87 */         ((Shipping)list.get(i)).setIsDefault(Boolean.valueOf(false));
/*  88 */         this.manager.update(bean);
/*     */       }
/*     */     }
/*  91 */     bean.setLogistics(this.logisticsMng.findById(logisticsId));
/*  92 */     bean = this.manager.update(bean);
/*  93 */     log.info("update Shipping. id={}.", bean.getId());
/*  94 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipping/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 100 */     WebErrors errors = validateDelete(ids, request);
/* 101 */     if (errors.hasErrors()) {
/* 102 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 106 */       Shipping[] beans = this.manager.deleteByIds(ids);
/* 107 */       for (Shipping bean : beans)
/* 108 */         log.info("delete Shipping. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 111 */       errors.addErrorString(this.productMng.getTipFile("delete.shipping"));
/* 112 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Shipping[] beans;
/* 114 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipping/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 120 */     WebErrors errors = validatePriority(wids, priority, request);
/* 121 */     if (errors.hasErrors()) {
/* 122 */       return errors.showErrorPage(model);
/*     */     }
/* 124 */     this.manager.updatePriority(wids, priority);
/* 125 */     model.addAttribute("message", "global.success");
/* 126 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Shipping bean, HttpServletRequest request)
/*     */   {
/* 132 */     WebErrors errors = WebErrors.create(request);
/* 133 */     bean.setWebsite(SiteUtils.getWeb(request));
/* 134 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 138 */     WebErrors errors = WebErrors.create(request);
/* 139 */     errors.ifNull(id, "id");
/* 140 */     vldExist(id, errors);
/* 141 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 145 */     WebErrors errors = WebErrors.create(request);
/* 146 */     errors.ifNull(id, "id");
/* 147 */     vldExist(id, errors);
/* 148 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 152 */     WebErrors errors = WebErrors.create(request);
/* 153 */     errors.ifEmpty(ids, "ids");
/* 154 */     for (Long id : ids) {
/* 155 */       vldExist(id, errors);
/*     */     }
/* 157 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 162 */     WebErrors errors = WebErrors.create(request);
/* 163 */     if (errors.ifEmpty(wids, "wids")) {
/* 164 */       return errors;
/*     */     }
/* 166 */     if (errors.ifEmpty(priority, "priority")) {
/* 167 */       return errors;
/*     */     }
/* 169 */     if (wids.length != priority.length) {
/* 170 */       errors.addErrorString("wids length not equals priority length");
/* 171 */       return errors;
/*     */     }
/* 173 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 174 */       vldExist(wids[i], errors);
/* 175 */       if (priority[i] == null) {
/* 176 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 179 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 183 */     Shipping entity = this.manager.findById(id);
/* 184 */     return errors.ifNotExist(entity, Shipping.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShippingAct
 * JD-Core Version:    0.6.0
 */