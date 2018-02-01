package com.mint.cms.entity.base;

import com.mint.cms.entity.Exended;
import com.mint.cms.entity.ExendedItem;

import java.io.Serializable;

public abstract class BaseExendedItem
        implements Serializable {
    public static String REF = "ExendedItem";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_EXENDED = "exended";
    public static String PROP_PRIORITY = "priority";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Exended exended;

    public BaseExendedItem() {
        initialize();
    }

    public BaseExendedItem(Long id) {
        setId(id);
        initialize();
    }

    public BaseExendedItem(Long id, Exended exended, String name) {
        setId(id);
        setExended(exended);
        setName(name);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Exended getExended() {
        return this.exended;
    }

    public void setExended(Exended exended) {
        this.exended = exended;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ExendedItem)) return false;

        ExendedItem exendedItem = (ExendedItem) obj;
        if ((getId() == null) || (exendedItem.getId() == null)) return false;
        return getId().equals(exendedItem.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

