package com.jspgou.core.manager;

import com.jspgou.core.entity.Ftp;

import java.util.List;

public abstract interface FtpMng {
    public abstract List<Ftp> getList();

    public abstract Ftp findById(Integer paramInteger);

    public abstract Ftp save(Ftp paramFtp);

    public abstract Ftp update(Ftp paramFtp);

    public abstract Ftp deleteById(Integer paramInteger);

    public abstract Ftp[] deleteByIds(Integer[] paramArrayOfInteger);
}

