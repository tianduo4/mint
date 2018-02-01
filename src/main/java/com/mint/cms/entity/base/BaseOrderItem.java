package com.mint.cms.entity.base;

import com.mint.cms.entity.Order;
import com.mint.cms.entity.OrderItem;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductFashion;
import com.mint.core.entity.Website;

import java.io.Serializable;

public abstract class BaseOrderItem
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "OrderItem";
    public static String PROP_SECKILLPRICE = "seckillprice";
    public static String PROP_COUNT = "count";
    public static String PROP_PRODUCT = "product";
    public static String PROP_SALE_PRICE = "salePrice";
    public static String PROP_PRODUCT_FASH = "productFash";
    public static String PROP_WEBSITE = "website";
    public static String PROP_ORDER = "order";
    public static String PROP_ID = "id";
    public static String PROP_MEMBER_PRICE = "memberPrice";
    public static String PROP_PRODUCT_NAME = "productName";
    public static String PROP_FINAL_PRICE = "finalPrice";
    public static String PROP_COST_PRICE = "costPrice";

    private int hashCode = -2147483648;
    private Long id;
    private Integer count;
    private Double salePrice;
    private Double memberPrice;
    private Double costPrice;
    private Double finalPrice;
    private Double seckillprice;
    private Boolean status;
    private Website website;
    private Product product;
    private Order ordeR;
    private ProductFashion productFash;

    public BaseOrderItem() {
        initialize();
    }

    public BaseOrderItem(Long id) {
        setId(id);
        initialize();
    }

    public BaseOrderItem(Long id, Website website, Product product, Order ordeR, Integer count) {
        setId(id);
        setWebsite(website);
        setProduct(product);
        setOrdeR(ordeR);
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

    public Double getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getMemberPrice() {
        return this.memberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getFinalPrice() {
        return this.finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getSeckillprice() {
        return this.seckillprice;
    }

    public void setSeckillprice(Double seckillprice) {
        this.seckillprice = seckillprice;
    }

    public Website getWebsite() {
        return this.website;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrdeR() {
        return this.ordeR;
    }

    public void setOrdeR(Order ordeR) {
        this.ordeR = ordeR;
    }

    public ProductFashion getProductFash() {
        return this.productFash;
    }

    public void setProductFash(ProductFashion productFash) {
        this.productFash = productFash;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) obj;
        if ((getId() == null) || (orderItem.getId() == null)) return false;
        return getId().equals(orderItem.getId());
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

