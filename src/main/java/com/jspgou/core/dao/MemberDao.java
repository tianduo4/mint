package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Member;

public abstract interface MemberDao
{
  public abstract Member getByUsername(String paramString);

  public abstract Member getByUserId(Long paramLong1, Long paramLong2);

  public abstract Member getByUserIdAndActive(String paramString, Long paramLong);

  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract Member findById(Long paramLong);

  public abstract Member save(Member paramMember);

  public abstract Member updateByUpdater(Updater<Member> paramUpdater);

  public abstract Member deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.MemberDao
 * JD-Core Version:    0.6.0
 */