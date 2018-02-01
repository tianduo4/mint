package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopMemberGroup;
import com.mint.core.entity.Website;

import java.io.Serializable;

public abstract class BaseShopMemberGroup
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopMemberGroup";
    public static String PROP_WEBSITE = "website";
    public static String PROP_DISCOUNT = "discount";
    public static String PROP_SCORE = "score";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Integer score;
    private Integer discount;
    private Website website;

    public BaseShopMemberGroup() {
        initialize();
    }

    public BaseShopMemberGroup(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopMemberGroup(Long id, Website website, String name, Integer score, Integer discount) {
        setId(id);
        setWebsite(website);
        setName(name);
        setScore(score);
        setDiscount(discount);
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopMemberGroup)) return false;

        ShopMemberGroup shopMemberGroup = (ShopMemberGroup) obj;
        if ((getId() == null) || (shopMemberGroup.getId() == null)) return false;
        return getId().equals(shopMemberGroup.getId());
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

