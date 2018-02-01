package com.mint.common.hibernate4;

import java.util.HashSet;
import java.util.Set;

public class Updater<T> {
    private T bean;
    private Set<String> includeProperties = new HashSet();

    private Set<String> excludeProperties = new HashSet();

    private UpdateMode mode = UpdateMode.MIDDLE;

    public Updater(T bean) {
        this.bean = bean;
    }

    public Updater(T bean, UpdateMode mode) {
        this.bean = bean;
        this.mode = mode;
    }

    public Updater<T> setUpdateMode(UpdateMode mode) {
        this.mode = mode;
        return this;
    }

    public Updater<T> include(String property) {
        this.includeProperties.add(property);
        return this;
    }

    public Updater<T> exclude(String property) {
        this.excludeProperties.add(property);
        return this;
    }

    public boolean isUpdate(String name, Object value) {
        if (this.mode == UpdateMode.MAX)
            return !this.excludeProperties.contains(name);
        if (this.mode == UpdateMode.MIN)
            return this.includeProperties.contains(name);
        if (this.mode == UpdateMode.MIDDLE) {
            if (value != null) {
                return !this.excludeProperties.contains(name);
            }
            return this.includeProperties.contains(name);
        }

        return true;
    }

    public T getBean() {
        return this.bean;
    }

    public Set<String> getExcludeProperties() {
        return this.excludeProperties;
    }

    public Set<String> getIncludeProperties() {
        return this.includeProperties;
    }

    public static enum UpdateMode {
        MAX, MIN, MIDDLE;
    }
}

