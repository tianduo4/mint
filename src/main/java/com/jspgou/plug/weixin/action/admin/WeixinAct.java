/*    */ package com.jspgou.plug.weixin.action.admin;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import com.jspgou.core.web.WebErrors;
/*    */ import com.jspgou.plug.weixin.entity.Weixin;
/*    */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class WeixinAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WeixinMng manager;
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng siteMng;
/*    */ 
/*    */   @RequiresPermissions({"weixin:v_edit"})
/*    */   public String add(HttpServletRequest request, ModelMap model)
/*    */   {
/* 23 */     return "weixin/add";
/*    */   }
/*    */   @RequiresPermissions({"weixin:v_edit"})
/*    */   @RequestMapping({"/weixin/v_edit.do"})
/*    */   public String edit(HttpServletRequest request, ModelMap model) {
/* 30 */     Weixin entity = this.manager.find(SiteUtils.getWebId(request));
/* 31 */     if (entity != null) {
/* 32 */       model.addAttribute("site", SiteUtils.getWeb(request));
/* 33 */       model.addAttribute("weixin", entity);
/* 34 */       return "weixin/edit";
/*    */     }
/* 36 */     return add(request, model);
/*    */   }
/*    */   @RequiresPermissions({"weixin:o_update"})
/*    */   @RequestMapping({"/weixin/o_save.do"})
/*    */   public String save(Weixin bean, String appKey, String appSecret, HttpServletRequest request, ModelMap model) {
/* 43 */     Website site = SiteUtils.getWeb(request);
/* 44 */     bean.setSite(site);
/* 45 */     bean.setAppKey(appKey);
/* 46 */     bean.setAppSecret(appSecret);
/* 47 */     this.manager.save(bean);
/* 48 */     return edit(request, model);
/*    */   }
/*    */ 
/*    */   @RequiresPermissions({"weixin:o_update"})
/*    */   @RequestMapping({"/weixin/o_update.do"})
/*    */   public String update(Weixin bean, String appKey, String appSecret, HttpServletRequest request, ModelMap model)
/*    */   {
/* 58 */     Website site = SiteUtils.getWeb(request);
/* 59 */     bean.setAppKey(appKey);
/* 60 */     bean.setAppSecret(appSecret);
/* 61 */     this.manager.update(bean);
/* 62 */     return edit(request, model);
/*    */   }
/*    */ 
/*    */   private WebErrors validateCheck(Integer[] ids, HttpServletRequest request)
/*    */   {
/* 67 */     WebErrors errors = WebErrors.create(request);
/*    */ 
/* 69 */     Website site = SiteUtils.getWeb(request);
/* 70 */     errors.ifEmpty(ids, "ids");
/*    */ 
/* 74 */     return errors;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.action.admin.WeixinAct
 * JD-Core Version:    0.6.0
 */