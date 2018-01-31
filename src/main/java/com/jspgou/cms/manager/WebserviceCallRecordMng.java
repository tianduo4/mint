package com.jspgou.cms.manager;

import com.jspgou.cms.entity.WebserviceCallRecord;
import com.jspgou.common.page.Pagination;

public abstract interface WebserviceCallRecordMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract WebserviceCallRecord findById(Integer paramInteger);

  public abstract WebserviceCallRecord save(String paramString1, String paramString2);

  public abstract WebserviceCallRecord save(WebserviceCallRecord paramWebserviceCallRecord);

  public abstract WebserviceCallRecord update(WebserviceCallRecord paramWebserviceCallRecord);

  public abstract WebserviceCallRecord deleteById(Integer paramInteger);

  public abstract WebserviceCallRecord[] deleteByIds(Integer[] paramArrayOfInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.WebserviceCallRecordMng
 * JD-Core Version:    0.6.0
 */