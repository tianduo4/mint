package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseLogistics;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Logistics extends BaseLogistics {
    private static final long serialVersionUID = 1L;

    public Logistics() {
    }

    public Logistics(Long id) {
        super(id);
    }

    public Logistics(Long id, String name, Integer priority) {
        super(id,
                name,
                priority);
    }

    public LogisticsText getLogisticsText() {
        Set set = getLogisticsTextSet();
        if (set != null) {
            Iterator it = set.iterator();
            if (it.hasNext()) {
                return (LogisticsText) it.next();
            }
        }
        return null;
    }

    public String getText() {
        LogisticsText logisticsText = getLogisticsText();
        if (logisticsText != null) {
            return logisticsText.getText();
        }
        return null;
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("webUrl", CommonUtils.parseStr(getWebUrl()));
        json.put("logoPath", CommonUtils.parseStr(getLogoPath()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("isCourier", CommonUtils.parseBoolean(getCourier()));
        return json;
    }

    public JSONObject convertToJson1() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("courier", CommonUtils.parseBoolean(getCourier()));
        json.put("imgUrl", CommonUtils.parseStr(getImgUrl()));
        json.put("evenSpacing", CommonUtils.parseInteger(getEvenSpacing()));

        json.put("fnt", CommonUtils.parseDouble(getFnt()));
        json.put("fnz", CommonUtils.parseDouble(getFaz()));
        json.put("fnw", CommonUtils.parseDouble(getFnw()));
        json.put("fnh", CommonUtils.parseDouble(getFnh()));
        json.put("fnwh", CommonUtils.parseStr(getFnwh()));

        json.put("fat", CommonUtils.parseDouble(getFat()));
        json.put("faz", CommonUtils.parseDouble(getFaz()));
        json.put("faw", CommonUtils.parseDouble(getFaw()));
        json.put("fah", CommonUtils.parseDouble(getFah()));
        json.put("fawh", CommonUtils.parseStr(getFawh()));

        json.put("fpt", CommonUtils.parseDouble(getFat()));
        json.put("fpz", CommonUtils.parseDouble(getFaz()));
        json.put("fpw", CommonUtils.parseDouble(getFaw()));
        json.put("fph", CommonUtils.parseDouble(getFah()));
        json.put("fpwh", CommonUtils.parseStr(getFawh()));

        json.put("snt", CommonUtils.parseDouble(getFat()));
        json.put("snz", CommonUtils.parseDouble(getFaz()));
        json.put("snw", CommonUtils.parseDouble(getFaw()));
        json.put("snh", CommonUtils.parseDouble(getFah()));
        json.put("snwh", CommonUtils.parseStr(getFawh()));

        json.put("sat", CommonUtils.parseDouble(getFat()));
        json.put("saz", CommonUtils.parseDouble(getFaz()));
        json.put("saw", CommonUtils.parseDouble(getFaw()));
        json.put("sah", CommonUtils.parseDouble(getFah()));
        json.put("sawh", CommonUtils.parseStr(getFawh()));

        json.put("spt", CommonUtils.parseDouble(getFat()));
        json.put("spz", CommonUtils.parseDouble(getFaz()));
        json.put("spw", CommonUtils.parseDouble(getFaw()));
        json.put("sph", CommonUtils.parseDouble(getFah()));
        json.put("spwh", CommonUtils.parseStr(getFawh()));

        return json;
    }
}

