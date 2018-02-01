package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopDictionaryType;

import java.io.Serializable;

public abstract class BaseShopDictionaryType
        implements Serializable {
    public static String REF = "ShopDictionaryType";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String name;

    public BaseShopDictionaryType() {
        initialize();
    }

    public BaseShopDictionaryType(Long id) {
        setId(id);
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopDictionaryType)) return false;

        ShopDictionaryType shopDictionaryType = (ShopDictionaryType) obj;
        if ((getId() == null) || (shopDictionaryType.getId() == null)) return false;
        return getId().equals(shopDictionaryType.getId());
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

