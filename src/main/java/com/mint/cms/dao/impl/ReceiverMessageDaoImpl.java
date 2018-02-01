package com.mint.cms.dao.impl;

import com.mint.cms.dao.ReceiverMessageDao;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiverMessageDaoImpl extends HibernateBaseDao<ReceiverMessage, Long>
        implements ReceiverMessageDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public Pagination getPage(Long sendMemberId, int pageNo, Integer box, int pageSize) {
        String hql = " select msg from ReceiverMessage msg where 1=1 ";
        Finder finder = Finder.create(hql);
        if (sendMemberId != null) {
            finder.append(" and msg.msgReceiverUser.id=:sendMemberId").setParam(
                    "sendMemberId", sendMemberId);
        }
        if (box != null) {
            finder.append(" and msg.msgBox =:box").setParam("box", box);
        }
        finder.append(" order by msg.id desc");
        return find(finder, pageNo, pageSize);
    }

    public Pagination getPage(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable, int pageNo, int pageSize) {
        String hql = " select msg from ReceiverMessage msg where 1=1 ";
        Finder finder = Finder.create(hql);

        if ((sendMemberId != null) && (receiverMemberId != null)) {
            finder.append(" and ((msg.msgReceiverUser.id=:receiverMemberId and msg.msgBox =:box) or (msg.msgSendUser.id=:sendMemberId  and msg.msgBox =:box) )")
                    .setParam("sendMemberId", sendMemberId).setParam(
                    "receiverMemberId", receiverMemberId).setParam("box",
                    box);
        } else {
            if (sendMemberId != null) {
                finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
                        "sendMemberId", sendMemberId);
            }
            if (receiverMemberId != null) {
                finder.append(" and msg.msgReceiverUser.id=:receiverMemberId")
                        .setParam("receiverMemberId", receiverMemberId);
            }
            if (box != null) {
                finder.append(" and msg.msgBox =:box").setParam("box", box);
            }
        }
        if (StringUtils.isNotBlank(title)) {
            finder.append(" and msg.msgTitle like:title").setParam("title",
                    "%" + title + "%");
        }
        if (sendBeginTime != null) {
            finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
                    "sendBeginTime", sendBeginTime);
        }
        if (sendEndTime != null) {
            finder.append(" and msg.sendTime <=:sendEndTime").setParam(
                    "sendEndTime", sendEndTime);
        }
        if (status != null) {
            if (status.booleanValue())
                finder.append(" and msg.msgStatus =true");
            else {
                finder.append(" and msg.msgStatus =false");
            }
        }
        finder.append(" order by msg.id desc");

        return find(finder, pageNo, pageSize);
    }

    public List<ReceiverMessage> getList(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable) {
        String hql = " select msg from ReceiverMessage msg where 1=1  ";
        Finder finder = Finder.create(hql);

        if ((sendMemberId != null) && (receiverMemberId != null)) {
            finder
                    .append(
                            " and ((msg.msgReceiverUser.id=:receiverMemberId and msg.msgBox =:box) or (msg.msgSendUser.id=:sendMemberId  and msg.msgBox =:box) )")
                    .setParam("sendMemberId", sendMemberId).setParam(
                    "receiverMemberId", receiverMemberId).setParam("box",
                    box);
        } else {
            if (sendMemberId != null) {
                finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
                        "sendMemberId", sendMemberId);
            }
            if (receiverMemberId != null) {
                finder.append(" and msg.msgReceiverUser.id=:receiverMemberId")
                        .setParam("receiverMemberId", receiverMemberId);
            }
            if (box != null) {
                finder.append(" and msg.msgBox =:box").setParam("box", box);
            }
        }
        if (StringUtils.isNotBlank(title)) {
            finder.append(" and msg.msgTitle like:title").setParam("title",
                    "%" + title + "%");
        }
        if (sendBeginTime != null) {
            finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
                    "sendBeginTime", sendBeginTime);
        }
        if (sendEndTime != null) {
            finder.append(" and msg.sendTime <=:sendEndTime").setParam(
                    "sendEndTime", sendEndTime);
        }
        if (status != null) {
            if (status.booleanValue())
                finder.append(" and msg.msgStatus =true");
            else {
                finder.append(" and msg.msgStatus =false");
            }
        }
        finder.append(" order by msg.id desc");
        return find(finder);
    }

    public Pagination getUnreadPage(Long sendMemberId, int pageNo, int pageSize) {
        String hql = " select msg from ReceiverMessage msg where 1=1 ";
        Finder finder = Finder.create(hql);
        if (sendMemberId != null) {
            finder.append(" and msg.msgReceiverUser.id=:sendMemberId").setParam(
                    "sendMemberId", sendMemberId);
        }

        finder.append(" and msg.msgStatus=:unread").setParam("unread", Boolean.valueOf(false));
        finder.append(" order by msg.id desc");
        return find(finder, pageNo, pageSize);
    }

    public ReceiverMessage findById(Long id) {
        ReceiverMessage entity = (ReceiverMessage) get(id);
        return entity;
    }

    public ReceiverMessage save(ReceiverMessage bean) {
        getSession().save(bean);
        return bean;
    }

    public ReceiverMessage deleteById(Long id) {
        ReceiverMessage entity = (ReceiverMessage) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ReceiverMessage> getEntityClass() {
        return ReceiverMessage.class;
    }
}

