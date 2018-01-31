package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Coupon;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.Date;
import java.util.List;

public abstract interface CouponDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2, Integer paramInteger);

  public abstract Pagination getPageByUsing(Date paramDate, int paramInt1, int paramInt2);

  public abstract List<Coupon> getList();

  public abstract Coupon findById(Long paramLong);

  public abstract Coupon save(Coupon paramCoupon);

  public abstract Coupon updateByUpdater(Updater<Coupon> paramUpdater);

  public abstract Coupon deleteById(Long paramLong);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.CouponDao
 * JD-Core Version:    0.6.0
 */