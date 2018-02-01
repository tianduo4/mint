package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseAdvertise;
import com.jspgou.common.util.DateUtils;

import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class Advertise extends BaseAdvertise {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getClickCount() == null) {
            setClickCount(Integer.valueOf(0));
        }
        if (getDisplayCount() == null) {
            setDisplayCount(Integer.valueOf(0));
        }
        if (getEnabled() == null) {
            setEnabled(Boolean.valueOf(true));
        }
        if (getWeight() == null)
            setWeight(Integer.valueOf(1));
    }

    public Advertise() {
    }

    public Advertise(Integer id) {
        super(id);
    }

    public Advertise(Integer id, Adspace adspace) {
        super(id,
                adspace);
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("weight", CommonUtils.parseInteger(getWeight()));
        json.put("displayCount", CommonUtils.parseInteger(getDisplayCount()));
        json.put("clickCount", CommonUtils.parseInteger(getClickCount()));
        json.put("startTime", CommonUtils.parseDate(getStartTime(), DateUtils.COMMON_FORMAT_SHORT));
        json.put("endTime", CommonUtils.parseDate(getEndTime(), DateUtils.COMMON_FORMAT_SHORT));
        json.put("enabled", CommonUtils.parseBoolean(getEnabled()));
        json.put("adspaceId", getAdspace() != null ? Integer.valueOf(CommonUtils.parseInteger(getAdspace().getId())) : "");
        if (getAttr() != null) {
            for (Map.Entry entry : getAttr().entrySet())
                json.put("attr_" + (String) entry.getKey(), entry.getValue());
        } else {
            json.put("attr_image_link", "");
            json.put("attr_image_url", "");
        }
        return json;
    }
}

