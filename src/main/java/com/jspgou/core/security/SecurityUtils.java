package com.jspgou.core.security;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.Subject.Builder;
import org.apache.shiro.util.ThreadContext;

public abstract class SecurityUtils {
    private static SecurityManager securityManager;

    public static Subject getSubject() {
        Subject subject = ThreadContext.getSubject();
        if (subject == null) {
            subject = new Subject.Builder().buildSubject();
            ThreadContext.bind(subject);
        }
        return subject;
    }

    public static void setSecurityManager(SecurityManager securityManager) {
        securityManager = securityManager;
    }

    public static SecurityManager getSecurityManager()
            throws UnavailableSecurityManagerException {
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if (securityManager == null) {
            securityManager = securityManager;
        }
        if (securityManager == null) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the " +
                    ThreadContext.class.getName() + " or as a vm static singleton.  This is an invalid application " +
                    "configuration.";
            throw new UnavailableSecurityManagerException(msg);
        }
        return securityManager;
    }
}

