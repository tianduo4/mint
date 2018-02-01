package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.CustomerService;

import java.io.Serializable;

public abstract class BaseCustomerService
        implements Serializable {
    public static String REF = "CustomerService";
    public static String PROP_NAME = "name";
    public static String PROP_DISABLE = "disable";
    public static String PROP_TYPE = "type";
    public static String PROP_ID = "id";
    public static String PROP_CONTENT = "content";
    public static String PROP_PRIORITY = "priority";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private String type;
    private String content;
    private Integer priority;
    private Boolean disable;

    public BaseCustomerService() {
        initialize();
    }

    public BaseCustomerService(Long id) {
        setId(id);
        initialize();
    }

    public BaseCustomerService(Long id, String name, String type, String content, Integer priority, Boolean disable) {
        setId(id);
        setName(name);
        setType(type);
        setContent(content);
        setPriority(priority);
        setDisable(disable);
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getDisable() {
        return this.disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof CustomerService)) return false;

        CustomerService customerService = (CustomerService) obj;
        if ((getId() == null) || (customerService.getId() == null)) return false;
        return getId().equals(customerService.getId());
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

