/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.web.FrontUtils;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.security.annotation.Secured;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Secured
/*     */ @Controller
/*     */ public class OrderReturnAct
/*     */ {
/*  40 */   private static final Logger log = LoggerFactory.getLogger(OrderReturnAct.class);
/*     */   private static final String NODELIVERY_ORDER_RETURN = "tpl.noDeliveryOrderReturn";
/*     */   private static final String DELIVERYED_ORDER_RETURN = "tpl.DeliveryedOrderReturn";
/*     */   private static final String NODELIVERY_RETURN_MONEY_SUCCESS = "tpl.NoDeliveryedReturnMoneySuccess";
/*     */   private static final String DELIVERY_RETURN_MONEY_SUCCESS = "tpl.DeliveryedReturnMoneySuccess";
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @RequestMapping({"/orderReturn/orderReturn.jspx"})
/*     */   public String getOrderReturn(Long orderId, Boolean delivery, HttpServletRequest request, ModelMap model)
/*     */   {
/*  55 */     Website web = SiteUtils.getWeb(request);
/*  56 */     ShopMember member = MemberThread.get();
/*  57 */     if (member == null) {
/*  58 */       return "redirect:../login.jspx";
/*     */     }
/*  60 */     WebErrors errors = validateOrderView(orderId, member, request);
/*  61 */     if (errors.hasErrors()) {
/*  62 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*  64 */     Order order = this.orderMng.findById(orderId);
/*  65 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  66 */     List ndList = null;
/*  67 */     List returnList = this.shopDictionaryMng.getListByType(Long.valueOf(9L));
/*  68 */     model.addAttribute("returnList", returnList);
/*  69 */     model.addAttribute("order", order);
/*  70 */     model.addAttribute("delivery", delivery);
/*  71 */     if (delivery.booleanValue())
/*     */     {
/*  73 */       ndList = this.shopDictionaryMng.getListByType(Long.valueOf(8L));
/*  74 */       model.addAttribute("ndList", ndList);
/*  75 */       return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.DeliveryedOrderReturn", new Object[0]), request);
/*     */     }
/*     */ 
/*  78 */     ndList = this.shopDictionaryMng.getListByType(Long.valueOf(6L));
/*  79 */     model.addAttribute("ndList", ndList);
/*  80 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.noDeliveryOrderReturn", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/orderReturn/orderReturnRefer.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String getOrderReturnRefer(OrderReturn bean, Long orderId, Boolean delivery, Long reasonId, String[] picPaths, String[] picDescs, HttpServletRequest request, ModelMap model)
/*     */   {
/*  90 */     Website web = SiteUtils.getWeb(request);
/*  91 */     ShopMember member = MemberThread.get();
/*  92 */     if (member == null) {
/*  93 */       return "redirect:../login.jspx";
/*     */     }
/*  95 */     WebErrors errors = validateOrderView(orderId, member, request);
/*  96 */     if (errors.hasErrors()) {
/*  97 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*  99 */     Order order = this.orderMng.findById(orderId);
/* 100 */     if (this.manager.findByOrderId(orderId) != null) {
/* 101 */       return FrontUtils.showMessage(request, model, "此订单不能重复提交到退款业务订单中");
/*     */     }
/*     */ 
/* 104 */     OrderReturn orderReturn = this.manager.save(bean, order, reasonId, delivery, picPaths, picDescs);
/* 105 */     log.debug("orderReturn createTime is: {}", orderReturn.getCreateTime());
/*     */ 
/* 107 */     order.setReturnOrder(orderReturn);
/* 108 */     this.orderMng.updateByUpdater(order);
/* 109 */     model.addAttribute("order", order);
/* 110 */     model.addAttribute("orderReturn", orderReturn);
/* 111 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 112 */     if (delivery.booleanValue()) {
/* 113 */       return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.DeliveryedReturnMoneySuccess", new Object[0]), request);
/*     */     }
/* 115 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.NoDeliveryedReturnMoneySuccess", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   private WebErrors validateOrderView(Long orderId, ShopMember member, HttpServletRequest request)
/*     */   {
/* 121 */     WebErrors errors = WebErrors.create(request);
/* 122 */     if (errors.ifNull(orderId, "orderId")) {
/* 123 */       return errors;
/*     */     }
/* 125 */     Order order = this.orderMng.findById(orderId);
/* 126 */     if (errors.ifNotExist(order, Order.class, orderId)) {
/* 127 */       return errors;
/*     */     }
/* 129 */     if (!order.getMember().getId().equals(member.getId())) {
/* 130 */       errors.noPermission(Order.class, orderId);
/* 131 */       return errors;
/*     */     }
/* 133 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.OrderReturnAct
 * JD-Core Version:    0.6.0
 */