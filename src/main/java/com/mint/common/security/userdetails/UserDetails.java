package com.mint.common.security.userdetails;

import java.io.Serializable;

public abstract interface UserDetails extends Serializable {
    public abstract String getPassword();

    public abstract String getUsername();

    public abstract Long getId();

    public abstract boolean isAccountNonExpired();

    public abstract boolean isAccountNonLocked();

    public abstract boolean isCredentialsNonExpired();

    public abstract boolean isEnabled();
}

