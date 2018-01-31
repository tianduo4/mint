package com.jspgou.core.cache;

public abstract interface DomainCacheSvc
{
  public abstract void put(String paramString, String[] paramArrayOfString, Long paramLong);

  public abstract boolean remove(String paramString, String[] paramArrayOfString);

  public abstract Long get(String paramString);

  public abstract void removeAll();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.cache.DomainCacheSvc
 * JD-Core Version:    0.6.0
 */