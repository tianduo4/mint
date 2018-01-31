/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.entity.Webservice;
/*    */ import com.jspgou.cms.manager.WebserviceMng;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.page.SimplePage;
/*    */ import com.jspgou.common.web.CookieUtils;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class WebserviceAct
/*    */ {
/* 19 */   private static final Logger log = LoggerFactory.getLogger(WebserviceAct.class);
/*    */ 
/*    */   @Autowired
/*    */   private WebserviceMng manager;
/*    */ 
/* 23 */   @RequestMapping({"/webservice/v_list.do"})
/*    */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 24 */     model.addAttribute("pagination", pagination);
/* 25 */     return "webservice/list"; }
/*    */ 
/*    */   @RequestMapping({"/webservice/v_add.do"})
/*    */   public String add(ModelMap model) {
/* 30 */     return "webservice/add";
/*    */   }
/*    */   @RequestMapping({"/webservice/v_edit.do"})
/*    */   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
/* 35 */     model.addAttribute("Webservice", this.manager.findById(id));
/* 36 */     return "webservice/edit";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webservice/o_save.do"})
/*    */   public String save(Webservice bean, String[] paramName, String[] defaultValue, HttpServletRequest request, ModelMap model) {
/* 42 */     bean = this.manager.save(bean, paramName, defaultValue);
/* 43 */     log.info("save Webservice id={}", bean);
/* 44 */     return "redirect:v_list.do";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webservice/o_update.do"})
/*    */   public String update(Webservice bean, String[] paramName, String[] defaultValue, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 50 */     bean = this.manager.update(bean, paramName, defaultValue);
/* 51 */     log.info("update Webservice id={}.", bean.getId());
/* 52 */     return list(pageNo, request, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webservice/o_delete.do"})
/*    */   public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 58 */     this.manager.deleteByIds(ids);
/* 59 */     return list(pageNo, request, model);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.WebserviceAct
 * JD-Core Version:    0.6.0
 */