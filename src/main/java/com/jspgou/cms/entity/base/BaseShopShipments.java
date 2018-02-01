package com.jspgou.cms.entity.base;

import java.io.Serializable;

public abstract class BaseShopShipments
        implements Serializable {
    public static String REF = "ShopShipments";
    public static String PROP_ID = "id";
    public static String PROP_NAME = "name";
    public static String PROP_MOBILE = "mobile";
    public static String PROP_ADDRESS = "address";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String mobile;
    private String address;
    private Boolean isDefault;

    public BaseShopShipments() {
        initialize();
    }

    public BaseShopShipments(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopShipments(Long id, String name, String mobile, String address, Boolean isDefault) {
        setId(id);
        setName(name);
        setMobile(mobile);
        setAddress(address);
        setIsDefault(isDefault);

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}

