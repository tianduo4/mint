package com.mint.core.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.cache.CoreCacheSvc;
import com.mint.core.cache.DomainCacheSvc;
import com.mint.core.dao.WebsiteDao;
import com.mint.core.entity.Website;
import com.mint.core.manager.FtpMng;
import com.mint.core.manager.WebsiteMng;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebsiteMngImpl
        implements WebsiteMng {

    @Autowired
    private FtpMng ftpMng;
    private CoreCacheSvc coreCacheSvc;
    private DomainCacheSvc domainCacheSvc;
    private WebsiteDao dao;

    @Transactional(readOnly = true)
    public Website getWebsite(String s) {
        Integer count = this.coreCacheSvc.getWebsiteCount();
        int i;
        Website website;
        Long id;
        if (count == null) {
            i = this.dao.countWebsite();
            this.coreCacheSvc.putWebsiteCount(new Integer(i).intValue());
        } else {
            i = count.intValue();
        }

        if (i == 1) {
            id = this.coreCacheSvc.getWebsiteId();
            if (id != null) {
                website = findById(id);
            } else {
                website = this.dao.getUniqueWebsite();
                this.coreCacheSvc.putWebsiteId(website.getId());
            }
        } else if (i > 1) {
            id = this.domainCacheSvc.get(s);
            if (id != null) {
                website = findById(id);
            } else {
                website = this.dao.findByDomain(s);
                if (website != null)
                    this.domainCacheSvc.put(website.getDomain(), website.getDomainAliasArray(), website.getId());
            }
        } else {
            throw new IllegalStateException("no website data in database, please init database!");
        }
        return website;
    }

    public Pagination getAllPage(int pageNo, int pageSize) {
        return this.dao.getAllPage(pageNo, pageSize);
    }

    public List<Website> getAllList() {
        return this.dao.getAllList();
    }

    public Website findById(Long id) {
        return this.dao.findById(id);
    }

    @Transactional(readOnly = true)
    public Website findById1(Long id) {
        Website entity = this.dao.findById1(id);
        return entity;
    }

    public Website save(Website bean) {
        Website entity = this.dao.save(bean);
        fireOnSave(entity);
        return entity;
    }

    public Website update1(Website bean, Integer uploadFtpId, Integer syncPageFtpId) {
        Website entity = findById1(bean.getId());
        if (uploadFtpId != null)
            entity.setUploadFtp(this.ftpMng.findById(uploadFtpId));
        else {
            entity.setUploadFtp(null);
        }
        if (syncPageFtpId != null)
            entity.setSyncPageFtp(this.ftpMng.findById(syncPageFtpId));
        else {
            entity.setSyncPageFtp(null);
        }
        String domain = entity.getDomain();
        String[] as = entity.getDomainAliasArray();
        entity = this.dao.updateByUpdater(new Updater(bean));
        fireOnUpdate(domain, as, entity);
        return entity;
    }

    public Website update(Website bean) {
        Website entity = findById(bean.getId());
        String domain = entity.getDomain();
        String[] as = entity.getDomainAliasArray();
        entity = this.dao.updateByUpdater(new Updater(bean));
        fireOnUpdate(domain, as, entity);
        return entity;
    }

    public Website deleteById(Long id) {
        Website entity = this.dao.deleteById(id);
        fireOnDelete(entity);
        return entity;
    }

    public Website[] deleteByIds(Long[] ids) {
        Website[] beans = new Website[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = this.dao.deleteById(ids[i]);
        }
        fireOnDeleteBatch(beans);
        return beans;
    }

    public void updateAttr(Long siteId, Map<String, String> attr) {
        Website site = findById(siteId);
        site.getAttr().putAll(attr);
    }

    private void resetWebsiteCache() {
        List list = this.dao.getAllList();
        int i = list.size();
        if (i == 0) {
            throw new IllegalStateException("no website data in database, please init database!");
        }
        this.coreCacheSvc.putWebsiteCount(i);

        if (i == 1) {
            Website entity = (Website) list.get(0);
            this.coreCacheSvc.putWebsiteId(entity.getId());
            this.domainCacheSvc.removeAll();
            this.domainCacheSvc.put(entity.getDomain(), entity.getDomainAliasArray(), entity.getId());
        } else {
            this.coreCacheSvc.removeWebsiteId();
            this.domainCacheSvc.removeAll();
            Object object = list.iterator();
            while (((Iterator) object).hasNext()) {
                Website localWebsite = (Website) ((Iterator) object).next();
                this.domainCacheSvc.put(localWebsite.getDomain(), localWebsite.getDomainAliasArray(), localWebsite.getId());
            }
        }
    }

    private void onDomainUpdated(String s, String[] as, Website website) {
        String domain = website.getDomain();
        String[] as1 = website.getDomainAliasArray();
        if ((!domain.equals(s)) || (!Arrays.equals(as1, as))) {
            this.domainCacheSvc.remove(s, as);
            this.domainCacheSvc.put(domain, as1, website.getId());
        }
    }

    private void onDomainDelete(Website bean) {
        resetWebsiteCache();
    }

    private void onDomainDeleteBatch(Website[] beans) {
        resetWebsiteCache();
    }

    private void onDomainSave(Website bean) {
        resetWebsiteCache();
    }

    private void fireOnUpdate(String s, String[] as, Website bean) {
        onDomainUpdated(s, as, bean);
    }

    private void fireOnDelete(Website bean) {
        onDomainDelete(bean);
    }

    private void fireOnDeleteBatch(Website[] beans) {
        onDomainDeleteBatch(beans);
    }

    private void fireOnSave(Website bean) {
        onDomainSave(bean);
    }

    @Autowired
    public void setCoreCacheSvc(CoreCacheSvc coreCacheSvc) {
        this.coreCacheSvc = coreCacheSvc;
    }

    @Autowired
    public void setDomainCacheSvc(DomainCacheSvc domainCacheSvc) {
        this.domainCacheSvc = domainCacheSvc;
    }

    @Transactional(readOnly = true)
    public Website get() {
        Website entity = this.dao.findById(Long.valueOf(1L));
        return entity;
    }

    public void updateSsoAttr(Map<String, String> ssoAttr) {
        Map oldAttr = get().getAttr();
        Iterator keys = oldAttr.keySet().iterator();
        String key = null;
        while (keys.hasNext()) {
            key = (String) keys.next();
            if (key.startsWith("sso_")) {
                keys.remove();
            }
        }
        oldAttr.putAll(ssoAttr);
    }

    @Transactional(readOnly = true)
    public Website findByDomain(String domain, boolean cacheable) {
        return this.dao.findByDomain(domain, cacheable);
    }

    @Transactional(readOnly = true)
    public List<Website> getListFromCache() {
        return this.dao.getList(true);
    }

    @Autowired
    public void setDao(WebsiteDao dao) {
        this.dao = dao;
    }

    public void updateTplSolution(Long siteId, String solution, boolean mobile) {
        Website site = findById(siteId);
        if (mobile)
            site.setTplMobileSolution(solution);
        else
            site.setTplSolution(solution);
    }
}

