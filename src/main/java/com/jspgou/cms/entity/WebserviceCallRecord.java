package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseWebserviceCallRecord;
import com.jspgou.common.util.DateUtils;

import java.util.Date;

import net.sf.json.JSONObject;

public class WebserviceCallRecord extends BaseWebserviceCallRecord {
    private static final long serialVersionUID = 1L;

    public WebserviceCallRecord() {
    }

    public WebserviceCallRecord(Integer id) {
        super(id);
    }

    public WebserviceCallRecord(Integer id, WebserviceAuth auth, String serviceCode, Date recordTime) {
        super(id,
                auth,
                serviceCode,
                recordTime);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("serviceCode", CommonUtils.parseStr(getServiceCode()));
        json.put("recordTime", CommonUtils.parseDate(getRecordTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("username", getAuth() != null ? CommonUtils.parseStr(getAuth().getUsername()) : "");
        json.put("system", getAuth() != null ? CommonUtils.parseStr(getAuth().getSystem()) : "");
        return json;
    }
}

