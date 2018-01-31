/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.Shipments;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.ArrayList;
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
/*     */ public class ShipmentsAct
/*     */ {
/*  33 */   private static final Logger log = LoggerFactory.getLogger(ShipmentsAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng logisticsMng;
/*     */ 
/*  37 */   @RequestMapping({"/Shipments/v_list.do"})
/*     */   public String list(Boolean isPrint, Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(isPrint, SimplePage.cpn(pageNo), 
/*  38 */       CookieUtils.getPageSize(request));
/*  39 */     List logistics = this.logisticsMng.getAllList();
/*  40 */     model.addAttribute("logistics", logistics);
/*  41 */     model.addAttribute("pagination", pagination);
/*  42 */     return "Shipments/list"; }
/*     */ 
/*     */   @RequestMapping({"/Shipments/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  47 */     return "Shipments/add";
/*     */   }
/*     */   @RequestMapping({"/Shipments/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  52 */     WebErrors errors = validateEdit(id, request);
/*  53 */     if (errors.hasErrors()) {
/*  54 */       return errors.showErrorPage(model);
/*     */     }
/*  56 */     model.addAttribute("order", this.manager.findById(id).getIndent());
/*  57 */     model.addAttribute("shipments", this.manager.findById(id));
/*  58 */     return "Shipments/edit";
/*     */   }
/*     */   @RequestMapping({"/Shipments/o_save.do"})
/*     */   public String save(Shipments bean, HttpServletRequest request, ModelMap model) {
/*  63 */     WebErrors errors = validateSave(bean, request);
/*  64 */     if (errors.hasErrors()) {
/*  65 */       return errors.showErrorPage(model);
/*     */     }
/*  67 */     bean = this.manager.save(bean);
/*  68 */     log.info("save Shipments id={}", bean.getId());
/*  69 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Shipments/o_update.do"})
/*     */   public String update(Shipments bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  75 */     WebErrors errors = validateUpdate(bean.getId(), request);
/*  76 */     if (errors.hasErrors()) {
/*  77 */       return errors.showErrorPage(model);
/*     */     }
/*  79 */     bean = this.manager.update(bean);
/*  80 */     log.info("update Shipments id={}.", bean.getId());
/*  81 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Shipments/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  87 */     WebErrors errors = validateDelete(ids, request);
/*  88 */     if (errors.hasErrors()) {
/*  89 */       return errors.showErrorPage(model);
/*     */     }
/*  91 */     Shipments[] beans = this.manager.deleteByIds(ids);
/*  92 */     for (Shipments bean : beans) {
/*  93 */       log.info("delete Shipments id={}", bean.getId());
/*     */     }
/*  95 */     return list(null, pageNo, request, model);
/*     */   }
/*     */   @RequestMapping({"/Shipments/o_printOrder.do"})
/*     */   public String printOrder(Long[] ids, Long logisticsId, HttpServletRequest request, ModelMap model) {
/* 100 */     List shipments = new ArrayList();
/* 101 */     for (int i = 0; i < ids.length; i++) {
/* 102 */       shipments.add(this.manager.findById(ids[i]));
/*     */     }
/* 104 */     Logistics logistics = this.logisticsMng.findById(logisticsId);
/* 105 */     model.addAttribute("shipments", shipments);
/* 106 */     model.addAttribute("logistics", logistics);
/* 107 */     return "shipments/printOrder";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Shipments/o_isPrintTrue.do"})
/*     */   public String isPrintTrue(Long[] ids, Long source, Boolean isPrint, Integer pageNo, Integer shipmentsType, HttpServletRequest request, ModelMap model)
/*     */   {
/* 115 */     WebErrors errors = validateDelete(ids, request);
/* 116 */     if (errors.hasErrors()) {
/* 117 */       return errors.showErrorPage(model);
/*     */     }
/* 119 */     for (Long id : ids) {
/* 120 */       Shipments shipments = this.manager.findById(id);
/* 121 */       shipments.setIsPrint(Boolean.valueOf(true));
/* 122 */       this.manager.update(shipments);
/*     */     }
/* 124 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Shipments/o_isPrintFalse.do"})
/*     */   public String isPrintFalse(Long[] ids, Long source, Boolean isPrint, Integer pageNo, Integer shipmentsType, HttpServletRequest request, ModelMap model) {
/* 130 */     WebErrors errors = validateDelete(ids, request);
/* 131 */     if (errors.hasErrors()) {
/* 132 */       return errors.showErrorPage(model);
/*     */     }
/* 134 */     for (Long id : ids) {
/* 135 */       Shipments shipments = this.manager.findById(id);
/* 136 */       shipments.setIsPrint(Boolean.valueOf(false));
/* 137 */       this.manager.update(shipments);
/*     */     }
/* 139 */     return list(null, pageNo, request, model);
/*     */   }
/*     */   private WebErrors validateSave(Shipments bean, HttpServletRequest request) {
/* 142 */     WebErrors errors = WebErrors.create(request);
/* 143 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 147 */     WebErrors errors = WebErrors.create(request);
/* 148 */     Website web = SiteUtils.getWeb(request);
/* 149 */     if (vldExist(id, web.getId(), errors)) {
/* 150 */       return errors;
/*     */     }
/* 152 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 156 */     WebErrors errors = WebErrors.create(request);
/* 157 */     Website web = SiteUtils.getWeb(request);
/* 158 */     if (vldExist(id, web.getId(), errors)) {
/* 159 */       return errors;
/*     */     }
/* 161 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 165 */     WebErrors errors = WebErrors.create(request);
/* 166 */     Website web = SiteUtils.getWeb(request);
/* 167 */     errors.ifEmpty(ids, "ids");
/* 168 */     for (Long id : ids) {
/* 169 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 171 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 175 */     if (errors.ifNull(id, "id")) {
/* 176 */       return true;
/*     */     }
/* 178 */     Shipments entity = this.manager.findById(id);
/*     */ 
/* 180 */     return errors.ifNotExist(entity, Shipments.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShipmentsAct
 * JD-Core Version:    0.6.0
 */