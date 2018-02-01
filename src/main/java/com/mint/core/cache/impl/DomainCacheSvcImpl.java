package com.mint.core.cache.impl;

import com.mint.core.cache.DomainCacheSvc;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DomainCacheSvcImpl
        implements DomainCacheSvc {
    private Ehcache domainCache;

    @Autowired
    public void setDomainCache(@Qualifier("domain") Ehcache domainCache) {
        this.domainCache = domainCache;
    }

    public void put(String paramString, String[] paramArrayOfString, Long paramLong) {
        this.domainCache.put(new Element(paramString, paramLong));
        if (paramArrayOfString != null)
            for (String s1 : paramArrayOfString)
                this.domainCache.put(new Element(s1, paramLong));
    }

    public boolean remove(String paramString, String[] as) {
        if (as != null) {
            for (String s1 : as) {
                this.domainCache.remove(s1);
            }
        }
        return this.domainCache.remove(paramString);
    }

    public Long get(String paramString) {
        Element element = this.domainCache.get(paramString);
        if (element != null) {
            return (Long) element.getValue();
        }
        return null;
    }

    public void removeAll() {
        this.domainCache.removeAll();
    }
}

