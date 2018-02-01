package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.CartItem;
import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.core.entity.Website;

import java.io.Serializable;

public abstract class BaseCartItem
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "CartItem";
    public static String PROP_COUNT = "count";
    public static String PROP_PRODUCT = "product";
    public static String PROP_PRODUCT_FASH = "productFash";
    public static String PROP_WEBSITE = "website";
    public static String PROP_CART = "cart";
    public static String PROP_ID = "id";
    public static String PROP_SCORE = "score";

    private int hashCode = -2147483648;
    private Long id;
    private Integer count;
    private Integer score;
    private Website website;
    private Cart cart;
    private Product product;
    private ProductFashion productFash;
    private PopularityGroup popularityGroup;

    public BaseCartItem() {
        initialize();
    }

    public BaseCartItem(Long id) {
        setId(id);
        initialize();
    }

    public BaseCartItem(Long id, Website website, Cart cart, Product product, Integer count) {
        setId(id);
        setWebsite(website);
        setCart(cart);
        setProduct(product);
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

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductFashion getProductFash() {
        return this.productFash;
    }

    public void setProductFash(ProductFashion productFash) {
        this.productFash = productFash;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof CartItem)) return false;

        CartItem cartItem = (CartItem) obj;
        if ((getId() == null) || (cartItem.getId() == null)) return false;
        return getId().equals(cartItem.getId());
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

    public void setPopularityGroup(PopularityGroup popularityGroup) {
        this.popularityGroup = popularityGroup;
    }

    public PopularityGroup getPopularityGroup() {
        return this.popularityGroup;
    }
}

