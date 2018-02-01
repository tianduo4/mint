package com.mint.cms.dao;

import com.mint.cms.entity.Webservice;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface WebserviceDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Webservice> getList(String paramString);

    public abstract Webservice findById(Integer paramInteger);

    public abstract Webservice save(Webservice paramWebservice);

    public abstract Webservice updateByUpdater(Updater<Webservice> paramUpdater);

    public abstract Webservice deleteById(Integer paramInteger);
}

