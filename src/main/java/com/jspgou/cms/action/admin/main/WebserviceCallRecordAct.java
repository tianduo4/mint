/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.manager.WebserviceCallRecordMng;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.page.SimplePage;
/*    */ import com.jspgou.common.web.CookieUtils;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class WebserviceCallRecordAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WebserviceCallRecordMng manager;
/*    */ 
/*    */   @RequestMapping({"/webserviceCallRecord/v_list.do"})
/*    */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model)
/*    */   {
/* 21 */     Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/* 22 */       CookieUtils.getPageSize(request));
/* 23 */     model.addAttribute("pagination", pagination);
/* 24 */     return "webserviceCallRecord/list";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/webserviceCallRecord/o_delete.do"})
/*    */   public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 30 */     this.manager.deleteByIds(ids);
/* 31 */     return list(pageNo, request, model);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.WebserviceCallRecordAct
 * JD-Core Version:    0.6.0
 */