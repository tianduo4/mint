package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.PosterDao;
import com.jspgou.cms.entity.Poster;
import com.jspgou.cms.manager.PosterMng;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PosterMngImpl
        implements PosterMng {
    private PosterDao dao;

    @Transactional(readOnly = true)
    public Poster findById(Integer id) {
        Poster entity = this.dao.findById(id);
        return entity;
    }

    public Poster saveOrUpdate(Poster bean) {
        this.dao.saveOrUpdate(bean);
        return bean;
    }

    public List<Poster> getPage() {
        return this.dao.getPage();
    }

    public Poster update(Poster Poster) {
        return this.dao.update(Poster);
    }

    public void deleteByIds(Integer[] ids) {
        for (Integer id : ids)
            deleteById(id);
    }

    public Poster deleteById(Integer id) {
        Poster bean = this.dao.deleteById(id);
        return bean;
    }

    @Autowired
    public void setDao(PosterDao dao) {
        this.dao = dao;
    }

    public void updateByApi(Integer[] ids, String[] picUrls, String[] interUrls) {
        for (int i = 0; i < ids.length; i++)
            if (ids[i].intValue() == 0) {
                Poster poster = new Poster();
                poster.setPicUrl(picUrls[i]);
                poster.setInterUrl(interUrls[i]);
                poster.setTime(new Date());
                this.dao.saveOrUpdate(poster);
            } else {
                Poster poster = this.dao.findById(ids[i]);
                poster.setPicUrl(picUrls[i]);
                poster.setInterUrl(interUrls[i]);
                poster.setTime(new Date());
                this.dao.update(poster);
            }
    }
}

