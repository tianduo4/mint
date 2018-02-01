package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BasePayment;
import com.mint.core.entity.Website;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends BasePayment {
    public void addToShippings(Shipping shipping) {
        if (shipping == null) {
            return;
        }
        Set set = getShippings();
        if (set == null) {
            set = new HashSet();
            setShippings(set);
        }
        set.add(shipping);
    }

    public Long[] getShippingIds() {
        Set shippings = getShippings();
        return Shipping.fetchIds(shippings);
    }

    public Payment() {
    }

    public Payment(Long id) {
        super(id);
    }

    public Payment(Long id, Website website, String name, Integer priority, Boolean disabled) {
        super(id, website, name, priority, disabled);
    }

    public JSONObject converToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("description", CommonUtils.parseStr(getDescription()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
        json.put("introduce", CommonUtils.parseStr(getIntroduce()));
        json.put("type", getType());
        json.put("shippingIds", getShippingIds() == null ? "" : getShippingIds());
        return json;
    }
}

