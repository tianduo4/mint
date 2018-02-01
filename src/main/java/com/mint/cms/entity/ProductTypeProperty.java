package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseProductTypeProperty;
import net.sf.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductTypeProperty extends BaseProductTypeProperty {
    private static final long serialVersionUID = 1L;

    public ProductTypeProperty() {
    }

    public ProductTypeProperty(Long id) {
        super(id);
    }

    public ProductTypeProperty(Long id, ProductType propertyType, String propertyName, String field, Integer isStart, Integer isNotNull, Integer sort, Integer dataType, Boolean single, Boolean category, Boolean custom) {
        super(id,
                propertyType,
                propertyName,
                field,
                isStart,
                isNotNull,
                sort,
                dataType,
                single,
                category,
                custom);
    }

    public JSONObject convertToJson(Long typeId, String name)
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("field", CommonUtils.parseStr(getField()));
        json.put("dataType", CommonUtils.parseInteger(getDataType()));
        json.put("propertyName", CommonUtils.parseStr(getPropertyName()));
        json.put("sort", CommonUtils.parseInteger(getSort()));
        json.put("single", CommonUtils.parseBoolean(getSingle()));
        json.put("name", CommonUtils.parseStr(name));
        json.put("typeId", CommonUtils.parseLong(typeId));
        if ((getDataType() != null) && ((getDataType().intValue() == 6) || (getDataType().intValue() == 7) || (getDataType().intValue() == 8)))
            json.put("optValue", CommonUtils.parseStr(getOptValue()).split(","));
        else {
            json.put("optValue", new JSONArray());
        }
        return json;
    }
}

