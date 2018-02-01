package com.mint.cms.entity.base;

import com.mint.cms.entity.ShopChannel;
import com.mint.cms.entity.ShopChannelContent;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseShopChannel
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopChannel";
    public static String PROP_TYPE = "type";
    public static String PROP_CHANNEL_CONTENT = "channelContent";
    public static String PROP_PARAM1 = "param1";
    public static String PROP_RGT = "rgt";
    public static String PROP_WEBSITE = "website";
    public static String PROP_TPL_CHANNEL = "tplChannel";
    public static String PROP_PRIORITY = "priority";
    public static String PROP_TPL_CONTENT = "tplContent";
    public static String PROP_OUTER_URL = "outerUrl";
    public static String PROP_BLANK = "blank";
    public static String PROP_PARAM3 = "param3";
    public static String PROP_LFT = "lft";
    public static String PROP_PARENT = "parent";
    public static String PROP_PATH = "path";
    public static String PROP_DISPLAY = "display";
    public static String PROP_NAME = "name";
    public static String PROP_PARAM2 = "param2";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private Integer lft;
    private Integer rgt;
    private Integer type;
    private String name;
    private String path;
    private String outerUrl;
    private String tplChannel;
    private String tplContent;
    private Integer priority;
    private Boolean blank;
    private Boolean display;
    private String param1;
    private String param2;
    private String param3;
    private ShopChannelContent channelContent;
    private ShopChannel parent;
    private Website website;
    private Set<ShopChannel> child;

    public BaseShopChannel() {
        initialize();
    }

    public BaseShopChannel(Integer id) {
        setId(id);
        initialize();
    }

    public BaseShopChannel(Integer id, Website website, Integer lft, Integer rgt, Integer type, String name, Integer priority) {
        setId(id);
        setWebsite(website);
        setLft(lft);
        setRgt(rgt);
        setType(type);
        setName(name);
        setPriority(priority);
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

    public Integer getLft() {
        return this.lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return this.rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOuterUrl() {
        return this.outerUrl;
    }

    public void setOuterUrl(String outerUrl) {
        this.outerUrl = outerUrl;
    }

    public String getTplChannel() {
        return this.tplChannel;
    }

    public void setTplChannel(String tplChannel) {
        this.tplChannel = tplChannel;
    }

    public String getTplContent() {
        return this.tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getBlank() {
        return this.blank;
    }

    public void setBlank(Boolean blank) {
        this.blank = blank;
    }

    public Boolean getDisplay() {
        return this.display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getParam1() {
        return this.param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
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

    public ShopChannelContent getChannelContent() {
        return this.channelContent;
    }

    public void setChannelContent(ShopChannelContent channelContent) {
        this.channelContent = channelContent;
    }

    public ShopChannel getParent() {
        return this.parent;
    }

    public void setParent(ShopChannel parent) {
        this.parent = parent;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Set<ShopChannel> getChild() {
        return this.child;
    }

    public void setChild(Set<ShopChannel> child) {
        this.child = child;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopChannel)) return false;

        ShopChannel shopChannel = (ShopChannel) obj;
        if ((getId() == null) || (shopChannel.getId() == null)) return false;
        return getId().equals(shopChannel.getId());
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

