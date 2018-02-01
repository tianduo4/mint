package com.mint.cms.entity.base;

import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.LogisticsText;

import java.io.Serializable;

public abstract class BaseLogisticsText
        implements Serializable {
    public static String REF = "LogisticsText";
    public static String PROP_TEXT = "text";
    public static String PROP_LOGISTICS = "logistics";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String text;
    private Logistics logistics;

    public BaseLogisticsText() {
        initialize();
    }

    public BaseLogisticsText(Long id) {
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

    public Logistics getLogistics() {
        return this.logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof LogisticsText)) return false;

        LogisticsText logisticsText = (LogisticsText) obj;
        if ((getId() == null) || (logisticsText.getId() == null)) return false;
        return getId().equals(logisticsText.getId());
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

