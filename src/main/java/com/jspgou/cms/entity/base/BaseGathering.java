package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.Gathering;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.ShopAdmin;

import java.io.Serializable;

public abstract class BaseGathering
        implements Serializable {
    public static String REF = "Gathering";
    public static String PROP_DRAWEE = "drawee";
    public static String PROP_ACCOUNTS = "accounts";
    public static String PROP_MONEY = "money";
    public static String PROP_BANK = "bank";
    public static String PROP_COMMENT = "comment";
    public static String PROP_ID = "id";
    public static String PROP_SHOP_ADMIN = "shopAdmin";
    public static String PROP_INDENT = "indent";

    private int hashCode = -2147483648;
    private Long id;
    private String bank;
    private String accounts;
    private double money;
    private String drawee;
    private String comment;
    private Order indent;
    private ShopAdmin shopAdmin;

    public BaseGathering() {
        initialize();
    }

    public BaseGathering(Long id) {
        setId(id);
        initialize();
    }

    public BaseGathering(Long id, Order indent, ShopAdmin shopAdmin, String bank, String accounts, String drawee, String comment) {
        setId(id);
        setIndent(indent);
        setShopAdmin(shopAdmin);
        setBank(bank);
        setAccounts(accounts);
        setDrawee(drawee);
        setComment(comment);
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

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccounts() {
        return this.accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDrawee() {
        return this.drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Order getIndent() {
        return this.indent;
    }

    public void setIndent(Order indent) {
        this.indent = indent;
    }

    public ShopAdmin getShopAdmin() {
        return this.shopAdmin;
    }

    public void setShopAdmin(ShopAdmin shopAdmin) {
        this.shopAdmin = shopAdmin;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Gathering)) return false;

        Gathering gathering = (Gathering) obj;
        if ((getId() == null) || (gathering.getId() == null)) return false;
        return getId().equals(gathering.getId());
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

