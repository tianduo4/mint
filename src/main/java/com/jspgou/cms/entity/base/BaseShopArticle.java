package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.core.entity.Website;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseShopArticle
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopArticle";
    public static String PROP_ARTICLE_CONTENT = "articleContent";
    public static String PROP_PUBLISH_TIME = "publishTime";
    public static String PROP_LINK = "link";
    public static String PROP_WEBSITE = "website";
    public static String PROP_PARAM3 = "param3";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_CHANNEL = "channel";
    public static String PROP_TITLE = "title";
    public static String PROP_PARAM2 = "param2";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String title;
    private Date publishTime;
    private Boolean disabled;
    private String link;
    private String param2;
    private String param3;
    private ShopArticleContent articleContent;
    private Website website;
    private ShopChannel channel;

    public BaseShopArticle() {
        initialize();
    }

    public BaseShopArticle(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopArticle(Long id, Website website, ShopChannel channel, String title, Date publishTime, Boolean disabled) {
        setId(id);
        setWebsite(website);
        setChannel(channel);
        setTitle(title);
        setPublishTime(publishTime);
        setDisabled(disabled);
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getParam2() {
        return this.param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return this.param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public ShopArticleContent getArticleContent() {
        return this.articleContent;
    }

    public void setArticleContent(ShopArticleContent articleContent) {
        this.articleContent = articleContent;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public ShopChannel getChannel() {
        return this.channel;
    }

    public void setChannel(ShopChannel channel) {
        this.channel = channel;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopArticle)) return false;

        ShopArticle shopArticle = (ShopArticle) obj;
        if ((getId() == null) || (shopArticle.getId() == null)) return false;
        return getId().equals(shopArticle.getId());
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

