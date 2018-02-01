package com.jspgou.cms.manager;

import com.jspgou.cms.entity.KeyWord;

import java.util.List;

public abstract interface KeyWordMng {
    public abstract List<KeyWord> getAllList();

    public abstract KeyWord findById(Integer paramInteger);

    public abstract List<KeyWord> getKeyWordContent(String paramString);

    public abstract KeyWord save(String paramString);

    public abstract List<KeyWord> findKeyWord(Integer paramInteger);
}

