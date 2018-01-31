/*     */ package com.jspgou.cms.action.admin;
/*     */ 
/*     */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class FrameAct
/*     */ {
/*     */   @RequestMapping({"/frame/config_main.do"})
/*     */   public String configMain(ModelMap model)
/*     */   {
/*  15 */     return "frame/config_main";
/*     */   }
/*     */   @RequestMapping({"/frame/config_left.do"})
/*     */   public String configLeft(ModelMap model) {
/*  20 */     return "frame/config_left";
/*     */   }
/*     */   @RequestMapping({"/frame/config_right.do"})
/*     */   public String configRight(ModelMap model) {
/*  25 */     return "frame/config_right";
/*     */   }
/*     */   @RequestMapping({"/frame/category_main.do"})
/*     */   public String categoryMain(ModelMap model) {
/*  30 */     return "frame/category_main";
/*     */   }
/*     */   @RequestMapping({"/frame/channel_main.do"})
/*     */   public String channelMain(ModelMap model) {
/*  35 */     return "frame/channel_main";
/*     */   }
/*     */   @RequestMapping({"/frame/article_main.do"})
/*     */   public String articleMain(ModelMap model) {
/*  40 */     return "frame/article_main";
/*     */   }
/*     */   @RequestMapping({"/frame/product_main.do"})
/*     */   public String productMain(ModelMap model) {
/*  45 */     return "frame/product_main";
/*     */   }
/*     */   @RequestMapping({"/frame/template_main.do"})
/*     */   public String templateMain(ModelMap model) {
/*  50 */     return "frame/template_main";
/*     */   }
/*     */   @RequestMapping({"/frame/resource_main.do"})
/*     */   public String resourceMain(ModelMap model) {
/*  55 */     return "frame/resource_main";
/*     */   }
/*     */   @RequestMapping({"/frame/member_main.do"})
/*     */   public String memberMain(ModelMap model) {
/*  60 */     return "frame/member_main";
/*     */   }
/*     */   @RequestMapping({"/frame/member_left.do"})
/*     */   public String memberLeft(ModelMap model) {
/*  65 */     return "frame/member_left";
/*     */   }
/*     */   @RequestMapping({"/frame/member_right.do"})
/*     */   public String memberRight(ModelMap model) {
/*  70 */     return "frame/member_right";
/*     */   }
/*     */   @RequestMapping({"/frame/order_main.do"})
/*     */   public String orderMain(ModelMap model) {
/*  75 */     return "frame/order_main";
/*     */   }
/*     */   @RequestMapping({"/frame/order_left.do"})
/*     */   public String orderLeft(ModelMap model) {
/*  80 */     return "frame/order_left";
/*     */   }
/*     */   @RequestMapping({"/frame/order_right.do"})
/*     */   public String orderRight(ModelMap model) {
/*  85 */     return "frame/order_right";
/*     */   }
/*     */   @RequestMapping({"/frame/assistant_main.do"})
/*     */   public String pageMain(ModelMap model) {
/*  90 */     return "frame/assistant_main";
/*     */   }
/*     */   @RequestMapping({"/frame/assistant_left.do"})
/*     */   public String pageLeft(ModelMap model) {
/*  95 */     return "frame/assistant_left";
/*     */   }
/*     */   @RequestMapping({"/frame/assistant_right.do"})
/*     */   public String pageRight(ModelMap model) {
/* 100 */     return "frame/assistant_right";
/*     */   }
/*     */   @RequestMapping({"/frame/marketing_main.do"})
/*     */   public String marketingMain(ModelMap model) {
/* 105 */     return "frame/marketing_main";
/*     */   }
/*     */   @RequestMapping({"/frame/marketing_right.do"})
/*     */   public String marketingRight(ModelMap model) {
/* 110 */     return "frame/marketing_right";
/*     */   }
/*     */   @RequestMapping({"/frame/marketing_left.do"})
/*     */   public String marketingLeft(ModelMap model) {
/* 115 */     return "frame/marketing_left";
/*     */   }
/*     */   @RequestMapping({"/frame/expand_main.do"})
/*     */   public String expandMain(ModelMap model) {
/* 120 */     return "frame/expand_main";
/*     */   }
/* 126 */   @RequiresPermissions({"frame:expand_left"})
/*     */   @RequestMapping({"/frame/expand_left.do"})
/*     */   public String expandLeft(ModelMap model) { return "frame/expand_left"; }
/*     */ 
/*     */   @RequestMapping({"/frame/permisssion_main.do"})
/*     */   public String permissionMain(ModelMap model) {
/* 131 */     return "frame/permisssion_main";
/*     */   }
/* 135 */   @RequestMapping({"/frame/permisssion_left.do"})
/*     */   public String permissionLeft(ModelMap model) { return "frame/permisssion_left";
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.FrameAct
 * JD-Core Version:    0.6.0
 */