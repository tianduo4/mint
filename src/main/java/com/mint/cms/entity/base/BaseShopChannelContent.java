package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopChannel;
import com.mint.cms.entity.ShopChannelContent;

import java.io.Serializable;

public abstract class BaseShopChannelContent
        implements Serializable {
    public static String REF = "ShopChannelContent";
    public static String PROP_CHANNEL = "channel";
    public static String PROP_CONTENT = "content";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String content;
    private ShopChannel channel;

    public BaseShopChannelContent() {
        initialize();
    }

    public BaseShopChannelContent(Integer id) {
        setId(id);
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ShopChannel getChannel() {
        return this.channel;
    }

    public void setChannel(ShopChannel channel) {
        this.channel = channel;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopChannelContent)) return false;

        ShopChannelContent shopChannelContent = (ShopChannelContent) obj;
        if ((getId() == null) || (shopChannelContent.getId() == null)) return false;
        return getId().equals(shopChannelContent.getId());
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

