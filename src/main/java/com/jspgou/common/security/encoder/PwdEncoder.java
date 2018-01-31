package com.jspgou.common.security.encoder;

public abstract interface PwdEncoder
{
  public abstract String encodePassword(String paramString);

  public abstract String encodePassword(String paramString1, String paramString2);

  public abstract boolean isPasswordValid(String paramString1, String paramString2);

  public abstract boolean isPasswordValid(String paramString1, String paramString2, String paramString3);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.encoder.PwdEncoder
 * JD-Core Version:    0.6.0
 */