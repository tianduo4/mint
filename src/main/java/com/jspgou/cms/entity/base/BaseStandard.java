package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.entity.StandardType;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseStandard
        implements Serializable {
    public static String REF = "Standard";
    public static String PROP_NAME = "name";
    public static String PROP_TYPE = "type";
    public static String PROP_ID = "id";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_COLOR = "color";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String color;
    private Integer priority;
    private StandardType type;
    private Set<ProductFashion> fashions;

    public BaseStandard() {
        initialize();
    }

    public BaseStandard(Long id) {
        setId(id);
        initialize();
    }

    public BaseStandard(Long id, StandardType type, String name) {
        setId(id);
        setType(type);
        setName(name);
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public StandardType getType() {
        return this.type;
    }

    public void setType(StandardType type) {
        this.type = type;
    }

    public void setFashions(Set<ProductFashion> fashions) {
        this.fashions = fashions;
    }

    public Set<ProductFashion> getFashions() {
        return this.fashions;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Standard)) return false;

        Standard standard = (Standard) obj;
        if ((getId() == null) || (standard.getId() == null)) return false;
        return getId().equals(standard.getId());
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

