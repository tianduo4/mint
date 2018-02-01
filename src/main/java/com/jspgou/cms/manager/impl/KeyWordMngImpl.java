package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.KeyWordDao;
import com.jspgou.cms.entity.KeyWord;
import com.jspgou.cms.manager.KeyWordMng;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeyWordMngImpl
        implements KeyWordMng {

    @Autowired
    private KeyWordDao dao;

    @Transactional(readOnly = true)
    public List<KeyWord> getAllList() {
        List list = this.dao.getAllList();
        return list;
    }

    @Transactional(readOnly = true)
    public KeyWord findById(Integer id) {
        KeyWord entity = this.dao.findById(id);
        return entity;
    }

    public List<KeyWord> findKeyWord(Integer count) {
        return this.dao.findKeyWord(count);
    }

    public KeyWord save(String keyword) {
        List list = getKeyWordContent(keyword);
        KeyWord bean = null;
        if (list.isEmpty()) {
            bean = new KeyWord();
            bean.setKeyword(keyword);
            bean.setTimes(Integer.valueOf(1));
            this.dao.save(bean);
        } else {
            bean = (KeyWord) list.iterator().next();
            bean.setTimes(Integer.valueOf(bean.getTimes().intValue() + 1));
        }
        return bean;
    }

    public List<KeyWord> getKeyWordContent(String keyWord) {
        return this.dao.getKeyWordContent(keyWord);
    }
}

