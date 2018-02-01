package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Discuss;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

public abstract interface DiscussMng {
    public abstract Discuss findById(Long paramLong);

    public abstract Discuss update(Discuss paramDiscuss);

    public abstract Discuss deleteById(Long paramLong);

    public abstract Discuss saveOrUpdate(Long paramLong1, String paramString1, Long paramLong2, String paramString2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract Discuss[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Pagination getPageByMember(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract List<Discuss> findByType(Long paramLong, String paramString);

    public abstract void deleteByMemberId(Long paramLong);
}

