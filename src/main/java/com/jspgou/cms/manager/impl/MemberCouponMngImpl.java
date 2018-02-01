package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.MemberCouponDao;
import com.jspgou.cms.entity.MemberCoupon;
import com.jspgou.cms.manager.MemberCouponMng;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCouponMngImpl
        implements MemberCouponMng {

    @Autowired
    private MemberCouponDao dao;

    public List<MemberCoupon> getList(Long memberId) {
        List list = this.dao.getList(memberId);
        return list;
    }

    public MemberCoupon findById(Long id) {
        return this.dao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> getList(Long memberId, BigDecimal price) {
        return this.dao.getList(memberId, new Date(), price);
    }

    @Transactional(readOnly = true)
    public MemberCoupon findByCoupon(Long memberId, Long couponId) {
        return this.dao.findByCoupon(memberId, couponId);
    }

    public MemberCoupon save(MemberCoupon bean) {
        return this.dao.save(bean);
    }

    public MemberCoupon update(MemberCoupon bean) {
        return this.dao.update(bean);
    }
}

