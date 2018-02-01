package com.mint.cms.action.front;

import com.mint.cms.entity.Order;
import com.mint.cms.entity.OrderReturn;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.OrderReturnMng;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.cms.web.FrontUtils;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.security.annotation.Secured;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.core.web.front.FrontHelper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured
@Controller
public class OrderReturnAct {
    private static final Logger log = LoggerFactory.getLogger(OrderReturnAct.class);
    private static final String NODELIVERY_ORDER_RETURN = "tpl.noDeliveryOrderReturn";
    private static final String DELIVERYED_ORDER_RETURN = "tpl.DeliveryedOrderReturn";
    private static final String NODELIVERY_RETURN_MONEY_SUCCESS = "tpl.NoDeliveryedReturnMoneySuccess";
    private static final String DELIVERY_RETURN_MONEY_SUCCESS = "tpl.DeliveryedReturnMoneySuccess";

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private OrderReturnMng manager;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @RequestMapping({"/orderReturn/orderReturn.jspx"})
    public String getOrderReturn(Long orderId, Boolean delivery, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderView(orderId, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        Order order = this.orderMng.findById(orderId);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        List ndList = null;
        List returnList = this.shopDictionaryMng.getListByType(Long.valueOf(9L));
        model.addAttribute("returnList", returnList);
        model.addAttribute("order", order);
        model.addAttribute("delivery", delivery);
        if (delivery.booleanValue()) {
            ndList = this.shopDictionaryMng.getListByType(Long.valueOf(8L));
            model.addAttribute("ndList", ndList);
            return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.DeliveryedOrderReturn", new Object[0]), request);
        }

        ndList = this.shopDictionaryMng.getListByType(Long.valueOf(6L));
        model.addAttribute("ndList", ndList);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.noDeliveryOrderReturn", new Object[0]), request);
    }

    @RequestMapping(value = {"/orderReturn/orderReturnRefer.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String getOrderReturnRefer(OrderReturn bean, Long orderId, Boolean delivery, Long reasonId, String[] picPaths, String[] picDescs, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderView(orderId, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        Order order = this.orderMng.findById(orderId);
        if (this.manager.findByOrderId(orderId) != null) {
            return FrontUtils.showMessage(request, model, "此订单不能重复提交到退款业务订单中");
        }

        OrderReturn orderReturn = this.manager.save(bean, order, reasonId, delivery, picPaths, picDescs);
        log.debug("orderReturn createTime is: {}", orderReturn.getCreateTime());

        order.setReturnOrder(orderReturn);
        this.orderMng.updateByUpdater(order);
        model.addAttribute("order", order);
        model.addAttribute("orderReturn", orderReturn);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        if (delivery.booleanValue()) {
            return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.DeliveryedReturnMoneySuccess", new Object[0]), request);
        }
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.NoDeliveryedReturnMoneySuccess", new Object[0]), request);
    }

    private WebErrors validateOrderView(Long orderId, ShopMember member, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(orderId, "orderId")) {
            return errors;
        }
        Order order = this.orderMng.findById(orderId);
        if (errors.ifNotExist(order, Order.class, orderId)) {
            return errors;
        }
        if (!order.getMember().getId().equals(member.getId())) {
            errors.noPermission(Order.class, orderId);
            return errors;
        }
        return errors;
    }
}

