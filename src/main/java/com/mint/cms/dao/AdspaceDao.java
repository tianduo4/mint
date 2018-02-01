package com.mint.cms.dao;

import com.mint.cms.entity.Adspace;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface AdspaceDao {
    public abstract Adspace findById(Integer paramInteger);

    public abstract Adspace save(Adspace paramAdspace);

    public abstract Adspace updateByUpdater(Updater<Adspace> paramUpdater);

    public abstract Adspace deleteById(Integer paramInteger);

    public abstract List<Adspace> getList();
}

