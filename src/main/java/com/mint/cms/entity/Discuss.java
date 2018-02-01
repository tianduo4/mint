package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseDiscuss;
import com.mint.common.util.DateUtils;
import net.sf.json.JSONObject;

public class Discuss extends BaseDiscuss {
    private static final long serialVersionUID = 1L;

    public Discuss() {
    }

    public Discuss(Long id) {
        super(id);
    }

    public Discuss(Long id, ShopMember member, Product product, String content) {
        super(id,
                member,
                product,
                content);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("content", CommonUtils.parseStr(getContent()));
        json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("replay", CommonUtils.parseStr(getReplay()));
        json.put("discussType", CommonUtils.parseStr(getDiscussType()));
        json.put("userName", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
        json.put("productName", getProduct() != null ? CommonUtils.parseStr(getProduct().getName()) : "");
        return json;
    }
}

