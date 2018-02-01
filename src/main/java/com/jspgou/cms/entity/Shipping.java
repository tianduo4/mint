package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseShipping;
import com.jspgou.core.entity.Website;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class Shipping extends BaseShipping {
    private static final long serialVersionUID = 1L;
    private Double price;
    public static final int UNIFORM = 1;
    public static final int BY_WEIGHT = 2;
    public static final int BY_COUNTRY = 3;

    public Double calPrice(Double weight) {
        int m = getMethod().intValue();
        Double p;
        if (m == 1) {
            p = getUniformPrice();
        } else {
            if (m == 2) {
                if (weight.doubleValue() <= 0.0D)
                    p = Double.valueOf(0.0D);
                else
                    p = byWeight(weight);
            } else {
                throw new RuntimeException("Shipping method not supported: " + m);
            }
        }
        return p;
    }

    public static Long[] fetchIds(Collection<Shipping> shippings) {
        if (shippings == null) {
            return null;
        }
        Long[] ids = new Long[shippings.size()];
        int i = 0;
        for (Shipping s : shippings) {
            ids[(i++)] = s.getId();
        }
        return ids;
    }

    public Double byWeight(Double weight) {
        Double price = getFirstPrice();
        Double ap = getAdditionalPrice();
        int fw = getFirstWeight().intValue();
        int aw = getAdditionalWeight().intValue();
        weight = Double.valueOf(weight.doubleValue() - fw);
        while (weight.doubleValue() > 0.0D) {
            weight = Double.valueOf(weight.doubleValue() - aw);
            price = Double.valueOf(price.doubleValue() + ap.doubleValue());
        }
        return price;
    }

    public JSONObject converToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("description", CommonUtils.parseStr(getDescription()));
        json.put("uniformPrice", CommonUtils.parseDouble(getUniformPrice()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("firstWeight", CommonUtils.parseInteger(getFirstWeight()));
        json.put("firstPrice", CommonUtils.parseDouble(getFirstPrice()));
        json.put("additionalWeight", CommonUtils.parseInteger(getAdditionalWeight()));
        json.put("additionalPrice", CommonUtils.parseDouble(getAdditionalPrice()));
        json.put("logisticsType", CommonUtils.parseStr(getLogisticsType()));
        json.put("logisticsId", getLogistics() == null ? "" : CommonUtils.parseLong(getLogistics().getId()));
        json.put("method", CommonUtils.parseInteger(getMethod()));
        return json;
    }

    public Shipping() {
    }

    public Shipping(Long id) {
        super(id);
    }

    public Shipping(Long id, Website website, String name, Integer method, Integer priority, Boolean disabled) {
        super(id, website, name, method, priority, disabled);
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

