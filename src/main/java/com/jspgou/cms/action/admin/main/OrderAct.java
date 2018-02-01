package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.Gathering;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderItem;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopDictionary;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopScore;
import com.jspgou.cms.entity.ShopShipments;
import com.jspgou.cms.manager.GatheringMng;
import com.jspgou.cms.manager.OrderItemMng;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.OrderReturnMng;
import com.jspgou.cms.manager.PaymentMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ShippingMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.manager.ShopScoreMng;
import com.jspgou.cms.manager.ShopShipmentsMng;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderAct {
    private static final Logger log = LoggerFactory.getLogger(OrderAct.class);
    public static final String ALL = "all";
    public static final String PENDING = "pending";
    public static final String PROSESSING = "processing";
    public static final String DELIVERED = "delivered";
    public static final String COMPLETE = "complete";
    public static final String[] TYPES = {"all", "pending", "processing", "delivered",
            "complete"};
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

    @Autowired
    private ShippingMng shippingMng;

    @Autowired
    private PaymentMng paymentMng;

    @Autowired
    private OrderMng manager;

    @Autowired
    private OrderReturnMng orderReturnMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private OrderItemMng orderItemMng;

    @Autowired
    private GatheringMng gatheringMng;

    @Autowired
    private ShopShipmentsMng shopShipmentsMng;

    @RequestMapping({"/order/v_list.do"})
    public String list(Long code, Integer status, Integer paymentStatus, Integer shippingStatus, Long paymentId, Long shoppingId, Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String userName = RequestUtils.getQueryParam(request, "userName");
        userName = StringUtils.trim(userName);
        Pagination pagination = this.manager.getPage(web.getId(), null, null, userName,
                paymentId, shoppingId, startTime, endTime, null, null, status, paymentStatus, shippingStatus, code,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);

        List shippingList = this.shippingMng.getList(web.getId(), true);
        List paymentList = this.paymentMng.getList(web.getId(), true);
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("shippingList", shippingList);
        model.addAttribute("paymentId", paymentId);
        model.addAttribute("shoppingId", shoppingId);
        model.addAttribute("userName", userName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("status", status);
        model.addAttribute("paymentStatus", paymentStatus);
        model.addAttribute("shippingStatus", shippingStatus);
        return "order/list";
    }

    @RequestMapping({"/order/v_view.do"})
    public String view(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("order", this.manager.findById(id));
        return "order/view";
    }

    @RequestMapping({"/order/o_affirm.do"})
    public String affirm(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        if (order.getStatus().intValue() == 1) {
            order.setStatus(Integer.valueOf(2));
            this.manager.updateByUpdater(order);
        }
        model.addAttribute("order", order);
        return "order/view";
    }

    @RequestMapping({"/order/o_abolish.do"})
    public String abolish(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        if ((order.getStatus().intValue() != 4) && (order.getShippingStatus().intValue() != 2) && (order.getPaymentStatus().intValue() != 2)) {
            order.setStatus(Integer.valueOf(3));
            ProductFashion fashion;
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                if (item.getProductFash() != null) {
                    fashion = item.getProductFash();
                    fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
                    product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                    this.productFashionMng.update(fashion);
                } else {
                    product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                }
                this.productMng.updateByUpdater(product);
            }

            ShopMember member = order.getMember();
            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
            for (ShopScore s : list) {
                this.shopScoreMng.deleteById(s.getId());
            }
            this.manager.updateByUpdater(order);
        }
        model.addAttribute("order", order);
        return "order/view";
    }

    @RequestMapping({"/order/v_payment.do"})
    public String zhifu(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        model.addAttribute("order", order);
        return "order/payment";
    }

    @RequestMapping({"/order/v_shipments.do"})
    public String shipmentses(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        Pagination pagination = this.shopShipmentsMng.getPage(1, 10);
        model.addAttribute("allList", pagination.getList());
        List<ShopShipments> list = this.shopShipmentsMng.getList(Boolean.valueOf(true));
        if ((list != null) && (list.size() > 0)) {
            for (ShopShipments li : list) {
                model.addAttribute("mentId", li.getId());
            }
        }

        model.addAttribute("order", order);
        return "order/shipments";
    }

    @RequestMapping({"/order/o_payment.do"})
    public String payment(Gathering bean, Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        ShopAdmin admin = AdminThread.get();
        bean.setShopAdmin(admin);
        bean.setIndent(order);
        if ((order.getPaymentStatus().intValue() == 1) && (order.getPayment().getType().byteValue() == 2)) {
            this.gatheringMng.save(bean);
            order.setPaymentStatus(Integer.valueOf(2));
            this.manager.updateByUpdater(order);
        }
        model.addAttribute("order", order);
        return "order/view";
    }

    @RequestMapping({"/order/o_accomplish.do"})
    public String accomplish(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        if ((order.getShippingStatus().intValue() == 2) && (order.getStatus().intValue() == 2) && (order.getPaymentStatus().intValue() == 2)) {
            order.setStatus(Integer.valueOf(4));

            ShopMember member = order.getMember();
            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
            member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
            for (ShopScore s : list) {
                s.setStatus(true);
                this.shopScoreMng.update(s);
            }
            this.manager.updateByUpdater(order);
            this.manager.updateliRun(id);
        }
        model.addAttribute("order", order);
        return "order/view";
    }

    @RequestMapping({"/order/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        List shippingList = this.shippingMng.getList(web.getId(), true);
        List paymentList = this.paymentMng.getList(web.getId(), true);
        model.addAttribute("order", this.manager.findById(id));
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("shippingList", shippingList);
        model.addAttribute("orderReturn", this.orderReturnMng.findByOrderId(id));
        return "order/edit";
    }

    @RequestMapping({"/order/o_update.do"})
    public String update(Long id, String comments, Long shippingId, Long paymentId, Long[] itemId, Integer[] itemCount, Double[] itemPrice, Double totalPrice, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebErrors errors = validateUpdate(id, shippingId, itemId, itemCount, itemPrice, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order order = this.manager.findById(id);
        int score = 0;
        int weight = 0;
        double price = 0.0D;
        for (int i = 0; i < itemId.length; i++) {
            OrderItem orderItem = this.orderItemMng.findById(itemId[i]);
            Product product = orderItem.getProduct();
            product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + orderItem.getCount().intValue() - itemCount[i].intValue()));
            if (orderItem.getProductFash() != null) {
                ProductFashion productFash = orderItem.getProductFash();
                productFash.setStockCount(Integer.valueOf(productFash.getStockCount().intValue() + orderItem.getCount().intValue() - itemCount[i].intValue()));
                this.productFashionMng.update(productFash);
            }
            orderItem.setCount(itemCount[i]);
            orderItem.setSalePrice(itemPrice[i]);
            score += itemCount[i].intValue() * orderItem.getProduct().getScore().intValue();
            weight += orderItem.getProduct().getWeight().intValue() * itemCount[i].intValue();
            if (orderItem.getProductFash() != null)
                price += itemPrice[i].doubleValue() * itemCount[i].intValue();
            else {
                price += itemPrice[i].doubleValue() * itemCount[i].intValue();
            }
            this.orderItemMng.updateByUpdater(orderItem);
            this.productMng.updateByUpdater(product);
        }
        order.setScore(Integer.valueOf(score));
        order.setWeight(Double.valueOf(weight));
        order.setProductPrice(Double.valueOf(price));
        double freight = this.shippingMng.findById(shippingId).calPrice(Double.valueOf(weight)).doubleValue();
        order.setFreight(Double.valueOf(freight));
        order.setTotal(Double.valueOf(freight + price));
        order.setComments(comments);
        order.setShipping(this.shippingMng.findById(shippingId));
        order.setPayment(this.paymentMng.findById(paymentId));
        order.setLastModified(new Timestamp(System.currentTimeMillis()));
        this.manager.updateByUpdater(order);
        log.info("update Order, id={}.", order.getId());
        return list(null, null, null, null, null, null, null, null, pageNo, request, model);
    }

    @RequestMapping({"/order/o_returnMoney.do"})
    public void returnMoney(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        OrderReturn orderReturn = this.orderReturnMng.findByOrderId(id);
        if (orderReturn.getPayType().intValue() == 2) {
            Payment pay = this.paymentMng.findById(Long.valueOf(3L));
            PrintWriter out = null;
            try {
                String aliURL = alipayReturn(pay, web, orderReturn, request, model);
                response.setContentType("text/html;charset=UTF-8");
                out = response.getWriter();
                out.print(aliURL);
            } catch (Exception localException) {
            } finally {
                out.close();
            }
        }
    }

    private String alipayReturn(Payment pay, Website web, OrderReturn orderReturn, HttpServletRequest request, ModelMap model) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(date);

        String batch_no = date1.concat(String.valueOf(orderReturn.getId().longValue() * 100L));

        String refund_date = sdf1.format(date);

        String batch_num = String.valueOf(1);

        String detail_data = orderReturn.getOrder().getId().toString() + "^" + 1.0D + "^" + orderReturn.getShopDictionary().getName();

        String notify_url = "http://" + web.getDomain() + "/cart/aliReturn.jspx";

        Map sParaTemp = new HashMap();

        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        sParaTemp.put("notify_url", notify_url);

        String sHtmlText = null;
        try {
            sHtmlText = refund_fastpay_by_platform_pwd(sParaTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sHtmlText;
    }

    public static String refund_fastpay_by_platform_pwd(Map<String, String> sParaTemp)
            throws Exception {
        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("_input_charset", "UTF-8");
        String strButtonName = "退款";
        return buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", strButtonName);
    }

    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName) {
        Map sPara = buildRequestPara(sParaTemp);
        List keys = new ArrayList(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway +
                "_input_charset=" + "UTF-8" + "\" method=\"" + strMethod +
                "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        Map sPara = paraFilter(sParaTemp);

        String mysign = buildMysign(sPara);

        sPara.put("sign", mysign);
        sPara.put("sign_type", "MD5");

        return sPara;
    }

    public static String buildMysign(Map<String, String> sArray) {
        String prestr = createLinkString(sArray);
        prestr = prestr + (String) sArray.get("key");
        String mysign = md5(prestr);
        return mysign;
    }

    public static String md5(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if ((charset == null) || ("".equals(charset))) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
        }
        throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
    }

    public static String createLinkString(Map<String, String> params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1)
                prestr = prestr + key + "=" + value;
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map result = new HashMap();

        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = (String) sArray.get(key);
            if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) ||
                    (key.equalsIgnoreCase("sign_type"))) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    @RequestMapping({"/order/ajaxtotalDeliveryFee.do"})
    public void ajaxtotalDeliveryFee(Long deliveryMethod, Double weight, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JSONException {
        ShopMember member = MemberThread.get();
        JSONObject json = new JSONObject();
        if (member == null) {
            json.put("status", 0);
        }
        Shipping shipping = this.shippingMng.findById(deliveryMethod);

        Double freight = shipping.calPrice(weight);
        json.put("status", 1);
        json.put("freight", freight);

        ResponseUtils.renderJson(response, json.toString());
    }

    private Integer findItem(Long[] itemIds, Long itemId) {
        for (int i = 0; i < itemIds.length; i++) {
            if (itemIds[i].equals(itemId)) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }

    @RequestMapping({"/order/o_delete.do"})
    public String delete(Long[] ids, String type, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Order[] beans = this.manager.deleteByIds(ids);
        for (Order bean : beans) {
            log.info("delete Order, id={}", bean.getId());
        }
        return list(null, null, null, null, null, null, null, null, pageNo, request, model);
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Long id, Long shippingId, Long[] itemId, Integer[] itemCount, Double[] itemPrice, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        if ((itemId == null) || (itemCount == null) || (itemPrice == null) ||
                (itemId.length == 0) || (itemId.length != itemCount.length) ||
                (itemCount.length != itemPrice.length)) {
            errors.addErrorString("order item invalid!");
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, Long webId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        Order entity = this.manager.findById(id);
        if (errors.ifNotExist(entity, Order.class, id)) {
            return true;
        }
        if (!entity.getWebsite().getId().equals(webId)) {
            errors.notInWeb(Order.class, id);
            return true;
        }
        if (entity.getReturnOrder() != null) {
            errors.notInReturn(entity.getReturnOrder(), Order.class, id);
            return true;
        }
        return false;
    }
}

