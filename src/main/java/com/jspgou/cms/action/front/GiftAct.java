/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.manager.GiftExchangeMng;
/*     */ import com.jspgou.cms.manager.GiftMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.web.FrontUtils;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class GiftAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private GiftMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng addressMng;
/*     */ 
/*     */   @Autowired
/*     */   private GiftExchangeMng giftExchangeMng;
/*     */ 
/*     */   @RequestMapping(value={"/gift*.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String list(HttpServletRequest request, ModelMap model)
/*     */   {
/*  46 */     Website web = SiteUtils.getWeb(request);
/*  47 */     int pageNo = URLHelper.getPageNo(request);
/*  48 */     ShopFrontHelper.setCommonData(request, model, web, pageNo);
/*  49 */     ShopFrontHelper.setDynamicPageData(request, model, web, RequestUtils.getLocation(request), "gift", ".jspx", pageNo);
/*  50 */     return web.getTplSys("gift", MessageResolver.getMessage(request, "tpl.gift", new Object[0]), request);
/*     */   }
/*     */   @RequestMapping(value={"/present.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String present(Long id, HttpServletRequest request, ModelMap model) {
/*  55 */     Website web = SiteUtils.getWeb(request);
/*  56 */     if ((id != null) && (this.manager.findById(id) != null))
/*  57 */       model.addAttribute("gift", this.manager.findById(id));
/*     */     else {
/*  59 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*  61 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  62 */     return web.getTplSys("gift", MessageResolver.getMessage(request, "tpl.present", new Object[0]), request);
/*     */   }
/*     */   @RequestMapping({"/fetchGift.jspx"})
/*     */   public void fetchGift(Long giftId, Integer giftNumb, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
/*  67 */     Website web = SiteUtils.getWeb(request);
/*  68 */     ShopMember member = MemberThread.get();
/*  69 */     JSONObject json = new JSONObject();
/*  70 */     if (member == null) {
/*  71 */       json.put("status", 0);
/*     */     } else {
/*  73 */       Gift gift = this.manager.findById(giftId);
/*  74 */       if (giftNumb.intValue() > gift.getGiftStock().intValue()) {
/*  75 */         json.put("status", 2);
/*  76 */         json.put("error", "库存不足");
/*  77 */       }if (gift.getGiftScore().intValue() * Long.parseLong(giftNumb.toString()) > member.getScore().intValue()) {
/*  78 */         json.put("status", 2);
/*  79 */         json.put("error", "积分不足");
/*     */       } else {
/*  81 */         json.put("status", 1);
/*     */       }
/*     */     }
/*  84 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  85 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/exchange.jspx"})
/*     */   public String shippingInput(Long id, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  92 */     Website web = SiteUtils.getWeb(request);
/*  93 */     ShopMember member = MemberThread.get();
/*  94 */     if (member == null) {
/*  95 */       return "redirect:login.jspx";
/*     */     }
/*  97 */     WebErrors errors = validateGiftView(id, request);
/*  98 */     if (errors.hasErrors()) {
/*  99 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 101 */     Gift gift = this.manager.findById(id);
/* 102 */     if (count.intValue() > gift.getGiftStock().intValue()) {
/* 103 */       return FrontUtils.showMessage(request, model, "库存不足");
/*     */     }
/* 105 */     if (gift.getGiftScore().intValue() * Long.parseLong(count.toString()) > member.getScore().intValue()) {
/* 106 */       return FrontUtils.showMessage(request, model, "积分不足");
/*     */     }
/* 108 */     model.addAttribute("gift", gift);
/* 109 */     model.addAttribute("count", count);
/* 110 */     model.addAttribute("totalScore", Long.valueOf(gift.getGiftScore().intValue() * Long.parseLong(count.toString())));
/*     */ 
/* 112 */     List smalist = this.shopMemberAddressMng.getList(member.getId());
/* 113 */     model.addAttribute("smalist", smalist);
/*     */ 
/* 115 */     List plist = this.addressMng.getListById(null);
/* 116 */     model.addAttribute("plist", plist);
/* 117 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 118 */     return web.getTplSys("gift", MessageResolver.getMessage(request, 
/* 119 */       "tpl.exchange", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/create_exchange.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String createExchange(Long deliveryInfo, Long id, Integer count, HttpServletRequest request, ModelMap model) {
/* 125 */     Website web = SiteUtils.getWeb(request);
/* 126 */     ShopMember member = MemberThread.get();
/* 127 */     if (member == null) {
/* 128 */       return "redirect:login.jspx";
/*     */     }
/* 130 */     WebErrors errors = validateGiftView(id, request);
/* 131 */     if (errors.hasErrors()) {
/* 132 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 134 */     Gift gift = this.manager.findById(id);
/* 135 */     if (count.intValue() > gift.getGiftStock().intValue()) {
/* 136 */       return FrontUtils.showMessage(request, model, "库存不足");
/*     */     }
/* 138 */     if (gift.getGiftScore().intValue() * Long.parseLong(count.toString()) > member.getScore().intValue()) {
/* 139 */       return FrontUtils.showMessage(request, model, "积分不足");
/*     */     }
/* 141 */     ShopMemberAddress shopMemberAddress = this.shopMemberAddressMng.findById(deliveryInfo);
/* 142 */     this.giftExchangeMng.save(gift, shopMemberAddress, member, count);
/* 143 */     return FrontUtils.showMessage(request, model, "兑换成功");
/*     */   }
/*     */ 
/*     */   private WebErrors validateGiftView(Long id, HttpServletRequest request) {
/* 147 */     WebErrors errors = WebErrors.create(request);
/* 148 */     if (errors.ifNull(id, "id")) {
/* 149 */       return errors;
/*     */     }
/* 151 */     Gift gift = this.manager.findById(id);
/* 152 */     if (errors.ifNotExist(gift, Gift.class, id)) {
/* 153 */       return errors;
/*     */     }
/* 155 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.GiftAct
 * JD-Core Version:    0.6.0
 */