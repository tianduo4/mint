package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.core.entity.Website;

import java.io.Serializable;

public abstract class BaseShipping
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Shipping";
    public static String PROP_FIRST_WEIGHT = "firstWeight";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_WEBSITE = "website";
    public static String PROP_FIRST_PRICE = "firstPrice";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_UNIFORM_PRICE = "uniformPrice";
    public static String PROP_METHOD = "method";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_ADDITIONAL_WEIGHT = "additionalWeight";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_ADDITIONAL_PRICE = "additionalPrice";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String description;
    private Double uniformPrice;
    private Integer firstWeight;
    private Integer additionalWeight;
    private Double firstPrice;
    private Double additionalPrice;
    private Integer method;
    private Integer priority;
    private Boolean disabled;
    private Boolean isDefault;
    private String logisticsType;
    private Website website;
    private Logistics logistics;

    public BaseShipping() {
        initialize();
    }

    public BaseShipping(Long id) {
        setId(id);
        initialize();
    }

    public BaseShipping(Long id, Website website, String name, Integer method, Integer priority, Boolean disabled) {
        setId(id);
        setWebsite(website);
        setName(name);
        setMethod(method);
        setPriority(priority);
        setDisabled(disabled);
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUniformPrice() {
        return this.uniformPrice;
    }

    public void setUniformPrice(Double uniformPrice) {
        this.uniformPrice = uniformPrice;
    }

    public Integer getFirstWeight() {
        return this.firstWeight;
    }

    public void setFirstWeight(Integer firstWeight) {
        this.firstWeight = firstWeight;
    }

    public Integer getAdditionalWeight() {
        return this.additionalWeight;
    }

    public void setAdditionalWeight(Integer additionalWeight) {
        this.additionalWeight = additionalWeight;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Double getFirstPrice() {
        return this.firstPrice;
    }

    public void setFirstPrice(Double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public Double getAdditionalPrice() {
        return this.additionalPrice;
    }

    public void setAdditionalPrice(Double additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public Integer getMethod() {
        return this.method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public Logistics getLogistics() {
        return this.logistics;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Shipping)) return false;

        Shipping shipping = (Shipping) obj;
        if ((getId() == null) || (shipping.getId() == null)) return false;
        return getId().equals(shipping.getId());
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

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsType() {
        return this.logisticsType;
    }
}

