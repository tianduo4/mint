package com.jspgou.common.security.userdetails;

import java.io.Serializable;

public abstract interface UserDetails extends Serializable
{
  public abstract String getPassword();

  public abstract String getUsername();

  public abstract Long getId();

  public abstract boolean isAccountNonExpired();

  public abstract boolean isAccountNonLocked();

  public abstract boolean isCredentialsNonExpired();

  public abstract boolean isEnabled();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.userdetails.UserDetails
 * JD-Core Version:    0.6.0
 */