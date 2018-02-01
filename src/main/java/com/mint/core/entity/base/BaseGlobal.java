package com.mint.core.entity.base;

import com.mint.core.entity.Global;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseGlobal
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Global";
    public static String PROP_CONTEXT_PATH = "contextPath";
    public static String PROP_PORT = "port";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String contextPath;
    private Integer port;
    private String defImg;
    private String treaty;
    private Integer activeScore;
    private Integer stockWarning;
    private String processUrl;
    private String password;
    private Map<String, String> attr;

    public BaseGlobal() {
        initialize();
    }

    public BaseGlobal(Long id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDefImg() {
        return this.defImg;
    }

    public void setDefImg(String defImg) {
        this.defImg = defImg;
    }

    public String getTreaty() {
        return this.treaty;
    }

    public void setTreaty(String treaty) {
        this.treaty = treaty;
    }

    public Integer getActiveScore() {
        return this.activeScore;
    }

    public void setActiveScore(Integer activeScore) {
        this.activeScore = activeScore;
    }

    public Integer getStockWarning() {
        return this.stockWarning;
    }

    public void setStockWarning(Integer stockWarning) {
        this.stockWarning = stockWarning;
    }

    public String getProcessUrl() {
        return this.processUrl;
    }

    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }

    public Map<String, String> getAttr() {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Global)) return false;

        Global global = (Global) obj;
        if ((getId() == null) || (global.getId() == null)) return false;
        return getId().equals(global.getId());
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

