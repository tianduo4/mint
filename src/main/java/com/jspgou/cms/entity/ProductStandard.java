package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseProductStandard;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductStandard extends BaseProductStandard {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
        json.put("standardId", getStandard() == null ? "" : CommonUtils.parseLong(getStandard().getId()));
        json.put("standardName", getStandard() == null ? "" : CommonUtils.parseStr(getStandard().getName()));
        json.put("imgPath", CommonUtils.parseStr(getImgPath()));
        return json;
    }

    public ProductStandard() {
    }

    public ProductStandard(Long id) {
        super(id);
    }

    public ProductStandard(Long id, Product product, Standard standard, StandardType type, String imgPath, boolean dataType) {
        super(id,
                product,
                standard,
                type,
                imgPath,
                dataType);
    }
}

