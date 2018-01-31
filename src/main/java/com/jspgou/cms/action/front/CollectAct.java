/*    */ package com.jspgou.cms.action.front;
/*    */ 
/*    */ import com.jspgou.cms.entity.Collect;
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.CollectMng;
/*    */ import com.jspgou.cms.web.ShopFrontHelper;
/*    */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import com.jspgou.common.web.springmvc.MessageResolver;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import com.jspgou.core.web.front.URLHelper;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.Cookie;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class CollectAct
/*    */ {
/*    */   private static final String MY_COLLECT = "tpl.mycollect";
/*    */ 
/*    */   @Autowired
/*    */   private CollectMng manager;
/*    */ 
/*    */   @RequestMapping({"/collect/add_to_collect.jspx"})
/*    */   public String addToCollect(Long productId, Long productFashId, Boolean isAdd, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*    */     throws JSONException
/*    */   {
/* 43 */     ShopMember member = MemberThread.get();
/* 44 */     if (member == null) {
/* 45 */       ResponseUtils.renderJson(response, new JSONObject().put("status", 0).toString());
/* 46 */       return null;
/*    */     }
/* 48 */     Collect collect = null;
/* 49 */     if (productFashId == null) {
/* 50 */       List clist = this.manager.findByProductId(productId);
/* 51 */       if (clist.size() > 0) {
/* 52 */         ResponseUtils.renderJson(response, new JSONObject().put("status", 2).toString());
/* 53 */         return null;
/*    */       }
/* 55 */       collect = this.manager.save(member, productId, null);
/*    */     } else {
/* 57 */       collect = this.manager.findByProductFashionId(productFashId);
/* 58 */       if (collect != null) {
/* 59 */         ResponseUtils.renderJson(response, new JSONObject().put("status", 2).toString());
/* 60 */         return null;
/*    */       }
/* 62 */       collect = this.manager.save(member, productId, productFashId);
/*    */     }
/* 64 */     ResponseUtils.renderJson(response, new JSONObject().put("status", 1).toString());
/* 65 */     return null;
/*    */   }
/*    */   @RequestMapping({"/collect/mycollect*.jspx"})
/*    */   public String myCollect(HttpServletRequest request, ModelMap model) {
/* 70 */     Website web = SiteUtils.getWeb(request);
/* 71 */     ShopMember member = MemberThread.get();
/* 72 */     if (member == null) {
/* 73 */       return "redirect:../login.jspx";
/*    */     }
/* 75 */     Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
/* 76 */     model.addAttribute("historyProductIds", "");
/* 77 */     Cookie[] cookie = request.getCookies();
/* 78 */     for (int i = 0; i < cookie.length; i++) {
/* 79 */       if (cookie[i].getName().equals("shop_record")) {
/* 80 */         String str = cookie[i].getValue();
/* 81 */         model.addAttribute("historyProductIds", str);
/* 82 */         break;
/*    */       }
/*    */     }
/* 85 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 86 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "mycollect", ".jspx", pageNo.intValue());
/* 87 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.mycollect", new Object[0]), request);
/*    */   }
/*    */   @RequestMapping({"/collect/delCollect.jspx"})
/*    */   public String delCollect(Integer[] collectIds, HttpServletResponse response, ModelMap model) {
/* 92 */     ShopMember member = MemberThread.get();
/* 93 */     if (member == null) {
/* 94 */       return "";
/*    */     }
/* 96 */     this.manager.deleteByIds(collectIds);
/* 97 */     ResponseUtils.renderJson(response, "删除成功!");
/* 98 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.CollectAct
 * JD-Core Version:    0.6.0
 */