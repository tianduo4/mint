package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BasePaymentPlugins;
import net.sf.json.JSONObject;

public class PaymentPlugins extends BasePaymentPlugins {
    private static final long serialVersionUID = 1L;

    public PaymentPlugins() {
    }

    public PaymentPlugins(Long id) {
        super(id);
    }

    public PaymentPlugins(Long id, String name, String code, Integer priority, Boolean disabled) {
        super(id,
                name,
                code,
                priority,
                disabled);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("description", CommonUtils.parseStr(getDescription()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        json.put("imgPath", CommonUtils.parseStr(getImgPath()));
        json.put("partner", CommonUtils.parseStr(getPartner()));
        json.put("sellerEmail", CommonUtils.parseStr(getSellerEmail()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
        json.put("sellerKey", CommonUtils.parseStr(getSellerKey()));
        json.put("code", CommonUtils.parseStr(getCode()));

        return json;
    }
}

