/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.dao.OrderDao;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ShopMemberAct
/*     */ {
/*  46 */   private static final Logger log = LoggerFactory.getLogger(ShopMemberAct.class);
/*     */   public static final String MEMBER_CENTER = "tpl.memberCenter";
/*     */   public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
/*     */   public static final String MEMBER_PROFILE = "tpl.memberProfile";
/*     */   public static final String MEMBER_PASSWORD = "tpl.memberPassword";
/*     */ 
/*     */   @Autowired
/*     */   private OrderDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/index.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String index(HttpServletRequest request, ModelMap model)
/*     */   {
/*  74 */     Website web = SiteUtils.getWeb(request);
/*  75 */     ShopMember member = MemberThread.get();
/*     */ 
/*  77 */     if (member == null) {
/*  78 */       return "redirect:../login.jspx";
/*     */     }
/*  80 */     BigDecimal money = this.dao.getMemberMoneyByYear(member.getId());
/*  81 */     Integer[] orders = this.dao.getOrderByMember(member.getId());
/*  82 */     Integer[] products = this.productMng.getProductByTag(web.getId());
/*  83 */     model.addAttribute("products", products);
/*  84 */     model.addAttribute("orders", orders);
/*  85 */     model.addAttribute("money", money);
/*  86 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/*  87 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  88 */     return web.getTplSys("member", MessageResolver.getMessage(request, 
/*  89 */       "tpl.memberCenter", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/profile.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 102 */     Website web = SiteUtils.getWeb(request);
/* 103 */     ShopMember member = MemberThread.get();
/*     */ 
/* 105 */     if (member == null) {
/* 106 */       return "redirect:../login.jspx";
/*     */     }
/*     */ 
/* 109 */     List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));
/*     */ 
/* 111 */     List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));
/*     */ 
/* 113 */     List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));
/*     */ 
/* 115 */     List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));
/*     */ 
/* 117 */     List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));
/* 118 */     model.addAttribute("member", member);
/* 119 */     model.addAttribute("userDegreeList", userDegreeList);
/* 120 */     model.addAttribute("familyMembersList", familyMembersList);
/* 121 */     model.addAttribute("incomeDescList", incomeDescList);
/* 122 */     model.addAttribute("workSeniorityList", workSeniorityList);
/* 123 */     model.addAttribute("degreeList", degreeList);
/* 124 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 125 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberProfile", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/profile.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String profileSubmit(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 141 */     ShopMember member = MemberThread.get();
/*     */ 
/* 143 */     if (member == null) {
/* 144 */       return "redirect:../login.jspx";
/*     */     }
/* 146 */     bean = this.manager.update(bean, groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId, familyMembersId);
/* 147 */     log.info("ShopMember update infomation: {}", bean.getUsername());
/* 148 */     return index(request, response, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/pwd.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String passwordInput(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 162 */     Website web = SiteUtils.getWeb(request);
/* 163 */     ShopMember member = MemberThread.get();
/*     */ 
/* 165 */     if (member == null) {
/* 166 */       return "redirect:../login.jspx";
/*     */     }
/* 168 */     model.addAttribute("username", member.getUsername());
/* 169 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 170 */     model.addAttribute("email", MemberThread.get().getEmail());
/* 171 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/* 172 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberPassword", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/portrait.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String portrait(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 186 */     Website web = SiteUtils.getWeb(request);
/* 187 */     ShopMember member = MemberThread.get();
/*     */ 
/* 189 */     if (member == null) {
/* 190 */       return "redirect:../login.jspx";
/*     */     }
/* 192 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 193 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberPortrait", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/updateAvatar.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String updateAvatar(String picPaths, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 206 */     ShopMember member = MemberThread.get();
/*     */ 
/* 208 */     if (member == null) {
/* 209 */       return "redirect:../login.jspx";
/*     */     }
/* 211 */     member.setAvatar(picPaths);
/* 212 */     this.manager.update(member);
/*     */ 
/* 214 */     return "redirect: profile.jspx";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMember/pwd.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String passwordSubmit(String origPwd, String newPwd, String email, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException
/*     */   {
/* 239 */     Website web = SiteUtils.getWeb(request);
/* 240 */     ShopMember member = MemberThread.get();
/*     */ 
/* 242 */     if (member == null) {
/* 243 */       return "redirect:../login.jspx";
/*     */     }
/* 245 */     Long userId = member.getMember().getUser().getId();
/* 246 */     WebErrors errors = validatePassword(userId, email, newPwd, member.getEmail(), origPwd, request);
/* 247 */     if (errors.hasErrors()) {
/* 248 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 250 */     this.userMng.updateUser(userId, newPwd, email);
/* 251 */     log.info("ShopMember update password: {}", member.getUsername());
/* 252 */     return FrontHelper.showSuccess("global.success", nextUrl, web, model, request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopMember/checkPwd.jspx"})
/*     */   public void checkPwd(String origPwd, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 266 */     ShopMember member = MemberThread.get();
/* 267 */     Long userId = member.getMember().getUser().getId();
/* 268 */     boolean pass = this.userMng.isPasswordValid(userId, origPwd);
/* 269 */     ResponseUtils.renderJson(response, pass ? "true" : "false");
/*     */   }
/*     */ 
/*     */   public String getHistoryProductIds(HttpServletRequest request) {
/* 273 */     String str = null;
/* 274 */     Cookie[] cookie = request.getCookies();
/* 275 */     int num = cookie.length;
/* 276 */     for (int i = 0; i < num; i++) {
/* 277 */       if (cookie[i].getName().equals("shop_record")) {
/* 278 */         str = cookie[i].getValue();
/* 279 */         break;
/*     */       }
/*     */     }
/* 282 */     return str;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePassword(Long userId, String email, String newPwd, String origEmail, String origPwd, HttpServletRequest request)
/*     */   {
/* 288 */     WebErrors errors = WebErrors.create(request);
/* 289 */     if ((!StringUtils.isBlank(newPwd)) && 
/* 290 */       (errors.ifOutOfLength(newPwd, "password", 3, 32))) {
/* 291 */       return errors;
/*     */     }
/* 293 */     if (!this.userMng.isPasswordValid(userId, origPwd)) {
/* 294 */       errors.addErrorCode("error.passwordInvalid");
/*     */     }
/* 296 */     if (errors.ifNotEmail(email, "email", 100)) {
/* 297 */       return errors;
/*     */     }
/* 299 */     if ((!email.equals(origEmail)) && (this.userMng.emailExist(email))) {
/* 300 */       errors.addErrorCode("error.emailExist");
/* 301 */       return errors;
/*     */     }
/* 303 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.ShopMemberAct
 * JD-Core Version:    0.6.0
 */