package com.jspgou.cms.dao;

import com.jspgou.cms.entity.BrandText;
import com.jspgou.common.hibernate4.Updater;

public abstract interface BrandTextDao {
    public abstract BrandText save(BrandText paramBrandText);

    public abstract BrandText updateByUpdater(Updater<BrandText> paramUpdater);
}

