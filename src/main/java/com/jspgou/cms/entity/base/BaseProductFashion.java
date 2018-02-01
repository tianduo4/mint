package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.CartItem;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.Standard;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseProductFashion
        implements Serializable {
    public static String REF = "ProductFashion";
    public static String PROP_DEFAULT = "default";
    public static String PROP_STANDARD = "standard";
    public static String PROP_SALE_COUNT = "saleCount";
    public static String PROP_MARKET_PRICE = "marketPrice";
    public static String PROP_PRODUCT_CODE = "productCode";
    public static String PROP_STOCK_COUNT = "stockCount";
    public static String PROP_PRODUCT_ID = "productId";
    public static String PROP_ON_SALE = "onSale";
    public static String PROP_SALE_PRICE = "salePrice";
    public static String PROP_STANDARD_SECOND = "standardSecond";
    public static String PROP_FASHION_STYLE = "fashionStyle";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_FASHION_PIC = "fashionPic";
    public static String PROP_FASHION_SIZE = "fashionSize";
    public static String PROP_ID = "id";
    public static String PROP_COST_PRICE = "costPrice";
    public static String PROP_FASHION_COLOR = "fashionColor";

    private int hashCode = -2147483648;
    private Long id;
    private String fashionStyle;
    private String productCode;
    private Integer saleCount;
    private Integer stockCount;
    private Integer onSale;
    private Date createTime;
    private Double marketPrice;
    private Double salePrice;
    private Double costPrice;
    private Integer lackRemind;
    private String fashionPic;
    private String fashionColor;
    private String fashionSize;
    private Boolean isDefault;
    private String nature;
    private String attitude;
    private Product productId;
    private Set<CartItem> cartItems;
    private Set<Standard> standards;

    public BaseProductFashion() {
        initialize();
    }

    public BaseProductFashion(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductFashion(Long id, Boolean isDefault) {
        setId(id);
        setIsDefault(isDefault);
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

    public String getFashionStyle() {
        return this.fashionStyle;
    }

    public void setFashionStyle(String fashionStyle) {
        this.fashionStyle = fashionStyle;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public Integer getOnSale() {
        return this.onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getLackRemind() {
        return this.lackRemind;
    }

    public void setLackRemind(Integer lackRemind) {
        this.lackRemind = lackRemind;
    }

    public String getFashionPic() {
        return this.fashionPic;
    }

    public void setFashionPic(String fashionPic) {
        this.fashionPic = fashionPic;
    }

    public String getFashionColor() {
        return this.fashionColor;
    }

    public void setFashionColor(String fashionColor) {
        this.fashionColor = fashionColor;
    }

    public String getFashionSize() {
        return this.fashionSize;
    }

    public void setFashionSize(String fashionSize) {
        this.fashionSize = fashionSize;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Product getProductId() {
        return this.productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Set<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setStandards(Set<Standard> standards) {
        this.standards = standards;
    }

    public Set<Standard> getStandards() {
        return this.standards;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNature() {
        return this.nature;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductFashion)) return false;

        ProductFashion productFashion = (ProductFashion) obj;
        if ((getId() == null) || (productFashion.getId() == null)) return false;
        return getId().equals(productFashion.getId());
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

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getAttitude() {
        return this.attitude;
    }
}

