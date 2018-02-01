package com.mint.common.security.userdetails;

import com.mint.common.security.AccountExpiredException;
import com.mint.common.security.AccountStatusException;
import com.mint.common.security.CredentialsExpiredException;
import com.mint.common.security.DisabledException;
import com.mint.common.security.LockedException;

public class AccountStatusUserDetailsChecker
        implements UserDetailsChecker {
    public void check(UserDetails user)
            throws AccountStatusException {
        if (!user.isAccountNonLocked()) {
            throw new LockedException();
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled", user);
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired", user);
        }

        if (!user.isCredentialsNonExpired())
            throw new CredentialsExpiredException(
                    "User credentials have expired", user);
    }
}

