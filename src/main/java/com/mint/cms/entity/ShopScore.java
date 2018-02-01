package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseShopScore;

public class ShopScore extends BaseShopScore {
    private static final long serialVersionUID = 1L;

    public ShopScore() {
    }

    public ShopScore(Long id) {
        super(id);
    }

    public ShopScore(Long id, Integer scoreType, boolean useStatus, boolean status) {
        super(id,
                scoreType,
                useStatus,
                status);
    }

    public static enum ScoreTypes {
        EMAIL_VALIDATE(10),

        ORDER_SCORE(11),

        BUYER_RETURN_PAY(22);

        private int value;

        private ScoreTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static ScoreTypes valueOf(int value) {
            for (ScoreTypes s : values()) {
                if (s.getValue() == value) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Connot find value " + value +
                    " in eunu OrderStatus.");
        }
    }
}

