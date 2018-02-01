package com.mint.core.entity;

import com.mint.core.entity.base.BaseAdmin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Admin extends BaseAdmin {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getCreateTime() == null)
            setCreateTime(new Date());
        if (getDisabled() == null)
            setDisabled(Boolean.valueOf(false));
    }

    public Set<String> getRolesPerms() {
        Set roles = getRoles();
        if (roles == null) {
            return null;
        }
        Set allPerms = new HashSet();
        for (Role role : getRoles()) {
            allPerms.addAll(role.getPerms());
        }
        return allPerms;
    }

    public Integer[] getRoleIds() {
        Set roles = getRoles();
        return Role.fetchIds(roles);
    }

    public void addToRoles(Role role) {
        if (role == null) {
            return;
        }
        Set set = getRoles();
        if (set == null) {
            set = new HashSet();
            setRoles(set);
        }
        set.add(role);
    }

    public boolean isSuper() {
        Set roles = getRoles();
        if (roles == null) {
            return false;
        }
        for (Role role : getRoles()) {
            if (role.getSuper().booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public Admin() {
    }

    public Admin(Long id) {
        super(id);
    }

    public Admin(Long id, User user, Website website, Date date, Boolean boolean1) {
        super(id,
                user,
                website,
                date,
                boolean1);
    }

    public String getUsername() {
        User user = getUser();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public String getEmail() {
        User user = getUser();
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    public String getLastLoginIp() {
        User user = getUser();
        if (user != null) {
            return user.getLastLoginIp();
        }
        return null;
    }

    public Date getLastLoginTime() {
        User user = getUser();
        if (user != null) {
            return user.getLastLoginTime();
        }
        return null;
    }

    public String getCurrentLoginIp() {
        User user = getUser();
        if (user != null) {
            return user.getCurrentLoginIp();
        }
        return null;
    }

    public Date getCurrentLoginTime() {
        User user = getUser();
        if (user != null) {
            return user.getCurrentLoginTime();
        }
        return null;
    }
}

