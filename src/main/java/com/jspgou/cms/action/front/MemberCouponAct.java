/*    */ package com.jspgou.cms.action.front;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.CouponMng;
/*    */ import com.jspgou.cms.manager.MemberCouponMng;
/*    */ import com.jspgou.cms.web.ShopFrontHelper;
/*    */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*    */ import com.jspgou.common.web.springmvc.MessageResolver;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.Cookie;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class MemberCouponAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private MemberCouponMng manage;
/*    */   private CouponMng couponMng;
/*    */ 
/*    */   @RequestMapping(value={"/myCoupon.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*    */   public String pay1(HttpServletRequest request, ModelMap model)
/*    */   {
/* 38 */     Website web = SiteUtils.getWeb(request);
/* 39 */     ShopMember member = MemberThread.get();
/* 40 */     if (member == null) {
/* 41 */       return "redirect:login.jspx";
/*    */     }
/* 43 */     List list = this.manage.getList(member.getId());
/* 44 */     model.addAttribute("couList", list);
/* 45 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/* 46 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 47 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myCoupon", new Object[0]), request);
/*    */   }
/*    */ 
/*    */   public String getHistoryProductIds(HttpServletRequest request) {
/* 51 */     String str = null;
/* 52 */     Cookie[] cookie = request.getCookies();
/* 53 */     int num = cookie.length;
/* 54 */     for (int i = 0; i < num; i++) {
/* 55 */       if (cookie[i].getName().equals("shop_record")) {
/* 56 */         str = cookie[i].getValue();
/* 57 */         break;
/*    */       }
/*    */     }
/* 60 */     return str;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.MemberCouponAct
 * JD-Core Version:    0.6.0
 */