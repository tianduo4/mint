package com.jspgou.core.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.core.entity.base.BaseGlobal;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Global extends BaseGlobal {
    private static final long serialVersionUID = 1L;

    public Global() {
    }

    public Global(Long id) {
        super(id);
    }

    public ConfigAttr getConfigAttr() {
        ConfigAttr configAttr = new ConfigAttr(getAttr());
        return configAttr;
    }

    public void setConfigAttr(ConfigAttr configAttr) {
        getAttr().putAll(configAttr.getAttr());
    }

    public Boolean getQqEnable() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqEnable();
    }

    public Boolean getSinaEnable() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getSinaEnable();
    }

    public Boolean getQqWeboEnable() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqWeboEnable();
    }

    public String getQqID() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqID();
    }

    public String getSinaID() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getSinaID();
    }

    public String getQqWeboID() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqWeboID();
    }

    public Boolean getWeixinEnable() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinEnable();
    }

    public String getWeixinID() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinID();
    }

    public String getWeixinKey() {
        ConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinKey();
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("contextPath", CommonUtils.parseStr(getContextPath()));
        json.put("port", CommonUtils.parseInteger(getPort()));
        json.put("treaty", CommonUtils.parseStr(getTreaty()));
        json.put("activeScore", CommonUtils.parseInteger(getActiveScore()));
        json.put("stockWarning", CommonUtils.parseInteger(getStockWarning()));
        json.put("defImg", CommonUtils.parseStr(getDefImg()));
        json.put("password", CommonUtils.parseStr(getPassword()));
        return json;
    }
}

