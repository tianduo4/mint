package com.mint.cms.manager.impl;

import com.mint.cms.dao.ReceiverMessageDao;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReceiverMessageMngImpl
        implements ReceiverMessageMng {
    private ReceiverMessageDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public Pagination getPage(Long sendMemberId, int pageNo, Integer box, int pageSize) {
        return this.dao.getPage(sendMemberId, pageNo, box, pageSize);
    }

    public Pagination getPage(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable, int pageNo, int pageSize) {
        return this.dao.getPage(sendMemberId, receiverMemberId, title,
                sendBeginTime, sendEndTime, status, box, cacheable, pageNo,
                pageSize);
    }

    public List<ReceiverMessage> getList(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable) {
        return this.dao.getList(sendMemberId, receiverMemberId, title,
                sendBeginTime, sendEndTime, status, box, cacheable);
    }

    public Pagination getUnreadPage(Long sendMemberId, int pageNo, int pageSize) {
        return this.dao.getUnreadPage(sendMemberId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public ReceiverMessage findById(Long id) {
        ReceiverMessage entity = this.dao.findById(id);
        return entity;
    }

    public ReceiverMessage save(ReceiverMessage bean) {
        this.dao.save(bean);
        return bean;
    }

    public ReceiverMessage update(ReceiverMessage bean) {
        Updater updater = new Updater(bean);
        ReceiverMessage entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ReceiverMessage deleteById(Long id) {
        ReceiverMessage bean = this.dao.deleteById(id);
        return bean;
    }

    public ReceiverMessage[] deleteByIds(Long[] ids) {
        ReceiverMessage[] beans = new ReceiverMessage[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ReceiverMessageDao dao) {
        this.dao = dao;
    }
}

