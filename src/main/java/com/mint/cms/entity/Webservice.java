package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseWebservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Webservice extends BaseWebservice {
    private static final long serialVersionUID = 1L;
    public static final String SERVICE_TYPE_ADD_USER = "addUser";
    public static final String SERVICE_TYPE_UPDATE_USER = "updateUser";
    public static final String SERVICE_TYPE_DELETE_USER = "deleteUser";

    public void addToParams(String name, String defaultValue) {
        List list = getParams();
        if (list == null) {
            list = new ArrayList();
            setParams(list);
        }
        WebserviceParam param = new WebserviceParam();
        param.setParamName(name);
        param.setDefaultValue(defaultValue);
        list.add(param);
    }

    public Webservice() {
    }

    public Webservice(Integer id) {
        super(id);
    }

    public Webservice(Integer id, String address) {
        super(id,
                address);
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("address", CommonUtils.parseStr(getAddress()));
        json.put("targetNamespace", CommonUtils.parseStr(getTargetNamespace()));
        json.put("successResult", CommonUtils.parseStr(getSuccessResult()));
        json.put("type", CommonUtils.parseStr(getType()));
        json.put("operate", CommonUtils.parseStr(getOperate()));

        List list = getParams();
        JSONArray jsonArray = new JSONArray();
        if ((list != null) && (list.size() > 0)) {
            JSONObject obj = new JSONObject();
            for (int i = 0; i < list.size(); i++) {
                obj.put("paramName", CommonUtils.parseStr(((WebserviceParam) list.get(i)).getParamName()));
                obj.put("defaultValue", CommonUtils.parseStr(((WebserviceParam) list.get(i)).getDefaultValue()));
                jsonArray.put(obj);
            }
            json.put("param", jsonArray);
        }
        return json;
    }
}

