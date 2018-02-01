package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.OrderDao;
import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.CartItem;
import com.jspgou.cms.entity.Coupon;
import com.jspgou.cms.entity.Gathering;
import com.jspgou.cms.entity.MemberCoupon;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderItem;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.cms.entity.PopularityItem;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.cms.entity.ShopScore;
import com.jspgou.cms.entity.ShopScore.ScoreTypes;
import com.jspgou.cms.manager.CartItemMng;
import com.jspgou.cms.manager.CartMng;
import com.jspgou.cms.manager.GatheringMng;
import com.jspgou.cms.manager.MemberCouponMng;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.OrderReturnMng;
import com.jspgou.cms.manager.PaymentMng;
import com.jspgou.cms.manager.PopularityItemMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ShipmentsMng;
import com.jspgou.cms.manager.ShippingMng;
import com.jspgou.cms.manager.ShopMemberAddressMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.manager.ShopScoreMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.util.DateUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderMngImpl
        implements OrderMng {

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private CartMng cartMng;

    @Autowired
    private OrderDao dao;

    @Autowired
    private CartItemMng cartItemMng;

    @Autowired
    private GatheringMng gatheringMng;

    @Autowired
    private ShipmentsMng shipmentMng;

    @Autowired
    private OrderReturnMng orderReturnMng;

    @Autowired
    private MemberCouponMng memberCouponMng;

    @Autowired
    private PaymentMng paymentMng;

    @Autowired
    private ShopMemberAddressMng shopMemberAddressMng;

    @Autowired
    private ShippingMng shippingMng;

    @Autowired
    private PopularityItemMng popularityItemMng;

    public Pagination getPageForOrderReturn(Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForOrderReturn(memberId, pageNo, pageSize);
    }

    public Order createOrder(Cart cart, Long[] cartItemId, Long shippingMethodId, Long deliveryInfo, Long paymentMethodId, String comments, String ip, ShopMember member, Long webId, String memberCouponId) {
        Website web = this.websiteMng.findById(webId);
        Long mcId = null;
        if (!StringUtils.isBlank(memberCouponId)) {
            mcId = Long.valueOf(Long.parseLong(memberCouponId));
        }
        Payment pay = this.paymentMng.findById(paymentMethodId);

        Shipping shipping = this.shippingMng.findById(shippingMethodId);
        ShopMemberAddress address = this.shopMemberAddressMng.findById(deliveryInfo);

        Order order = new Order();
        order.setShipping(shipping);
        order.setWebsite(web);
        order.setMember(member);
        order.setPayment(pay);
        order.setIp(ip);
        order.setComments(comments);
        order.setStatus(Integer.valueOf(1));
        order.setShippingStatus(Integer.valueOf(1));
        order.setPaymentStatus(Integer.valueOf(1));
        order.setReceiveName(address.getUsername());
        order.setReceiveAddress(getAddress(address));
        order.setReceiveMobile(address.getTel());
        order.setReceivePhone(address.getMobile());
        order.setReceiveZip(address.getPostCode());
        order.setDelStatus(Boolean.valueOf(true));
        int score = 0;
        int weight = 0;
        double price = 0.0D;
        Double couponPrice = Double.valueOf(0.0D);
        Double popularityPrice = Double.valueOf(0.0D);
        if (cart != null) {
            List<PopularityItem> popularityItems = this.popularityItemMng.getlist(cart.getId(), null);
            for (PopularityItem popularityItem : popularityItems) {
                popularityPrice = Double.valueOf(popularityPrice.doubleValue() + popularityItem.getPopularityGroup().getPrivilege().doubleValue() * popularityItem.getCount().intValue());
            }
        }
        if (mcId != null) {
            MemberCoupon memberCoupon = this.memberCouponMng.findById(mcId);
            if ((memberCoupon != null) &&
                    (memberCoupon.getCoupon().getIsusing().booleanValue()) && (!memberCoupon.getIsuse().booleanValue())) {
                couponPrice = Double.valueOf(memberCoupon.getCoupon().getCouponPrice().doubleValue());
                memberCoupon.setIsuse(Boolean.valueOf(true));
                this.memberCouponMng.update(memberCoupon);
            }

        }

        List<CartItem> itemList = new ArrayList();
        for (Long cId : cartItemId) {
            itemList.add(this.cartItemMng.findById(cId));
        }
        for (CartItem item : itemList) {
            score += item.getProduct().getScore().intValue() * item.getCount().intValue();
            weight += item.getProduct().getWeight().intValue() * item.getCount().intValue();
            if (item.getProductFash() != null)
                price += item.getProductFash().getSalePrice().doubleValue() * item.getCount().intValue();
            else {
                price += item.getProduct().getSalePrice().doubleValue() * item.getCount().intValue();
            }
        }
        if (member.getFreezeScore() != null)
            member.setFreezeScore(Integer.valueOf(score + member.getFreezeScore().intValue()));
        else {
            member.setFreezeScore(Integer.valueOf(score + 0));
        }
        order.setScore(Integer.valueOf(score));
        order.setWeight(Double.valueOf(weight));
        order.setProductPrice(Double.valueOf(price));
        double freight = shipping.calPrice(Double.valueOf(weight)).doubleValue();
        order.setFreight(Double.valueOf(freight));
        order.setTotal(Double.valueOf(freight + price - couponPrice.doubleValue() - popularityPrice.doubleValue()));
        Long date = Long.valueOf(new Date().getTime() + member.getId().longValue());
        order.setCode(date);

        CartItem cartItem = null;
        OrderItem orderItem = null;
        String productName = "";
        for (int j = 0; j < itemList.size(); j++) {
            orderItem = new OrderItem();
            cartItem = (CartItem) itemList.get(j);
            orderItem.setOrdeR(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setWebsite(web);
            orderItem.setCount(cartItem.getCount());
            if (cartItem.getProductFash() != null) {
                orderItem.setProductFash(cartItem.getProductFash());
                orderItem.setSalePrice(cartItem.getProductFash().getSalePrice());
            } else {
                orderItem.setSalePrice(cartItem.getProduct().getSalePrice());
            }
            productName = productName + orderItem.getProduct().getName();
            order.addToItems(orderItem);
        }
        order.setProductName(productName);
        List<PopularityItem> popularityItemList = this.popularityItemMng.getlist(cart.getId(), null);
        for (PopularityItem popularityItem : popularityItemList) {
            this.popularityItemMng.deleteById(popularityItem.getId());
        }
        cart.getItems().removeAll(itemList);
        this.cartMng.update(cart);
        order = save(order);
        ShopScore shopScore = null;
        Product product = null;
        ProductFashion fashion = null;
        for (OrderItem item : order.getItems()) {
            product = item.getProduct();
            if (item.getProductFash() != null) {
                fashion = item.getProductFash();
                fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() - item.getCount().intValue()));
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - item.getCount().intValue()));
                this.productFashionMng.update(fashion);
            } else {
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - item.getCount().intValue()));
            }
            this.productMng.updateByUpdater(product);
            shopScore = new ShopScore();
            shopScore.setMember(member);
            shopScore.setName(cartItem.getProduct().getName());
            shopScore.setScoreTime(new Date());
            shopScore.setStatus(false);
            shopScore.setUseStatus(false);
            shopScore.setScoreType(Integer.valueOf(ShopScore.ScoreTypes.ORDER_SCORE.getValue()));
            shopScore.setScore(item.getProduct().getScore());
            shopScore.setCode(Long.toString(order.getCode().longValue()));
            this.shopScoreMng.save(shopScore);
        }
        return order;
    }

    public String getAddress(ShopMemberAddress address) {
        String str = "";
        if (address.getProvince() != null) {
            str = str + address.getProvince() + "-";
        }
        if (address.getCity() != null) {
            str = str + address.getCity() + "-";
        }
        if (address.getCountry() != null) {
            str = str + address.getCountry() + "-";
        }
        str = str + address.getDetailaddress();
        return str;
    }

    public Order updateByUpdater(Order bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public Pagination getOrderByReturn(Long memberId, Integer pageNo, Integer pageSize) {
        return this.dao.getOrderByReturn(memberId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(webId, memberId, productName, userName, paymentId, shippingId,
                startTime, endTime, startOrderTotal, endOrderTotal, status, paymentStatus, shippingStatus, code, pageNo, pageSize);
        return page;
    }

    public List<Order> getlist() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = new Date();
        Date endDate = null;
        Calendar date = Calendar.getInstance();
        date.setTime(newDate);
        date.set(5, date.get(5) - 1);
        try {
            endDate = sdf.parse(sdf.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List list = this.dao.getlist(endDate);
        return list;
    }

    public void abolishOrder() {
        List<Order> orderList = getlist();
        for (Order order : orderList) {
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
            updateByUpdater(order);
        }
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(webId, memberId, productName, userName, paymentId, shippingId,
                startTime, endTime, startOrderTotal, endOrderTotal, status, code, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Pagination getPageForMember(Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForMember(memberId, pageNo, pageSize);
    }

    public Pagination getPageForMember1(Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForMember1(memberId, pageNo, pageSize);
    }

    public Pagination getPageForMember2(Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForMember2(memberId, pageNo, pageSize);
    }

    public Pagination getPageForMember3(Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForMember3(memberId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        Order entity = this.dao.findById(id);
        return entity;
    }

    public Order findByCode(Long code) {
        Order entity = this.dao.findByCode(code);
        return entity;
    }

    public void updateSaleCount(Long id) {
        Order entity = this.dao.findById(id);
        for (OrderItem item : entity.getItems()) {
            Product product = item.getProduct();
            product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() + item.getCount().intValue()));
            this.productMng.update(product);
        }
    }

    public void updateliRun(Long id) {
        Order entity = this.dao.findById(id);
        for (OrderItem item : entity.getItems()) {
            Product product = item.getProduct();
            ProductFashion productFashion = item.getProductFash();
            if (productFashion != null)
                product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() + item.getCount().intValue() * (productFashion.getSalePrice().doubleValue() - productFashion.getCostPrice().doubleValue())));
            else {
                product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() + item.getCount().intValue() * (product.getSalePrice().doubleValue() - product.getCostPrice().doubleValue())));
            }

            this.productMng.update(product);
        }
    }

    public Order save(Order bean) {
        bean.init();
        this.dao.save(bean);
        return bean;
    }

    public List<Object> getTotlaOrder() {
        return this.dao.getTotlaOrder();
    }

    public BigDecimal getMemberMoneyByYear(Long memberId) {
        return this.dao.getMemberMoneyByYear(memberId);
    }

    public Order deleteById(Long id) {
        Order bean = this.dao.findById(id);
        this.gatheringMng.deleteByorderId(id);
        this.shipmentMng.deleteByorderId(id);
        if (bean.getReturnOrder() != null) {
            this.orderReturnMng.deleteById(bean.getReturnOrder().getId());
        }
        if (((bean.getShippingStatus().intValue() == 1) && (bean.getStatus().intValue() == 1)) || ((bean.getShippingStatus().intValue() == 1) && (bean.getStatus().intValue() == 2))) {
            Set<OrderItem> set = bean.getItems();
            ProductFashion fashion;
            for (OrderItem item : set) {
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

            ShopMember member = bean.getMember();
            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - bean.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(bean.getCode().longValue()));
            for (ShopScore s : list) {
                this.shopScoreMng.deleteById(s.getId());
            }

        }

        bean = this.dao.deleteById(id);
        return bean;
    }

    public Order[] deleteByIds(Long[] ids) {
        Order[] beans = new Order[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Order[] updateByIds(Long[] ids) {
        Order[] beans = new Order[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = updateById(ids[i]);
        }
        return beans;
    }

    public Order updateById(Long id) {
        return this.dao.updateById(id);
    }

    public List<Order> getCountByStatus(Date startTime, Date endTime, Integer status) {
        return this.dao.getCountByStatus(startTime, endTime, status);
    }

    public List<Order> getCountByStatus1(Date startTime, Date endTime, Integer status) {
        return this.dao.getCountByStatus1(startTime, endTime, status);
    }

    public List<Order> getStatisticByYear(Integer year, Integer status) {
        return this.dao.getStatisticByYear(year, status);
    }

    public List<Order> getStatisticByYear1(Integer year, Integer status) {
        return this.dao.getStatisticByYear1(year, status);
    }

    public List<Order> getOrderList(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int firstResult, int maxResults) {
        return this.dao.getOrderList(webId, memberId, productName, userName, paymentId, shippingId, startTime, endTime, startOrderTotal, endOrderTotal, status, code, firstResult, maxResults);
    }

    public List<Object[]> findListByIds(Long[] ids) {
        return this.dao.findListByIds(ids);
    }

    public BigDecimal getOrderSale(Date date, Long webId) {
        return this.dao.getOrderSale(date, webId);
    }

    public Long getOrderCount(Date date, Long webId) {
        return this.dao.getOrderCount(date, webId);
    }

    public Long getUnSendOrderCount(Long webId) {
        return this.dao.getUnSendOrderCount(webId);
    }

    public Long getUnPayOrderCount(Long webId) {
        return this.dao.getUnPayOrderCount(webId);
    }

    public Long getReturnCount(Long webId) {
        return this.dao.getReturnCount(webId);
    }

    public JSONObject getOrderSale(Long webId, String type, String monthStr, String yearStr)
            throws ParseException {
        List list = this.dao.getOrderSale(webId, type, monthStr, yearStr);

        int years = DateUtils.getCurrentYear();
        int months = DateUtils.getCurrentMonth();
        int days = DateUtils.getCurrentDay();

        if ((StringUtils.isNotEmpty(monthStr)) && (!(years + "-" + months).equals(monthStr))) {
            Date d = DateUtils.pasreToDate(monthStr, DateUtils.COMMON_FORMAT_MONTH);
            days = DateUtils.getDaysOfMonth(d);
            months = Integer.parseInt(DateUtils.formateDate(d, DateUtils.COMMON_FORMAT_MONTH_STR));
        }

        if ((StringUtils.isNotEmpty(yearStr)) && (years != Integer.parseInt(yearStr))) {
            years = Integer.parseInt(yearStr);
            months = 12;
        }

        JSONArray xJson = new JSONArray();
        JSONArray ySaleJson = new JSONArray();
        JSONArray yCountJson = new JSONArray();
        int firstLoop = 0;
        String suffix = "";
        if ("month".equals(type)) {
            firstLoop = days;
            suffix = months + "";
        } else if ("year".equals(type)) {
            firstLoop = months;
            suffix = years + "";
        }
        for (int i = 1; i <= firstLoop; i++) {
            String firstVal = StringUtils.leftPad(i + "", 2, '0');
            xJson.add(suffix + "-" + firstVal);
            String ySaleVal = "0";
            String yCountVal = "0";
            for (int j = 0; j < list.size(); j++) {
                Object[] obj = (Object[]) list.get(j);
                String seVal = StringUtils.leftPad(String.valueOf(obj[2]), 2, '0');
                if (firstVal.equals(seVal)) {
                    ySaleVal = String.valueOf(obj[0]);
                    yCountVal = String.valueOf(obj[1]);
                }
            }
            ySaleJson.add(Double.valueOf(Double.parseDouble(ySaleVal)));
            yCountJson.add(Double.valueOf(Double.parseDouble(yCountVal)));
        }
        JSONObject json = new JSONObject();
        json.put("xdata", xJson);
        json.put("ySaleData", ySaleJson);
        json.put("yCountData", yCountJson);
        return json;
    }

    public void updateOrderPay(Gathering gathering, Order bean) {
        this.gatheringMng.save(gathering);
        updateByUpdater(bean);
    }

    public void updateOrderCanel(Order order) {
        order.setStatus(Integer.valueOf(3));

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            if (item.getProductFash() != null) {
                ProductFashion fashion = item.getProductFash();
                fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                this.productFashionMng.update(fashion);
            } else {
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
            }
            this.productMng.updateByUpdater(product);

            ShopMember member = order.getMember();
            member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
            this.shopMemberMng.update(member);
            List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
            for (ShopScore s : list) {
                this.shopScoreMng.deleteById(s.getId());
            }
            updateByUpdater(order);
        }
    }
}

