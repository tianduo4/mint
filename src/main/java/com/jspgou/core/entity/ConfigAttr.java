package com.jspgou.core.entity;

import com.jspgou.cms.api.CommonUtils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ConfigAttr {
    private Map<String, String> attr;
    public static final String PICTURENEW = "new_picture";
    public static final String DAYNEW = "day";
    public static final String PREVIEW = "preview";
    public static final String QQ_ENABLE = "qqEnable";
    public static final String QQ_ID = "qqID";
    public static final String QQ_KEY = "qqKey";
    public static final String SINA_ENABLE = "sinaEnable";
    public static final String SINA_ID = "sinaID";
    public static final String SINA_KEY = "sinaKey";
    public static final String QQWEBO_ENABLE = "qqWeboEnable";
    public static final String QQWEBO_ID = "qqWeboID";
    public static final String QQWEBO_KEY = "qqWeboKey";
    public static final String WEIXIN_ENABLE = "weixinEnable";
    public static final String WEIXIN_ID = "weixinID";
    public static final String WEIXIN_KEY = "weixinKey";
    public static final String SSO_ENABLE = "ssoEnable";
    public static final String FLOW_SWITCH = "flowSwitch";
    public static final String CONTENT_FRESH_MINUTE = "contentFreshMinute";
    public static final String CODE_IMG_WIDTH = "codeImgWidth";
    public static final String CODE_IMG_HEIGHT = "codeImgHeight";

    public ConfigAttr() {
    }

    public ConfigAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public Map<String, String> getAttr() {
        if (this.attr == null) {
            this.attr = new HashMap();
        }
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public String getPictureNew() {
        return (String) getAttr().get("new_picture");
    }

    public int getDayNew() {
        String day = (String) getAttr().get("day");
        if (StringUtils.isNotBlank(day)) {
            return Integer.parseInt(day);
        }
        return 0;
    }

    public Boolean getSsoEnable() {
        String enable = (String) getAttr().get("ssoEnable");
        return Boolean.valueOf(!"false".equals(enable));
    }

    public Boolean getFlowSwitch() {
        String flowSwitch = (String) getAttr().get("flowSwitch");
        return Boolean.valueOf(!"false".equals(flowSwitch));
    }

    public void setPictureNew(String path) {
        getAttr().put("new_picture", path);
    }

    public void setDayNew(Integer day) {
        getAttr().put("day", day.toString());
    }

    public Boolean getPreview() {
        String preview = (String) getAttr().get("preview");
        return Boolean.valueOf(!"false".equals(preview));
    }

    public void setPreview(boolean preview) {
        getAttr().put("preview", String.valueOf(preview));
    }

    public Boolean getQqEnable() {
        String enable = (String) getAttr().get("qqEnable");
        return Boolean.valueOf(!"false".equals(enable));
    }

    public String getQqID() {
        return (String) getAttr().get("qqID");
    }

    public String getQqKey() {
        return (String) getAttr().get("qqKey");
    }

    public Boolean getSinaEnable() {
        String enable = (String) getAttr().get("sinaEnable");
        return Boolean.valueOf(!"false".equals(enable));
    }

    public String getSinaID() {
        return (String) getAttr().get("sinaID");
    }

    public String getSinaKey() {
        return (String) getAttr().get("sinaKey");
    }

    public Boolean getQqWeboEnable() {
        String enable = (String) getAttr().get("qqWeboEnable");
        return Boolean.valueOf(!"false".equals(enable));
    }

    public String getQqWeboID() {
        return (String) getAttr().get("qqWeboID");
    }

    public String getQqWeboKey() {
        return (String) getAttr().get("qqWeboKey");
    }

    public Boolean getWeixinEnable() {
        String enable = (String) getAttr().get("weixinEnable");
        return Boolean.valueOf(!"false".equals(enable));
    }

    public String getWeixinID() {
        return (String) getAttr().get("weixinID");
    }

    public Integer getContentFreshMinute() {
        return Integer.valueOf(Integer.parseInt((String) getAttr().get("contentFreshMinute")));
    }

    public String getWeixinKey() {
        return (String) getAttr().get("weixinKey");
    }

    public void setQqEnable(boolean enable) {
        getAttr().put("qqEnable", String.valueOf(enable));
    }

    public void setQqID(String id) {
        getAttr().put("qqID", id);
    }

    public void setQqKey(String key) {
        getAttr().put("qqKey", key);
    }

    public void setSinaEnable(boolean enable) {
        getAttr().put("sinaEnable", String.valueOf(enable));
    }

    public void setFlowSwitch(boolean flowSwitch) {
        getAttr().put("flowSwitch", String.valueOf(flowSwitch));
    }

    public void setContentFreshMinute(Integer minute) {
        getAttr().put("contentFreshMinute", String.valueOf(minute));
    }

    public void setSinaID(String id) {
        getAttr().put("sinaID", id);
    }

    public void setSinaKey(String key) {
        getAttr().put("sinaKey", key);
    }

    public void setQqWeboEnable(boolean enable) {
        getAttr().put("qqWeboEnable", String.valueOf(enable));
    }

    public void setQqWeboID(String id) {
        getAttr().put("qqWeboID", id);
    }

    public void setQqWeboKey(String key) {
        getAttr().put("qqWeboKey", key);
    }

    public void setWeixinEnable(boolean enable) {
        getAttr().put("weixinEnable", String.valueOf(enable));
    }

    public void setWeixinID(String id) {
        getAttr().put("weixinID", id);
    }

    public void setWeixinKey(String key) {
        getAttr().put("weixinKey", key);
    }

    public int getCodeImgWidth() {
        String width = (String) getAttr().get("codeImgWidth");
        if (StringUtils.isNotBlank(width)) {
            return Integer.parseInt(width);
        }
        return 100;
    }

    public void setCodeImgWidth(Integer width) {
        getAttr().put("codeImgWidth", width.toString());
    }

    public int getCodeImgHeight() {
        String height = (String) getAttr().get("codeImgHeight");
        if (StringUtils.isNotBlank(height)) {
            return Integer.parseInt(height);
        }
        return 100;
    }

    public void setCodeImgHeight(Integer height) {
        getAttr().put("codeImgHeight", height.toString());
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("sinaKey", "");
        json.put("qqKey", "");
        json.put("weixinKey", "");
        json.put("weixinEnable", CommonUtils.parseBoolean(getWeixinEnable()));
        json.put("weixinID", CommonUtils.parseStr(getWeixinID()));
        json.put("sinaEnable", CommonUtils.parseBoolean(getSinaEnable()));
        json.put("qqEnable", CommonUtils.parseBoolean(getQqEnable()));
        json.put("sinaID", CommonUtils.parseStr(getSinaID()));
        json.put("qqID", CommonUtils.parseStr(getQqID()));
        return json;
    }
}

