package com.jspgou.plug.weixin.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.plug.weixin.entity.base.BaseWeixinMenu;
import net.sf.json.JSONObject;

public class WeixinMenu extends BaseWeixinMenu {
    private static final long serialVersionUID = 1L;

    public WeixinMenu() {
    }

    public WeixinMenu(Integer id) {
        super(id);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("type", CommonUtils.parseStr(getType()));
        json.put("url", CommonUtils.parseStr(getUrl()));
        json.put("key", CommonUtils.parseStr(getKey()));
        json.put("parentId", getParent() != null ? Integer.valueOf(CommonUtils.parseInteger(getParent().getId())) : "");
        return json;
    }
}

