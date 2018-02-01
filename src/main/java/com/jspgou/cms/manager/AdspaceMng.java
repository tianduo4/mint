package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Adspace;

import java.util.List;

public abstract interface AdspaceMng {
    public abstract Adspace findById(Integer paramInteger);

    public abstract Adspace save(Adspace paramAdspace);

    public abstract Adspace updateByUpdater(Adspace paramAdspace);

    public abstract Adspace deleteById(Integer paramInteger);

    public abstract Adspace updateByAdspacenumb(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

    public abstract Adspace[] deleteByIds(Integer[] paramArrayOfInteger);

    public abstract List<Adspace> getList();
}

