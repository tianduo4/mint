package com.mint.cms.entity.base;

import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductStandard;
import com.mint.cms.entity.Standard;
import com.mint.cms.entity.StandardType;

import java.io.Serializable;

public abstract class BaseProductStandard
        implements Serializable {
    public static String REF = "ProductStandard";
    public static String PROP_STANDARD = "standard";
    public static String PROP_DATA_TYPE = "dataType";
    public static String PROP_PRODUCT = "product";
    public static String PROP_TYPE = "type";
    public static String PROP_ID = "id";
    public static String PROP_IMG_PATH = "imgPath";

    private int hashCode = -2147483648;
    private Long id;
    private String imgPath;
    private boolean dataType;
    private Product product;
    private Standard standard;
    private StandardType type;

    public BaseProductStandard() {
        initialize();
    }

    public BaseProductStandard(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductStandard(Long id, Product product, Standard standard, StandardType type, String imgPath, boolean dataType) {
        setId(id);
        setProduct(product);
        setStandard(standard);
        setType(type);
        setImgPath(imgPath);
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

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isDataType() {
        return this.dataType;
    }

    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Standard getStandard() {
        return this.standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public StandardType getType() {
        return this.type;
    }

    public void setType(StandardType type) {
        this.type = type;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductStandard)) return false;

        ProductStandard productStandard = (ProductStandard) obj;
        if ((getId() == null) || (productStandard.getId() == null)) return false;
        return getId().equals(productStandard.getId());
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

