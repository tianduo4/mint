package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopMemberAddressDao;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShopMemberAddressDaoImpl extends HibernateBaseDao<ShopMemberAddress, Long>
        implements ShopMemberAddressDao {
    public List<ShopMemberAddress> getList(Long memberId) {
        String hql = "from ShopMemberAddress bean where bean.member.id=? order by bean.isDefault desc";
        return find(hql, new Object[]{memberId});
    }

    public List<ShopMemberAddress> findByMemberDefault(Long memberId, Boolean isDefault) {
        Finder f = Finder.create("from ShopMemberAddress bean where 1=1");
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (isDefault != null) {
            f.append(" and bean.isDefault=:isDefault ");
            f.setParam("isDefault", isDefault);
        }
        f.append(" order by bean.isDefault desc");
        return find(f);
    }

    public ShopMemberAddress findById(Long id) {
        ShopMemberAddress entity = (ShopMemberAddress) get(id);
        return entity;
    }

    public ShopMemberAddress save(ShopMemberAddress bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopMemberAddress deleteById(Long id) {
        ShopMemberAddress entity = (ShopMemberAddress) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopMemberAddress> getEntityClass() {
        return ShopMemberAddress.class;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_member_address where member_id = " + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }
}

