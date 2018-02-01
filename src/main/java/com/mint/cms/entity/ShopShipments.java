package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShopShipments;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopShipments extends BaseShopShipments {
    private static final long serialVersionUID = 1L;

    public ShopShipments() {
    }

    public ShopShipments(Long id) {
        super(id);
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseLong(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("mobile", CommonUtils.parseStr(getMobile()));
        json.put("address", CommonUtils.parseStr(getAddress()));
        json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
        return json;
    }

    public ShopShipments(Long id, String name, String mobile, String address, Boolean isDefault) {
        super(id,
                name,
                mobile,
                address,
                isDefault);
    }
}

