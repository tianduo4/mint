package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseAddress;
import net.sf.json.JSONObject;

public class Address extends BaseAddress {
    private static final long serialVersionUID = 1L;

    public Address() {
    }

    public Address(Long id) {
        super(id);
    }

    public Address(Long id, String name) {
        super(id,
                name);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("parentId", getParent() != null ? CommonUtils.parseLong(getParent().getId()) : "");
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        return json;
    }
}

