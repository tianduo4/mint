package com.jspgou.core.entity.base;

import com.jspgou.core.entity.User;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseUser
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "User";
    public static String PROP_LOGIN_COUNT = "loginCount";
    public static String PROP_LAST_LOGIN_IP = "lastLoginIp";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_RESET_KEY = "resetKey";
    public static String PROP_LAST_LOGIN_TIME = "lastLoginTime";
    public static String PROP_RESET_PWD = "resetPwd";
    public static String PROP_PASSWORD = "password";
    public static String PROP_EMAIL = "email";
    public static String PROP_CURRENT_LOGIN_TIME = "currentLoginTime";
    public static String PROP_CURRENT_LOGIN_IP = "currentLoginIp";
    public static String PROP_REGISTER_IP = "registerIp";
    public static String PROP_ID = "id";
    public static String PROP_USERNAME = "username";
    public static String PROP_ERR_TIME = "errTime";
    public static String PROP_ERR_COUNT = "errCount";
    public static String PROP_ERR_IP = "errIp";

    private int hashCode = -2147483648;
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date createTime;
    private Long loginCount;
    private String registerIp;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Date currentLoginTime;
    private String currentLoginIp;
    private String resetKey;
    private String resetPwd;
    private String sessionId;
    private Date errTime;
    private Integer errCount;
    private String errIp;

    public BaseUser() {
        initialize();
    }

    public BaseUser(Long id) {
        setId(id);
        initialize();
    }

    public BaseUser(Long id, String username, String password, Date createTime, Long loginCount, Integer errCount) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setCreateTime(createTime);
        setLoginCount(loginCount);
        setErrCount(errCount);

        initialize();
    }

    protected void initialize() {
    }

    public Integer getErrCount() {
        return this.errCount;
    }

    public void setErrCount(Integer errCount) {
        this.errCount = errCount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrIp() {
        return this.errIp;
    }

    public void setErrIp(String errIp) {
        this.errIp = errIp;
    }

    public Date getErrTime() {
        return this.errTime;
    }

    public void setErrTime(Date errTime) {
        this.errTime = errTime;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getLoginCount() {
        return this.loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public String getRegisterIp() {
        return this.registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getCurrentLoginTime() {
        return this.currentLoginTime;
    }

    public void setCurrentLoginTime(Date currentLoginTime) {
        this.currentLoginTime = currentLoginTime;
    }

    public String getCurrentLoginIp() {
        return this.currentLoginIp;
    }

    public void setCurrentLoginIp(String currentLoginIp) {
        this.currentLoginIp = currentLoginIp;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getResetPwd() {
        return this.resetPwd;
    }

    public void setResetPwd(String resetPwd) {
        this.resetPwd = resetPwd;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;

        User user = (User) obj;
        if ((getId() == null) || (user.getId() == null)) return false;
        return getId().equals(user.getId());
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

