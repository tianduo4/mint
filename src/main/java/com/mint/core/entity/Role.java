package com.mint.core.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.core.entity.base.BaseRole;

import java.util.Collection;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class Role extends BaseRole {
    private static final long serialVersionUID = 1L;

    public static Integer[] fetchIds(Collection<Role> roles) {
        if (roles == null) {
            return null;
        }
        Integer[] ids = new Integer[roles.size()];
        int i = 0;
        for (Role r : roles) {
            ids[(i++)] = r.getId();
        }
        return ids;
    }

    public Role() {
    }

    public Role(Integer id) {
        super(id);
    }

    public Role(Integer id, String name, Integer priority, Boolean m_super) {
        super(id,
                name,
                priority,
                m_super);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        json.put("super", CommonUtils.parseBoolean(getSuper()));
        if (getSuper() == null) {
            json.put("perms", "");
        } else if (!getSuper().booleanValue())
            json.put("perms", StringUtils.join(getPerms().toArray(), ","));
        else {
            json.put("perms", "*");
        }

        return json;
    }
}

