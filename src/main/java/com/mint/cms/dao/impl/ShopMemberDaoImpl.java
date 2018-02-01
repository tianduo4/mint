package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopMemberDao;
import com.mint.cms.entity.ShopMember;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import com.mint.core.entity.Member;
import com.mint.core.manager.MemberMng;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShopMemberDaoImpl extends HibernateBaseDao<ShopMember, Long>
        implements ShopMemberDao {

    @Autowired
    private MemberMng memberMng;

    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from ShopMember bean where bean.website.id=:webId order by bean.id desc");
        f.setParam("webId", webId);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage(Long webId, int pageNo, int pageSize, String username) {
        Finder f =
                Finder.create("from ShopMember bean where bean.website.id=:webId ");
        f.setParam("webId", webId);
        if (StringUtils.isNotEmpty(username)) {
            f.append(" and bean.member.user.username like :username");
            f.setParam("username", "%" + username + "%");
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public ShopMember getByUserId(Long webId, Long userId) {
        Member coreMember = this.memberMng.getByUserId(webId, userId);
        if (coreMember != null) {
            return findById(coreMember.getId());
        }
        return null;
    }

    public ShopMember findById(Long id) {
        ShopMember entity = (ShopMember) get(id);
        return entity;
    }

    public ShopMember save(ShopMember bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopMember deleteById(Long id) {
        ShopMember entity = (ShopMember) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopMember> getEntityClass() {
        return ShopMember.class;
    }

    public ShopMember findUsername(String username) {
        String hql = "from ShopMember bean where bean.member.user.username=:realName";
        Query query = getSession().createQuery(hql);
        query.setParameter("realName", username);
        query.setMaxResults(1);
        return (ShopMember) query.uniqueResult();
    }

    public ShopMember findByUsername(String realName) {
        return (ShopMember) findUniqueByProperty("realName", realName);
    }

    public List<ShopMember> getList(String realName, Long groupId) {
        Finder f = Finder.create("select bean from ShopMember bean where 1=1");
        if (!StringUtils.isBlank(realName)) {
            f.append(" and bean.realName like :realName");
            f.setParam("realName", "%" + realName + "%");
        }
        if (groupId != null) {
            f.append(" and bean.group.id=:groupId");
            f.setParam("groupId", groupId);
        }
        f.append(" order by bean.id desc");
        return find(f);
    }

    public int countByUsername(String realName) {
        String hql = "select count(*) from ShopMember bean where bean.member.user.username=:realName";
        Query query = getSession().createQuery(hql);
        query.setParameter("realName", realName);
        return ((Number) query.iterate().next()).intValue();
    }

    public Long getMemberCount(Long webId) {
        Finder finder = Finder.create("select count(1) from ShopMember bean where  bean.website.id=:webId");
        finder.setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }
}

