package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.OrderReturnPicture;
import com.jspgou.cms.entity.ShopDictionary;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class BaseOrderReturn
        implements Serializable {
    public static String REF = "OrderReturn";
    public static String PROP_SELLER_MONEY = "sellerMoney";
    public static String PROP_MONEY = "money";
    public static String PROP_SHOP_DICTIONARY = "shopDictionary";
    public static String PROP_ORDER = "order";
    public static String PROP_ALIPAY_ID = "alipayId";
    public static String PROP_RETURN_TYPE = "returnType";
    public static String PROP_CODE = "code";
    public static String PROP_STATUS = "status";
    public static String PROP_PAY_TYPE = "PayType";
    public static String PROP_FINISHED_TIME = "finishedTime";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_ID = "id";
    public static String PROP_REASON = "reason";

    private int hashCode = -2147483648;
    private Long id;
    private String code;
    private String reason;
    private String alipayId;
    private Integer status;
    private Double money;
    private Double sellerMoney;
    private Integer returnType;
    private Date createTime;
    private Date finishedTime;
    private Integer payType;
    private String invoiceNo;
    private String shippingLogistics;
    private Order order;
    private ShopDictionary shopDictionary;
    private List<OrderReturnPicture> pictures;

    public BaseOrderReturn() {
        initialize();
    }

    public BaseOrderReturn(Long id) {
        setId(id);
        initialize();
    }

    public BaseOrderReturn(Long id, Order order, ShopDictionary shopDictionary, Integer payType, Integer status, Double money, Integer returnType, Date createTime) {
        setId(id);
        setOrder(order);
        setShopDictionary(shopDictionary);
        setPayType(payType);
        setStatus(status);
        setMoney(money);
        setReturnType(returnType);
        setCreateTime(createTime);
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAlipayId() {
        return this.alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getSellerMoney() {
        return this.sellerMoney;
    }

    public void setSellerMoney(Double sellerMoney) {
        this.sellerMoney = sellerMoney;
    }

    public Integer getReturnType() {
        return this.returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishedTime() {
        return this.finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ShopDictionary getShopDictionary() {
        return this.shopDictionary;
    }

    public void setShopDictionary(ShopDictionary shopDictionary) {
        this.shopDictionary = shopDictionary;
    }

    public List<OrderReturnPicture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<OrderReturnPicture> pictures) {
        this.pictures = pictures;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    public void setShippingLogistics(String shippingLogistics) {
        this.shippingLogistics = shippingLogistics;
    }

    public String getShippingLogistics() {
        return this.shippingLogistics;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof OrderReturn)) return false;

        OrderReturn orderReturn = (OrderReturn) obj;
        if ((getId() == null) || (orderReturn.getId() == null)) return false;
        return getId().equals(orderReturn.getId());
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
}

