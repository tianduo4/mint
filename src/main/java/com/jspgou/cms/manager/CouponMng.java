package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Coupon;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Website;
import java.util.List;

public abstract interface CouponMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2, Integer paramInteger);

  public abstract Pagination getPageByUsing(int paramInt1, int paramInt2);

  public abstract List<Coupon> getList();

  public abstract Coupon findById(Long paramLong);

  public abstract Coupon save(Coupon paramCoupon, Long paramLong);

  public abstract Coupon update(Coupon paramCoupon);

  public abstract Coupon deleteById(Long paramLong, String paramString);

  public abstract Coupon[] deleteByIds(Long[] paramArrayOfLong, String paramString);

  public abstract void deleteByMemberId(Long paramLong);

  public abstract Coupon save(Coupon paramCoupon, Website paramWebsite, Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.CouponMng
 * JD-Core Version:    0.6.0
 */