package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.BrandText;
import com.jspgou.cms.entity.Category;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseBrand
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Brand";
    public static String PROP_NAME = "name";
    public static String PROP_ALIAS = "alias";
    public static String PROP_WEBSITE = "website";
    public static String PROP_ID = "id";
    public static String PROP_WEB_URL = "webUrl";
    public static String PROP_SIFT = "sift";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_LOGO_PATH = "logoPath";
    public static String PROP_CATEGORY = "category";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String alias;
    private String webUrl;
    private String logoPath;
    private Integer priority;
    private Boolean sift;
    private Boolean disabled;
    private String firstCharacter;
    private String brandtype;
    private Website website;
    private Category category;
    private Set<Category> categorys;
    private Set<BrandText> brandTextSet;

    public BaseBrand() {
        initialize();
    }

    public BaseBrand(Long id) {
        setId(id);
        initialize();
    }

    public BaseBrand(Long id, Website website, Category category, String name, Integer priority) {
        setId(id);
        setWebsite(website);
        setCategory(category);
        setName(name);
        setPriority(priority);
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

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getFirstCharacter() {
        return this.firstCharacter;
    }

    public void setFirstCharacter(String firstCharacter) {
        this.firstCharacter = firstCharacter;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrandtype(String brandtype) {
        this.brandtype = brandtype;
    }

    public String getBrandtype() {
        return this.brandtype;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getSift() {
        return this.sift;
    }

    public void setSift(Boolean sift) {
        this.sift = sift;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Set<Category> getCategorys() {
        return this.categorys;
    }

    public void setCategorys(Set<Category> categorys) {
        this.categorys = categorys;
    }

    public Set<BrandText> getBrandTextSet() {
        return this.brandTextSet;
    }

    public void setBrandTextSet(Set<BrandText> brandTextSet) {
        this.brandTextSet = brandTextSet;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Brand)) return false;

        Brand brand = (Brand) obj;
        if ((getId() == null) || (brand.getId() == null)) return false;
        return getId().equals(brand.getId());
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

