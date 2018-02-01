package com.mint.cms.dao;

import com.mint.cms.entity.MemberCoupon;
import com.mint.common.hibernate4.Updater;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public abstract interface MemberCouponDao {
    public abstract MemberCoupon findByCoupon(Long paramLong1, Long paramLong2);

    public abstract MemberCoupon findById(Long paramLong);

    public abstract MemberCoupon save(MemberCoupon paramMemberCoupon);

    public abstract MemberCoupon updateByUpdater(Updater<MemberCoupon> paramUpdater);

    public abstract MemberCoupon deleteById(Long paramLong);

    public abstract List<MemberCoupon> getList(Long paramLong, Date paramDate, BigDecimal paramBigDecimal);

    public abstract List<MemberCoupon> getList(Long paramLong);

    public abstract MemberCoupon update(MemberCoupon paramMemberCoupon);
}

