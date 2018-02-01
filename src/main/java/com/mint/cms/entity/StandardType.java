package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseStandardType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StandardType extends BaseStandardType {
    private static final long serialVersionUID = 1L;

    public static Long[] fetchIds(Collection<StandardType> standardTypes) {
        Long[] ids = new Long[standardTypes.size()];
        int i = 0;
        for (StandardType sdt : standardTypes) {
            ids[(i++)] = sdt.getId();
        }
        return ids;
    }

    public void removeFromCategorys(Category category) {
        Set set = getCategorys();
        if (set != null)
            set.remove(category);
    }

    public void addToCategory(Category category) {
        Set set = getCategorys();
        if (set == null) {
            set = new HashSet();
            setCategorys(set);
        }
        set.add(category);
    }

    public JSONObject convertToJson(Long[] standardTypeIds) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("remark", CommonUtils.parseStr(getRemark()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        if (standardTypeIds != null) {
            json.put("standardTypeIds", standardTypeIds);
        }
        return json;
    }

    public JSONObject convertToJson1() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("remark", CommonUtils.parseStr(getRemark()));
        json.put("field", CommonUtils.parseStr(getField()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("dataType", CommonUtils.parseBoolean(Boolean.valueOf(getDataType())));

        if (getStandardSet() != null) {
            Set<Standard> standards = getStandardSet();
            JSONArray jsonArray = new JSONArray();
            for (Standard standard : standards) {
                JSONObject obj = new JSONObject();
                obj.put("standardId", CommonUtils.parseId(standard.getId()));
                obj.put("standardName", CommonUtils.parseStr(standard.getName()));
                jsonArray.put(obj);
            }
            json.put("standard", jsonArray);
        }

        return json;
    }

    public StandardType() {
    }

    public StandardType(Long id) {
        super(id);
    }

    public StandardType(Long id, String name, String field, boolean dataType) {
        super(id,
                name,
                field,
                dataType);
    }
}

