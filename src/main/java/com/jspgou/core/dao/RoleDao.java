package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.core.entity.Role;

import java.util.List;

public abstract interface RoleDao {
    public abstract List<Role> getList();

    public abstract Role findById(Integer paramInteger);

    public abstract Role save(Role paramRole);

    public abstract Role updateByUpdater(Updater<Role> paramUpdater);

    public abstract Role deleteById(Integer paramInteger);
}

