package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShopMemberGroup;
import com.mint.core.entity.Website;
import net.sf.json.JSONObject;

public class ShopMemberGroup extends BaseShopMemberGroup {
    private static final long serialVersionUID = 1L;

    public ShopMemberGroup() {
    }

    public ShopMemberGroup(Long id) {
        super(id);
    }

    public ShopMemberGroup(Long id, Website website, String name, Integer score, Integer discount) {
        super(id,
                website,
                name,
                score,
                discount);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("score", Integer.valueOf(CommonUtils.parseInteger(getScore())));
        json.put("discount", Integer.valueOf(CommonUtils.parseInteger(getDiscount())));
        return json;
    }
}

