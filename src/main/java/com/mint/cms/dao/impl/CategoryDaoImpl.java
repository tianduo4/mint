package com.mint.cms.dao.impl;

import com.mint.cms.dao.CategoryDao;
import com.mint.cms.entity.Category;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends HibernateBaseDao<Category, Integer>
        implements CategoryDao {
    public List<Category> getAll(Long webId) {
        String hql = "from Category bean where bean.website.id=? order by bean.priority";
        return createQuery(hql, new Object[]{webId}).setCacheable(false).list();
    }

    public Category getByPath(Long webId, String path, boolean cacheable) {
        String hql = "from Category bean where bean.website.id=? and bean.path=?";
        return (Category) createQuery(hql, new Object[]{webId, path}).setCacheable(cacheable)
                .uniqueResult();
    }

    public List<Category> getListForParent(Long webId, Integer ctgId) {
        Finder f = Finder.create("select node");
        f.append(" from Category node,Category exclude");
        f.append(" where ex.id=:ctgId and node.website.id=?");
        f.append(" and node.lft<exclude.lft and node.rgt>exclude.rgt");
        f.append(" order by node.priority");
        f.setParam("webId", webId);
        f.setParam("ctgId", ctgId);
        return find(f);
    }

    public List<Category> getListForChild(Long webId, Integer ctgId) {
        Finder f = Finder.create("select node");
        f.append(" from Category node, Category parent");
        f.append(" where parent.id=:ctgId and node.website.id=:webId");
        f.append(" and node.lft>=parent.lft and node.rgt<=parent.rgt");
        f.setParam("webId", webId);
        f.setParam("ctgId", ctgId);
        return find(f);
    }

    public List<Category> getTopList(Long webId, boolean cacheable) {
        String hql = "from Category bean where bean.website.id=? and bean.parent.id is null order by bean.priority";
        return createQuery(hql, new Object[]{webId}).setCacheable(cacheable).list();
    }

    public List<Category> getChildList(Long webId, Integer parentId) {
        Finder f = Finder.create("from Category bean");
        f.append(" where bean.parent.id=:parentId");
        f.setParam("parentId", parentId);
        f.append(" order by bean.priority asc,bean.id asc");
        return find(f);
    }

    public int countPath(Long webId, String path) {
        String hql = "select count(*) from Category bean where bean.website.id=:webId and bean.path=:path";
        return
                ((Number) getSession().createQuery(hql).setParameter("webId",
                        webId).setParameter("path", path).iterate().next()).intValue();
    }

    public List<Category> getListByptype(Long webId, Long pTypeId, Integer count) {
        String hql = "from Category bean where bean.website.id=? and bean.type.id=?";
        if ((count != null) && (count.intValue() != 0)) {
            return getSession().createQuery(hql).setParameter(0, webId).setParameter(1, pTypeId).setFirstResult(0)
                    .setMaxResults(count.intValue()).list();
        }
        return getSession().createQuery(hql).setParameter(0, webId).setParameter(1, pTypeId).list();
    }

    public Category findById(Integer id) {
        Category entity = (Category) get(id);
        return entity;
    }

    public Category save(Category bean) {
        getSession().save(bean);
        return bean;
    }

    public Category deleteById(Integer id) {
        Category entity = (Category) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}

