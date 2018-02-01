package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseCustomerService;
import net.sf.json.JSONObject;

public class CustomerService extends BaseCustomerService {
    private static final long serialVersionUID = 1L;

    public CustomerService() {
    }

    public CustomerService(Long id) {
        super(id);
    }

    public CustomerService(Long id, String name, String type, String content, Integer priority, Boolean disable) {
        super(id,
                name,
                type,
                content,
                priority,
                disable);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("type", CommonUtils.parseStr(getType()));
        json.put("content", CommonUtils.parseStr(getContent()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        json.put("disable", CommonUtils.parseBoolean(getDisable()));
        return json;
    }
}

