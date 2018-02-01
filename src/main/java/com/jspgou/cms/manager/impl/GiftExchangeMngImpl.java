package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.GiftExchangeDao;
import com.jspgou.cms.entity.Gift;
import com.jspgou.cms.entity.GiftExchange;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.cms.entity.ShopScore;
import com.jspgou.cms.entity.ShopScore.ScoreTypes;
import com.jspgou.cms.manager.GiftExchangeMng;
import com.jspgou.cms.manager.ShopScoreMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GiftExchangeMngImpl
        implements GiftExchangeMng {
    private GiftExchangeDao dao;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public List<GiftExchange> getlist(Long memberId) {
        return this.dao.getlist(memberId);
    }

    @Transactional(readOnly = true)
    public GiftExchange findById(Long id) {
        GiftExchange entity = this.dao.findById(id);
        return entity;
    }

    public GiftExchange save(GiftExchange bean) {
        this.dao.save(bean);
        return bean;
    }

    public GiftExchange save(Gift gift, ShopMemberAddress shopMemberAddress, ShopMember member, Integer count) {
        GiftExchange giftExchange = new GiftExchange();
        Date now = new Timestamp(System.currentTimeMillis());
        giftExchange.setAmount(count);
        giftExchange.setCreateTime(now);
        if ((StringUtils.isBlank(shopMemberAddress.getTel())) && (StringUtils.isNotBlank(shopMemberAddress.getPhone())))
            giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",固话：" + shopMemberAddress.getPhone() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
        else if ((StringUtils.isBlank(shopMemberAddress.getPhone())) && (StringUtils.isNotBlank(shopMemberAddress.getTel())))
            giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",移动电话：" + shopMemberAddress.getTel() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
        else if ((StringUtils.isNotBlank(shopMemberAddress.getTel())) && (StringUtils.isNotBlank(shopMemberAddress.getPhone()))) {
            giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",移动电话：" + shopMemberAddress.getTel() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
        }
        giftExchange.setGift(gift);
        giftExchange.setMember(member);
        giftExchange.setScore(gift.getGiftScore());
        giftExchange.setTotalScore(Integer.valueOf(gift.getGiftScore().intValue() * count.intValue()));
        giftExchange.setStatus(Integer.valueOf(1));

        ShopScore shopScore = new ShopScore();
        shopScore.setMember(member);
        shopScore.setName(gift.getGiftName());
        shopScore.setScoreTime(new Date());
        shopScore.setStatus(true);
        shopScore.setUseStatus(true);
        shopScore.setScoreType(Integer.valueOf(ShopScore.ScoreTypes.ORDER_SCORE.getValue()));
        shopScore.setScore(Integer.valueOf(gift.getGiftScore().intValue() * count.intValue()));
        this.shopScoreMng.save(shopScore);
        member.setScore(Integer.valueOf(member.getScore().intValue() - gift.getGiftScore().intValue() * count.intValue()));
        return save(giftExchange);
    }

    public GiftExchange update(GiftExchange bean) {
        Updater updater = new Updater(bean);
        GiftExchange entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public GiftExchange deleteById(Long id) {
        GiftExchange bean = this.dao.deleteById(id);
        return bean;
    }

    public GiftExchange[] deleteByIds(Long[] ids) {
        GiftExchange[] beans = new GiftExchange[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(GiftExchangeDao dao) {
        this.dao = dao;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

