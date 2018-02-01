package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseProductExt
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ProductExt";
    public static String PROP_SECKILLPRICE = "seckillprice";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_PRODUCT = "product";
    public static String PROP_DETAIL_IMG = "detailImg";
    public static String PROP_MTITLE = "mtitle";
    public static String PROP_MIN_IMG = "minImg";
    public static String PROP_MDESCRIPTION = "mdescription";
    public static String PROP_LIST_IMG = "listImg";
    public static String PROP_UNIT = "unit";
    public static String PROP_PRODUCT_IMG_DESC = "productImgDesc";
    public static String PROP_PRODUCT_IMGS = "productImgs";
    public static String PROP_TIME_LIMIT = "timeLimit";
    public static String PROP_WEIGHT = "weight";
    public static String PROP_PRODUCT_PROPERTY = "productProperty";
    public static String PROP_ID = "id";
    public static String PROP_ISLIMIT_TIME = "islimitTime";
    public static String PROP_MKEYWORDS = "mkeywords";

    private int hashCode = -2147483648;
    private Long id;
    private Long code;
    private String mtitle;
    private String mkeywords;
    private String mdescription;
    private String detailImg;
    private String listImg;
    private String minImg;
    private String coverImg;
    private Integer weight;
    private String unit;
    private Boolean islimitTime;
    private Date timeLimit;
    private Double seckillprice;
    private String productImgs;
    private String productImgDesc;
    private Product product;

    public BaseProductExt() {
        initialize();
    }

    public BaseProductExt(Long id) {
        setId(id);
        initialize();
    }

    public BaseProductExt(Long id, Integer weight, String unit) {
        setId(id);
        setWeight(weight);
        setUnit(unit);
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

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMtitle() {
        return this.mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMkeywords() {
        return this.mkeywords;
    }

    public void setMkeywords(String mkeywords) {
        this.mkeywords = mkeywords;
    }

    public String getMdescription() {
        return this.mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getDetailImg() {
        return this.detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public String getListImg() {
        return this.listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    public String getMinImg() {
        return this.minImg;
    }

    public void setMinImg(String minImg) {
        this.minImg = minImg;
    }

    public String getCoverImg() {
        return this.coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getIslimitTime() {
        return this.islimitTime;
    }

    public void setIslimitTime(Boolean islimitTime) {
        this.islimitTime = islimitTime;
    }

    public Date getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(Date timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Double getSeckillprice() {
        return this.seckillprice;
    }

    public void setSeckillprice(Double seckillprice) {
        this.seckillprice = seckillprice;
    }

    public String getProductImgs() {
        return this.productImgs;
    }

    public void setProductImgs(String productImgs) {
        this.productImgs = productImgs;
    }

    public String getProductImgDesc() {
        return this.productImgDesc;
    }

    public void setProductImgDesc(String productImgDesc) {
        this.productImgDesc = productImgDesc;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ProductExt)) return false;

        ProductExt productExt = (ProductExt) obj;
        if ((getId() == null) || (productExt.getId() == null)) return false;
        return getId().equals(productExt.getId());
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

