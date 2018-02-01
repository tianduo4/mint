package com.jspgou.core.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.core.entity.base.BaseLog;

import java.util.Date;

import net.sf.json.JSONObject;

public class Log extends BaseLog {
    private static final long serialVersionUID = 1L;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final int OPERATING = 3;
    public static final String LOGIN_SUCCESS_TITLE = "login success";
    public static final String LOGIN_FAILURE_TITLE = "login failure";

    public Log() {
    }

    public Log(Long id) {
        super(id);
    }

    public Log(Long id, Integer category, Date time) {
        super(id,
                category,
                time);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("username", getUser() != null ? CommonUtils.parseStr(getUser().getUsername()) : "");
        json.put("ip", CommonUtils.parseStr(getIp()));
        json.put("url", CommonUtils.parseStr(getUrl()));
        json.put("title", CommonUtils.parseStr(getTitle()));
        json.put("content", CommonUtils.parseStr(getContent()));
        return json;
    }
}

