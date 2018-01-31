package com.jspgou.cms.manager;

import com.jspgou.cms.entity.MemberCoupon;
import java.math.BigDecimal;
import java.util.List;

public abstract interface MemberCouponMng
{
  public abstract MemberCoupon findByCoupon(Long paramLong1, Long paramLong2);

  public abstract MemberCoupon findById(Long paramLong);

  public abstract List<MemberCoupon> getList(Long paramLong, BigDecimal paramBigDecimal);

  public abstract List<MemberCoupon> getList(Long paramLong);

  public abstract MemberCoupon save(MemberCoupon paramMemberCoupon);

  public abstract MemberCoupon update(MemberCoupon paramMemberCoupon);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.MemberCouponMng
 * JD-Core Version:    0.6.0
 */