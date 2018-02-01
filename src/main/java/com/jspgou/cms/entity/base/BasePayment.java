package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Set;

public abstract class BasePayment
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Payment";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_WEBSITE = "website";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_CODE = "code";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_CONFIG = "config";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String description;
    private Integer priority;
    private Boolean disabled;
    private Boolean isDefault;
    private Byte type;
    private String introduce;
    private Website website;
    private Set<Shipping> shippings;

    public BasePayment() {
        initialize();
    }

    public BasePayment(Long id) {
        setId(id);
        initialize();
    }

    public BasePayment(Long id, Website website, String name, Integer priority, Boolean disabled) {
        setId(id);
        setWebsite(website);
        setName(name);
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

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Payment)) return false;

        Payment payment = (Payment) obj;
        if ((getId() == null) || (payment.getId() == null)) return false;
        return getId().equals(payment.getId());
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

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getType() {
        return this.type;
    }

    public void setShippings(Set<Shipping> shippings) {
        this.shippings = shippings;
    }

    public Set<Shipping> getShippings() {
        return this.shippings;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduce() {
        return this.introduce;
    }
}

