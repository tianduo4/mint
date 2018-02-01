package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseProductFashion;
import com.jspgou.core.entity.Website;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ProductFashion extends BaseProductFashion {
    private static final long serialVersionUID = 1L;

    public ProductFashion() {
    }

    public ProductFashion(Long id) {
        super(id);
    }

    public ProductFashion(Long id, Boolean m_default) {
        super(id,
                m_default);
    }

    public void init() {
        if (getSaleCount() == null) {
            setSaleCount(Integer.valueOf(0));
        }
        if (getStockCount() == null) {
            setStockCount(Integer.valueOf(0));
        }
        if (getMarketPrice() == null) {
            setMarketPrice(Double.valueOf(0.0D));
        }
        if (getSalePrice() == null) {
            setSalePrice(Double.valueOf(0.0D));
        }
        if (getCostPrice() == null) {
            setCostPrice(Double.valueOf(0.0D));
        }

        setCreateTime(new Timestamp(System.currentTimeMillis()));
    }

    public List<String> getPropertysName() {
        String propertys = getFashionStyle();
        List t = new ArrayList();
        String[] c = propertys.split("@");
        for (int i = 0; i < c.length; i++) {
            if (c[i].indexOf("??") != -1) {
                t.add(c[i].substring(0, c[i].indexOf("??")));
            }
        }
        return t;
    }

    public List<String> getPropertysValue() {
        String propertys = getFashionStyle();
        List u = new ArrayList();
        String[] c = propertys.split("@");
        for (int i = 0; i < c.length; i++) {
            if (c[i].indexOf("??") != -1) {
                u.add(c[i].substring(c[i].indexOf("??") + 2));
            }
        }
        return u;
    }

    public String getFashPic() {
        return getImageUrl(getFashionPic());
    }

    private String getImageUrl(String img) {
        if (StringUtils.isBlank(img)) {
            return null;
        }
        return getProductId().getWebsite().getUploadUrl(img);
    }

    public void addTostandards(Standard standard) {
        Set set = getStandards();
        if (set == null) {
            set = new HashSet();
            setStandards(set);
        }
        set.add(standard);
        standard.addToProductFashions(this);
    }
}

