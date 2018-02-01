package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Advertise;
import com.jspgou.common.page.Pagination;

import java.util.List;
import java.util.Map;

public abstract interface AdvertiseMng {
    public abstract Pagination getPage(Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2);

    public abstract List<Advertise> getList(Integer paramInteger, Boolean paramBoolean);

    public abstract Advertise findById(Integer paramInteger);

    public abstract Advertise save(Advertise paramAdvertise, Integer paramInteger, Map<String, String> paramMap);

    public abstract Advertise update(Advertise paramAdvertise, Integer paramInteger, Map<String, String> paramMap);

    public abstract Advertise deleteById(Integer paramInteger);

    public abstract Advertise[] deleteByIds(Integer[] paramArrayOfInteger);

    public abstract void display(Integer paramInteger);

    public abstract void click(Integer paramInteger);
}

