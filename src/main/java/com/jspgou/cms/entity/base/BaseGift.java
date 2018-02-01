package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Gift;

import java.io.Serializable;

public abstract class BaseGift
        implements Serializable {
    public static String REF = "Gift";
    public static String PROP_GIFT_STOCK = "giftStock";
    public static String PROP_GIFT_NAME = "giftName";
    public static String PROP_GIFT_PICTURE = "giftPicture";
    public static String PROP_GIFT_SCORE = "giftScore";
    public static String PROP_ID = "id";
    public static String PROP_ISGIFT = "isgift";

    private int hashCode = -2147483648;
    private Long id;
    private String giftName;
    private Integer giftScore;
    private Integer giftStock;
    private String giftPicture;
    private Boolean isgift;
    private String introduced;

    public BaseGift() {
        initialize();
    }

    public BaseGift(Long id) {
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

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftScore() {
        return this.giftScore;
    }

    public void setGiftScore(Integer giftScore) {
        this.giftScore = giftScore;
    }

    public Integer getGiftStock() {
        return this.giftStock;
    }

    public void setGiftStock(Integer giftStock) {
        this.giftStock = giftStock;
    }

    public String getGiftPicture() {
        return this.giftPicture;
    }

    public void setGiftPicture(String giftPicture) {
        this.giftPicture = giftPicture;
    }

    public Boolean getIsgift() {
        return this.isgift;
    }

    public void setIsgift(Boolean isgift) {
        this.isgift = isgift;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Gift)) return false;

        Gift gift = (Gift) obj;
        if ((getId() == null) || (gift.getId() == null)) return false;
        return getId().equals(gift.getId());
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

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getIntroduced() {
        return this.introduced;
    }
}

