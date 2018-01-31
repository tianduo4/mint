/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.entity.Poster;
/*    */ import com.jspgou.cms.manager.PosterMng;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class PosterAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PosterMng manager;
/*    */ 
/*    */   @RequestMapping({"/poster/v_list.do"})
/*    */   public String list(HttpServletRequest request, ModelMap model)
/*    */   {
/* 28 */     List listPoster = this.manager.getPage();
/* 29 */     model.addAttribute("listPoster", listPoster);
/* 30 */     return "poster/list";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/poster/o_updateCare.do"})
/*    */   public String o_updateCare(String val, Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 36 */     Poster bean = this.manager.findById(id);
/* 37 */     bean.setInterUrl(val);
/* 38 */     this.manager.update(bean);
/* 39 */     ResponseUtils.renderJson(response, "success");
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/poster/o_updateImageAddres.do"})
/*    */   public String updateImageAddres(String val, Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 46 */     Poster bean = this.manager.findById(id);
/* 47 */     bean.setPicUrl(val);
/* 48 */     this.manager.update(bean);
/* 49 */     ResponseUtils.renderJson(response, "success");
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/poster/o_update.do"})
/*    */   public String edit(String[] picUrl, String[] interUrl, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 56 */     if ((picUrl != null) && (picUrl.length > 0)) {
/* 57 */       for (int i = 0; i < picUrl.length; i++) {
/* 58 */         Poster p = new Poster();
/* 59 */         p.setTime(new Date());
/* 60 */         p.setPicUrl(picUrl[i]);
/* 61 */         p.setInterUrl(interUrl[i]);
/* 62 */         this.manager.saveOrUpdate(p);
/*    */       }
/*    */     }
/*    */ 
/* 66 */     return "redirect:v_list.do";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/poster/o_delPoster.do"})
/*    */   public String delete(Integer id, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 72 */     this.manager.deleteById(id);
/* 73 */     ResponseUtils.renderJson(response, "success");
/* 74 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.PosterAct
 * JD-Core Version:    0.6.0
 */