package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.PaymentPlugins;

import java.io.Serializable;

public abstract class BasePaymentPlugins
        implements Serializable {
    public static String REF = "PaymentPlugins";
    public static String PROP_NAME = "name";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_SELLER_KEY = "sellerKey";
    public static String PROP_ID = "id";
    public static String PROP_IMG_PATH = "imgPath";
    public static String PROP_SELLER_EMAIL = "sellerEmail";
    public static String PROP_PARTNER = "partner";
    public static String PROP_CODE = "code";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_DISABLED = "disabled";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer priority;
    private String imgPath;
    private String partner;
    private String sellerKey;
    private String sellerEmail;
    private String platform;
    private String publicKey;
    private Boolean disabled;
    private Boolean isDefault;

    public BasePaymentPlugins() {
        initialize();
    }

    public BasePaymentPlugins(Long id) {
        setId(id);
        initialize();
    }

    public BasePaymentPlugins(Long id, String name, String code, Integer priority, Boolean disabled) {
        setId(id);
        setName(name);
        setCode(code);
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSellerKey() {
        return this.sellerKey;
    }

    public void setSellerKey(String sellerKey) {
        this.sellerKey = sellerKey;
    }

    public String getSellerEmail() {
        return this.sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PaymentPlugins)) return false;

        PaymentPlugins paymentPlugins = (PaymentPlugins) obj;
        if ((getId() == null) || (paymentPlugins.getId() == null)) return false;
        return getId().equals(paymentPlugins.getId());
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

