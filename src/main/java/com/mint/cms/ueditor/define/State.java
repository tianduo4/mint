package com.mint.cms.ueditor.define;

public abstract interface State {
    public abstract boolean isSuccess();

    public abstract void putInfo(String paramString1, String paramString2);

    public abstract void putInfo(String paramString, long paramLong);

    public abstract String toJSONString();

    public abstract String toJSONString2();
}

