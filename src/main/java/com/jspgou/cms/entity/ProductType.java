package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseProductType;
import com.jspgou.core.entity.Website;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;


public class ProductType extends BaseProductType {
    private static final long serialVersionUID = 1L;

    public String getTplDirRel() {
        return "/" + "category";
    }

    public String getCtgTplDirRel() {
        return "/" + "category";
    }

    public String getTxtTplDirRel() {
        return "/" + "product";
    }

    public String[] getChannelTpls(String realDir, String relPath) {
        return getTpls(realDir, relPath, getChannelPrefix());
    }

    public String[] getContentTpls(String realDir, String relPath) {
        return getTpls(realDir, relPath, getContentPrefix());
    }

    public static String[] getTpls(String realDir, String relPath, String prefix) {
        File dir = new File(realDir);
//     File[] files = dir.listFiles(new FilenameFilter(prefix) //TODO
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.substring(0, name.indexOf("."));
//         return name.endsWith(ProductType.this);
                return false; //TODO
            }
        });
        int len = files.length;
        String[] paths = new String[len];
        for (int i = 0; i < len; i++) {
            paths[i] = (relPath + "/" + files[i].getName());
        }
        return paths;
    }

    public void addToexendeds(Exended exended) {
        Set set = getExendeds();
        if (set == null) {
            set = new HashSet();
            setExendeds(set);
        }
        set.add(exended);
        exended.addToProductTypes(this);
    }

    public void removeFromExendeds(Exended exended) {
        Set set = getExendeds();
        if (set != null)
            set.remove(exended);
    }

    public ProductType() {
    }

    public ProductType(Long id) {
        super(id);
    }

    public JSONObject convertToJson(Long root) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("channelPrefix", CommonUtils.parseStr(getChannelPrefix()));
        json.put("contentPrefix", CommonUtils.parseStr(getContentPrefix()));
        json.put("listImgHeight", CommonUtils.parseInteger(getListImgHeight()));
        json.put("listImgWidth", CommonUtils.parseInteger(getListImgWidth()));
        json.put("minImgHeight", CommonUtils.parseInteger(getMinImgHeight()));
        json.put("minImgWidth", CommonUtils.parseInteger(getMinImgWidth()));
        json.put("detailImgHeight", CommonUtils.parseInteger(getDetailImgHeight()));
        json.put("detailImgWidth", CommonUtils.parseInteger(getDetailImgWidth()));
        if (root != null) {
            json.put("root", root);
        }
        return json;
    }

    public ProductType(Long id, Website website, String name, String path, String channelPrefix, String contentPrefix, Boolean refBrand, Integer detailImgWidth, Integer detailImgHeight, Integer listImgWidth, Integer listImgHeight, Integer minImgWidth, Integer minImgHeight) {
        super(id,
                website,
                name,
                channelPrefix,
                contentPrefix,
                refBrand,
                detailImgWidth,
                detailImgHeight,
                listImgWidth,
                listImgHeight,
                minImgWidth,
                minImgHeight);
    }
}

