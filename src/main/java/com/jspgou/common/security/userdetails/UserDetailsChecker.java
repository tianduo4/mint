package com.jspgou.common.security.userdetails;

import com.jspgou.common.security.AccountStatusException;

public abstract interface UserDetailsChecker {
    public abstract void check(UserDetails paramUserDetails)
            throws AccountStatusException;
}

