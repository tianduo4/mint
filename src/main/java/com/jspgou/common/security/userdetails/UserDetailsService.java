package com.jspgou.common.security.userdetails;

import com.jspgou.common.security.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

public abstract interface UserDetailsService
{
  public abstract UserDetails loadUser(Long paramLong, String paramString)
    throws UsernameNotFoundException, DataAccessException;
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.userdetails.UserDetailsService
 * JD-Core Version:    0.6.0
 */