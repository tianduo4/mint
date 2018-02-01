package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShopDictionaryType;
import net.sf.json.JSONObject;

public class ShopDictionaryType extends BaseShopDictionaryType {
    private static final long serialVersionUID = 1L;

    public ShopDictionaryType() {
    }

    public ShopDictionaryType(Long id) {
        super(id);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        return json;
    }
}

