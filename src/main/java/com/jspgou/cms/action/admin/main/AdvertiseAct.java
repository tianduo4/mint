/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Advertise;
/*     */ import com.jspgou.cms.manager.AdspaceMng;
/*     */ import com.jspgou.cms.manager.AdvertiseMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.web.RequestUtils1;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public class AdvertiseAct
/*     */ {
/*  33 */   private static final Logger log = LoggerFactory.getLogger(AdvertiseAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private AdspaceMng adspaceMng;
/*     */ 
/*     */   @Autowired
/*     */   private AdvertiseMng manager;
/*     */ 
/*  38 */   @RequestMapping({"/advertise/v_list.do"})
/*     */   public String list(Integer queryAdspaceId, Boolean queryEnabled, Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(queryAdspaceId, 
/*  39 */       queryEnabled, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  40 */     model.addAttribute("pagination", pagination);
/*  41 */     model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
/*  42 */     if (queryAdspaceId != null) {
/*  43 */       model.addAttribute("queryAdspaceId", queryAdspaceId);
/*     */     }
/*  45 */     if (queryEnabled != null) {
/*  46 */       model.addAttribute("queryEnabled", queryEnabled);
/*     */     }
/*  48 */     return "advertise/list"; }
/*     */ 
/*     */   @RequestMapping({"/advertise/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  53 */     List adspaceList = this.adspaceMng.getList();
/*  54 */     model.addAttribute("adspaceList", adspaceList);
/*  55 */     return "advertise/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advertise/v_edit.do"})
/*     */   public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  61 */     Advertise advertise = this.manager.findById(id);
/*  62 */     model.addAttribute("advertise", advertise);
/*  63 */     model.addAttribute("attr", advertise.getAttr());
/*  64 */     model.addAttribute("adspaceList", this.adspaceMng.getList());
/*  65 */     model.addAttribute("pageNo", pageNo);
/*  66 */     return "advertise/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advertise/o_save.do"})
/*     */   public String save(Advertise bean, Integer adspaceId, HttpServletRequest request, ModelMap model) {
/*  72 */     Map attr = RequestUtils1.getRequestMap(request, "attr_");
/*     */ 
/*  74 */     Set toRemove = new HashSet();
/*  75 */     for (Map.Entry entry : attr.entrySet()) {
/*  76 */       if (StringUtils.isBlank((String)entry.getValue())) {
/*  77 */         toRemove.add((String)entry.getKey());
/*     */       }
/*     */     }
/*  80 */     for (String key : toRemove) {
/*  81 */       attr.remove(key);
/*     */     }
/*  83 */     bean = this.manager.save(bean, adspaceId, attr);
/*  84 */     log.info("save advertise id={}", bean.getId());
/*  85 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advertise/o_update.do"})
/*     */   public String update(Integer queryAdspaceId, Boolean queryEnabled, Advertise bean, Integer adspaceId, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  92 */     Map attr = RequestUtils1.getRequestMap(request, "attr_");
/*     */ 
/*  94 */     Set toRemove = new HashSet();
/*  95 */     for (Map.Entry entry : attr.entrySet()) {
/*  96 */       if (StringUtils.isBlank((String)entry.getValue())) {
/*  97 */         toRemove.add((String)entry.getKey());
/*     */       }
/*     */     }
/* 100 */     for (String key : toRemove) {
/* 101 */       attr.remove(key);
/*     */     }
/* 103 */     bean = this.manager.update(bean, adspaceId, attr);
/* 104 */     log.info("update advertise id={}.", bean.getId());
/* 105 */     return list(queryAdspaceId, queryEnabled, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advertise/o_delete.do"})
/*     */   public String delete(Integer[] ids, Integer queryAdspaceId, Boolean queryEnabled, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 112 */     WebErrors errors = validateDelete(ids, request);
/* 113 */     if (errors.hasErrors()) {
/* 114 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 118 */       Advertise[] beans = this.manager.deleteByIds(ids);
/* 119 */       for (Advertise bean : beans)
/* 120 */         log.info("delete advertise. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 123 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.data.to.change.the.advertising.management"));
/* 124 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Advertise[] beans;
/* 126 */     return list(queryAdspaceId, queryEnabled, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 130 */     WebErrors errors = WebErrors.create(request);
/* 131 */     errors.ifEmpty(ids, "ids");
/* 132 */     for (Integer id : ids) {
/* 133 */       vldExist(id, errors);
/*     */     }
/* 135 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Integer id, WebErrors errors) {
/* 139 */     if (errors.hasErrors()) {
/* 140 */       return;
/*     */     }
/* 142 */     Advertise entity = this.manager.findById(id);
/* 143 */     errors.ifNotExist(entity, Advertise.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.AdvertiseAct
 * JD-Core Version:    0.6.0
 */