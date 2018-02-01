package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductText;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ProductTextDao {
    public abstract ProductText updateByUpdater(Updater<ProductText> paramUpdater);

    public abstract ProductText save(ProductText paramProductText);
}

