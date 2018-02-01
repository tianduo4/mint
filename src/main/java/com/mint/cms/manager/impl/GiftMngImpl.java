package com.mint.cms.manager.impl;

import com.mint.cms.dao.GiftDao;
import com.mint.cms.dao.ShopMemberDao;
import com.mint.cms.entity.Gift;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.GiftMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GiftMngImpl
        implements GiftMng {

    @Autowired
    private GiftDao dao;

    @Autowired
    private ShopMemberDao memberDao;

    public Gift deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public Gift[] deleteByIds(Long[] ids) {
        Gift[] beans = new Gift[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Gift findById(Long id) {
        return this.dao.findById(id);
    }

    public Pagination getPageGift(int pageNo, int pageSize) {
        return this.dao.getPageGift(pageNo, pageSize);
    }

    public Gift save(Gift bean) {
        return this.dao.save(bean);
    }

    public Gift updateByGiftnumb(Long giftId, Integer giftNumb, Long shopMemberId) {
        Gift gift = this.dao.findById(giftId);
        ShopMember smber = this.memberDao.findById(shopMemberId);
        Integer stock = gift.getGiftStock();
        Integer totalScore = Integer.valueOf(gift.getGiftScore().intValue() * giftNumb.intValue());
        if (stock.intValue() < giftNumb.intValue())
            return null;
        if (totalScore.intValue() > smber.getScore().intValue()) {
            return null;
        }
        gift.setGiftStock(Integer.valueOf(stock.intValue() - giftNumb.intValue()));
        smber.setScore(Integer.valueOf(smber.getScore().intValue() - totalScore.intValue()));

        return gift;
    }

    public Gift updateByUpdater(Gift bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }
}

