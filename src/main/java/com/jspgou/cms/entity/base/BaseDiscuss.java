package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Discuss;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ShopMember;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseDiscuss
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Discuss";
    public static String PROP_MEMBER = "member";
    public static String PROP_TIME = "time";
    public static String PROP_PRODUCT = "product";
    public static String PROP_ID = "id";
    public static String PROP_CONTENT = "content";

    private int hashCode = -2147483648;
    private Long id;
    private String content;
    private Date time;
    private String replay;
    private String discussType;
    private ShopMember member;
    private Product product;

    public BaseDiscuss() {
        initialize();
    }

    public BaseDiscuss(Long id) {
        setId(id);
        initialize();
    }

    public BaseDiscuss(Long id, ShopMember member, Product product, String content) {
        setId(id);
        setMember(member);
        setProduct(product);
        setContent(content);
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDiscussType() {
        return this.discussType;
    }

    public void setDiscussType(String discussType) {
        this.discussType = discussType;
    }

    public String getReplay() {
        return this.replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
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
        if (!(obj instanceof Discuss)) return false;

        Discuss discuss = (Discuss) obj;
        if ((getId() == null) || (discuss.getId() == null)) return false;
        return getId().equals(discuss.getId());
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

