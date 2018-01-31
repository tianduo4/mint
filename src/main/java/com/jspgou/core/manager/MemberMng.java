package com.jspgou.core.manager;

import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Member;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;

public abstract interface MemberMng
{
  public abstract Member getByUsername(Long paramLong, String paramString);

  public abstract Member getByUsername(String paramString);

  public abstract Member getByUserIdAndActive(String paramString, Long paramLong);

  public abstract Member getByUserId(Long paramLong1, Long paramLong2);

  public abstract Member register(String paramString1, String paramString2, String paramString3, Boolean paramBoolean1, String paramString4, String paramString5, Boolean paramBoolean2, Website paramWebsite);

  public abstract Member join(String paramString, Website paramWebsite);

  public abstract Member join(User paramUser, Website paramWebsite);

  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract Member findById(Long paramLong);

  public abstract Member save(Member paramMember);

  public abstract Member update(Member paramMember);

  public abstract Member deleteById(Long paramLong);

  public abstract Member[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.MemberMng
 * JD-Core Version:    0.6.0
 */