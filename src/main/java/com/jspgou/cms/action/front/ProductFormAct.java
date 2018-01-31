/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.DiscussMng;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductFormAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng consultMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private DiscussMng discussMng;
/*     */ 
/*     */   @RequestMapping({"/searchDiscussPage*.jspx"})
/*     */   public String searchDiscussPage(Long productId, Integer pageNo, HttpServletResponse response, HttpServletRequest request, ModelMap model)
/*     */   {
/*  48 */     Website web = SiteUtils.getWeb(request);
/*  49 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/*  50 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*  52 */     Product bean = this.productMng.findById(productId);
/*  53 */     model.addAttribute("product", bean);
/*  54 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  55 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "searchDiscussPage", ".jspx", SimplePage.cpn(pageNo));
/*  56 */     return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.discussContentPage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/pingjia*.jspx"})
/*     */   public String pingjia(Long productId, Long orderId, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  67 */     Website web = SiteUtils.getWeb(request);
/*  68 */     ShopMember member = MemberThread.get();
/*  69 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/*  70 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*  72 */     Product bean = this.productMng.findById(productId);
/*  73 */     OrderItem orderItem = this.orderItemMng.findByMember(member.getId(), productId, orderId);
/*  74 */     model.addAttribute("orderItem", orderItem);
/*  75 */     model.addAttribute("product", bean);
/*  76 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  77 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "discussContentPage", ".jspx", SimplePage.cpn(pageNo));
/*  78 */     return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.assess", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/haveDiscuss*.jspx"})
/*     */   public String haveDiscuss(Long productId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  88 */     Website web = SiteUtils.getWeb(request);
/*  89 */     ShopMember member = MemberThread.get();
/*  90 */     if (member == null) {
/*  91 */       ResponseUtils.renderJson(response, "denru");
/*  92 */       return null;
/*     */     }
/*  94 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/*  95 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*  97 */     OrderItem bean = this.orderItemMng.findByMember(member.getId(), productId, null);
/*  98 */     if (bean != null) {
/*  99 */       ResponseUtils.renderJson(response, "success");
/* 100 */       return null;
/*     */     }
/* 102 */     ResponseUtils.renderJson(response, "false");
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consultProduct*.jspx"})
/*     */   public String consultProduct(Long productId, HttpServletResponse response, HttpServletRequest request, ModelMap model)
/*     */   {
/* 114 */     Website web = SiteUtils.getWeb(request);
/* 115 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/* 116 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*     */ 
/* 119 */     int pageNo = URLHelper.getPageNo(request);
/* 120 */     ShopFrontHelper.setCommonData(request, model, web, SimplePage.cpn(Integer.valueOf(pageNo)));
/* 121 */     Product bean = this.productMng.findById(productId);
/* 122 */     Pagination page = this.consultMng.getPage(productId, null, null, null, null, SimplePage.cpn(Integer.valueOf(pageNo)), 5, Boolean.valueOf(true));
/* 123 */     model.addAttribute("product", bean);
/* 124 */     model.addAttribute("pagination", page);
/* 125 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "consultProduct", ".jspx", SimplePage.cpn(Integer.valueOf(pageNo)));
/* 126 */     return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.consultProduct", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/bargain*.jspx"})
/*     */   public String list(Long productId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 134 */     Website web = SiteUtils.getWeb(request);
/* 135 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/* 136 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/* 138 */     int pageNo = URLHelper.getPageNo(request);
/* 139 */     Product bean = this.productMng.findById(productId);
/* 140 */     Pagination page = this.orderItemMng.getOrderItem(Integer.valueOf(SimplePage.cpn(Integer.valueOf(pageNo))), Integer.valueOf(4), productId);
/* 141 */     model.addAttribute("pagination", page);
/* 142 */     model.addAttribute("product", bean);
/* 143 */     ShopFrontHelper.setCommonData(request, model, web, pageNo);
/* 144 */     ShopFrontHelper.setDynamicPageData(request, model, web, RequestUtils.getLocation(request), "bargain", ".jspx", pageNo);
/* 145 */     return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.bargain", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/insertConsult.jspx"})
/*     */   public void insertConsult(Long productId, String content, HttpServletResponse response, HttpServletRequest request, ModelMap model)
/*     */   {
/* 178 */     Website web = SiteUtils.getWeb(request);
/* 179 */     ShopMember user = MemberThread.get();
/* 180 */     if (user != null) {
/* 181 */       if (StringUtils.isEmpty(content)) {
/* 182 */         ResponseUtils.renderJson(response, "empty");
/*     */       } else {
/* 184 */         Consult bean = this.consultMng.saveOrUpdate(productId, content, user.getId());
/* 185 */         if (bean == null)
/* 186 */           ResponseUtils.renderJson(response, "sameUsually");
/*     */         else
/* 188 */           ResponseUtils.renderJson(response, "success");
/*     */       }
/*     */     }
/*     */     else
/* 192 */       ResponseUtils.renderJson(response, "false");
/*     */   }
/*     */ 
/*     */   @RequestMapping({"showProblem.jspx"})
/*     */   public void showProblem(HttpServletResponse response, Long productId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 201 */     ShopMember user = MemberThread.get();
/* 202 */     if (user != null)
/* 203 */       ResponseUtils.renderJson(response, "success");
/*     */     else
/* 205 */       ResponseUtils.renderJson(response, "false");
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/insertDiscuss.jspx "})
/*     */   public String insertDiscuss(Long productId, String disCon, String discussType, HttpServletResponse response, HttpServletRequest request, ModelMap model)
/*     */   {
/* 215 */     Website web = SiteUtils.getWeb(request);
/* 216 */     ShopMember member = MemberThread.get();
/* 217 */     if (member == null) {
/* 218 */       ResponseUtils.renderJson(response, "false");
/* 219 */       return null;
/*     */     }
/* 221 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/* 222 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/* 224 */     Discuss bean = this.discussMng.saveOrUpdate(productId, disCon, member.getId(), discussType);
/* 225 */     if (bean == null) {
/* 226 */       ResponseUtils.renderJson(response, "sameUsually");
/* 227 */       return null;
/*     */     }
/* 229 */     ResponseUtils.renderJson(response, "success");
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyRecord.jspx"})
/*     */   public String historyRecord(Long productId, HttpServletResponse response, HttpServletRequest request, ModelMap model)
/*     */   {
/* 238 */     Website web = SiteUtils.getWeb(request);
/* 239 */     ShopMember member = MemberThread.get();
/* 240 */     if (member == null) {
/* 241 */       ResponseUtils.renderJson(response, "false");
/* 242 */       return null;
/*     */     }
/* 244 */     if ((productId == null) || (this.productMng.findById(productId) == null)) {
/* 245 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/* 247 */     String str = "";
/* 248 */     Cookie[] cookeis = request.getCookies();
/* 249 */     int num = cookeis.length;
/* 250 */     for (int i = 0; i < num; i++) {
/* 251 */       if (cookeis[i].getName().equals("shop_record")) {
/* 252 */         str = ',' + cookeis[i].getValue();
/* 253 */         break;
/*     */       }
/*     */     }
/* 256 */     str = productId + str;
/* 257 */     int n = 0; int m = 0;
/* 258 */     int j = str.length();
/* 259 */     for (int i = 0; i < j; i++) {
/* 260 */       if (str.charAt(i) == ',') {
/* 261 */         n++;
/*     */       }
/* 263 */       if (n == 6) {
/*     */         break;
/*     */       }
/* 266 */       m = i + 1;
/*     */     }
/* 268 */     Cookie cook = new Cookie("shop_record", str.substring(0, m));
/* 269 */     String path = request.getContextPath();
/* 270 */     cook.setPath(StringUtils.isBlank(path) ? "/" : path);
/* 271 */     response.addCookie(cook);
/* 272 */     return null;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.ProductFormAct
 * JD-Core Version:    0.6.0
 */