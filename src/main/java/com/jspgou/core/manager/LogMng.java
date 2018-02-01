package com.jspgou.core.manager;

import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Log;
import com.jspgou.core.entity.User;

import javax.servlet.http.HttpServletRequest;

public abstract interface LogMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Log operating(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2);

    public abstract Log findById(Long paramLong);

    public abstract Log save(Log paramLog);

    public abstract void save(String paramString1, String paramString2);

    public abstract Log update(Log paramLog);

    public abstract Log deleteById(Long paramLong);

    public abstract Log[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Log loginFailure(HttpServletRequest paramHttpServletRequest, String paramString);

    public abstract Log loginSuccess(HttpServletRequest paramHttpServletRequest, User paramUser);
}

