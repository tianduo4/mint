package com.mint.core.dao.impl;

import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import com.mint.core.dao.ThirdAccountDao;
import com.mint.core.entity.ThirdAccount;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ThirdAccountDaoImpl extends HibernateBaseDao<ThirdAccount, Long>
        implements ThirdAccountDao {
    public Pagination getPage(String username, String source, int pageNo, int pageSize) {
        String hql = "from ThirdAccount bean where 1=1 ";
        Finder f = Finder.create(hql);
        if (StringUtils.isNotBlank(username)) {
            f.append(" and bean.username like :username").setParam("username", "%" + username + "%");
        }
        if (StringUtils.isNotBlank(source)) {
            f.append(" and bean.source=:source").setParam("source", source);
        }
        return find(f, pageNo, pageSize);
    }

    public ThirdAccount findById(Long id) {
        ThirdAccount entity = (ThirdAccount) get(id);
        return entity;
    }

    public ThirdAccount findByKey(String key) {
        String hql = "from ThirdAccount bean where bean.accountKey=:accountKey";
        Finder f = Finder.create(hql).setParam("accountKey", key);
        List li = find(f);
        if (li.size() > 0) {
            return (ThirdAccount) li.get(0);
        }
        return null;
    }

    public ThirdAccount save(ThirdAccount bean) {
        getSession().save(bean);
        return bean;
    }

    public ThirdAccount deleteById(Long id) {
        ThirdAccount entity = (ThirdAccount) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ThirdAccount> getEntityClass() {
        return ThirdAccount.class;
    }
}

