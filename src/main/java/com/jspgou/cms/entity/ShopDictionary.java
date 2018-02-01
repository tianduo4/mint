package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseShopDictionary;
import net.sf.json.JSONObject;

public class ShopDictionary extends BaseShopDictionary {
    private static final long serialVersionUID = 1L;

    public ShopDictionary() {
    }

    public ShopDictionary(Long id) {
        super(id);
    }

    public ShopDictionary(Long id, ShopDictionaryType shopDictionaryType, String name) {
        super(id,
                shopDictionaryType,
                name);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        json.put("typeId", getShopDictionaryType() != null ? CommonUtils.parseLong(getShopDictionaryType().getId()) : "");
        json.put("typeName", getShopDictionaryType() != null ? CommonUtils.parseStr(getShopDictionaryType().getName()) : "");
        return json;
    }
}

