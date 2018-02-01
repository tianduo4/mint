package com.jspgou.core.entity.base;

import com.jspgou.core.entity.WebsiteExt;

import java.io.Serializable;

public abstract class BaseWebsiteExt
        implements Serializable {
    public static String REF = "WebsiteExt";
    public static String PROP_VALUE = "value";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String value;

    public BaseWebsiteExt() {
        initialize();
    }

    public BaseWebsiteExt(String id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof WebsiteExt)) return false;

        WebsiteExt websiteExt = (WebsiteExt) obj;
        if ((getId() == null) || (websiteExt.getId() == null)) return false;
        return getId().equals(websiteExt.getId());
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

