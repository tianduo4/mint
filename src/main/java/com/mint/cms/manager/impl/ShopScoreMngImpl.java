package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopScoreDao;
import com.mint.cms.entity.ShopScore;
import com.mint.cms.manager.ShopScoreMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopScoreMngImpl
        implements ShopScoreMng {
    private ShopScoreDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Long memberId, Boolean status, Boolean useStatus, Date startTime, Date endTime, Integer pageSize, Integer pageNo) {
        Pagination page = this.dao.getPage(memberId, status, useStatus,
                startTime, endTime, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public List<ShopScore> getlist(String code) {
        return this.dao.getlist(code);
    }

    @Transactional(readOnly = true)
    public ShopScore findById(Long id) {
        ShopScore entity = this.dao.findById(id);
        return entity;
    }

    public ShopScore save(ShopScore bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopScore update(ShopScore bean) {
        Updater updater = new Updater(bean);
        ShopScore entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopScore deleteById(Long id) {
        ShopScore bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopScore[] deleteByIds(Long[] ids) {
        ShopScore[] beans = new ShopScore[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopScoreDao dao) {
        this.dao = dao;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

