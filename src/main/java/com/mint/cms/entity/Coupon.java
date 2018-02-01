package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseCoupon;
import com.mint.common.util.DateUtils;
import com.mint.core.entity.Website;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONObject;

public class Coupon extends BaseCoupon {
    private static final long serialVersionUID = 1L;

    public Coupon() {
    }

    public Coupon(Long id) {
        super(id);
    }

    public Coupon(Long id, Website website, String couponName, Date couponTime, Date couponEndTime, String couponPicture, BigDecimal couponPrice, BigDecimal leastPrice, Boolean isusing, Integer couponCount) {
        super(id,
                website,
                couponName,
                couponTime,
                couponEndTime,
                couponPicture,
                couponPrice,
                leastPrice,
                isusing,
                couponCount);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("couponName", CommonUtils.parseStr(getCouponName()));
        json.put("couponTime", CommonUtils.parseDate(getCouponTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("couponEndTime", CommonUtils.parseDate(getCouponEndTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("couponPicture", CommonUtils.parseStr(getCouponPicture()));
        json.put("isusing", CommonUtils.parseBoolean(getIsusing()));
        json.put("couponPrice", CommonUtils.parseBigDecimal(getCouponPrice()));
        json.put("leastPrice", CommonUtils.parseBigDecimal(getLeastPrice()));
        json.put("couponCount", Integer.valueOf(CommonUtils.parseInteger(getCouponCount())));
        json.put("categoryId", getCategory() != null ? Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())) : "");
        json.put("categoryName", getCategory() != null ? CommonUtils.parseStr(getCategory().getName()) : "全品类");
        json.put("comments", CommonUtils.parseStr(getComments()));
        return json;
    }
}

