/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.entity.Adspace;
/*    */ import com.jspgou.cms.manager.AdspaceMng;
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.core.web.WebErrors;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class AdspaceAct
/*    */ {
/* 27 */   private static final Logger log = LoggerFactory.getLogger(AdspaceAct.class);
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/*    */   @Autowired
/*    */   private AdspaceMng manager;
/*    */ 
/* 32 */   @RequestMapping({"/adspace/v_list.do"})
/*    */   public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) { List list = this.manager.getList();
/* 33 */     model.addAttribute("list", list);
/* 34 */     return "adspace/list"; }
/*    */ 
/*    */   @RequestMapping({"/adspace/v_add.do"})
/*    */   public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*    */   {
/* 40 */     return "adspace/add";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/adspace/v_edit.do"})
/*    */   public String edit(Integer pageNo, Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 46 */     Adspace bean = this.manager.findById(id);
/* 47 */     model.addAttribute("adspace", bean);
/* 48 */     return "adspace/edit";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/adspace/o_update.do"})
/*    */   public String update(Adspace bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 54 */     this.manager.updateByUpdater(bean);
/* 55 */     return list(request, response, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/adspace/o_save.do"})
/*    */   public String save(Integer pageNo, Adspace bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 61 */     this.manager.save(bean);
/* 62 */     return list(request, response, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/adspace/o_delete.do"})
/*    */   public String delete(Integer[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 68 */     WebErrors errors = validateDelete(ids, request);
/* 69 */     if (errors.hasErrors()) {
/* 70 */       return errors.showErrorPage(model);
/*    */     }
/*    */     try
/*    */     {
/* 74 */       Adspace[] beans = this.manager.deleteByIds(ids);
/* 75 */       for (Adspace bean : beans)
/* 76 */         log.info("delete Adspace. id={}", bean.getId());
/*    */     }
/*    */     catch (Exception e) {
/* 79 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.advertisement.to.contain.advertising.management.data"));
/* 80 */       return errors.showErrorPage(model);
/*    */     }
/*    */     Adspace[] beans;
/* 82 */     return list(request, response, model);
/*    */   }
/*    */ 
/*    */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 86 */     WebErrors errors = WebErrors.create(request);
/* 87 */     errors.ifEmpty(ids, "ids");
/* 88 */     for (Integer id : ids) {
/* 89 */       vldExist(id, errors);
/*    */     }
/* 91 */     return errors;
/*    */   }
/*    */ 
/*    */   private void vldExist(Integer id, WebErrors errors) {
/* 95 */     if (errors.hasErrors()) {
/* 96 */       return;
/*    */     }
/* 98 */     Adspace entity = this.manager.findById(id);
/* 99 */     errors.ifNotExist(entity, Adspace.class, id);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.AdspaceAct
 * JD-Core Version:    0.6.0
 */