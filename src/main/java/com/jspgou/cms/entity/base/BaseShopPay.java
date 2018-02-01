package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopPay;

import java.io.Serializable;

public abstract class BaseShopPay
        implements Serializable {
    public static String REF = "ShopPay";
    public static String PROP_BANK_NUM = "bankNum";
    public static String PROP_BANKID = "bankid";
    public static String PROP_ADDRESS = "address";
    public static String PROP_ID = "id";
    public static String PROP_BANKKEY = "bankkey";

    private int hashCode = -2147483648;
    private Integer id;
    private String address;
    private String bankNum;
    private String bankid;
    private String bankkey;

    public BaseShopPay() {
        initialize();
    }

    public BaseShopPay(Integer id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankNum() {
        return this.bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankid() {
        return this.bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankkey() {
        return this.bankkey;
    }

    public void setBankkey(String bankkey) {
        this.bankkey = bankkey;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopPay)) return false;

        ShopPay shopPay = (ShopPay) obj;
        if ((getId() == null) || (shopPay.getId() == null)) return false;
        return getId().equals(shopPay.getId());
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

