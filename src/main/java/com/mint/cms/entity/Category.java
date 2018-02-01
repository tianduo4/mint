package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseCategory;
import com.mint.common.hibernate4.HibernateTree;
import com.mint.common.hibernate4.PriorityComparator;
import com.mint.common.hibernate4.PriorityInterface;
import com.mint.core.entity.Website;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Category extends BaseCategory
        implements HibernateTree<Integer>, PriorityInterface {
    private static final long serialVersionUID = 1L;
    private int leval;

    public int getLeval() {
        return this.leval;
    }

    public void setLeval(int leval) {
        this.leval = leval;
    }

    public String getUrl() {
        return "/" + getPath() +
                "/" + "index" + getWebsite().getSuffix();
    }

    public String getTplChannelRel(HttpServletRequest request) {
        String tpl = getTplChannel();
        if (StringUtils.isBlank(tpl)) {
            tpl = getType().getChannelPrefix();
            return getWebsite().getTemplate("category", tpl, request);
        }
        return getWebsite().getTemplateRel(tpl, request);
    }

    public String getTplContentRel(HttpServletRequest request) {
        String tpl = getTplContent();
        if (StringUtils.isBlank(tpl)) {
            tpl = getType().getContentPrefix();
            return getWebsite().getTemplate("product", tpl, request);
        }
        return getWebsite().getTemplateRel(tpl, request);
    }

    public Category getTopCategory() {
        Category ctg = this;
        Category parent = ctg.getParent();
        while (parent != null) {
            ctg = parent;
            parent = parent.getParent();
        }
        return ctg;
    }

    public List<Category> getCategoryList() {
        List list = new LinkedList();
        Category ctg = this;
        while (ctg != null) {
            list.add(0, ctg);
            ctg = ctg.getParent();
        }
        return list;
    }

    public int getDeep() {
        int deep = 0;
        Category parent = getParent();
        while (parent != null) {
            deep++;
            parent = parent.getParent();
        }
        return deep;
    }

    public boolean isLeaf() {
        Set set = getChild();
        return (set != null) && (set.size() > 0);
    }

    public void addToChild(Category category) {
        Set set = getChild();
        if (set == null) {
            set = new TreeSet(PriorityComparator.INSTANCE);
            setChild(set);
        }
        set.add(category);
    }

    public void removeFromChild(Category category) {
        Set set = getChild();
        if (set != null)
            set.remove(category);
    }

    public String getTreeCondition() {
        return "bean.website.id=" + getWebsite().getId();
    }

    public Integer getParentId() {
        Category parent = getParent();
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    public Long[] getBrandIds() {
        Set<Brand> set = getBrands();
        if (set == null) {
            return null;
        }
        Long[] ids = new Long[set.size()];
        int i = 0;
        for (Brand brand : set) {
            ids[i] = brand.getId();
            i++;
        }
        return ids;
    }

    public void addToBrands(Brand brand) {
        Set set = getBrands();
        if (set == null) {
            set = new HashSet();
            setBrands(set);
        }
        set.add(brand);
        brand.addToCategory(this);
    }

    public void addToStandardTypes(StandardType sType) {
        Set set = getStandardType();
        if (set == null) {
            set = new HashSet();
            setStandardType(set);
        }
        set.add(sType);
        sType.addToCategory(this);
    }

    public Long[] getStandardTypeIds() {
        Set<StandardType> set = getStandardType();
        if (set == null) {
            return null;
        }
        Long[] ids = new Long[set.size()];
        int i = 0;
        for (StandardType st : set) {
            ids[i] = st.getId();
            i++;
        }
        return ids;
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

    public Category() {
    }

    public Category(Integer id) {
        super(id);
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("parentId", CommonUtils.parseInteger(getParentId()));
        json.put("categoryName", CommonUtils.parseStr(getName()));
        json.put("leval", CommonUtils.parseInteger(Integer.valueOf(getLeval())));
        return json;
    }

    public JSONObject convertToJson1() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("categoryName", CommonUtils.parseStr(getName()));
        json.put("path", CommonUtils.parseStr(getPath()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
        json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
        return json;
    }

    public JSONObject convertToJson2(boolean isRoot) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("isRoot", CommonUtils.parseBoolean(Boolean.valueOf(isRoot)));
        json.put("isChild", getChild().size() > 0);
        json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
        json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
        return json;
    }

    public JSONObject convertToJson3() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("parentId", CommonUtils.parseInteger(getParentId()));
        json.put("path", CommonUtils.parseStr(getPath()));
        json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
        json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
        json.put("title", CommonUtils.parseStr(getTitle()));
        json.put("keywords", CommonUtils.parseStr(getKeywords()));
        json.put("description", CommonUtils.parseStr(getDescription()));
        json.put("tplChannel", CommonUtils.parseStr(getTplChannel()));
        json.put("tplContent", CommonUtils.parseStr(getTplContent()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("imagePath", CommonUtils.parseStr(getImagePath()));
        json.put("colorsize", CommonUtils.parseBoolean(getColorsize()));

        return json;
    }

    public Category(Integer id, ProductType type, Website website, String name, String path, Integer lft, Integer rgt, Integer priority) {
        super(id,
                type,
                website,
                name,
                path,
                lft,
                rgt,
                priority);
    }
}

