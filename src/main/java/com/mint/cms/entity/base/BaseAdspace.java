package com.mint.cms.entity.base;

import com.mint.cms.entity.Adspace;

import java.io.Serializable;

public abstract class BaseAdspace
        implements Serializable {
    public static String REF = "Adspace";
    public static String PROP_NAME = "name";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_ENABLE = "enable";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private String description;
    private Boolean enabled;

    public BaseAdspace() {
        initialize();
    }

    public BaseAdspace(Integer id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Adspace)) return false;

        Adspace adspace = (Adspace) obj;
        if ((getId() == null) || (adspace.getId() == null)) return false;
        return getId().equals(adspace.getId());
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

