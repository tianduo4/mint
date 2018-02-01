package com.mint.plug.weixin.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.plug.weixin.entity.base.BaseWeixinMessage;
import net.sf.json.JSONObject;

public class WeixinMessage extends BaseWeixinMessage {
    private static final long serialVersionUID = 1L;
    public static final int CONTENT_ONLY = 2;
    public static final int CONTENT_WITH_KEY = 1;
    public static final int CONTENT_WITH_IMG = 0;

    public Boolean getWelcome() {
        return super.isWelcome();
    }

    public WeixinMessage() {
    }

    public WeixinMessage(Integer id) {
        super(id);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();

        json.put("id", CommonUtils.parseId(getId()));
        json.put("number", CommonUtils.parseStr(getNumber()));
        json.put("title", CommonUtils.parseStr(getTitle()));
        json.put("path", CommonUtils.parseStr(getPath()));
        json.put("url", CommonUtils.parseStr(getUrl()));
        json.put("content", CommonUtils.parseStr(getContent()));
        json.put("welcome", CommonUtils.parseBoolean(getWelcome()));
        json.put("type", Integer.valueOf(CommonUtils.parseInteger(getType())));

        return json;
    }
}

