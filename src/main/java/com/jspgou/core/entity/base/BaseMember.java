package com.jspgou.core.entity.base;

import com.jspgou.core.entity.Member;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseMember
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Member";
    public static String PROP_WEBSITE = "website";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_USER = "user";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private Date createTime;
    private Boolean disabled;
    private Boolean active;
    private String activationCode;
    private User user;
    private Website website;

    public BaseMember() {
        initialize();
    }

    public BaseMember(Long id) {
        setId(id);
        initialize();
    }

    public BaseMember(Long id, User user, Website website, Date createTime, Boolean disabled, Boolean active, String activationCode) {
        setId(id);
        setUser(user);
        setWebsite(website);
        setCreateTime(createTime);
        setDisabled(disabled);
        setActive(active);
        setActivationCode(activationCode);
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Member)) return false;

        Member member = (Member) obj;
        if ((getId() == null) || (member.getId() == null)) return false;
        return getId().equals(member.getId());
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

