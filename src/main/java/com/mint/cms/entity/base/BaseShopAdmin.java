package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopAdmin;
import com.mint.core.entity.Admin;
import com.mint.core.entity.Website;

import java.io.Serializable;

public abstract class BaseShopAdmin
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopAdmin";
    public static String PROP_LASTNAME = "lastname";
    public static String PROP_WEBSITE = "website";
    public static String PROP_FIRSTNAME = "firstname";
    public static String PROP_ADMIN = "admin";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String firstname;
    private String lastname;
    private Admin admin;
    private Website website;

    public BaseShopAdmin() {
        initialize();
    }

    public BaseShopAdmin(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopAdmin(Long id, Website website) {
        setId(id);
        setWebsite(website);
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

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopAdmin)) return false;

        ShopAdmin shopAdmin = (ShopAdmin) obj;
        if ((getId() == null) || (shopAdmin.getId() == null)) return false;
        return getId().equals(shopAdmin.getId());
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

