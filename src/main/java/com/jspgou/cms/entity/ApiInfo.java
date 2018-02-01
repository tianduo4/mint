package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseApiInfo;
import com.jspgou.common.util.DateUtils;
import net.sf.json.JSONObject;

public class ApiInfo extends BaseApiInfo {
    private static final long serialVersionUID = 1L;

    public ApiInfo() {
    }

    public ApiInfo(Long id) {
        super(id);
    }

    public ApiInfo(Long id, String apiName, String apiUrl, String apiCode, boolean disabled, Integer limitCallDay, Integer callTotalCount, Integer callMonthCount, Integer callWeekCount, Integer callDayCount) {
        super(id,
                apiName,
                apiUrl,
                apiCode,
                Boolean.valueOf(disabled),
                limitCallDay,
                callTotalCount,
                callMonthCount,
                callWeekCount,
                callDayCount);
    }

    public void init() {
        if (getCallTotalCount() == null) {
            setCallTotalCount(Integer.valueOf(0));
        }
        if (getCallMonthCount() == null) {
            setCallMonthCount(Integer.valueOf(0));
        }
        if (getCallWeekCount() == null) {
            setCallWeekCount(Integer.valueOf(0));
        }
        if (getCallDayCount() == null)
            setCallDayCount(Integer.valueOf(0));
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("apiName", CommonUtils.parseStr(getApiName()));
        json.put("apiUrl", CommonUtils.parseStr(getApiUrl()));
        json.put("apiCode", CommonUtils.parseStr(getApiCode()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("limitCallDay", Integer.valueOf(CommonUtils.parseInteger(getLimitCallDay())));
        json.put("callTotalCount", Integer.valueOf(CommonUtils.parseInteger(getCallTotalCount())));
        json.put("callMonthCount", Integer.valueOf(CommonUtils.parseInteger(getCallMonthCount())));
        json.put("callWeekCount", Integer.valueOf(CommonUtils.parseInteger(getCallWeekCount())));
        json.put("callDayCount", Integer.valueOf(CommonUtils.parseInteger(getCallDayCount())));
        json.put("lastCallTime", CommonUtils.parseDate(getLastCallTime(), DateUtils.COMMON_FORMAT_STR));

        return json;
    }
}

