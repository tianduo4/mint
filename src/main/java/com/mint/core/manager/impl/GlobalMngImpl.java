package com.mint.core.manager.impl;

import com.mint.common.security.encoder.PwdEncoder;
import com.mint.core.dao.GlobalDao;
import com.mint.core.entity.ConfigAttr;
import com.mint.core.entity.Global;
import com.mint.core.manager.GlobalMng;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GlobalMngImpl
        implements GlobalMng {

    @Autowired
    private PwdEncoder pwdEncoder;
    private GlobalDao dao;

    @Transactional(readOnly = true)
    public Global get() {
        Global entity = this.dao.findById(Long.valueOf(1L));
        return entity;
    }

    public Global findById(Long id) {
        return this.dao.findById(id);
    }

    public Global update(Global bean) {
        return this.dao.update(bean);
    }

    public void updateConfigAttr(ConfigAttr configAttr) {
        findIdkey().getAttr().putAll(configAttr.getAttr());
    }

    public Global findIdkey() {
        return this.dao.findIdkey();
    }

    @Autowired
    public void setDao(GlobalDao dao) {
        this.dao = dao;
    }

    public Global updateGlobalPwd(Long id, String password) {
        Global entity = findById(id);
        if (!StringUtils.isBlank(password)) {
            entity.setPassword(this.pwdEncoder.encodePassword(password));
        }
        return entity;
    }
}

