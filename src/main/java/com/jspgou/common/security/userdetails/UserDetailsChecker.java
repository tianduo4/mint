package com.jspgou.common.security.userdetails;

import com.jspgou.common.security.AccountStatusException;

public abstract interface UserDetailsChecker
{
  public abstract void check(UserDetails paramUserDetails)
    throws AccountStatusException;
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.userdetails.UserDetailsChecker
 * JD-Core Version:    0.6.0
 */