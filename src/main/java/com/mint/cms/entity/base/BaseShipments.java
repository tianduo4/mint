package com.mint.cms.entity.base;

import com.mint.cms.entity.Order;
import com.mint.cms.entity.Shipments;
import com.mint.cms.entity.ShopAdmin;

import java.io.Serializable;

public abstract class BaseShipments
        implements Serializable {
    public static String REF = "Shipments";
    public static String PROP_RECEIVING = "receiving";
    public static String PROP_MONEY = "money";
    public static String PROP_COMMENT = "comment";
    public static String PROP_WAYBILL = "waybill";
    public static String PROP_ID = "id";
    public static String PROP_SHOP_ADMIN = "shopAdmin";
    public static String PROP_INDENT = "indent";

    private int hashCode = -2147483648;
    private Long id;
    private String waybill;
    private double money;
    private String receiving;
    private String comment;
    private String shippingName;
    private String shippingMobile;
    private String shippingAddress;
    private Boolean isPrint;
    private Order indent;
    private ShopAdmin shopAdmin;

    public BaseShipments() {
        initialize();
    }

    public BaseShipments(Long id) {
        setId(id);
        initialize();
    }

    public BaseShipments(Long id, Order indent, ShopAdmin shopAdmin, String waybill, String receiving, String comment) {
        setId(id);
        setIndent(indent);
        setShopAdmin(shopAdmin);
        setWaybill(waybill);
        setReceiving(receiving);
        setComment(comment);
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

    public String getWaybill() {
        return this.waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getReceiving() {
        return this.receiving;
    }

    public void setReceiving(String receiving) {
        this.receiving = receiving;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getShippingName() {
        return this.shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingMobile() {
        return this.shippingMobile;
    }

    public void setShippingMobile(String shippingMobile) {
        this.shippingMobile = shippingMobile;
    }

    public String getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Boolean getIsPrint() {
        return this.isPrint;
    }

    public void setIsPrint(Boolean isPrint) {
        this.isPrint = isPrint;
    }

    public Order getIndent() {
        return this.indent;
    }

    public void setIndent(Order indent) {
        this.indent = indent;
    }

    public ShopAdmin getShopAdmin() {
        return this.shopAdmin;
    }

    public void setShopAdmin(ShopAdmin shopAdmin) {
        this.shopAdmin = shopAdmin;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Shipments)) return false;

        Shipments shipments = (Shipments) obj;
        if ((getId() == null) || (shipments.getId() == null)) return false;
        return getId().equals(shipments.getId());
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

