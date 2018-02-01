package com.jspgou.common.security.userdetails;

import com.jspgou.common.security.AccountExpiredException;
import com.jspgou.common.security.AccountStatusException;
import com.jspgou.common.security.CredentialsExpiredException;
import com.jspgou.common.security.DisabledException;
import com.jspgou.common.security.LockedException;

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

