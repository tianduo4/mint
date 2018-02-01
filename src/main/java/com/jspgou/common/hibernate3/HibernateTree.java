package com.jspgou.common.hibernate3;

public abstract interface HibernateTree {
    public static final String DEF_LEFT_NAME = "lft";
    public static final String DEF_RIGHT_NAME = "rgt";
    public static final String DEF_PARENT_NAME = "parent";
    public static final String ENTITY_ALIAS = "bean";

    public abstract String getLftName();

    public abstract String getRgtName();

    public abstract String getParentName();

    public abstract Integer getLft();

    public abstract void setLft(Integer paramInteger);

    public abstract Integer getRgt();

    public abstract void setRgt(Integer paramInteger);

    public abstract Long getParentId();

    public abstract Long getId();

    public abstract String getTreeCondition();
}

