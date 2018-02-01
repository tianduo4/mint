package com.mint.common.hibernate4;

public abstract interface HibernateTree<T extends Number> {
    public static final String DEF_LEFT_NAME = "lft";
    public static final String DEF_RIGHT_NAME = "rgt";
    public static final String DEF_PARENT_NAME = "parent";
    public static final String ENTITY_ALIAS = "bean";

    public abstract String getLftName();

    public abstract String getRgtName();

    public abstract String getParentName();

    public abstract T getLft();

    public abstract void setLft(T paramT);

    public abstract T getRgt();

    public abstract void setRgt(T paramT);

    public abstract T getParentId();

    public abstract T getId();

    public abstract String getTreeCondition();
}

