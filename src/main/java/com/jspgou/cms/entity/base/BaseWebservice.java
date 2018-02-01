package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Webservice;
import com.jspgou.cms.entity.WebserviceParam;

import java.io.Serializable;
import java.util.List;

public abstract class BaseWebservice
        implements Serializable {
    public static String REF = "Webservice";
    public static String PROP_OPERATE = "operate";
    public static String PROP_TYPE = "type";
    public static String PROP_ADDRESS = "address";
    public static String PROP_TARGET_NAMESPACE = "targetNamespace";
    public static String PROP_ID = "id";
    public static String PROP_SUCCESS_RESULT = "successResult";

    private int hashCode = -2147483648;
    private Integer id;
    private String address;
    private String targetNamespace;
    private String successResult;
    private String type;
    private String operate;
    private List<WebserviceParam> params;

    public BaseWebservice() {
        initialize();
    }

    public BaseWebservice(Integer id) {
        setId(id);
        initialize();
    }

    public BaseWebservice(Integer id, String address) {
        setId(id);
        setAddress(address);
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTargetNamespace() {
        return this.targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    public String getSuccessResult() {
        return this.successResult;
    }

    public void setSuccessResult(String successResult) {
        this.successResult = successResult;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public List<WebserviceParam> getParams() {
        return this.params;
    }

    public void setParams(List<WebserviceParam> params) {
        this.params = params;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Webservice)) return false;

        Webservice cmsWebservice = (Webservice) obj;
        if ((getId() == null) || (cmsWebservice.getId() == null)) return false;
        return getId().equals(cmsWebservice.getId());
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

