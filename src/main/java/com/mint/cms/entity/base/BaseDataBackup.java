package com.mint.cms.entity.base;

import com.mint.cms.entity.DataBackup;

import java.io.Serializable;

public abstract class BaseDataBackup
        implements Serializable {
    public static String REF = "DataBackup";
    public static String PROP_PASSWORD = "password";
    public static String PROP_USERNAME = "username";
    public static String PROP_ADDRESS = "address";
    public static String PROP_ID = "id";
    public static String PROP_DATA_BASE_NAME = "dataBaseName";
    public static String PROP_DATA_BASE_PATH = "dataBasePath";

    private int hashCode = -2147483648;
    private Integer id;
    private String dataBasePath;
    private String address;
    private String dataBaseName;
    private String username;
    private String password;

    public BaseDataBackup() {
        initialize();
    }

    public BaseDataBackup(Integer id) {
        setId(id);
        initialize();
    }

    public BaseDataBackup(Integer id, String dataBasePath, String address, String dataBaseName, String username, String password) {
        setId(id);
        setDataBasePath(dataBasePath);
        setAddress(address);
        setDataBaseName(dataBaseName);
        setUsername(username);
        setPassword(password);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getDataBasePath() {
        return this.dataBasePath;
    }

    public void setDataBasePath(String dataBasePath) {
        this.dataBasePath = dataBasePath;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDataBaseName() {
        return this.dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof DataBackup)) return false;

        DataBackup dataBackup = (DataBackup) obj;
        if ((getId() == null) || (dataBackup.getId() == null)) return false;
        return getId().equals(dataBackup.getId());
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

