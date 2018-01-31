package com.jspgou.core.manager;

import com.jspgou.core.entity.Role;
import java.util.List;
import java.util.Set;

public abstract interface RoleMng
{
  public abstract List<Role> getList();

  public abstract Role findById(Integer paramInteger);

  public abstract Role save(Role paramRole, Set<String> paramSet);

  public abstract Role update(Role paramRole, Set<String> paramSet);

  public abstract Role deleteById(Integer paramInteger);

  public abstract Role[] deleteByIds(Integer[] paramArrayOfInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.RoleMng
 * JD-Core Version:    0.6.0
 */