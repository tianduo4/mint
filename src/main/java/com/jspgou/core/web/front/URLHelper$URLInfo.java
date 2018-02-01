package com.jspgou.core.web.front;

public class URLHelper$URLInfo {
    private String[] paths;
    private int pageNo;
    private String[] params;
    private String queryString;
    private String urlPrefix;
    private String urlSuffix;

    public URLHelper$URLInfo(String[] paths, int pageNo, String[] params, String urlPrefix, String urlSuffix, String queryString) {
        this.paths = paths;
        this.pageNo = pageNo;
        this.params = params;
        this.urlPrefix = urlPrefix;
        this.urlSuffix = urlSuffix;
        this.queryString = queryString;
    }

    public String[] getPaths() {
        return this.paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String[] getParams() {
        return this.params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getUrlPrefix() {
        return this.urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getUrlSuffix() {
        return this.urlSuffix;
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}

