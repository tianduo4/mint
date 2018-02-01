package com.mint.core.manager;

import com.mint.core.entity.ConfigAttr;
import com.mint.core.entity.Global;

public abstract interface GlobalMng {
    public abstract Global get();

    public abstract Global findById(Long paramLong);

    public abstract Global update(Global paramGlobal);

    public abstract void updateConfigAttr(ConfigAttr paramConfigAttr);

    public abstract Global findIdkey();

    public abstract Global updateGlobalPwd(Long paramLong, String paramString);
}

