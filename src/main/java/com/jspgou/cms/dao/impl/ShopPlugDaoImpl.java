package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopPlugDao;
import com.jspgou.cms.entity.ShopPlug;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopPlugDaoImpl extends HibernateBaseDao<ShopPlug, Long>
        implements ShopPlugDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public List<ShopPlug> getList(String author, Boolean used) {
        Finder f = Finder.create("from ShopPlug plug where 1=1 ");
        if (StringUtils.isNotBlank(author)) {
            f.append("and plug.author=:author").setParam("author", author);
        }
        if (used != null) {
            if (used.booleanValue())
                f.append("and plug.used=true");
            else {
                f.append("and plug.used=false");
            }
        }
        return find(f);
    }

    public ShopPlug findById(Long id) {
        ShopPlug entity = (ShopPlug) get(id);
        return entity;
    }

    public ShopPlug findByPath(String plugPath) {
        Finder f = Finder.create("from ShopPlug plug where plug.path=:path").setParam("path", plugPath);
        List list = find(f);
        if ((list != null) && (list.size() > 0)) {
            return (ShopPlug) list.get(0);
        }
        return null;
    }

    public ShopPlug save(ShopPlug bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopPlug deleteById(Long id) {
        ShopPlug entity = (ShopPlug) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopPlug> getEntityClass() {
        return ShopPlug.class;
    }
}

