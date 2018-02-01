package com.mint.cms.entity.base;

import com.mint.cms.entity.Exended;
import com.mint.cms.entity.ExendedItem;
import com.mint.cms.entity.ProductType;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseExended
        implements Serializable {
    public static String REF = "Exended";
    public static String PROP_NAME = "name";
    public static String PROP_DATA_TYPE = "dataType";
    public static String PROP_FIELD = "field";
    public static String PROP_ID = "id";
    public static String PROP_PRIORITY = "priority";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String field;
    private Byte dataType;
    private Integer priority;
    private Set<ProductType> productTypes;
    private Set<ExendedItem> items;

    public BaseExended() {
        initialize();
    }

    public BaseExended(Long id) {
        setId(id);
        initialize();
    }

    public BaseExended(Long id, String name, String field) {
        setId(id);
        setName(name);
        setField(field);
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

    public Byte getDataType() {
        return this.dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<ProductType> getProductTypes() {
        return this.productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public void setItems(Set<ExendedItem> items) {
        this.items = items;
    }

    public Set<ExendedItem> getItems() {
        return this.items;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Exended)) return false;

        Exended exended = (Exended) obj;
        if ((getId() == null) || (exended.getId() == null)) return false;
        return getId().equals(exended.getId());
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

