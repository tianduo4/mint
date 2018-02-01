package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.ProductTypeProperty;

import java.io.Serializable;

public abstract class BaseProductTypeProperty
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ProductTypeProperty";
    public static String PROP_COLS = "cols";
    public static String PROP_SORT = "sort";
    public static String PROP_ROWS = "rows";
    public static String PROP_FIELD = "field";
    public static String PROP_DATA_TYPE = "dataType";
    public static String PROP_IS_NOT_NULL = "isNotNull";
    public static String PROP_PROPERTY_TYPE = "propertyType";
    public static String PROP_CUSTOM = "custom";
    public static String PROP_PROPERTY_NAME = "propertyName";
    public static String PROP_IS_START = "isStart";
    public static String PROP_SINGLE = "single";
    public static String PROP_OPT_VALUE = "optValue";
    public static String PROP_CATEGORY = "category";
    public static String PROP_DEF_VALUE = "defValue";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String propertyName;
    private String field;
    private Integer isStart;
    private Integer isNotNull;
    private Integer sort;
    private String defValue;
    private String optValue;
    private String rows;
    private String cols;
    private Integer dataType;
    private Boolean single;
    private Boolean category;
    private Boolean custom;
    private ProductType propertyType;

    public BaseProductTypeProperty() {
        initialize();
    }

    public BaseProductTypeProperty(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductTypeProperty(Long id, ProductType propertyType, String propertyName, String field, Integer isStart, Integer isNotNull, Integer sort, Integer dataType, Boolean single, Boolean category, Boolean custom) {
        setId(id);
        setPropertyType(propertyType);
        setPropertyName(propertyName);
        setField(field);
        setIsStart(isStart);
        setIsNotNull(isNotNull);
        setSort(sort);
        setDataType(dataType);
        setSingle(single);
        setCategory(category);
        setCustom(custom);
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

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getIsStart() {
        return this.isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public Integer getIsNotNull() {
        return this.isNotNull;
    }

    public void setIsNotNull(Integer isNotNull) {
        this.isNotNull = isNotNull;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDefValue() {
        return this.defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public String getOptValue() {
        return this.optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }

    public String getRows() {
        return this.rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getCols() {
        return this.cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Boolean getSingle() {
        return this.single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public Boolean getCategory() {
        return this.category;
    }

    public void setCategory(Boolean category) {
        this.category = category;
    }

    public Boolean getCustom() {
        return this.custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public ProductType getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(ProductType propertyType) {
        this.propertyType = propertyType;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductTypeProperty)) return false;

        ProductTypeProperty productTypeProperty = (ProductTypeProperty) obj;
        if ((getId() == null) || (productTypeProperty.getId() == null)) return false;
        return getId().equals(productTypeProperty.getId());
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

