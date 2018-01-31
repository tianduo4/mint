package com.jspgou.common.security.rememberme;

import com.jspgou.common.security.userdetails.UserDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface RememberMeService
{
  public abstract UserDetails autoLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws CookieTheftException;

  public abstract void loginFail(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract boolean loginSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, UserDetails paramUserDetails);

  public abstract void logout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.rememberme.RememberMeService
 * JD-Core Version:    0.6.0
 */