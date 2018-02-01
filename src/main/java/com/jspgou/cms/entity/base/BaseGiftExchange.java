package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Gift;
import com.jspgou.cms.entity.GiftExchange;
import com.jspgou.cms.entity.ShopMember;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseGiftExchange
        implements Serializable {
    public static String REF = "GiftExchange";
    public static String PROP_STATUS = "status";
    public static String PROP_MEMBER = "member";
    public static String PROP_AMOUNT = "amount";
    public static String PROP_DETAILADDRESS = "detailaddress";
    public static String PROP_GIFT = "gift";
    public static String PROP_CREATE_TIME = "createTime";
    public static String PROP_WAYBILL = "waybill";
    public static String PROP_TOTAL_SCORE = "totalScore";
    public static String PROP_ID = "id";
    public static String PROP_SCORE = "score";

    private int hashCode = -2147483648;
    private Long id;
    private Integer score;
    private Integer amount;
    private Date createTime;
    private Integer totalScore;
    private String detailaddress;
    private Integer status;
    private String waybill;
    private ShopMember member;
    private Gift gift;

    public BaseGiftExchange() {
        initialize();
    }

    public BaseGiftExchange(Long id) {
        setId(id);
        initialize();
    }

    public BaseGiftExchange(Long id, ShopMember member, Gift gift, Date createTime, Integer status) {
        setId(id);
        setMember(member);
        setGift(gift);
        setCreateTime(createTime);
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

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getDetailaddress() {
        return this.detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWaybill() {
        return this.waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public Gift getGift() {
        return this.gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof GiftExchange)) return false;

        GiftExchange giftExchange = (GiftExchange) obj;
        if ((getId() == null) || (giftExchange.getId() == null)) return false;
        return getId().equals(giftExchange.getId());
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

