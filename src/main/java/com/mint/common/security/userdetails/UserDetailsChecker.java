package com.mint.common.security.userdetails;

import com.mint.common.security.AccountStatusException;

public abstract interface UserDetailsChecker {
    public abstract void check(UserDetails paramUserDetails)
            throws AccountStatusException;
}

