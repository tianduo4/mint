package com.jspgou.core.dao.impl;

import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.dao.MemberDao;
import com.jspgou.core.entity.Member;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl extends HibernateBaseDao<Member, Long>
        implements MemberDao {
    public Member getByUsername(String username) {
        String f = "from Member bean where bean.user.username=:username";
        return (Member) getSession().createQuery(f).setParameter("username", username).uniqueResult();
    }

    public Member getByUserId(Long webId, Long userId) {
        String hql = "from Member bean where bean.user.id=:userId";

        Finder finder = Finder.create(hql).setParam("userId", userId);
        if (webId != null) {
            finder.append(" and bean.website.id=:webId").setParam("webId", webId);
        }
        List li = find(finder);
        if (li.size() > 0) {
            return (Member) li.get(0);
        }
        return null;
    }

    public Member getByUserIdAndActive(String activationCode, Long userId) {
        String s = "from Member bean where bean.activationCode=? and bean.user.id=?";
        return (Member) findUnique(s, new Object[]{
                activationCode, userId});
    }

    public Pagination getPage(int pageNo, int pageSize) {
        Criteria criteria = createCriteria(new Criterion[0]);
        Pagination pagination = findByCriteria(criteria, pageNo, pageSize);
        return pagination;
    }

    public Member findById(Long id) {
        Member entity = (Member) get(id);
        return entity;
    }

    public Member save(Member bean) {
        getSession().save(bean);
        return bean;
    }

    public Member deleteById(Long id) {
        Member entity = (Member) super.get(id);
        if (entity != null)
            getSession().delete(entity);
        return entity;
    }

    protected Class<Member> getEntityClass() {
        return Member.class;
    }
}

