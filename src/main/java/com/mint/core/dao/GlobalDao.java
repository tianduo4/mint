package com.mint.core.dao;

import com.mint.core.entity.Global;

public abstract interface GlobalDao {
    public abstract Global findById(Long paramLong);

    public abstract Global update(Global paramGlobal);

    public abstract Global findIdkey();
}

