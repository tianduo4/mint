package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseApiAccount;
import net.sf.json.JSONObject;

public class ApiAccount extends BaseApiAccount {
    private static final long serialVersionUID = 1L;

    public ApiAccount() {
    }

    public ApiAccount(Long id) {
        super(id);
    }

    public ApiAccount(Long id, String appId, String appKey, String aesKey, boolean disabled) {
        super(id,
                appId,
                appKey,
                aesKey,
                disabled);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("acount", CommonUtils.parseStr(getAppId()));
        json.put("appKey", CommonUtils.parseStr(getAppKey()));
        json.put("aesKey", CommonUtils.parseStr(getAesKey()));
        json.put("disabled", CommonUtils.parseBoolean(Boolean.valueOf(getDisabled())));
        json.put("ivKey", CommonUtils.parseStr(getIvKey()));
        return json;
    }
}

