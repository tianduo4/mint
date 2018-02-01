package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.CartGift;
import com.jspgou.cms.entity.Gift;
import com.jspgou.core.entity.Website;

import java.io.Serializable;

public abstract class BaseCartGift
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "CartGift";
    public static String PROP_COUNT = "count";
    public static String PROP_GIFT = "gift";
    public static String PROP_WEBSITE = "website";
    public static String PROP_CART = "cart";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private Integer count;
    private Website website;
    private Cart cart;
    private Gift gift;

    public BaseCartGift() {
        initialize();
    }

    public BaseCartGift(Long id) {
        setId(id);
        initialize();
    }

    public BaseCartGift(Long id, Website website, Cart cart, Gift gift, Integer count) {
        setId(id);
        setWebsite(website);
        setCart(cart);
        setGift(gift);
        setCount(count);
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

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Gift getGift() {
        return this.gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof CartGift)) return false;

        CartGift cartGift = (CartGift) obj;
        if ((getId() == null) || (cartGift.getId() == null)) return false;
        return getId().equals(cartGift.getId());
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

