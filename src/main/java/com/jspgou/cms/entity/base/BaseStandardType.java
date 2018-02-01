package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.entity.StandardType;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseStandardType
        implements Serializable {
    public static String REF = "StandardType";
    public static String PROP_NAME = "name";
    public static String PROP_DATA_TYPE = "dataType";
    public static String PROP_FIELD = "field";
    public static String PROP_ID = "id";
    public static String PROP_PRIORITY = "priority";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String field;
    private String remark;
    private boolean dataType;
    private Integer priority;
    private Set<Standard> standardSet;
    private Set<Category> categorys;

    public BaseStandardType() {
        initialize();
    }

    public BaseStandardType(Long id) {
        setId(id);
        initialize();
    }

    public BaseStandardType(Long id, String name, String field, boolean dataType) {
        setId(id);
        setName(name);
        setField(field);
        setDataType(dataType);
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

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean getDataType() {
        return this.dataType;
    }

    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<Standard> getStandardSet() {
        return this.standardSet;
    }

    public void setStandardSet(Set<Standard> standardSet) {
        this.standardSet = standardSet;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof StandardType)) return false;

        StandardType standardType = (StandardType) obj;
        if ((getId() == null) || (standardType.getId() == null)) return false;
        return getId().equals(standardType.getId());
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

    public void setCategorys(Set<Category> categorys) {
        this.categorys = categorys;
    }

    public Set<Category> getCategorys() {
        return this.categorys;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }
}

