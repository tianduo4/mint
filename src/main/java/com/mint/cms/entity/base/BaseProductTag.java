package com.mint.cms.entity.base;

import com.mint.cms.entity.ProductTag;
import com.mint.core.entity.Website;

import java.io.Serializable;

public abstract class BaseProductTag
        implements Serializable {
    public static String REF = "ProductTag";
    public static String PROP_WEBSITE = "website";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_COUNT = "count";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Integer count;
    private Website website;

    public BaseProductTag() {
        initialize();
    }

    public BaseProductTag(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductTag(Long id, Website website, String name, Integer count) {
        setId(id);
        setWebsite(website);
        setName(name);
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductTag)) return false;

        ProductTag productTag = (ProductTag) obj;
        if ((getId() == null) || (productTag.getId() == null)) return false;
        return getId().equals(productTag.getId());
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

