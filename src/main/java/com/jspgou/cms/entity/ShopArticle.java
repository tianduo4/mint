package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseShopArticle;
import com.jspgou.core.entity.Website;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ShopArticle extends BaseShopArticle {
    private static final long serialVersionUID = 1L;

    public String getUrl() {
        if (!StringUtils.isBlank(getLink())) {
            return getLink();
        }
        return "/" +
                getChannel().getPath() + "/" + getId() +
                getWebsite().getSuffix();
    }

    public String getContent() {
        ShopArticleContent content = getArticleContent();
        if (content != null) {
            return content.getContent();
        }
        return null;
    }

    public void init() {
        Date d = getPublishTime();
        if (d == null)
            setPublishTime(new Timestamp(System.currentTimeMillis()));
    }

    public ShopArticle() {
    }

    public ShopArticle(Long id) {
        super(id);
    }

    public ShopArticle(Long id, Website website, ShopChannel channel, String title, Date publishTime, Boolean disabled) {
        super(id, website, channel, title, publishTime, disabled);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("channelId", getChannel() != null ? Integer.valueOf(CommonUtils.parseInteger(getChannel().getId())) : "");
        json.put("channelName", getChannel() != null ? CommonUtils.parseStr(getChannel().getName()) : "");
        json.put("title", CommonUtils.parseStr(getTitle()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("content", CommonUtils.parseStr(getContent()));
        return json;
    }
}

