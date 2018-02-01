package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseWebserviceAuth;
import net.sf.json.JSONObject;

public class WebserviceAuth extends BaseWebserviceAuth {
    private static final long serialVersionUID = 1L;

    public boolean getEnable() {
        return super.isEnable();
    }

    public WebserviceAuth() {
    }

    public WebserviceAuth(Integer id) {
        super(id);
    }

    public WebserviceAuth(Integer id, String username, String password, String system, boolean enable) {
        super(id,
                username,
                password,
                system,
                enable);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("username", CommonUtils.parseStr(getUsername()));
        json.put("password", "");
        json.put("system", CommonUtils.parseStr(getSystem()));
        json.put("enable", CommonUtils.parseBoolean(Boolean.valueOf(getEnable())));

        return json;
    }
}

