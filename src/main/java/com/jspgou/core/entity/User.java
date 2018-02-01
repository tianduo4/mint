package com.jspgou.core.entity;

import com.jspgou.core.entity.base.BaseUser;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class User extends BaseUser {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (StringUtils.isBlank(getEmail())) {
            setEmail(null);
        }
        if (getCreateTime() == null) {
            setCreateTime(new Date());
        }
        setLoginCount(Long.valueOf(0L));
    }

    public User() {
    }

    public User(Long id) {
        super(id);
    }

    public User(Long id, String username, String password, Date createTime, Long long2, Integer errCount) {
        super(id,
                username,
                password,
                createTime,
                long2,
                errCount);
    }
}

