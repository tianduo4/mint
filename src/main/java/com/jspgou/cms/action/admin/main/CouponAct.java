/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.entity.Coupon;
/*    */ import com.jspgou.cms.manager.CategoryMng;
/*    */ import com.jspgou.cms.manager.CouponMng;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class CouponAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private CouponMng manager;
/*    */ 
/*    */   @Autowired
/*    */   private CategoryMng categoryMng;
/*    */ 
/*    */   @RequestMapping({"/coupon/v_add.do"})
/*    */   public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*    */   {
/* 30 */     List parentList = this.categoryMng.getListForParent(SiteUtils.getWebId(request), null);
/* 31 */     model.addAttribute("parentList", parentList);
/* 32 */     return "coupon/add";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/coupon/o_save.do"})
/*    */   public String save(Coupon bean, Integer categoryId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 38 */     Website site = SiteUtils.getWeb(request);
/* 39 */     this.manager.save(bean, site, categoryId);
/* 40 */     return list(request, response, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/coupon/v_list.do"})
/*    */   public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 46 */     List cList = this.manager.getList();
/* 47 */     model.addAttribute("list", cList);
/* 48 */     return "coupon/list";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/coupon/v_edit.do"})
/*    */   public String edit(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 54 */     model.addAttribute("coupon", this.manager.findById(id));
/* 55 */     return "coupon/edit";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/coupon/o_update.do"})
/*    */   public String update(Coupon bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 61 */     this.manager.update(bean);
/* 62 */     return list(request, response, model);
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/coupon/o_delete.do"})
/*    */   public String delete(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 68 */     Website site = SiteUtils.getWeb(request);
/* 69 */     String url = site.getUploadUrl();
/* 70 */     this.manager.deleteByIds(ids, url);
/* 71 */     return list(request, response, model);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.CouponAct
 * JD-Core Version:    0.6.0
 */