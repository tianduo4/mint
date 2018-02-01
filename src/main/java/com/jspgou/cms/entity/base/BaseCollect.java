package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Collect;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ShopMember;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseCollect
        implements Serializable {
    public static String REF = "Collect";
    public static String PROP_MEMBER = "member";
    public static String PROP_TIME = "time";
    public static String PROP_PRODUCT = "product";
    public static String PROP_FASHION = "fashion";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private Date time;
    private ShopMember member;
    private Product product;
    private ProductFashion fashion;

    public BaseCollect() {
        initialize();
    }

    public BaseCollect(Integer id) {
        setId(id);
        initialize();
    }

    public BaseCollect(Integer id, ShopMember member, Product product) {
        setId(id);
        setMember(member);
        setProduct(product);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductFashion getFashion() {
        return this.fashion;
    }

    public void setFashion(ProductFashion fashion) {
        this.fashion = fashion;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Collect)) return false;

        Collect collect = (Collect) obj;
        if ((getId() == null) || (collect.getId() == null)) return false;
        return getId().equals(collect.getId());
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

