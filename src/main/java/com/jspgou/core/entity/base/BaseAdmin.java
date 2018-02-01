package com.jspgou.core.entity.base;

import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Role;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseAdmin
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Admin";
    public static String PROP_WEBSITE = "website";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_USER = "user";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private Date createTime;
    private Boolean disabled;
    private Boolean viewonlyAdmin;
    private User user;
    private Website website;
    private Set<Role> roles;

    public BaseAdmin() {
        initialize();
    }

    public BaseAdmin(Long id) {
        setId(id);
        initialize();
    }

    public BaseAdmin(Long id, User user, Website website, Date date, Boolean disabled) {
        setId(id);
        setUser(user);
        setWebsite(website);
        setCreateTime(date);
        setDisabled(disabled);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Admin)) return false;

        Admin admin = (Admin) obj;
        if ((getId() == null) || (admin.getId() == null)) return false;
        return getId().equals(admin.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setViewonlyAdmin(Boolean viewonlyAdmin) {
        this.viewonlyAdmin = viewonlyAdmin;
    }

    public Boolean getViewonlyAdmin() {
        return this.viewonlyAdmin;
    }
}

