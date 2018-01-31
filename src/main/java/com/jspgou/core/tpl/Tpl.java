package com.jspgou.core.tpl;

import java.util.Date;

public abstract interface Tpl
{
  public abstract String getName();

  public abstract String getPath();

  public abstract String getFilename();

  public abstract String getSource();

  public abstract long getLastModified();

  public abstract Date getLastModifiedDate();

  public abstract long getLength();

  public abstract int getSize();

  public abstract boolean isDirectory();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.tpl.Tpl
 * JD-Core Version:    0.6.0
 */