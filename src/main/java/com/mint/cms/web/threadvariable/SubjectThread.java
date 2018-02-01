package com.mint.cms.web.threadvariable;

import org.apache.shiro.subject.Subject;

public class SubjectThread {
    private static ThreadLocal<Subject> instance = new ThreadLocal();

    public static Subject get() {
        return (Subject) instance.get();
    }

    public static void set(Subject subject) {
        instance.set(subject);
    }

    public static void remove() {
        instance.remove();
    }
}

