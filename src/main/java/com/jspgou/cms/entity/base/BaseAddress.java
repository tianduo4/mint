package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Address;

import java.io.Serializable;

public abstract class BaseAddress
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Address";
    public static String PROP_NAME = "name";
    public static String PROP_PARENT = "parent";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Integer priority;
    private Address parent;

    public BaseAddress() {
        initialize();
    }

    public BaseAddress(Long id) {
        setId(id);
        initialize();
    }

    public BaseAddress(Long id, String name) {
        setId(id);
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

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Address getParent() {
        return this.parent;
    }

    public void setParent(Address parent) {
        this.parent = parent;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Address)) return false;

        Address address = (Address) obj;
        if ((getId() == null) || (address.getId() == null)) return false;
        return getId().equals(address.getId());
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

