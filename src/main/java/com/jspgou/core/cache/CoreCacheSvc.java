package com.jspgou.core.cache;

public abstract interface CoreCacheSvc
{
  public abstract void putWebsiteCount(int paramInt);

  public abstract Integer getWebsiteCount();

  public abstract void putWebsiteId(Long paramLong);

  public abstract boolean removeWebsiteId();

  public abstract Long getWebsiteId();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.cache.CoreCacheSvc
 * JD-Core Version:    0.6.0
 */