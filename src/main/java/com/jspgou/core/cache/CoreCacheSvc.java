package com.jspgou.core.cache;

public abstract interface CoreCacheSvc {
    public abstract void putWebsiteCount(int paramInt);

    public abstract Integer getWebsiteCount();

    public abstract void putWebsiteId(Long paramLong);

    public abstract boolean removeWebsiteId();

    public abstract Long getWebsiteId();
}

