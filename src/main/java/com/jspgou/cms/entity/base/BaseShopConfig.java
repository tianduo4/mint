package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.cms.entity.ShopMemberGroup;
import com.jspgou.core.entity.Website;

import java.io.Serializable;

public abstract class BaseShopConfig
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopConfig";
    public static String PROP_REGISTER_AUTO = "registerAuto";
    public static String PROP_FAVORITE_SIZE = "favoriteSize";
    public static String PROP_WEBSITE = "website";
    public static String PROP_REGISTER_GROUP = "registerGroup";
    public static String PROP_ID = "id";
    public static String PROP_MARKET_PRICE_NAME = "marketPriceName";
    public static String PROP_SHOP_PRICE_NAME = "shopPriceName";

    private int hashCode = -2147483648;
    private Long id;
    private String shopPriceName;
    private String marketPriceName;
    private Integer favoriteSize;
    private Boolean registerAuto;
    private Website website;
    private ShopMemberGroup registerGroup;

    public BaseShopConfig() {
        initialize();
    }

    public BaseShopConfig(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopConfig(Long id, ShopMemberGroup registerGroup, String shopPriceName, String marketPriceName, Integer favoriteSize, Boolean registerAuto) {
        setId(id);
        setRegisterGroup(registerGroup);
        setShopPriceName(shopPriceName);
        setMarketPriceName(marketPriceName);
        setFavoriteSize(favoriteSize);
        setRegisterAuto(registerAuto);
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

    public String getShopPriceName() {
        return this.shopPriceName;
    }

    public void setShopPriceName(String shopPriceName) {
        this.shopPriceName = shopPriceName;
    }

    public String getMarketPriceName() {
        return this.marketPriceName;
    }

    public void setMarketPriceName(String marketPriceName) {
        this.marketPriceName = marketPriceName;
    }

    public Integer getFavoriteSize() {
        return this.favoriteSize;
    }

    public void setFavoriteSize(Integer favoriteSize) {
        this.favoriteSize = favoriteSize;
    }

    public Boolean getRegisterAuto() {
        return this.registerAuto;
    }

    public void setRegisterAuto(Boolean registerAuto) {
        this.registerAuto = registerAuto;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public ShopMemberGroup getRegisterGroup() {
        return this.registerGroup;
    }

    public void setRegisterGroup(ShopMemberGroup registerGroup) {
        this.registerGroup = registerGroup;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopConfig)) return false;

        ShopConfig shopConfig = (ShopConfig) obj;
        if ((getId() == null) || (shopConfig.getId() == null)) return false;
        return getId().equals(shopConfig.getId());
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

