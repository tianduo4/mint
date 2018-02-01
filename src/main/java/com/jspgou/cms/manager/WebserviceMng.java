package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.Webservice;
import com.jspgou.common.page.Pagination;

import java.util.List;
import java.util.Map;

public abstract interface WebserviceMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Webservice> getList(String paramString);

    public abstract boolean hashWebservice(String paramString);

    public abstract String callWebService(Webservice paramWebservice, Map<String, String> paramMap);

    public abstract void callWebService(String paramString, Map<String, String> paramMap);

    public abstract void callWebService(String paramString1, String paramString2, String paramString3, String paramString4, ShopMember paramShopMember, String paramString5);

    public abstract Webservice findById(Integer paramInteger);

    public abstract Webservice save(Webservice paramWebservice, String[] paramArrayOfString1, String[] paramArrayOfString2);

    public abstract Webservice update(Webservice paramWebservice, String[] paramArrayOfString1, String[] paramArrayOfString2);

    public abstract Webservice deleteById(Integer paramInteger);

    public abstract Webservice[] deleteByIds(Integer[] paramArrayOfInteger);
}

