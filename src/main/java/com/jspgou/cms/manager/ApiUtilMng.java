package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopMember;
import javax.servlet.http.HttpServletRequest;

public abstract interface ApiUtilMng
{
  public abstract ShopMember findbysessionKey(HttpServletRequest paramHttpServletRequest);

  public abstract String getJsonStrng(String paramString1, String paramString2, Boolean paramBoolean, HttpServletRequest paramHttpServletRequest);

  public abstract String getJsonStrng(String paramString1, String paramString2, HttpServletRequest paramHttpServletRequest);

  public abstract String getEncryptpass(HttpServletRequest paramHttpServletRequest)
    throws Exception;

  public abstract Boolean verify(HttpServletRequest paramHttpServletRequest);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ApiUtilMng
 * JD-Core Version:    0.6.0
 */