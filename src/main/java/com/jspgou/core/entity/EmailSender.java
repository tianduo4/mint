package com.jspgou.core.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.core.entity.base.BaseEmailSender;
import org.json.JSONException;
import org.json.JSONObject;

public class EmailSender extends BaseEmailSender {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("host", CommonUtils.parseStr(getHost()));
        json.put("username", CommonUtils.parseStr(getUsername()));
        json.put("password", CommonUtils.parseStr(getPassword()));
        json.put("personal", CommonUtils.parseStr(getPersonal()));
        json.put("encoding", CommonUtils.parseStr(getEncoding()));
        return json;
    }
}

