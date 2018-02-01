package com.mint.cms.dao.impl;

import com.mint.cms.dao.KeyWordDao;
import com.mint.cms.entity.KeyWord;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class KeyWordDaoImpl extends HibernateBaseDao<KeyWord, Integer>
        implements KeyWordDao {
    public KeyWord findById(Integer id) {
        KeyWord entity = (KeyWord) get(id);
        return entity;
    }

    public List<KeyWord> findKeyWord(Integer count) {
        List list = getSession().createQuery("from KeyWord bean order by bean.times desc")
                .setMaxResults(count.intValue()).list();
        return list;
    }

    public List<KeyWord> getKeyWordContent(String keyWord) {
        List keyContent = getSession().createQuery("from KeyWord bean where bean.keyword=:keyword ")
                .setParameter("keyword", keyWord).list();
        return keyContent;
    }

    public KeyWord save(KeyWord bean) {
        getSession().save(bean);
        return bean;
    }

    public KeyWord deleteById(Integer id) {
        KeyWord entity = (KeyWord) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<KeyWord> getEntityClass() {
        return KeyWord.class;
    }

    public List<KeyWord> getAllList() {
        return null;
    }
}

