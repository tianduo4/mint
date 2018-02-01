package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.dao.OrderDao;
import com.mint.cms.entity.Gathering;
import com.mint.cms.entity.Order;
import com.mint.cms.entity.OrderItem;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductFashion;
import com.mint.cms.entity.Shipments;
import com.mint.cms.entity.Shipping;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopScore;
import com.mint.cms.entity.ShopShipments;
import com.mint.cms.manager.OrderItemMng;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShipmentsMng;
import com.mint.cms.manager.ShippingMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.manager.ShopScoreMng;
import com.mint.cms.manager.ShopShipmentsMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private ShopShipmentsMng shopShipmentsMng;

    @Autowired
    private OrderItemMng orderItemMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ShippingMng shippingMng;

    @Autowired
    private ShipmentsMng shipmentMng;

    @RequestMapping({"/order/list"})
    public void orderList(Long code1, String userName, Integer status, Integer paymentStatus, Integer shippingStatus, Long paymentId, Long shoppingId, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        try {
            if (pageNo == null) {
                pageNo = Integer.valueOf(1);
            }
            if (pageSize == null) {
                pageSize = Integer.valueOf(10);
            }
            Website web = SiteUtils.getWeb(request);
            Pagination page = this.orderDao.getPage1(web.getId(), null, null,
                    userName, paymentId, shoppingId, startTime, endTime, null,
                    null, status, paymentStatus, shippingStatus, code1, pageNo.intValue(),
                    pageSize.intValue());

            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Order) list.get(i)).convertToJson());
                }
            }
            message = "\"success\"";
            body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/order/view"})
    public void view(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Order order = new Order();
                if (id.longValue() != 0L) {
                    order = this.orderMng.findById(id);
                }
                body = order.convertToJson().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/update"})
    public void update(Long id, String itemId, String itemCount, String itemPrice, Long shippingId, Double freight, String comments, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Order order = this.orderMng.findById(id);
                int score = 0;
                int weight = 0;
                double price = 0.0D;
                Long[] itemIds = null;
                Integer[] itemCounts = null;
                Double[] itemPrices = null;
                if (StringUtils.isNotBlank(itemId)) {
                    String[] str = itemId.split(",");
                    itemIds = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemIds[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }
                if (StringUtils.isNotBlank(itemCount)) {
                    String[] str1 = itemCount.split(",");
                    itemCounts = new Integer[str1.length];
                    for (int i = 0; i < str1.length; i++) {
                        itemCounts[i] = Integer.valueOf(Integer.parseInt(str1[i]));
                    }
                }
                if (StringUtils.isNotBlank(itemPrice)) {
                    String[] str2 = itemPrice.split(",");
                    itemPrices = new Double[str2.length];
                    for (int i = 0; i < str2.length; i++) {
                        itemPrices[i] = Double.valueOf(Double.parseDouble(str2[i]));
                    }
                }
                for (int i = 0; i < itemIds.length; i++) {
                    OrderItem[] orderItem = this.orderItemMng.findById(itemIds);
                    Product product = orderItem[i].getProduct();
                    product.setStockCount(
                            Integer.valueOf(product.getStockCount().intValue() +
                                    orderItem[i].getCount().intValue() - itemCounts[i].intValue()));
                    if (orderItem[i].getProductFash() != null) {
                        ProductFashion productFash = orderItem[i]
                                .getProductFash();
                        productFash.setStockCount(
                                Integer.valueOf(productFash.getStockCount().intValue() +
                                        orderItem[i].getCount().intValue() - itemCounts[i].intValue()));
                        this.productFashionMng.update(productFash);
                    }
                    orderItem[i].setCount(itemCounts[i]);
                    orderItem[i].setSalePrice(itemPrices[i]);

                    score = score + itemCounts[i].intValue() *
                            orderItem[i].getProduct().getScore().intValue();

                    weight = weight + orderItem[i].getProduct().getWeight().intValue() *
                            itemCounts[i].intValue();
                    if (orderItem[i].getProductFash() != null)
                        price += itemPrices[i].doubleValue() * itemCounts[i].intValue();
                    else {
                        price += itemPrices[i].doubleValue() * itemCounts[i].intValue();
                    }
                    this.orderItemMng.updateByUpdaters(orderItem);
                    this.productMng.updateByUpdater(product);
                }
                order.setScore(Integer.valueOf(score));
                order.setWeight(Double.valueOf(weight));
                order.setProductPrice(Double.valueOf(price));

                order.setFreight(freight);
                order.setTotal(Double.valueOf(freight.doubleValue() + price));
                if (comments != null) {
                    order.setComments(comments);
                }

                order.setShipping(this.shippingMng.findById(shippingId));
                order.setLastModified(new Timestamp(System.currentTimeMillis()));
                this.orderMng.updateByUpdater(order);
                log.info("update Order, id={}.", order.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/affirm"})
    public void affirm(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Order order = this.orderMng.findById(id);
                if (order.getStatus().intValue() == 1) {
                    order.setStatus(Integer.valueOf(2));
                    this.orderMng.updateByUpdater(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/accomplish"})
    public void accomplish(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Order order = this.orderMng.findById(id);
                if ((order.getShippingStatus().intValue() == 2) && (order.getStatus().intValue() == 2) &&
                        (order.getPaymentStatus().intValue() == 2)) {
                    order.setStatus(Integer.valueOf(4));

                    ShopMember member = order.getMember();
                    member.setFreezeScore(
                            Integer.valueOf(member.getFreezeScore().intValue() -
                                    order.getScore().intValue()));
                    member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
                    this.shopMemberMng.update(member);
                    List<ShopScore> list = this.shopScoreMng.getlist(
                            Long.toString(order.getCode().longValue()));
                    for (ShopScore s : list) {
                        s.setStatus(true);
                        this.shopScoreMng.update(s);
                    }
                    this.orderMng.updateByUpdater(order);
                    this.orderMng.updateliRun(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/order/shopShipments"})
    public void shopShipments(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        try {
            if (pageNo == null) {
                pageNo = Integer.valueOf(1);
            }
            if (pageSize == null) {
                pageSize = Integer.valueOf(10);
            }
            Pagination page = this.shopShipmentsMng.getPage(pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            JSONArray jsonArray = new JSONArray();
            int totalCount = page.getTotalCount();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((ShopShipments) list.get(i)).convertToJson());
                }
            }
            message = "\"success\"";
            body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.convertException(response, request, e);
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/save"})
    public void shopShipments_save(Long orderId, Long id, Shipments bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{orderId, id});
            if (!errors.hasErrors()) {
                Order order = this.orderMng.findById(orderId);
                bean.setShopAdmin(CmsThreadVariable.getAdminUser());
                bean.setIndent(order);
                bean.setIsPrint(Boolean.valueOf(false));
                if (id != null) {
                    ShopShipments shipment = this.shopShipmentsMng.findById(id);
                    bean.setShippingName(shipment.getName());
                    bean.setShippingMobile(shipment.getMobile());
                    bean.setShippingAddress(shipment.getAddress());
                }
                this.shipmentMng.save(bean);
                order.setShippingStatus(Integer.valueOf(2));
                this.orderMng.updateByUpdater(order);
                this.orderMng.updateSaleCount(orderId);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/pay"})
    public void orderPay(Long orderId, Gathering bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{orderId, bean.getBank(), bean.getAccounts(), Double.valueOf(bean.getMoney()), bean.getDrawee()});
            if (!errors.hasErrors()) {
                Order order = this.orderMng.findById(orderId);
                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                bean.setShopAdmin(admin);
                bean.setIndent(order);

                if ((order.getPayment().getType().byteValue() == 1) || (order.getPayment().getType().byteValue() == 2)) {
                    order.setPaymentStatus(Integer.valueOf(2));
                    this.orderMng.updateOrderPay(bean, order);
                }
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/order/shipmentList"})
    public void shipmentsList(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            List list = this.shippingMng.getList(web.getId(), true);
            JSONArray jsonArray = new JSONArray();
            if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Shipping) list.get(i)).converToJson());
                }
            }
            body = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                Long[] id = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.orderMng.updateByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/order/cancel"})
    public void orderClanel(Long orderId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{orderId});
            if (!errors.hasErrors()) {
                Order order = this.orderMng.findById(orderId);
                if ((order.getPayment() != null) && (((order.getPayment().getId().longValue() == 1L) && (order.getPaymentStatus().intValue() == 1)) || (
                        (order.getPayment().getId().longValue() != 1L) && (order.getPaymentStatus().intValue() == 1) && (order.getShippingStatus().intValue() == 1)))) {
                    this.orderMng.updateOrderCanel(order);
                }

            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

