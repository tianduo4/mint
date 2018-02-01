package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExended;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ProductPicture;
import com.jspgou.cms.entity.ProductTag;
import com.jspgou.cms.entity.ProductText;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseProduct
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Product";
    public static String PROP_PRODUCT_EXT = "productExt";
    public static String PROP_BRAND = "brand";
    public static String PROP_CONFIG = "config";
    public static String PROP_SALE_COUNT = "saleCount";
    public static String PROP_SPECIAL = "special";
    public static String PROP_TYPE = "type";
    public static String PROP_SHARE_CONTENT = "shareContent";
    public static String PROP_RECOMMEND = "recommend";
    public static String PROP_NEW_PRODUCT = "newProduct";
    public static String PROP_VIEW_COUNT = "viewCount";
    public static String PROP_HOTSALE = "hotsale";
    public static String PROP_SCORE = "score";
    public static String PROP_MARKET_PRICE = "marketPrice";
    public static String PROP_WEBSITE = "website";
    public static String PROP_STOCK_COUNT = "stockCount";
    public static String PROP_PRODUCT_TEXT = "productText";

    public static String PROP_ON_SALE = "status";
    public static String PROP_NAME = "name";
    public static String PROP_CATEGORY = "category";
    public static String PROP_SALE_PRICE = "salePrice";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_ID = "id";
    public static String PROP_COST_PRICE = "costPrice";
    public static String PROP_RELATED_GOODS = "relatedGoods";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Double marketPrice;
    private Double salePrice;
    private Double costPrice;
    private Long viewCount;
    private Integer saleCount;
    private Integer stockCount;
    private Date createTime;
    private Date expireTime;
    private Boolean special;
    private Boolean recommend;
    private Boolean hotsale;
    private Boolean newProduct;
    private Integer status;
    private Boolean lackRemind;
    private Integer score;
    private String shareContent;
    private Integer alertInventory;
    private Boolean relatedGoods;
    private Double liRun;
    private ProductText productText;
    private ProductExt productExt;
    private Brand brand;
    private ShopConfig config;
    private Category category;
    private ProductType type;
    private Website website;
    private Set<ProductTag> tags;
    private Set<ProductFashion> fashions;
    private List<String> keywords;
    private List<ProductPicture> pictures;
    private Map<String, String> attr;
    private List<ProductExended> exendeds;
    private Set<PopularityGroup> popularityGroups;

    public BaseProduct() {
        initialize();
    }

    public BaseProduct(Long id) {
        setId(id);
        initialize();
    }

    public BaseProduct(Long id, ShopConfig config, Category category, ProductType type, Website website, String name, Double marketPrice, Double salePrice, Double costPrice, Long viewCount, Integer saleCount, Integer stockCount, Date createTime, Boolean special, Boolean recommend, Boolean hotsale, Boolean newProduct, Boolean relatedGoods, Integer status) {
        setId(id);
        setConfig(config);
        setCategory(category);
        setType(type);
        setWebsite(website);
        setName(name);
        setMarketPrice(marketPrice);
        setSalePrice(salePrice);
        setCostPrice(costPrice);
        setViewCount(viewCount);
        setSaleCount(saleCount);
        setStockCount(stockCount);
        setCreateTime(createTime);
        setSpecial(special);
        setRecommend(recommend);
        setHotsale(hotsale);
        setNewProduct(newProduct);
        setRelatedGoods(relatedGoods);

        setStatus(status);
        initialize();
    }

    protected void initialize() {
    }

    public Boolean getRelatedGoods() {
        return this.relatedGoods;
    }

    public void setRelatedGoods(Boolean relatedGoods) {
        this.relatedGoods = relatedGoods;
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

    public Double getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Long getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getSaleCount() {
        return this.saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getSpecial() {
        return this.special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getRecommend() {
        return this.recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getHotsale() {
        return this.hotsale;
    }

    public void setHotsale(Boolean hotsale) {
        this.hotsale = hotsale;
    }

    public Boolean getNewProduct() {
        return this.newProduct;
    }

    public void setNewProduct(Boolean newProduct) {
        this.newProduct = newProduct;
    }

    public Boolean getLackRemind() {
        return this.lackRemind;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setLackRemind(Boolean lackRemind) {
        this.lackRemind = lackRemind;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getShareContent() {
        return this.shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public ProductText getProductText() {
        return this.productText;
    }

    public void setProductText(ProductText productText) {
        this.productText = productText;
    }

    public ProductExt getProductExt() {
        return this.productExt;
    }

    public void setProductExt(ProductExt productExt) {
        this.productExt = productExt;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ShopConfig getConfig() {
        return this.config;
    }

    public void setConfig(ShopConfig config) {
        this.config = config;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Set<ProductTag> getTags() {
        return this.tags;
    }

    public void setTags(Set<ProductTag> tags) {
        this.tags = tags;
    }

    public Set<ProductFashion> getFashions() {
        return this.fashions;
    }

    public void setFashions(Set<ProductFashion> fashions) {
        this.fashions = fashions;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<ProductPicture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<ProductPicture> pictures) {
        this.pictures = pictures;
    }

    public Map<String, String> getAttr() {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Product)) return false;

        Product product = (Product) obj;
        if ((getId() == null) || (product.getId() == null)) return false;
        return getId().equals(product.getId());
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

    public void setAlertInventory(Integer alertInventory) {
        this.alertInventory = alertInventory;
    }

    public Integer getAlertInventory() {
        return this.alertInventory;
    }

    public void setExendeds(List<ProductExended> exendeds) {
        this.exendeds = exendeds;
    }

    public List<ProductExended> getExendeds() {
        return this.exendeds;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setLiRun(Double liRun) {
        this.liRun = liRun;
    }

    public Double getLiRun() {
        return this.liRun;
    }

    public void setPopularityGroups(Set<PopularityGroup> popularityGroups) {
        this.popularityGroups = popularityGroups;
    }

    public Set<PopularityGroup> getPopularityGroups() {
        return this.popularityGroups;
    }
}

