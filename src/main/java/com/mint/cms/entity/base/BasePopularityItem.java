package com.mint.cms.entity.base;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.PopularityGroup;
import com.mint.cms.entity.PopularityItem;

import java.io.Serializable;

public abstract class BasePopularityItem
        implements Serializable {
    public static String REF = "PopularityItem";
    public static String PROP_COUNT = "count";
    public static String PROP_CART = "cart";
    public static String PROP_ID = "id";
    public static String PROP_POPULARITY_GROUP = "popularityGroup";

    private int hashCode = -2147483648;
    private Long id;
    private Integer count;
    private Cart cart;
    private PopularityGroup popularityGroup;

    public BasePopularityItem() {
        initialize();
    }

    public BasePopularityItem(Long id) {
        setId(id);
        initialize();
    }

    public BasePopularityItem(Long id, Cart cart, Integer count) {
        setId(id);
        setCart(cart);
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

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public PopularityGroup getPopularityGroup() {
        return this.popularityGroup;
    }

    public void setPopularityGroup(PopularityGroup popularityGroup) {
        this.popularityGroup = popularityGroup;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PopularityItem)) return false;

        PopularityItem popularityItem = (PopularityItem) obj;
        if ((getId() == null) || (popularityItem.getId() == null)) return false;
        return getId().equals(popularityItem.getId());
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

