package com.jspgou.common.security.userdetails;

import com.jspgou.common.security.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

public abstract interface UserDetailsService {
    public abstract UserDetails loadUser(Long paramLong, String paramString)
            throws UsernameNotFoundException, DataAccessException;
}

