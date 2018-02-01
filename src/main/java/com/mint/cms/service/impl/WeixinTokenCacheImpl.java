package com.mint.cms.service.impl;

import com.mint.cms.service.WeixinSvc;
import com.mint.cms.service.WeixinTokenCache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WeixinTokenCacheImpl
        implements WeixinTokenCache {
    private int interval = 3600000;

    private long refreshTime = System.currentTimeMillis();

    @Autowired
    private WeixinSvc weixinSvc;
    private Ehcache cache;

    public String getToken() {
        String token = "";
        Element e = this.cache.get("token");
        if (e != null) {
            token = (String) e.getValue();
            String tokenFresh = refreshCache();
            if (StringUtils.isNotBlank(tokenFresh))
                token = tokenFresh;
        } else {
            token = this.weixinSvc.getToken();
            this.cache.put(new Element("token", token));
        }
        return token;
    }

    private String refreshCache() {
        long time = System.currentTimeMillis();
        String token = "";
        if (time > this.refreshTime + this.interval) {
            this.refreshTime = time;

            token = this.weixinSvc.getToken();
            this.cache.put(new Element("token", token));
        }
        return token;
    }

    public void setInterval(int interval) {
        this.interval = (interval * 60 * 1000);
    }

    @Autowired
    public void setCache(@Qualifier("tokenCache") Ehcache cache) {
        this.cache = cache;
    }
}

