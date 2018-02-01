package com.mint.cms.entity.base;

import com.mint.cms.entity.WebserviceAuth;
import com.mint.cms.entity.WebserviceCallRecord;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseWebserviceCallRecord
        implements Serializable {
    public static String REF = "WebserviceCallRecord";
    public static String PROP_SERVICE_CODE = "serviceCode";
    public static String PROP_ID = "id";
    public static String PROP_RECORD_TIME = "recordTime";
    public static String PROP_AUTH = "auth";

    private int hashCode = -2147483648;
    private Integer id;
    private String serviceCode;
    private Date recordTime;
    private WebserviceAuth auth;

    public BaseWebserviceCallRecord() {
        initialize();
    }

    public BaseWebserviceCallRecord(Integer id) {
        setId(id);
        initialize();
    }

    public BaseWebserviceCallRecord(Integer id, WebserviceAuth auth, String serviceCode, Date recordTime) {
        setId(id);
        setAuth(auth);
        setServiceCode(serviceCode);
        setRecordTime(recordTime);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Date getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public WebserviceAuth getAuth() {
        return this.auth;
    }

    public void setAuth(WebserviceAuth auth) {
        this.auth = auth;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof WebserviceCallRecord)) return false;

        WebserviceCallRecord cmsWebserviceCallRecord = (WebserviceCallRecord) obj;
        if ((getId() == null) || (cmsWebserviceCallRecord.getId() == null)) return false;
        return getId().equals(cmsWebserviceCallRecord.getId());
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

