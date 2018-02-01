package com.mint.cms.entity.base;

import com.mint.cms.entity.ApiInfo;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseApiInfo
        implements Serializable {
    public static String REF = "ApiInfo";
    public static String PROP_LAST_CALL_TIME = "lastCallTime";
    public static String PROP_API_URL = "apiUrl";
    public static String PROP_API_NAME = "apiName";
    public static String PROP_API_CODE = "apiCode";
    public static String PROP_LIMIT_CALL_DAY = "limitCallDay";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_ID = "id";
    public static String PROP_CALL_MONTH_COUNT = "callMonthCount";
    public static String PROP_CALL_DAY_COUNT = "callDayCount";
    public static String PROP_CALL_WEEK_COUNT = "callWeekCount";
    public static String PROP_CALL_TOTAL_COUNT = "callTotalCount";

    private int hashCode = -2147483648;
    private Long id;
    private String apiName;
    private String apiUrl;
    private String apiCode;
    private Boolean disabled;
    private Integer limitCallDay;
    private Integer callTotalCount;
    private Integer callMonthCount;
    private Integer callWeekCount;
    private Integer callDayCount;
    private Date lastCallTime;

    public BaseApiInfo() {
        initialize();
    }

    public BaseApiInfo(Long id) {
        setId(id);
        initialize();
    }

    public BaseApiInfo(Long id, String apiName, String apiUrl, String apiCode, Boolean disabled, Integer limitCallDay, Integer callTotalCount, Integer callMonthCount, Integer callWeekCount, Integer callDayCount) {
        setId(id);
        setApiName(apiName);
        setApiUrl(apiUrl);
        setApiCode(apiCode);
        setDisabled(disabled);
        setLimitCallDay(limitCallDay);
        setCallTotalCount(callTotalCount);
        setCallMonthCount(callMonthCount);
        setCallWeekCount(callWeekCount);
        setCallDayCount(callDayCount);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getApiName() {
        return this.apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiCode() {
        return this.apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getLimitCallDay() {
        return this.limitCallDay;
    }

    public void setLimitCallDay(Integer limitCallDay) {
        this.limitCallDay = limitCallDay;
    }

    public Integer getCallTotalCount() {
        return this.callTotalCount;
    }

    public void setCallTotalCount(Integer callTotalCount) {
        this.callTotalCount = callTotalCount;
    }

    public Integer getCallMonthCount() {
        return this.callMonthCount;
    }

    public void setCallMonthCount(Integer callMonthCount) {
        this.callMonthCount = callMonthCount;
    }

    public Integer getCallWeekCount() {
        return this.callWeekCount;
    }

    public void setCallWeekCount(Integer callWeekCount) {
        this.callWeekCount = callWeekCount;
    }

    public Integer getCallDayCount() {
        return this.callDayCount;
    }

    public void setCallDayCount(Integer callDayCount) {
        this.callDayCount = callDayCount;
    }

    public Date getLastCallTime() {
        return this.lastCallTime;
    }

    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ApiInfo)) return false;

        ApiInfo apiInfo = (ApiInfo) obj;
        if ((getId() == null) || (apiInfo.getId() == null)) return false;
        return getId().equals(apiInfo.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

