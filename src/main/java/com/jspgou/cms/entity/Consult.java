package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseConsult;
import com.jspgou.common.util.DateUtils;
import net.sf.json.JSONObject;

public class Consult extends BaseConsult {
    private static final long serialVersionUID = 1L;

    public Consult() {
    }

    public Consult(Long id) {
        super(id);
    }

    public Consult(Long id, ShopMember member, Product product) {
        super(id,
                member,
                product);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("consult", CommonUtils.parseStr(getConsult()));
        json.put("adminReplay", CommonUtils.parseStr(getAdminReplay()));
        json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("userName", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
        json.put("productName", getProduct() != null ? CommonUtils.parseStr(getProduct().getName()) : "");

        return json;
    }
}

