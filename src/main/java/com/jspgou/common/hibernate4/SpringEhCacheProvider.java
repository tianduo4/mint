package com.jspgou.common.hibernate4;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ObjectExistsException;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public final class SpringEhCacheProvider extends EhCacheRegionFactory {
    private static final long serialVersionUID = -8476537268197646948L;
    private static final Logger log = LoggerFactory.getLogger(SpringEhCacheProvider.class);
    private Resource configLocation;
    private Resource diskStoreLocation;
    private CacheManager manager;

    public void setConfigLocation(Resource resource) {
        this.configLocation = resource;
    }

    public void setDiskStoreLocation(Resource resource) {
        this.diskStoreLocation = resource;
    }

    public final void start(Properties properties)
            throws CacheException {
        if (this.manager != null) {
            String s = "Attempt to restart an already started EhCacheProvider. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Using previously created EhCacheProvider. If this behaviour is required, consider using SingletonEhCacheProvider.";

            log.warn(s);
            return;
        }
        Configuration config = null;
        try {
            if (this.configLocation != null) {
                config = ConfigurationFactory.parseConfiguration(this.configLocation
                        .getInputStream());
                if (this.diskStoreLocation != null) {
                    DiskStoreConfiguration dc = new DiskStoreConfiguration();
                    dc.setPath(this.diskStoreLocation.getFile()
                            .getAbsolutePath());
                    try {
                        config.addDiskStore(dc);
                    } catch (ObjectExistsException e) {
                        String s = "if you want to config distStore in spring, please remove diskStore in config file!";

                        log.warn(s, e);
                    }
                }
            }
        } catch (IOException e) {
            log.warn("create ehcache config failed!", e);
        }
        if (config != null)
            this.manager = new CacheManager(config);
        else
            this.manager = new CacheManager();
    }

    public final void stop() {
        if (this.manager != null) {
            this.manager.shutdown();
            this.manager = null;
        }
    }

    public final boolean isMinimalPutsEnabledByDefault() {
        return false;
    }
}

