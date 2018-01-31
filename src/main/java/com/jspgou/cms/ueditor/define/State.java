package com.jspgou.cms.ueditor.define;

public abstract interface State
{
  public abstract boolean isSuccess();

  public abstract void putInfo(String paramString1, String paramString2);

  public abstract void putInfo(String paramString, long paramLong);

  public abstract String toJSONString();

  public abstract String toJSONString2();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.State
 * JD-Core Version:    0.6.0
 */