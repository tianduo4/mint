package com.mint.cms.dao;

import com.mint.cms.entity.BrandText;
import com.mint.common.hibernate4.Updater;

public abstract interface BrandTextDao {
    public abstract BrandText save(BrandText paramBrandText);

    public abstract BrandText updateByUpdater(Updater<BrandText> paramUpdater);
}

