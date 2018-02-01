package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.cms.entity.ApiInfo;
import com.jspgou.cms.entity.ApiRecord;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseApiRecord
        implements Serializable {
    public static String REF = "ApiRecord";
    public static String PROP_API_ACCOUNT = "apiAccount";
    public static String PROP_CALL_TIME_STAMP = "callTimeStamp";
    public static String PROP_SIGN = "sign";
    public static String PROP_API_INFO = "apiInfo";
    public static String PROP_ID = "id";
    public static String PROP_API_IP = "apiIp";
    public static String PROP_API_CALL_TIME = "apiCallTime";

    private int hashCode = -2147483648;
    private Long id;
    private String apiIp;
    private Date apiCallTime;
    private Long callTimeStamp;
    private String sign;
    private ApiAccount apiAccount;
    private ApiInfo apiInfo;

    public BaseApiRecord() {
        initialize();
    }

    public BaseApiRecord(Long id) {
        setId(id);
        initialize();
    }

    public BaseApiRecord(Long id, ApiAccount apiAccount, Date apiCallTime, Long callTimeStamp, String sign) {
        setId(id);
        setApiAccount(apiAccount);
        setApiCallTime(apiCallTime);
        setCallTimeStamp(callTimeStamp);
        setSign(sign);
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

    public String getApiIp() {
        return this.apiIp;
    }

    public void setApiIp(String apiIp) {
        this.apiIp = apiIp;
    }

    public Date getApiCallTime() {
        return this.apiCallTime;
    }

    public void setApiCallTime(Date apiCallTime) {
        this.apiCallTime = apiCallTime;
    }

    public Long getCallTimeStamp() {
        return this.callTimeStamp;
    }

    public void setCallTimeStamp(Long callTimeStamp) {
        this.callTimeStamp = callTimeStamp;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ApiAccount getApiAccount() {
        return this.apiAccount;
    }

    public void setApiAccount(ApiAccount apiAccount) {
        this.apiAccount = apiAccount;
    }

    public ApiInfo getApiInfo() {
        return this.apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ApiRecord)) return false;

        ApiRecord apiRecord = (ApiRecord) obj;
        if ((getId() == null) || (apiRecord.getId() == null)) return false;
        return getId().equals(apiRecord.getId());
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

