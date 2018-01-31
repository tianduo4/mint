package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Discuss;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.Date;
import java.util.List;

public abstract interface DiscussDao
{
  public abstract Discuss findById(Long paramLong);

  public abstract Discuss saveOrUpdate(Discuss paramDiscuss);

  public abstract Discuss updateByUpdater(Updater<Discuss> paramUpdater);

  public abstract Discuss update(Discuss paramDiscuss);

  public abstract Discuss deleteById(Long paramLong);

  public abstract Pagination getPageByProduct(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Pagination getPageByMember(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract List<Discuss> findByType(Long paramLong, String paramString);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.DiscussDao
 * JD-Core Version:    0.6.0
 */