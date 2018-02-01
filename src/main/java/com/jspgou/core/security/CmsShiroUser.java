package com.jspgou.core.security;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CmsShiroUser
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String username;

    public CmsShiroUser(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return this.username;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,
                new String[]{this.username});
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj,
                new String[]{this.username});
    }
}

