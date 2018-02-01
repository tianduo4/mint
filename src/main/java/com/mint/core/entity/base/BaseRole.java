package com.mint.core.entity.base;

import com.mint.core.entity.Role;
import com.mint.core.entity.User;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseRole
        implements Serializable {
    public static String REF = "Role";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_SUPER = "super";
    public static String PROP_PRIORITY = "priority";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private Integer priority;
    private Boolean m_super;
    private Set<String> perms;
    private Set<User> users;

    public BaseRole() {
        initialize();
    }

    public BaseRole(Integer id) {
        setId(id);
        initialize();
    }

    public BaseRole(Integer id, String name, Integer priority, Boolean m_super) {
        setId(id);
        setName(name);
        setPriority(priority);
        setSuper(m_super);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getSuper() {
        return this.m_super;
    }

    public void setSuper(Boolean m_super) {
        this.m_super = m_super;
    }

    public Set<String> getPerms() {
        return this.perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Role)) return false;

        Role role = (Role) obj;
        if ((getId() == null) || (role.getId() == null)) return false;
        return getId().equals(role.getId());
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
}

