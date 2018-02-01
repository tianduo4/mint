package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseBrand;
import com.jspgou.core.entity.Website;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Brand extends BaseBrand {
    private static final long serialVersionUID = 1L;

    public String getText() {
        BrandText brandText = getBrandText();
        if (brandText != null) {
            return brandText.getText();
        }
        return null;
    }

    public BrandText getBrandText() {
        Set set = getBrandTextSet();
        if (set != null) {
            Iterator it = set.iterator();
            if (it.hasNext()) {
                return (BrandText) it.next();
            }
        }
        return null;
    }

    public void removeFromCategorys(Category category) {
        Set set = getCategorys();
        if (set != null)
            set.remove(category);
    }

    public void addToCategory(Category category) {
        Set set = getCategorys();
        if (set == null) {
            set = new HashSet();
            setCategorys(set);
        }
        set.add(category);
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("alias", CommonUtils.parseStr(getAlias()));
        json.put("categoryName", getCategory() == null ? "" : CommonUtils.parseStr(getCategory().getName()));
        json.put("categoryId", getCategory() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())));
        json.put("webUrl", CommonUtils.parseStr(getWebUrl()));
        json.put("text", getBrandText() == null ? "" : CommonUtils.parseStr(getText()));
        json.put("priority", CommonUtils.parseInteger(getPriority()));
        json.put("sift", CommonUtils.parseBoolean(getSift()));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("logoPath", CommonUtils.parseStr(getLogoPath()));
        return json;
    }

    public JSONObject convertToJson1() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("brandId", CommonUtils.parseId(getId()));
        json.put("brandName", CommonUtils.parseStr(getName()));
        return json;
    }

    public Brand() {
    }

    public Brand(Long id) {
        super(id);
    }

    public Brand(Long id, Website website, Category category, String name, Integer priority) {
        super(id,
                website,
                category,
                name,
                priority);
    }
}

