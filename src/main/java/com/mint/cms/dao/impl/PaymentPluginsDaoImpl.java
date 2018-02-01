package com.mint.cms.dao.impl;

import com.mint.cms.dao.PaymentPluginsDao;
import com.mint.cms.entity.PaymentPlugins;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentPluginsDaoImpl extends HibernateBaseDao<PaymentPlugins, Long>
        implements PaymentPluginsDao {
    public List<PaymentPlugins> getList() {
        Finder f = Finder.create("from PaymentPlugins bean where 1=1");
        f.append(" order by bean.priority");
        return find(f);
    }

    public List<PaymentPlugins> getList1(String platform) {
        Finder f = Finder.create("from PaymentPlugins bean where 1=1");
        if (StringUtils.isNotBlank(platform)) {
            f.append(" and bean.platform=:platform");
            f.setParam("platform", platform);
        }
        f.append(" and bean.disabled=false");

        return find(f);
    }

    public PaymentPlugins findById(Long id) {
        PaymentPlugins entity = (PaymentPlugins) get(id);
        return entity;
    }

    public PaymentPlugins findByCode(String code) {
        return (PaymentPlugins) findUniqueByProperty("code", code);
    }

    public PaymentPlugins save(PaymentPlugins bean) {
        getSession().save(bean);
        return bean;
    }

    public PaymentPlugins deleteById(Long id) {
        PaymentPlugins entity = (PaymentPlugins) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<PaymentPlugins> getEntityClass() {
        return PaymentPlugins.class;
    }
}

