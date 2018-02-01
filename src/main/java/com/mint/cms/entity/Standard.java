package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseStandard;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Standard extends BaseStandard {
    private static final long serialVersionUID = 1L;

    public Standard() {
    }

    public Standard(Long id) {
        super(id);
    }

    public Standard(Long id, StandardType type, String name) {
        super(id,
                type,
                name);
    }

    public void addToProductFashions(ProductFashion productFashion) {
        Set set = getFashions();
        if (set == null) {
            set = new HashSet();
            setFashions(set);
        }
        set.add(productFashion);
    }

    public JSONObject convertToJson(Long id, String name, String field, String remark, boolean dataType, Integer priority)
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("itemId", CommonUtils.parseId(getId()));
        json.put("itemName", CommonUtils.parseStr(getName()));
        json.put("itemColor", CommonUtils.parseStr(getColor()));
        json.put("itemPriority", CommonUtils.parseInteger(getPriority()));

        json.put("id", CommonUtils.parseId(id));
        json.put("name", CommonUtils.parseStr(name));
        json.put("field", CommonUtils.parseStr(field));
        json.put("remark", CommonUtils.parseStr(remark));
        json.put("dataType", CommonUtils.parseBoolean(Boolean.valueOf(dataType)));
        json.put("priority", CommonUtils.parseInteger(priority));
        return json;
    }

    public JSONObject convertToJson1() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("itemId", CommonUtils.parseId(getId()));
        json.put("itemName", CommonUtils.parseStr(getName()));
        json.put("itemColor", CommonUtils.parseStr(getColor()));
        json.put("itemPriority", CommonUtils.parseInteger(getPriority()));
        return json;
    }
}

