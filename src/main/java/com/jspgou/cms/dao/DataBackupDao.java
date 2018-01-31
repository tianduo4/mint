package com.jspgou.cms.dao;

import com.jspgou.cms.entity.DataBackup;
import com.jspgou.common.hibernate4.Updater;

public abstract interface DataBackupDao
{
  public abstract DataBackup updateByUpdater(Updater<DataBackup> paramUpdater);

  public abstract DataBackup getDataBackup();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.DataBackupDao
 * JD-Core Version:    0.6.0
 */