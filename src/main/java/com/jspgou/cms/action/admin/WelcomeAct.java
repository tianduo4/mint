/*    */ package com.jspgou.cms.action.admin;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopAdmin;
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.OrderMng;
/*    */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*    */ import com.jspgou.cms.manager.ShopMemberMng;
/*    */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.page.SimplePage;
/*    */ import com.jspgou.common.web.CookieUtils;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class WelcomeAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private OrderMng manager;
/*    */ 
/*    */   @Autowired
/*    */   private ShopMemberMng shopMemberMng;
/*    */ 
/*    */   @Autowired
/*    */   private ReceiverMessageMng receiverMessageMng;
/*    */ 
/*    */   @RequestMapping({"/main.do"})
/*    */   public String main()
/*    */   {
/* 32 */     return "main";
/*    */   }
/*    */   @RequestMapping({"/left.do"})
/*    */   public String left() {
/* 37 */     return "left";
/*    */   }
/*    */   @RequestMapping({"/right.do"})
/*    */   public String right(HttpServletRequest request, ModelMap model) {
/* 42 */     List o = this.manager.getTotlaOrder();
/* 43 */     ShopAdmin admin = AdminThread.get();
/* 44 */     Long[] c = (Long[])o.get(0);
/* 45 */     Runtime runtime = Runtime.getRuntime();
/* 46 */     long freeMemoery = runtime.freeMemory();
/* 47 */     long totalMemory = runtime.totalMemory();
/* 48 */     long usedMemory = totalMemory - freeMemoery;
/* 49 */     long maxMemory = runtime.maxMemory();
/* 50 */     long useableMemory = maxMemory - totalMemory + freeMemoery;
/* 51 */     model.addAttribute("c", c);
/* 52 */     model.addAttribute("admin", admin);
/* 53 */     model.addAttribute("restart", Double.valueOf(4.6D));
/* 54 */     model.addAttribute("site", SiteUtils.getWeb(request));
/* 55 */     model.addAttribute("freeMemoery", Long.valueOf(freeMemoery));
/* 56 */     model.addAttribute("totalMemory", Long.valueOf(totalMemory));
/* 57 */     model.addAttribute("usedMemory", Long.valueOf(usedMemory));
/* 58 */     model.addAttribute("maxMemory", Long.valueOf(maxMemory));
/* 59 */     model.addAttribute("useableMemory", Long.valueOf(useableMemory));
/* 60 */     return "right";
/*    */   }
/*    */   @RequestMapping({"/top.do"})
/*    */   public String top(HttpServletRequest request, ModelMap model) {
/* 65 */     ShopAdmin admin = AdminThread.get();
/* 66 */     model.addAttribute("admin", admin);
/*    */ 
/* 69 */     ShopMember Receiver = this.shopMemberMng.findUsername(admin.getUsername());
/* 70 */     if (Receiver != null) {
/* 71 */       Pagination pagination = this.receiverMessageMng.getUnreadPage(Receiver.getId(), SimplePage.cpn(null), 
/* 72 */         CookieUtils.getPageSize(request));
/* 73 */       model.addAttribute("rcvMsgUnRead", Integer.valueOf(pagination.getTotalCount()));
/*    */     }
/*    */ 
/* 76 */     return "top";
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.WelcomeAct
 * JD-Core Version:    0.6.0
 */