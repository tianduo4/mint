package com.mint.cms.entity.base;

import com.mint.cms.entity.Address;

import java.io.Serializable;

public abstract class BaseRelatedgoods
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String PROP_PRODUCTIDS = "productIds";
    public static String PROP_PRODUCTID = "productId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private Long productId;
    private Long productIds;

    public BaseRelatedgoods() {
        initialize();
    }

    public BaseRelatedgoods(Long id) {
        setId(id);
        initialize();
    }

    public BaseRelatedgoods(Long id, Long productId, Long productIds) {
        setId(id);
        setProductId(productId);
        setProductIds(productIds);
        initialize();
    }

    protected void initialize() {
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public Long getProductIds() {
        return this.productIds;
    }

    public void setProductIds(Long productIds) {
        this.productIds = productIds;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Address)) return false;

        Address address = (Address) obj;
        if ((getId() == null) || (address.getId() == null)) return false;
        return getId().equals(address.getId());
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

