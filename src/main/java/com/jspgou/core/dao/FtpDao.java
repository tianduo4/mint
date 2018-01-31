package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.core.entity.Ftp;
import java.util.List;

public abstract interface FtpDao
{
  public abstract List<Ftp> getList();

  public abstract Ftp findById(Integer paramInteger);

  public abstract Ftp save(Ftp paramFtp);

  public abstract Ftp updateByUpdater(Updater<Ftp> paramUpdater);

  public abstract Ftp deleteById(Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.FtpDao
 * JD-Core Version:    0.6.0
 */