package com.jspgou.cms.service;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.common.security.BadCredentialsException;
import com.jspgou.common.security.UserNotAcitveException;
import com.jspgou.common.security.UsernameNotFoundException;
import com.jspgou.core.entity.Website;
import com.jspgou.core.security.UserNotInWebsiteException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface LoginSvc
{
  public abstract ShopMember memberLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite, String paramString1, String paramString2)
    throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException, UserNotAcitveException;

  public abstract ShopMember getMember(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite);

  public abstract ShopAdmin adminLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite, String paramString1, String paramString2)
    throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException;

  public abstract ShopAdmin getAdmin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite);

  public abstract void logout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract void clearCookie(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.LoginSvc
 * JD-Core Version:    0.6.0
 */