package com.mint.core.cache;

public abstract interface DomainCacheSvc {
    public abstract void put(String paramString, String[] paramArrayOfString, Long paramLong);

    public abstract boolean remove(String paramString, String[] paramArrayOfString);

    public abstract Long get(String paramString);

    public abstract void removeAll();
}

