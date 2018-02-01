package com.mint.plug.weixin.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.plug.weixin.entity.base.BaseWeixin;
import net.sf.json.JSONObject;

public class Weixin extends BaseWeixin {
    private static final long serialVersionUID = 1L;

    public Weixin() {
    }

    public Weixin(Long id) {
        super(id);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("welcome", CommonUtils.parseStr(getWelcome()));
        json.put("pic", CommonUtils.parseStr(getPic()));
        json.put("appKey", CommonUtils.parseStr(getAppKey()));
        json.put("appSecret", CommonUtils.parseStr(getAppSecret()));
        json.put("token", CommonUtils.parseStr(getToken()));
        return json;
    }
}

