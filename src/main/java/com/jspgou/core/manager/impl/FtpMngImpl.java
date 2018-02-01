package com.jspgou.core.manager.impl;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.core.dao.FtpDao;
import com.jspgou.core.entity.Ftp;
import com.jspgou.core.manager.FtpMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FtpMngImpl
        implements FtpMng {
    private FtpDao dao;

    @Transactional(readOnly = true)
    public List<Ftp> getList() {
        return this.dao.getList();
    }

    @Transactional(readOnly = true)
    public Ftp findById(Integer id) {
        Ftp entity = this.dao.findById(id);
        return entity;
    }

    public Ftp save(Ftp bean) {
        this.dao.save(bean);
        return bean;
    }

    public Ftp update(Ftp bean) {
        Updater updater = new Updater(bean);
        Ftp entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Ftp deleteById(Integer id) {
        Ftp bean = this.dao.deleteById(id);
        return bean;
    }

    public Ftp[] deleteByIds(Integer[] ids) {
        Ftp[] beans = new Ftp[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(FtpDao dao) {
        this.dao = dao;
    }
}

