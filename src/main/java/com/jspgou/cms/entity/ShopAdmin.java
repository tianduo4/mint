package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseShopAdmin;
import com.jspgou.common.util.DateUtils;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Role;
import com.jspgou.core.entity.Website;

import java.util.Date;
import java.util.Set;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ShopAdmin extends BaseShopAdmin {
    private static final long serialVersionUID = 1L;

    public boolean isSuper() {
        Set<Role> roles = getAdmin().getRoles();
        for (Role role : roles) {
            if (role.getSuper().booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getPerms() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getRolesPerms();
        }
        return null;
    }

    public String getUsername() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getUsername();
        }
        return null;
    }

    public String getEmail() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getEmail();
        }
        return null;
    }

    public Date getLastLoginTime() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getLastLoginTime();
        }
        return null;
    }

    public String getLastLoginIp() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getLastLoginIp();
        }
        return null;
    }

    public Boolean getViewonlyAdmin() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getViewonlyAdmin();
        }
        return null;
    }

    public Date getCurrentLoginTime() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getCurrentLoginTime();
        }
        return null;
    }

    public String getCurrentLoginIp() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getCurrentLoginIp();
        }
        return null;
    }

    public Boolean getDisabled() {
        Admin admin = getAdmin();
        if (admin != null) {
            return admin.getDisabled();
        }
        return null;
    }

    public ShopAdmin() {
    }

    public ShopAdmin(Long id) {
        super(id);
    }

    public ShopAdmin(Long id, Website website) {
        super(id, website);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("username", CommonUtils.parseStr(getUsername()));
        json.put("firstname", CommonUtils.parseStr(getFirstname()));
        json.put("createTime", getAdmin() == null ? "" : CommonUtils.parseDate(getAdmin().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("currentLoginTime", getAdmin() == null ? "" : CommonUtils.parseDate(getAdmin().getCurrentLoginTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("disabled", getAdmin() == null ? "" : getAdmin().getDisabled());
        json.put("viewonlyAdmin", getAdmin() == null ? "" : getAdmin().getViewonlyAdmin());
        json.put("email", getAdmin() == null ? "" : CommonUtils.parseStr(getAdmin().getEmail()));
        String str = "";
        if (getAdmin() != null) {
            Set<Role> roles = getAdmin().getRoles();
            if (roles != null) {
                for (Role role : roles) {
                    str = str + "," + role.getId();
                }
                if (StringUtils.isNotEmpty(str)) {
                    str = str.substring(1, str.length());
                }
            }
        }
        json.put("roleIds", str);
        return json;
    }
}

