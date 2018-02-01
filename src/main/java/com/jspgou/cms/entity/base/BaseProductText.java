package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductText;

import java.io.Serializable;

public abstract class BaseProductText
        implements Serializable {
    public static String REF = "ProductText";
    public static String PROP_TEXT = "text";
    public static String PROP_PRODUCT = "product";
    public static String PROP_TEXT1 = "text1";
    public static String PROP_TEXT2 = "text2";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String text;
    private String text1;
    private String text2;
    private Product product;

    public BaseProductText() {
        initialize();
    }

    public BaseProductText(Long id) {
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

    public String getText1() {
        return this.text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return this.text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductText)) return false;

        ProductText productText = (ProductText) obj;
        if ((getId() == null) || (productText.getId() == null)) return false;
        return getId().equals(productText.getId());
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

