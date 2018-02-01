package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Coupon;
import com.jspgou.cms.entity.MemberCoupon;
import com.jspgou.cms.entity.ShopMember;

import java.io.Serializable;

public abstract class BaseMemberCoupon
        implements Serializable {
    public static String REF = "MemberCoupon";
    public static String PROP_MEMBER = "member";
    public static String PROP_COUPON = "coupon";
    public static String PROP_ID = "id";
    public static String PROP_ISUSE = "isuse";

    private int hashCode = -2147483648;
    private Long id;
    private Boolean isuse;
    private ShopMember member;
    private Coupon coupon;

    public BaseMemberCoupon() {
        initialize();
    }

    public BaseMemberCoupon(Long id) {
        setId(id);
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

    public Boolean getIsuse() {
        return this.isuse;
    }

    public void setIsuse(Boolean isuse) {
        this.isuse = isuse;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MemberCoupon)) return false;

        MemberCoupon memberCoupon = (MemberCoupon) obj;
        if ((getId() == null) || (memberCoupon.getId() == null)) return false;
        return getId().equals(memberCoupon.getId());
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

