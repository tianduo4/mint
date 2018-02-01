package com.mint.cms.entity.base;

import com.mint.cms.entity.Brand;
import com.mint.cms.entity.Category;
import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.StandardType;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public abstract class BaseCategory
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Category";
    public static String PROP_RGT = "rgt";
    public static String PROP_PARENT = "parent";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_WEBSITE = "website";
    public static String PROP_TPL_CHANNEL = "tplChannel";
    public static String PROP_TYPE = "type";
    public static String PROP_TITLE = "title";
    public static String PROP_PATH = "path";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_LFT = "lft";
    public static String PROP_IMAGE_PATH = "imagePath";
    public static String PROP_KEYWORDS = "keywords";
    public static String PROP_TPL_CONTENT = "tplContent";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private String path;
    private String tplChannel;
    private String tplContent;
    private Integer lft;
    private Integer rgt;
    private Integer priority;
    private String title;
    private String imagePath;
    private String keywords;
    private String description;
    private Boolean colorsize;
    private Category parent;
    private ProductType type;
    private Website website;
    private Set<Category> child;
    private Set<Brand> brands;
    private Set<StandardType> standardType;
    private Map<String, String> attr;

    public BaseCategory() {
        initialize();
    }

    public BaseCategory(Integer id) {
        setId(id);
        initialize();
    }

    public BaseCategory(Integer id, ProductType type, Website website, String name, String path, Integer lft, Integer rgt, Integer priority) {
        setId(id);
        setType(type);
        setWebsite(website);
        setName(name);
        setPath(path);
        setLft(lft);
        setRgt(rgt);
        setPriority(priority);
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTplChannel() {
        return this.tplChannel;
    }

    public void setTplChannel(String tplChannel) {
        this.tplChannel = tplChannel;
    }

    public String getTplContent() {
        return this.tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public Integer getLft() {
        return this.lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return this.rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getColorsize() {
        return this.colorsize;
    }

    public void setColorsize(Boolean colorsize) {
        this.colorsize = colorsize;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParent() {
        return this.parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public ProductType getType() {
        return this.type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Set<Category> getChild() {
        return this.child;
    }

    public void setChild(Set<Category> child) {
        this.child = child;
    }

    public Set<Brand> getBrands() {
        return this.brands;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }

    public Set<StandardType> getStandardType() {
        return this.standardType;
    }

    public void setStandardType(Set<StandardType> standardType) {
        this.standardType = standardType;
    }

    public Map<String, String> getAttr() {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Category)) return false;

        Category category = (Category) obj;
        if ((getId() == null) || (category.getId() == null)) return false;
        return getId().equals(category.getId());
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

