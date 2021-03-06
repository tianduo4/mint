package com.mint.core.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.core.entity.Ftp;

import java.util.List;

public abstract interface FtpDao {
    public abstract List<Ftp> getList();

    public abstract Ftp findById(Integer paramInteger);

    public abstract Ftp save(Ftp paramFtp);

    public abstract Ftp updateByUpdater(Updater<Ftp> paramUpdater);

    public abstract Ftp deleteById(Integer paramInteger);
}

