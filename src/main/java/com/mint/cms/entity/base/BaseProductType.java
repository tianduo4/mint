package com.mint.cms.entity.base;

import com.mint.cms.entity.Exended;
import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.ProductTypeProperty;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseProductType
        implements Serializable {
    public static String REF = "ProductType";
    public static String PROP_LIST_IMG_HEIGHT = "listImgHeight";
    public static String PROP_WEBSITE = "website";
    public static String PROP_DETAIL_IMG_WIDTH = "detailImgWidth";
    public static String PROP_LIST_IMG_WIDTH = "listImgWidth";
    public static String PROP_MIN_IMG_HEIGHT = "minImgHeight";
    public static String PROP_PATH = "path";
    public static String PROP_DETAIL_IMG_HEIGHT = "detailImgHeight";
    public static String PROP_NAME = "name";
    public static String PROP_ALIAS = "alias";
    public static String PROP_PROPS = "props";
    public static String PROP_ID = "id";
    public static String PROP_MIN_IMG_WIDTH = "minImgWidth";
    public static String PROP_CONTENT_PREFIX = "contentPrefix";
    public static String PROP_REF_BRAND = "refBrand";
    public static String PROP_CHANNEL_PREFIX = "channelPrefix";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String alias;
    private String channelPrefix;
    private String contentPrefix;
    private String props;
    private Integer detailImgWidth;
    private Integer detailImgHeight;
    private Integer listImgWidth;
    private Integer listImgHeight;
    private Integer minImgWidth;
    private Integer minImgHeight;
    private Website website;
    private Set<ProductTypeProperty> properties;
    private Set<Exended> exendeds;

    public BaseProductType() {
        initialize();
    }

    public BaseProductType(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductType(Long id, Website website, String name, String channelPrefix, String contentPrefix, Boolean refBrand, Integer detailImgWidth, Integer detailImgHeight, Integer listImgWidth, Integer listImgHeight, Integer minImgWidth, Integer minImgHeight) {
        setId(id);
        setWebsite(website);
        setName(name);
        setChannelPrefix(channelPrefix);
        setContentPrefix(contentPrefix);
        setDetailImgWidth(detailImgWidth);
        setDetailImgHeight(detailImgHeight);
        setListImgWidth(listImgWidth);
        setListImgHeight(listImgHeight);
        setMinImgWidth(minImgWidth);
        setMinImgHeight(minImgHeight);
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

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getChannelPrefix() {
        return this.channelPrefix;
    }

    public void setChannelPrefix(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }

    public String getContentPrefix() {
        return this.contentPrefix;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public Integer getDetailImgWidth() {
        return this.detailImgWidth;
    }

    public void setDetailImgWidth(Integer detailImgWidth) {
        this.detailImgWidth = detailImgWidth;
    }

    public Integer getDetailImgHeight() {
        return this.detailImgHeight;
    }

    public void setDetailImgHeight(Integer detailImgHeight) {
        this.detailImgHeight = detailImgHeight;
    }

    public Integer getListImgWidth() {
        return this.listImgWidth;
    }

    public void setListImgWidth(Integer listImgWidth) {
        this.listImgWidth = listImgWidth;
    }

    public Integer getListImgHeight() {
        return this.listImgHeight;
    }

    public void setListImgHeight(Integer listImgHeight) {
        this.listImgHeight = listImgHeight;
    }

    public Integer getMinImgWidth() {
        return this.minImgWidth;
    }

    public void setMinImgWidth(Integer minImgWidth) {
        this.minImgWidth = minImgWidth;
    }

    public Integer getMinImgHeight() {
        return this.minImgHeight;
    }

    public void setMinImgHeight(Integer minImgHeight) {
        this.minImgHeight = minImgHeight;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Set<ProductTypeProperty> getProperties() {
        return this.properties;
    }

    public void setProperties(Set<ProductTypeProperty> properties) {
        this.properties = properties;
    }

    public void setExendeds(Set<Exended> exendeds) {
        this.exendeds = exendeds;
    }

    public Set<Exended> getExendeds() {
        return this.exendeds;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductType)) return false;

        ProductType productType = (ProductType) obj;
        if ((getId() == null) || (productType.getId() == null)) return false;
        return getId().equals(productType.getId());
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

