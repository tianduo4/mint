package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderItem;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseOrder
        implements Serializable {
    public static String REF = "Order";
    public static String PROP_IP = "ip";
    public static String PROP_MEMBER = "member";
    public static String PROP_COMMENTS = "comments";
    public static String PROP_WEBSITE = "website";
    public static String PROP_RETURN_REASON = "returnReason";
    public static String PROP_FREIGHT = "freight";
    public static String PROP_CODE = "code";
    public static String PROP_PAYMENT = "payment";
    public static String PROP_PRODUCT_PRICE = "productPrice";
    public static String PROP_COUPON_PRICE = "couponPrice";
    public static String PROP_STATUS = "status";
    public static String PROP_SHIPPING_TIME = "shippingTime";
    public static String PROP_FINISHED_TIME = "finishedTime";
    public static String PROP_WEIGHT = "weight";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_ID = "id";
    public static String PROP_SHOP_DIRECTORY = "shopDirectory";
    public static String PROP_SHIPPING = "shipping";
    public static String PROP_PRODUCT_NAME = "productName";
    public static String PROP_LAST_MODIFIED = "lastModified";
    public static String PROP_SCORE = "score";
    public static String PROP_TOTAL = "total";

    private int hashCode = -2147483648;
    private Long id;
    private Long code;
    private String comments;
    private String ip;
    private Date createTime;
    private Date shippingTime;
    private Date finishedTime;
    private Date lastModified;
    private Double productPrice;
    private Double freight;
    private Double total;
    private Integer score;
    private Double weight;
    private Double couponPrice;
    private String productName;
    private Integer paymentStatus;
    private Integer shippingStatus;
    private Integer status;
    private String receiveName;
    private String receiveAddress;
    private String receiveZip;
    private String receivePhone;
    private String receiveMobile;
    private String tradeNo;
    private String paymentCode;
    private Boolean delStatus;
    private Website website;
    private ShopMember member;
    private Payment payment;
    private Shipping shipping;
    private OrderReturn returnOrder;
    private Set<OrderItem> items;

    public BaseOrder() {
        initialize();
    }

    public BaseOrder(Long id) {
        setId(id);
        initialize();
    }

    public BaseOrder(Long id, Website website, ShopMember member, Payment payment, Shipping shipping, Shipping shopDirectory, long code, String ip, Date createTime, Date lastModified, Double total, Integer score, Double weight) {
        setId(id);
        setWebsite(website);
        setMember(member);
        setPayment(payment);
        setShipping(shipping);

        setCode(Long.valueOf(code));
        setIp(ip);
        setCreateTime(createTime);
        setLastModified(lastModified);
        setTotal(total);
        setScore(score);
        setWeight(weight);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getShippingTime() {
        return this.shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Date getFinishedTime() {
        return this.finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Double getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getFreight() {
        return this.freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getCouponPrice() {
        return this.couponPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipping getShipping() {
        return this.shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Set<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Order)) return false;

        Order order = (Order) obj;
        if ((getId() == null) || (order.getId() == null)) return false;
        return getId().equals(order.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Integer getShippingStatus() {
        return this.shippingStatus;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setReturnOrder(OrderReturn returnOrder) {
        this.returnOrder = returnOrder;
    }

    public OrderReturn getReturnOrder() {
        return this.returnOrder;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveName() {
        return this.receiveName;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveZip(String receiveZip) {
        this.receiveZip = receiveZip;
    }

    public String getReceiveZip() {
        return this.receiveZip;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceivePhone() {
        return this.receivePhone;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceiveMobile() {
        return this.receiveMobile;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentCode() {
        return this.paymentCode;
    }

    public void setDelStatus(Boolean delStatus) {
        this.delStatus = delStatus;
    }

    public Boolean getDelStatus() {
        return this.delStatus = this.delStatus;
    }
}

