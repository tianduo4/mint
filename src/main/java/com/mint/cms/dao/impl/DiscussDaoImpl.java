package com.mint.cms.dao.impl;

import com.mint.cms.dao.DiscussDao;
import com.mint.cms.entity.Discuss;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DiscussDaoImpl extends HibernateBaseDao<Discuss, Long>
        implements DiscussDao {
    public Discuss findById(Long id) {
        Discuss entity = (Discuss) get(id);
        return entity;
    }

    public Discuss saveOrUpdate(Discuss bean) {
        getSession().saveOrUpdate(bean);
        return bean;
    }

    public Discuss update(Discuss bean) {
        getSession().update(bean);
        return bean;
    }

    public Pagination getPageByProduct(Long memberId, Long productId, String discussType, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache) {
        Finder f = Finder.create("from Discuss bean where 1=1 ");

        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (productId != null) {
            f.append(" and bean.product.id=:id");
            f.setParam("id", productId);
        }

        if (discussType != null) {
            f.append(" and bean.discussType=:discussType");
            f.setParam("discussType", discussType);
        }
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.member.member.user.username like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.product.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (startTime != null) {
            f.append(" and bean.time>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.time<:endTime");
            f.setParam("endTime", endTime);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache) {
        Finder f = Finder.create("from Discuss bean");
        if (memberId != null) {
            f.append(" where bean.member.id=:id");
            f.setParam("id", memberId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public List<Discuss> findByType(Long productId, String discussType) {
        Finder f = Finder.create("from Discuss bean where 1=1");
        if (productId != null) {
            f.append(" and bean.product.id=:productId");
            f.setParam("productId", productId);
        }
        if (discussType != null) {
            f.append(" and bean.discussType=:discussType");
            f.setParam("discussType", discussType);
        }
        return find(f);
    }

    public Discuss deleteById(Long id) {
        Discuss entity = (Discuss) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Discuss> getEntityClass() {
        return Discuss.class;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_discuss where member_id=" + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }
}

