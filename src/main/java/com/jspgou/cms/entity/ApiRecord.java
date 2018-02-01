package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseApiRecord;
import com.jspgou.common.util.DateUtils;

import java.util.Date;

import net.sf.json.JSONObject;

public class ApiRecord extends BaseApiRecord {
    private static final long serialVersionUID = 1L;

    public ApiRecord() {
    }

    public ApiRecord(Long id) {
        super(id);
    }

    public ApiRecord(Long id, ApiAccount apiAccount, Date apiCallTime, Long callTimeStamp, String sign) {
        super(id,
                apiAccount,
                apiCallTime,
                callTimeStamp,
                sign);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("apiIp", CommonUtils.parseStr(getApiIp()));
        json.put("apiCallTime", CommonUtils.parseDate(getApiCallTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("sign", CommonUtils.parseStr(getSign()));
        json.put("appId", getApiAccount() != null ? CommonUtils.parseStr(getApiAccount().getAppId()) : "");
        return json;
    }
}

