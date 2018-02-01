package com.mint.cms.dao.impl;

import com.mint.cms.dao.MessageDao;
import com.mint.cms.entity.Message;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl extends HibernateBaseDao<Message, Long>
        implements MessageDao {
    public Pagination getPage(Long sendMemberId, int pageNo, int pageSize) {
        String hql = " select msg from Message msg where 1=1 ";
        Finder finder = Finder.create(hql);
        if (sendMemberId != null) {
            finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
                    "sendMemberId", sendMemberId);
        }
        finder.append(" order by msg.id desc");
        return find(finder, pageNo, pageSize);
    }

    public Message findById(Long id) {
        Message entity = (Message) get(id);
        return entity;
    }

    public Message save(Message bean) {
        getSession().save(bean);
        return bean;
    }

    public Message deleteById(Long id) {
        Message entity = (Message) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Message> getEntityClass() {
        return Message.class;
    }
}

