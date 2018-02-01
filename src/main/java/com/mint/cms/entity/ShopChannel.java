package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShopChannel;
import com.mint.common.hibernate4.HibernateTree;
import com.mint.common.hibernate4.PriorityComparator;
import com.mint.common.hibernate4.PriorityInterface;
import com.mint.core.entity.Website;

import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ShopChannel extends BaseShopChannel
        implements HibernateTree<Integer>, PriorityInterface {
    private static final long serialVersionUID = 1L;
    public static final int ALONE = 1;
    public static final int ARTICLE = 2;
    public static final int OUTER_URL = 3;
    public static final String CHANNEL_SUFFIX = "栏目";
    public static final String CONTENT_SUFFIX = "内容";
    public static final String ALONE_SUFFIX = "单页";

    public static String getChannelTplDirRel(Website web) {
        return "/" + "channel";
    }

    public static String getContentTplDirRel(Website web) {
        return "/" + "article";
    }

    public static String[] getChannelTpls(Integer type, String realChannelDir, String relChannelPath) {
        String prefix = getPrefix(type, true);
        if (prefix != null) {
            return ProductType.getTpls(realChannelDir, relChannelPath, prefix);
        }
        return null;
    }

    public static String[] getContentTpls(Integer type, String realContentDir, String relContentPath) {
        String prefix = getPrefix(type, false);
        if (prefix != null) {
            return ProductType.getTpls(realContentDir, relContentPath, prefix);
        }
        return null;
    }

    public static String getPrefix(Integer type, boolean isChannel) {
        if (type == null) {
            throw new IllegalStateException("ShopChannle type connot be null");
        }
        if (type.intValue() == 1)
            return "单页";
        if (type.intValue() == 2) {
            return isChannel ? "栏目" : "内容";
        }
        return null;
    }

    public String getTplChannelRel(HttpServletRequest request) {
        String tpl = getTplChannel();
        if (StringUtils.isBlank(tpl)) {
            String suffix = getPrefix(getType(), true);
            if (suffix != null) {
                return getWebsite().getTemplate("channel", "栏目", request);
            }
            return null;
        }

        return getWebsite().getTemplateRel(tpl, request);
    }

    public String getTplContentRel(HttpServletRequest request) {
        String tpl = getTplContent();
        if (StringUtils.isBlank(tpl)) {
            String suffix = getPrefix(getType(), false);

            if (suffix != null) {
                return getWebsite().getTemplate("article", "内容", request);
            }
            return null;
        }

        return getWebsite().getTemplateRel(tpl, request);
    }

    public String getUrl() {
        int type = getType().intValue();
        if (type == 3) {
            String url = getOuterUrl();
            if (StringUtils.isBlank(url)) {
                throw new IllegalStateException(
                        "ShopChannel outerUrl cannot be blank while type is OUTER_URL, ID=" +
                                getId());
            }
            if (url.startsWith("/"))
                return url;
            if (url.startsWith("http")) {
                return url;
            }
            return "http://" + url;
        }
        if (type == 1)
            return "/" + getPath() +
                    getWebsite().getSuffix();
        if (type == 2) {
            return "/" + getPath() +
                    "/" + "index" + getWebsite().getSuffix();
        }

        throw new IllegalStateException(
                "ShopChannel type not supported: id=" + getId() + " type=" +
                        type);
    }

    public String getContent() {
        ShopChannelContent content = getChannelContent();
        if (content != null) {
            return content.getContent();
        }
        return null;
    }

    public int getDeep() {
        int deep = 0;
        ShopChannel parent = getParent();
        while (parent != null) {
            deep++;
            parent = parent.getParent();
        }
        return deep;
    }

    public void addToChild(ShopChannel shopChannel) {
        Set set = getChild();
        if (set == null) {
            set = new TreeSet(PriorityComparator.INSTANCE);
            setChild(set);
        }
        set.add(shopChannel);
    }

    public String getTreeCondition() {
        return "bean.website.id=" + getWebsite().getId();
    }

    public Integer getParentId() {
        ShopChannel parent = getParent();
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    public String getLftName() {
        return "lft";
    }

    public String getParentName() {
        return "parent";
    }

    public String getRgtName() {
        return "rgt";
    }

    public ShopChannel() {
    }

    public ShopChannel(Integer id) {
        super(id);
    }

    public ShopChannel(Integer id, Website website, Integer lft, Integer rgt, Integer type, String name, Integer priority) {
        super(id, website, lft, rgt, type, name, priority);
    }

    public JSONObject converToTreeJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("pid", Integer.valueOf(getParent() != null ? CommonUtils.parseInteger(getParent().getId()) : 0));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("leval", Integer.valueOf(CommonUtils.parseInteger(Integer.valueOf(getDeep()))));
        if ((getChild() != null) && (getChild().size() > 0))
            json.put("isChild", Boolean.valueOf(true));
        else {
            json.put("isChild", Boolean.valueOf(false));
        }
        return json;
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("pid", Integer.valueOf(getParent() != null ? CommonUtils.parseInteger(getParent().getId()) : 0));
        json.put("name", CommonUtils.parseStr(getName()));
        if ((getChild() != null) && (getChild().size() > 0))
            json.put("isChild", Boolean.valueOf(true));
        else {
            json.put("isChild", Boolean.valueOf(false));
        }
        json.put("type", Integer.valueOf(CommonUtils.parseInteger(getType())));
        json.put("content", CommonUtils.parseStr(getContent()));
        json.put("path", CommonUtils.parseStr(getPath()));
        json.put("tplChannel", CommonUtils.parseStr(getTplChannel()));
        json.put("tplContent", CommonUtils.parseStr(getTplContent()));
        json.put("display", CommonUtils.parseBoolean(getDisplay()));
        json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
        json.put("outerUrl", CommonUtils.parseStr(getOuterUrl()));
        json.put("blank", CommonUtils.parseBoolean(getBlank()));
        return json;
    }
}

