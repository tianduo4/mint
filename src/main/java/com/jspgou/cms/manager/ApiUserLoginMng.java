package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.common.page.Pagination;

public abstract interface ApiUserLoginMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ApiUserLogin findById(Long paramLong);

  public abstract ApiUserLogin save(ApiUserLogin paramApiUserLogin);

  public abstract ApiUserLogin update(ApiUserLogin paramApiUserLogin);

  public abstract ApiUserLogin deleteById(Long paramLong);

  public abstract ApiUserLogin[] deleteByIds(Long[] paramArrayOfLong);

  public abstract ApiUserLogin findBySessionKey(String paramString);

  public abstract ApiUserLogin findByUsername(String paramString);

  public abstract void updateLoginSuccess(String paramString1, String paramString2);

  public abstract void saveLoginSuccess(String paramString1, String paramString2);

  public abstract ShopAdmin findUser(String paramString1, String paramString2, String paramString3)
    throws Exception;

  public abstract ApiUserLogin findUserLogin(String paramString1, String paramString2);

  public abstract Short getUserStatus(String paramString);

  public abstract ApiUserLogin userActive(String paramString);

  public abstract ApiUserLogin userLogin(String paramString1, String paramString2);

  public abstract ApiUserLogin userLogout(String paramString);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ApiUserLoginMng
 * JD-Core Version:    0.6.0
 */