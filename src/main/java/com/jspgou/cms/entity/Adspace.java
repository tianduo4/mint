package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseAdspace;
import org.json.JSONException;
import org.json.JSONObject;

public class Adspace extends BaseAdspace {
    private static final long serialVersionUID = 1L;

    public Adspace() {
    }

    public Adspace(Integer id) {
        super(id);
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("description", CommonUtils.parseStr(getName()));
        json.put("enabled", CommonUtils.parseBoolean(getEnabled()));
        return json;
    }
}

