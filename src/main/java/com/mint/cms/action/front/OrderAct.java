package com.mint.cms.action.front;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.Order;
import com.mint.cms.entity.OrderItem;
import com.mint.cms.entity.OrderReturn;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductFashion;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopScore;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.OrderReturnMng;
import com.mint.cms.manager.PaymentMng;
import com.mint.cms.manager.PaymentPluginsMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShipmentsMng;
import com.mint.cms.manager.ShippingMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.manager.ShopScoreMng;
import com.mint.cms.service.ShoppingSvc;
import com.mint.cms.web.FrontUtils;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.page.Pagination;
import com.mint.common.security.annotation.Secured;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.core.web.front.FrontHelper;
import com.mint.core.web.front.URLHelper;

import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured
@Controller
public class OrderAct {
    private static final String MY_ORDER = "tpl.myOrder";
    private static final String MY_RETURN_ORDER = "tpl.myReturnOrder";
    private static final String MY_ORDER_VIEW = "tpl.myOrderView";
    private static final String SUCCESSFULLY_ORDER = "tpl.successfullyOrder";
    private static final String CHECKOUT_SHIPPING = "tpl.checkoutShipping";
    private static final String RETURN_ORDER_SHIP = "tpl.returnOrderShip";

    @Autowired
    private OrderMng manager;

    @Autowired
    private ShippingMng shippingMng;

    @Autowired
    private PaymentMng paymentMng;

    @Autowired
    private ShoppingSvc shoppingSvc;

    @Autowired
    private PaymentPluginsMng paymentPluginsMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private OrderReturnMng orderReturnMng;

    @Autowired
    private ShipmentsMng shipMentsMng;

    @RequestMapping({"/order/myorder*.jspx"})
    public String myOrder(String productName, Integer status, Integer shippingStatus, Integer paymentStatus, String code, String userName, Long paymentId, Long shippingId, String startTime, String endTime, Double startOrderTotal, Double endOrderTotal, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        if (StringUtils.isBlank(userName)) {
            userName = null;
        }
        if (StringUtils.isBlank(startTime)) {
            startTime = null;
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = null;
        }
        List shippingList = this.shippingMng.getList(web.getId(), true);
        List paymentList = this.paymentMng.getList(web.getId(), true);
        model.addAttribute("productName", productName);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("shippingList", shippingList);
        model.addAttribute("status", status);
        model.addAttribute("code", code);
        model.addAttribute("userName", userName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("paymentId", paymentId);
        model.addAttribute("shippingId", shippingId);
        model.addAttribute("startOrderTotal", startOrderTotal);
        model.addAttribute("endOrderTotal", endOrderTotal);
        model.addAttribute("shippingStatus", shippingStatus);
        model.addAttribute("paymentStatus", paymentStatus);
        Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "myorder", ".jspx", pageNo.intValue());
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myOrder", new Object[0]), request);
    }

    @RequestMapping({"/order/myOrderView.jspx"})
    public String myOrderView(Long orderId, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderView(orderId, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        Order order = this.manager.findById(orderId);
        model.addAttribute("order", order);
        List shipments = this.shipMentsMng.getlist(orderId);
        model.addAttribute("shipments", shipments);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myOrderView", new Object[0]), request);
    }

    @RequestMapping({"/order/order_shipping.jspx"})
    public String orderShipping(Long deliveryInfo, Long shippingMethodId, Long paymentMethodId, Integer[] cartCountId, Long[] cartProductId, Long[] cartItemId, String comments, String memberCouponId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        String message = null;
        boolean createOrder = true;
        if (cartCountId != null) {
            for (Integer cId : cartCountId) {
                for (Long pId : cartProductId) {
                    if (this.productMng.findById(pId) == null) {
                        createOrder = false;
                        ShopFrontHelper.setCommonData(request, model, web, 1);
                        message = FrontUtils.showMessage(request, model, "含有商品已被删除的情况,不能提交订单，跳转回首页再进行正确的操作");
                    } else {
                        Product product = this.productMng.findById(pId);

                        Integer status = Integer.valueOf(product.getStatus() != null ? product.getStatus().intValue() : 0);
                        Long productFashionId = null;
                        if (product.getProductFashion() != null) {
                            productFashionId = product.getProductFashion().getId();
                        }
                        if (productFashionId == null) {
                            if (cId.intValue() > product.getStockCount().intValue()) {
                                createOrder = false;
                                ShopFrontHelper.setCommonData(request, model, web, 1);
                                message = FrontUtils.showMessage(request, model, "商品库存不足，不能提交订单，跳转回首页再进行正确的操作");
                            } else {
                                if (status.intValue() == Product.ON_SALE_STATUS)
                                    continue;
                                createOrder = false;
                                ShopFrontHelper.setCommonData(request, model, web, 1);
                                message = FrontUtils.showMessage(request, model, "商品已经下架,不能提交订单，跳转回首页再进行正确的操作");
                            }
                        } else if (this.productFashionMng.findById(productFashionId) == null) {
                            createOrder = false;
                            ShopFrontHelper.setCommonData(request, model, web, 1);
                            message = FrontUtils.showMessage(request, model, "含有款式商品已被删除的情况,不能提交订单，跳转回首页再进行正确的操作");
                        } else {
                            ProductFashion productFashion = this.productFashionMng.findById(productFashionId);
                            if (cId.intValue() > productFashion.getStockCount().intValue()) {
                                createOrder = false;
                                ShopFrontHelper.setCommonData(request, model, web, 1);
                                message = FrontUtils.showMessage(request, model, "该款式库存不足,不能提交订单，跳转回首页再进行正确的操作");
                            } else {
                                if (status.intValue() == Product.ON_SALE_STATUS)
                                    continue;
                                createOrder = false;
                                ShopFrontHelper.setCommonData(request, model, web, 1);
                                message = FrontUtils.showMessage(request, model, "该款式商品已经下架,不能提交订单，跳转回首页再进行正确的操作");
                            }
                        }
                    }
                }
            }

            if (createOrder) {
                Order order = null;
                Cart cart = this.shoppingSvc.getCart(member.getId());
                if (cart != null) {
                    order = this.manager.createOrder(cart, cartItemId, shippingMethodId, deliveryInfo, paymentMethodId, comments, request.getRemoteAddr(), member, web.getId(), memberCouponId);
                }
                this.shoppingSvc.clearCookie(request, response);
                List list = this.paymentPluginsMng.getList();
                model.addAttribute("list", list);
                model.addAttribute("order", order);
                ShopFrontHelper.setCommonData(request, model, web, 1);
                message = web.getTplSys("member", MessageResolver.getMessage(request, "tpl.successfullyOrder", new Object[0]), request);
            }
        } else {
            return "redirect:../cart/checkout_shipping.jspx";
        }
        return message;
    }

    @RequestMapping({"/order/deleteOrder.jspx"})
    public void deleteOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONObject json = new JSONObject();
        if (orderId != null) {
            Order order = this.manager.findById(orderId);
            order.getItems().clear();
            this.manager.deleteById(orderId);
        }
        json.put("success", true);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/order/abolishOrder.jspx"})
    public void abolishOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONObject json = new JSONObject();
        ShopMember member = MemberThread.get();
        if (orderId != null) {
            Order order = this.manager.findById(orderId);
            order.setStatus(Integer.valueOf(3));
            Set<OrderItem> set = order.getItems();
            Product product;
            for (OrderItem item : set) {
                product = item.getProduct();
                if (item.getProductFash() != null) {
                    ProductFashion fashion = item.getProductFash();
                    fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
                    product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                    this.productFashionMng.update(fashion);
                } else {
                    product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                }
                this.productMng.updateByUpdater(product);
            }

            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
            for (ShopScore s : list) {
                this.shopScoreMng.deleteById(s.getId());
            }
            this.manager.updateByUpdater(order);
        }
        json.put("success", true);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/order/accomplishOrder.jspx"})
    public void accomplishOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONObject json = new JSONObject();
        ShopMember member = MemberThread.get();
        if (orderId != null) {
            Order order = this.manager.findById(orderId);
            order.setStatus(Integer.valueOf(4));

            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
            member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
            for (ShopScore s : list) {
                s.setStatus(true);
                this.shopScoreMng.update(s);
            }
            this.manager.updateliRun(orderId);
            this.manager.updateByUpdater(order);
        }
        json.put("success", true);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/order/order_payAgain.jspx"})
    public String payOrderAgain(Long orderId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderView(orderId, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        this.shoppingSvc.clearCookie(request, response);
        Order order = this.manager.findById(orderId);
        List list = this.paymentPluginsMng.getList();
        model.addAttribute("list", list);
        model.addAttribute("order", order);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.successfullyOrder", new Object[0]), request);
    }

    @RequestMapping({"/order/myReturnOrder*.jspx"})
    public String myReturnOrder(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }

        Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
        Pagination pagination = this.manager.getPageForOrderReturn(member.getId(), pageNo.intValue(), 10);
        model.addAttribute("pagination", pagination);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "myReturnOrder", ".jspx", pageNo.intValue());
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myReturnOrder", new Object[0]), request);
    }

    @RequestMapping({"/order/shipments.jspx"})
    public String shipments(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderReturnView(id, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        OrderReturn orderReturn = this.orderReturnMng.findById(id);
        if (orderReturn.getStatus().intValue() == 4) {
            errors.addError("请勿重复给卖家发货");
            return FrontHelper.showError(errors, web, model, request);
        }

        List shipments = this.shipMentsMng.getlist(orderReturn.getOrder().getId());
        model.addAttribute("shipments", shipments);
        model.addAttribute("orderReturn", orderReturn);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.returnOrderShip", new Object[0]), request);
    }

    @RequestMapping({"/order/shipmentsSend.jspx"})
    public void shipmentsSend(Long id, String shippingLogistics, String invoiceNo, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JSONException {
        ShopMember member = MemberThread.get();
        JSONObject json = new JSONObject();
        if (member == null) {
            json.put("status", 1);
        }
        OrderReturn bean = this.orderReturnMng.findById(id);
        if (StringUtils.isBlank(invoiceNo)) {
            json.put("status", 4);
        }
        bean.setInvoiceNo(invoiceNo);
        bean.setShippingLogistics(shippingLogistics);

        if ((bean.getReturnType().intValue() == 1) && (bean.getStatus().intValue() == 2)) {
            bean.setStatus(Integer.valueOf(4));
            this.orderReturnMng.update(bean);
            json.put("status", 0);
        } else if (bean.getStatus().intValue() == 4) {
            json.put("status", 3);
        }

        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/order/accomplish.jspx"})
    public String accomplish(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateOrderReturnView(id, member, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        OrderReturn entity = this.orderReturnMng.findById(id);
        entity.setStatus(Integer.valueOf(7));
        Set<OrderItem> set = entity.getOrder().getItems();
        Product product;
        for (OrderItem item : set) {
            product = item.getProduct();
            if (item.getProductFash() != null) {
                ProductFashion fashion = item.getProductFash();
                fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                this.productFashionMng.update(fashion);
            } else {
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
            }
            this.productMng.updateByUpdater(product);
        }

        member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - entity.getOrder().getScore().intValue()));
        this.shopMemberMng.update(member);
        List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(entity.getOrder().getCode().longValue()));
        for (ShopScore s : list) {
            this.shopScoreMng.deleteById(s.getId());
        }
        this.orderReturnMng.update(entity);
        return myReturnOrder(request, model);
    }

    private WebErrors validateOrderView(Long orderId, ShopMember member, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(orderId, "orderId")) {
            return errors;
        }
        Order order = this.manager.findById(orderId);
        if (errors.ifNotExist(order, Order.class, orderId)) {
            return errors;
        }
        if (!order.getMember().getId().equals(member.getId())) {
            errors.noPermission(Order.class, orderId);
            return errors;
        }
        return errors;
    }

    public String getHistoryProductIds(HttpServletRequest request) {
        String str = null;
        Cookie[] cookie = request.getCookies();
        int num = cookie.length;
        for (int i = 0; i < num; i++) {
            if (cookie[i].getName().equals("shop_record")) {
                str = cookie[i].getValue();
                break;
            }
        }
        return str;
    }

    private WebErrors validateOrderReturnView(Long orderReturnId, ShopMember member, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(orderReturnId, "orderReturnId")) {
            return errors;
        }
        OrderReturn orderReturn = this.orderReturnMng.findById(orderReturnId);
        if (errors.ifNotExist(orderReturn, OrderReturn.class, orderReturnId)) {
            return errors;
        }
        if (!orderReturn.getOrder().getMember().getId().equals(member.getId())) {
            errors.noPermission(OrderReturn.class, orderReturnId);
            return errors;
        }
        return errors;
    }
}

