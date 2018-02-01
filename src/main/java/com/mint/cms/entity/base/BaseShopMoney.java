package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopMoney;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseShopMoney
        implements Serializable {
    public static String REF = "ShopMoney";
    public static String PROP_NAME = "name";
    public static String PROP_STATUS = "status";
    public static String PROP_MEMBER = "member";
    public static String PROP_MONEY = "money";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_ID = "id";
    public static String PROP_REMARK = "remark";

    private int hashCode = -2147483648;
    private Long id;
    private String name;
    private Double money;
    private boolean status;
    private Date createTime;
    private String remark;
    private ShopMember member;

    public BaseShopMoney() {
        initialize();
    }

    public BaseShopMoney(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopMoney(Long id, String name, Double money, boolean status, Date createTime) {
        setId(id);
        setName(name);
        setMoney(money);
        setStatus(status);
        setCreateTime(createTime);
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

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopMoney)) return false;

        ShopMoney shopMoney = (ShopMoney) obj;
        if ((getId() == null) || (shopMoney.getId() == null)) return false;
        return getId().equals(shopMoney.getId());
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

