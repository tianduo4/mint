package com.mint.cms.entity.base;

import com.mint.cms.entity.Brand;
import com.mint.cms.entity.BrandText;

import java.io.Serializable;

public abstract class BaseBrandText
        implements Serializable {
    public static String REF = "BrandText";
    public static String PROP_BRAND = "brand";
    public static String PROP_TEXT = "text";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String text;
    private Brand brand;

    public BaseBrandText() {
        initialize();
    }

    public BaseBrandText(Long id) {
        setId(id);
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof BrandText)) return false;

        BrandText brandText = (BrandText) obj;
        if ((getId() == null) || (brandText.getId() == null)) return false;
        return getId().equals(brandText.getId());
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

