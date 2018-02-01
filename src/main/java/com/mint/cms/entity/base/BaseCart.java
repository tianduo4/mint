package com.mint.cms.entity.base;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.CartGift;
import com.mint.cms.entity.CartItem;
import com.mint.core.entity.Member;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseCart
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Cart";
    public static String PROP_MEMBER = "member";
    public static String PROP_WEBSITE = "website";
    public static String PROP_ADDRESS = "address";
    public static String PROP_TOTAL_ITEMS = "totalItems";
    public static String PROP_ID = "id";
    public static String PROP_SHIPPING = "shipping";
    public static String PROP_TOTAL_GIFTS = "totalGifts";
    public static String PROP_PAYMENT = "payment";

    private int hashCode = -2147483648;
    private Long id;
    private Integer totalItems;
    private Integer totalGifts;
    private Member member;
    private Website website;
    private Set<CartItem> items;
    private Set<CartGift> gifts;

    public BaseCart() {
        initialize();
    }

    public BaseCart(Long id) {
        setId(id);
        initialize();
    }

    public BaseCart(Long id, Website website, Integer totalItems) {
        setId(id);
        setWebsite(website);
        setTotalItems(totalItems);
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

    public Integer getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalGifts() {
        return this.totalGifts;
    }

    public void setTotalGifts(Integer totalGifts) {
        this.totalGifts = totalGifts;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Set<CartItem> getItems() {
        return this.items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public Set<CartGift> getGifts() {
        return this.gifts;
    }

    public void setGifts(Set<CartGift> gifts) {
        this.gifts = gifts;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Cart)) return false;

        Cart cart = (Cart) obj;
        if ((getId() == null) || (cart.getId() == null)) return false;
        return getId().equals(cart.getId());
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

