package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.Coupon;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseCoupon
        implements Serializable {
    public static String REF = "Coupon";
    public static String PROP_COUPON_COUNT = "couponCount";
    public static String PROP_WEBSITE = "website";
    public static String PROP_COUPON_TIME = "couponTime";
    public static String PROP_ID = "id";
    public static String PROP_COUPON_NAME = "couponName";
    public static String PROP_ISUSING = "isusing";
    public static String PROP_LEAST_PRICE = "leastPrice";
    public static String PROP_COUPON_END_TIME = "couponEndTime";
    public static String PROP_COUPON_PRICE = "couponPrice";
    public static String PROP_COUPON_PICTURE = "couponPicture";

    private int hashCode = -2147483648;
    private Long id;
    private Category category;
    private String couponName;
    private Date couponTime;
    private Date couponEndTime;
    private String couponPicture;
    private BigDecimal couponPrice;
    private BigDecimal leastPrice;
    private Boolean isusing;
    private Integer couponCount;
    private String comments;
    private Website website;

    public BaseCoupon() {
        initialize();
    }

    public BaseCoupon(Long id) {
        setId(id);
        initialize();
    }

    public BaseCoupon(Long id, Website website, String couponName, Date couponTime, Date couponEndTime, String couponPicture, BigDecimal couponPrice, BigDecimal leastPrice, Boolean isusing, Integer couponCount) {
        setId(id);
        setWebsite(website);
        setCouponName(couponName);
        setCouponTime(couponTime);
        setCouponEndTime(couponEndTime);
        setCouponPicture(couponPicture);
        setCouponPrice(couponPrice);
        setLeastPrice(leastPrice);
        setIsusing(isusing);
        setCouponCount(couponCount);
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Date getCouponTime() {
        return this.couponTime;
    }

    public void setCouponTime(Date couponTime) {
        this.couponTime = couponTime;
    }

    public Date getCouponEndTime() {
        return this.couponEndTime;
    }

    public void setCouponEndTime(Date couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponPicture() {
        return this.couponPicture;
    }

    public void setCouponPicture(String couponPicture) {
        this.couponPicture = couponPicture;
    }

    public BigDecimal getCouponPrice() {
        return this.couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getLeastPrice() {
        return this.leastPrice;
    }

    public void setLeastPrice(BigDecimal leastPrice) {
        this.leastPrice = leastPrice;
    }

    public Boolean getIsusing() {
        return this.isusing;
    }

    public void setIsusing(Boolean isusing) {
        this.isusing = isusing;
    }

    public Integer getCouponCount() {
        return this.couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Coupon)) return false;

        Coupon coupon = (Coupon) obj;
        if ((getId() == null) || (coupon.getId() == null)) return false;
        return getId().equals(coupon.getId());
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

