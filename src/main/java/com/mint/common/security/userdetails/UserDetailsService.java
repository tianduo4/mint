package com.mint.common.security.userdetails;

import com.mint.common.security.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

public abstract interface UserDetailsService {
    public abstract UserDetails loadUser(Long paramLong, String paramString)
            throws UsernameNotFoundException, DataAccessException;
}

