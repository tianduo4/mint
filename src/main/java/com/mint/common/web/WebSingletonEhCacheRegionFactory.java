package com.mint.common.web;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.CacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.cfg.Settings;

public class WebSingletonEhCacheRegionFactory extends SingletonEhCacheRegionFactory {
    private static final long serialVersionUID = 7227034033610133673L;
    protected final Log logger = LogFactory.getLog(getClass());

    private static final AtomicInteger REFERENCE_COUNT = new AtomicInteger();

    public WebSingletonEhCacheRegionFactory() {
    }

    public WebSingletonEhCacheRegionFactory(Properties prop) {
    }

    public void start(Settings settings, Properties properties)
            throws org.hibernate.cache.CacheException {
        this.settings = settings;
        try {
            this.manager = CacheManager.getInstance();
            this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
        } catch (net.sf.ehcache.CacheException e) {
            throw new org.hibernate.cache.CacheException(e);
        }
    }

    public void stop() {
        try {
            if (this.manager != null) {
                if (REFERENCE_COUNT.decrementAndGet() == 0) {
                    this.manager.shutdown();
                }
                this.manager = null;
            }
        } catch (net.sf.ehcache.CacheException e) {
            throw new org.hibernate.cache.CacheException(e);
        }
    }
}

