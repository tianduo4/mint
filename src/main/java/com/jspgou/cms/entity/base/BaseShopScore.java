package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopScore;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseShopScore
        implements Serializable {
    public static String REF = "ShopScore";
    public static String PROP_NAME = "name";
    public static String PROP_STATUS = "status";
    public static String PROP_MEMBER = "member";
    public static String PROP_ORDER_ITEM = "orderItem";
    public static String PROP_ORDER = "order";
    public static String PROP_ID = "id";
    public static String PROP_SCORE_TIME = "scoreTime";
    public static String PROP_REMARK = "remark";
    public static String PROP_SCORE_TYPE = "scoreType";
    public static String PROP_SCORE = "score";
    public static String PROP_USE_STATUS = "useStatus";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Integer score;
    private Date scoreTime;
    private Integer scoreType;
    private boolean useStatus;
    private boolean status;
    private String remark;
    private String code;
    private ShopMember member;

    public BaseShopScore() {
        initialize();
    }

    public BaseShopScore(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopScore(Long id, Integer scoreType, boolean useStatus, boolean status) {
        setId(id);
        setScoreType(scoreType);
        setUseStatus(useStatus);
        setStatus(status);
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

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getScoreTime() {
        return this.scoreTime;
    }

    public void setScoreTime(Date scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getScoreType() {
        return this.scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public boolean getUseStatus() {
        return this.useStatus;
    }

    public void setUseStatus(boolean useStatus) {
        this.useStatus = useStatus;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopScore)) return false;

        ShopScore shopScore = (ShopScore) obj;
        if ((getId() == null) || (shopScore.getId() == null)) return false;
        return getId().equals(shopScore.getId());
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

