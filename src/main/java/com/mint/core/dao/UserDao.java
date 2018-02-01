package com.mint.core.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.entity.User;

public abstract interface UserDao {
    public abstract User getByUsername(String paramString);

    public abstract User getByEmail(String paramString);

    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract User findById(Long paramLong);

    public abstract User save(User paramUser);

    public abstract User updateByUpdater(Updater<User> paramUpdater);

    public abstract User deleteById(Long paramLong);
}

