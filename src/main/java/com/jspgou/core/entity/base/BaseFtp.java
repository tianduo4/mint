package com.jspgou.core.entity.base;

import com.jspgou.core.entity.Ftp;

import java.io.Serializable;

public abstract class BaseFtp
        implements Serializable {
    public static String REF = "Ftp";
    public static String PROP_TIMEOUT = "timeout";
    public static String PROP_PASSWORD = "password";
    public static String PROP_ENCODING = "encoding";
    public static String PROP_PATH = "path";
    public static String PROP_URL = "url";
    public static String PROP_IP = "ip";
    public static String PROP_PORT = "port";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
    public static String PROP_USERNAME = "username";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String encoding;
    private Integer timeout;
    private String path;
    private String url;

    public BaseFtp() {
        initialize();
    }

    public BaseFtp(Integer id) {
        setId(id);
        initialize();
    }

    public BaseFtp(Integer id, String name, String ip, Integer port, String encoding, String url) {
        setId(id);
        setName(name);
        setIp(ip);
        setPort(port);
        setEncoding(encoding);
        setUrl(url);
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Ftp)) return false;

        Ftp ftp = (Ftp) obj;
        if ((getId() == null) || (ftp.getId() == null)) return false;
        return getId().equals(ftp.getId());
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

