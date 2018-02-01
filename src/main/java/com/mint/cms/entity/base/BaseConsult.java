package com.mint.cms.entity.base;

import com.mint.cms.entity.Consult;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ShopMember;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseConsult
        implements Serializable {
    public static String REF = "Consult";
    public static String PROP_MEMBER = "member";
    public static String PROP_TIME = "time";
    public static String PROP_PRODUCT = "product";
    public static String PROP_ID = "id";
    public static String PROP_CONSULT = "consult";
    public static String PROP_ADMIN_REPLAY = "adminReplay";

    private int hashCode = -2147483648;
    private Long id;
    private String consult;
    private String adminReplay;
    private Date time;
    private ShopMember member;
    private Product product;

    public BaseConsult() {
        initialize();
    }

    public BaseConsult(Long id) {
        setId(id);
        initialize();
    }

    public BaseConsult(Long id, ShopMember member, Product product) {
        setId(id);
        setMember(member);
        setProduct(product);
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

    public String getConsult() {
        return this.consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getAdminReplay() {
        return this.adminReplay;
    }

    public void setAdminReplay(String adminReplay) {
        this.adminReplay = adminReplay;
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Consult)) return false;

        Consult consult = (Consult) obj;
        if ((getId() == null) || (consult.getId() == null)) return false;
        return getId().equals(consult.getId());
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

