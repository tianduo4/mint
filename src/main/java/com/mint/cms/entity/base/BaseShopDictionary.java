package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopDictionary;
import com.mint.cms.entity.ShopDictionaryType;

import java.io.Serializable;

public abstract class BaseShopDictionary
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopDictionary";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_SHOP_DICTIONARY_TYPE = "shopDictionaryType";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Integer priority;
    private ShopDictionaryType shopDictionaryType;

    public BaseShopDictionary() {
        initialize();
    }

    public BaseShopDictionary(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopDictionary(Long id, ShopDictionaryType shopDictionaryType, String name) {
        setId(id);
        setShopDictionaryType(shopDictionaryType);
        setName(name);
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

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ShopDictionaryType getShopDictionaryType() {
        return this.shopDictionaryType;
    }

    public void setShopDictionaryType(ShopDictionaryType shopDictionaryType) {
        this.shopDictionaryType = shopDictionaryType;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopDictionary)) return false;

        ShopDictionary shopDictionary = (ShopDictionary) obj;
        if ((getId() == null) || (shopDictionary.getId() == null)) return false;
        return getId().equals(shopDictionary.getId());
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

