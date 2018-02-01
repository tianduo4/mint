package com.mint.core.cache.impl;

import com.mint.core.cache.CoreCacheSvc;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CoreCacheSvcImpl
        implements CoreCacheSvc {
    private static final String WEBSITE_COUNT = "core_website_count";
    private static final String WEBSITE_ID = "core_website_id";
    private Ehcache globalCache;

    @Autowired
    public void setGlobalCache(@Qualifier("global") Ehcache globalCache) {
        this.globalCache = globalCache;
    }

    public void putWebsiteCount(int paramInt) {
        this.globalCache.put(new Element("core_website_count", Integer.valueOf(paramInt)));
    }

    public Integer getWebsiteCount() {
        Element element = this.globalCache.get("core_website_count");
        if (element != null) {
            return (Integer) element.getValue();
        }
        return null;
    }

    public void putWebsiteId(Long paramLong) {
        this.globalCache.put(new Element("core_website_id", paramLong));
    }

    public Long getWebsiteId() {
        Element element = this.globalCache.get("core_website_id");
        if (element != null) {
            return (Long) element.getValue();
        }
        return null;
    }

    public boolean removeWebsiteId() {
        return this.globalCache.remove("core_website_id");
    }
}

