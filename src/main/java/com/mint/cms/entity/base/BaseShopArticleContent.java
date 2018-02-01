package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopArticle;
import com.mint.cms.entity.ShopArticleContent;

import java.io.Serializable;

public abstract class BaseShopArticleContent
        implements Serializable {
    public static String REF = "ShopArticleContent";
    public static String PROP_CONTENT = "content";
    public static String PROP_ID = "id";
    public static String PROP_ARTICLE = "article";

    private int hashCode = -2147483648;
    private Long id;
    private String content;
    private ShopArticle article;

    public BaseShopArticleContent() {
        initialize();
    }

    public BaseShopArticleContent(Long id) {
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ShopArticle getArticle() {
        return this.article;
    }

    public void setArticle(ShopArticle article) {
        this.article = article;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopArticleContent)) return false;

        ShopArticleContent shopArticleContent = (ShopArticleContent) obj;
        if ((getId() == null) || (shopArticleContent.getId() == null)) return false;
        return getId().equals(shopArticleContent.getId());
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

