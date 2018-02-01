package com.mint.cms.entity.base;

import com.mint.cms.entity.PopularityGroup;
import com.mint.cms.entity.Product;

import java.io.Serializable;
import java.util.Set;

public abstract class BasePopularityGroup
        implements Serializable {
    public static String REF = "PopularityGroup";
    public static String PROP_NAME = "name";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_BEGIN_TIME = "beginTime";
    public static String PROP_PRICE = "price";
    public static String PROP_PRIVILEGE = "privilege";
    public static String PROP_ID = "id";
    public static String PROP_END_TIME = "endTime";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Double price;
    private Double privilege;
    private String description;
    private Set<Product> products;

    public BasePopularityGroup() {
        initialize();
    }

    public BasePopularityGroup(Long id) {
        setId(id);
        initialize();
    }

    public BasePopularityGroup(Long id, String name, Double price, Double privilege) {
        setId(id);
        setName(name);
        setPrice(price);
        setPrivilege(privilege);
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

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(Double privilege) {
        this.privilege = privilege;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PopularityGroup)) return false;

        PopularityGroup popularityGroup = (PopularityGroup) obj;
        if ((getId() == null) || (popularityGroup.getId() == null)) return false;
        return getId().equals(popularityGroup.getId());
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

