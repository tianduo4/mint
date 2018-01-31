/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.DiscussMng;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.annotation.Secured;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Secured
/*     */ @Controller
/*     */ public class MyInfomationAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng consultMng;
/*     */ 
/*     */   @Autowired
/*     */   private DiscussMng discussMng;
/*     */ 
/*     */   @RequestMapping({"/my_discuss*.jspx"})
/*     */   public String getMyDiscuss(Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  40 */     Website web = SiteUtils.getWeb(request);
/*  41 */     ShopMember member = MemberThread.get();
/*  42 */     if (member == null) {
/*  43 */       return "redirect:../login.jspx";
/*     */     }
/*  45 */     Pagination pagination = this.discussMng.getPageByMember(member.getId(), SimplePage.cpn(pageNo), 10, true);
/*  46 */     model.addAttribute("pagination", pagination);
/*  47 */     model.addAttribute("member", member);
/*  48 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/*  49 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  50 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "my_discuss", ".jspx", SimplePage.cpn(pageNo));
/*  51 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.mydiscuss", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/my_cousult*.jspx"})
/*     */   public String getMyCousult(Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  60 */     Website web = SiteUtils.getWeb(request);
/*  61 */     ShopMember member = MemberThread.get();
/*  62 */     if (member == null) {
/*  63 */       return "redirect:../login.jspx";
/*     */     }
/*  65 */     Pagination pagination = this.consultMng.getPageByMember(member.getId(), SimplePage.cpn(pageNo), 1, true);
/*  66 */     model.addAttribute("pagination", pagination);
/*  67 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/*  68 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  69 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "my_cousult", ".jspx", SimplePage.cpn(pageNo));
/*  70 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myconsult", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/buyRecord*.jspx"})
/*     */   public String getBuyRecord(Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  79 */     Website web = SiteUtils.getWeb(request);
/*  80 */     ShopMember member = MemberThread.get();
/*  81 */     if (member == null) {
/*  82 */       return "redirect:../login.jspx";
/*     */     }
/*  84 */     Pagination pagination = this.orderItemMng.getPageByMember(Integer.valueOf(4), member.getId(), SimplePage.cpn(pageNo), 2);
/*  85 */     model.addAttribute("pagination", pagination);
/*  86 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/*  87 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  88 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "buyRecord", ".jspx", SimplePage.cpn(pageNo));
/*  89 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.buyRecord", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public String getHistoryProductIds(HttpServletRequest request) {
/*  93 */     String str = null;
/*  94 */     Cookie[] cookie = request.getCookies();
/*  95 */     int num = cookie.length;
/*  96 */     for (int i = 0; i < num; i++) {
/*  97 */       if (cookie[i].getName().equals("shop_record")) {
/*  98 */         str = cookie[i].getValue();
/*  99 */         break;
/*     */       }
/*     */     }
/* 102 */     return str;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.MyInfomationAct
 * JD-Core Version:    0.6.0
 */