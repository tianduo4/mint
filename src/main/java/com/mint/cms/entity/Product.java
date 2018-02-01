package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseProduct;
import com.mint.cms.web.threadvariable.GroupThread;
import com.mint.core.entity.Website;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product extends BaseProduct
        implements ProductInfo {
    private static final long serialVersionUID = 1L;
    public static int DEL_STATUS = 0;

    public static int ON_SALE_STATUS = 1;

    public static int NOT_SALE_STATUS = 2;

    public static int RECLE_STATUS = 3;

    public static String DETAIL_SUFFIX = "_d";

    public static String LIST_SUFFIX = "_l";

    public static String MIN_SUFFIX = "_m";

    public Double getMemberPrice() {
        ShopMemberGroup group = GroupThread.get();
        if (group == null) {
            return getSalePrice();
        }
        return getMemberPrice(group);
    }

    public Double getMemberPrice(ShopMemberGroup group) {
        return Double.valueOf(getSalePrice().doubleValue() * group.getDiscount().intValue() / 100.0D);
    }

    public String getUrl() {
        return
                "/" +
                        getCategory().getPath() +
                        "/" +
                        getId() +
                        getWebsite().getSuffix();
    }

    public String getAdminURl() {
        return "/" +
                getCategory().getPath() +
                "/" +
                getId() +
                getWebsite().getSuffix();
    }

    public String getDetailImgUrl() {
        return getImageUrl(getDetailImg());
    }

    public String getListImgUrl() {
        return getImageUrl(getListImg());
    }

    public String getCoverImgUrl() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getCoverImg();
        }
        return null;
    }

    public Double getPrice() {
        ProductFashion fashion = getProductFashion();
        if (fashion != null) {
            return fashion.getSalePrice();
        }
        return getSalePrice();
    }

    public String getMinImgUrl() {
        return getImageUrl(getMinImg());
    }

    public ProductFashion getProductFashion() {
        Set<ProductFashion> set = getFashions();
        for (ProductFashion p : set) {
            if (p.getIsDefault().booleanValue()) {
                return p;
            }
        }
        return null;
    }

    private String getImageUrl(String img) {
        if (StringUtils.isBlank(img)) {
            return null;
        }
        return getWebsite().getUploadUrl(img);
    }

    public List<Category> getCategoryList() {
        return getCategory().getCategoryList();
    }

    public void addToTags(ProductTag tag) {
        Set set = getTags();
        if (set == null) {
            set = new HashSet();
            setTags(set);
        }
        tag.increaseCount();
        set.add(tag);
    }

    public void removeFromTags(ProductTag tag) {
        Set set = getTags();
        if (set != null) {
            tag.reduceCount();
            set.remove(tag);
        }
    }

    public void removeAllTags() {
        Set<ProductTag> set = getTags();
        for (ProductTag tag : set) {
            tag.reduceCount();
        }
        set.clear();
    }

    public Long[] getTagIds() {
        Set<ProductTag> set = getTags();
        if (set == null) {
            return null;
        }
        Long[] tagIds = new Long[set.size()];
        int i = 0;
        for (ProductTag tag : set) {
            tagIds[(i++)] = tag.getId();
        }
        return tagIds;
    }

    public void addToExendeds(String name, String value) {
        List list = getExendeds();
        if (list == null) {
            list = new ArrayList();
            setExendeds(list);
        }
        ProductExended cp = new ProductExended();
        cp.setName(name);
        cp.setValue(value);
        list.add(cp);
    }

    public void addToPictures(String fashionSwitchPic, String fashionBigPic, String fashionAmpPic) {
        List list = getPictures();
        if (list == null) {
            list = new ArrayList();
            setPictures(list);
        }
        ProductPicture pp = new ProductPicture();
        pp.setPicturePath(fashionSwitchPic);
        pp.setBigPicturePath(fashionBigPic);
        pp.setAppPicturePath(fashionAmpPic);
        list.add(pp);
    }

    public String getText() {
        ProductText productText = getProductText();
        if (productText != null) {
            return productText.getText();
        }
        return null;
    }

    public void setText(String s) {
        ProductText productText = getProductText();
        if (productText != null) {
            productText.setText(s);
        } else {
            productText = new ProductText();
            productText.setText(s);
            setProductText(productText);
        }
    }

    public String getText1() {
        ProductText productText = getProductText();
        if (productText != null) {
            return productText.getText1();
        }
        return null;
    }

    public void setText1(String s) {
        ProductText productText = getProductText();
        if (productText != null) {
            productText.setText1(s);
        } else {
            productText = new ProductText();
            productText.setText1(s);
            setProductText(productText);
        }
    }

    public String getText2() {
        ProductText productText = getProductText();
        if (productText != null) {
            return productText.getText2();
        }
        return null;
    }

    public void setText2(String s) {
        ProductText productText = getProductText();
        if (productText != null) {
            productText.setText2(s);
        } else {
            productText = new ProductText();
            productText.setText2(s);
            setProductText(productText);
        }
    }

    public String getWeixinProductUrl() {
        String sourceUrl = getUrl();
        StringBuilder url = new StringBuilder();
        if (!sourceUrl.startsWith(getWebsite().getContextPath())) {
            Website site = getWebsite();
            url.append(site.getContextPath()).append(site.getDomain());
            if (site.getPort() != null) {
                url.append(":").append(site.getPort());
            }
            url.append(sourceUrl);
            sourceUrl = url.toString();
        }
        return sourceUrl;
    }

    public Collection<String> getKeywordArray() {
        return getKeywords();
    }

    public Collection<String> getTagArray() {
        Set<ProductTag> tags = getTags();
        if (tags != null) {
            List list = new ArrayList(tags.size());
            for (ProductTag tag : tags) {
                list.add(tag.getName());
            }
            return list;
        }
        return null;
    }

    public String getBrandName() {
        Brand brand = getBrand();
        if (brand != null) {
            return brand.getName();
        }
        return null;
    }

    public String getBrandId() {
        Brand brand = getBrand();
        if (brand != null) {
            return brand.getId().toString();
        }
        return null;
    }

    public Collection<String> getCategoryNameArray() {
        List<Category> list = getCategoryList();
        List nameList = new ArrayList(list.size());
        for (Category c : list) {
            nameList.add(c.getName());
        }
        return nameList;
    }

    public Collection<Integer> getCategoryIdArray() {
        List<Category> list = getCategoryList();
        List nameList = new ArrayList(list.size());
        for (Category c : list) {
            nameList.add(c.getId());
        }
        return nameList;
    }

    public void init() {
        if (getLackRemind() == null) {
            setLackRemind(Boolean.valueOf(true));
        }

        if (getStatus() == null) {
            setStatus(Integer.valueOf(ON_SALE_STATUS));
        }
        if (getViewCount() == null) {
            setViewCount(Long.valueOf(0L));
        }
        if (getSaleCount() == null) {
            setSaleCount(Integer.valueOf(0));
        }
        if (getStockCount() == null) {
            setStockCount(Integer.valueOf(10));
        }
        if (getMarketPrice() == null) {
            setMarketPrice(Double.valueOf(0.0D));
        }
        if (getSalePrice() == null) {
            setSalePrice(Double.valueOf(0.0D));
        }
        if (getCostPrice() == null) {
            setCostPrice(Double.valueOf(0.0D));
        }
        if (getRecommend() == null) {
            setRecommend(Boolean.valueOf(false));
        }
        if (getSpecial() == null) {
            setSpecial(Boolean.valueOf(false));
        }
        if (getScore() == null) {
            setScore(Integer.valueOf(0));
        }
        if (getLiRun() == null) {
            setLiRun(Double.valueOf(0.0D));
        }
        setCreateTime(new Timestamp(System.currentTimeMillis()));
    }

    public String getMtitle() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getMtitle();
        }
        return null;
    }

    public String getMkeywords() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getMkeywords();
        }
        return null;
    }

    public String getMdescription() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getMdescription();
        }
        return null;
    }

    public String getDetailImg() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getDetailImg();
        }
        return null;
    }

    public String getListImg() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getListImg();
        }
        return null;
    }

    public String getCoverImg() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getCoverImg();
        }
        return null;
    }

    public String getMinImg() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getMinImg();
        }
        return null;
    }

    public Integer getWeight() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getWeight();
        }
        return null;
    }

    public String getUnit() {
        ProductExt ext = getProductExt();
        if (ext != null) {
            return ext.getUnit();
        }
        return null;
    }

    public Integer getProductTotalStockCount() {
        Set<ProductFashion> set = getFashions();
        int store = 0;
        if (set != null) {
            for (ProductFashion s : set) {
                store += s.getStockCount().intValue();
            }
        }

        return Integer.valueOf(store);
    }

    public Integer getProductTotalSaleCount() {
        Set<ProductFashion> set = getFashions();
        int sale = 0;
        if (set != null) {
            for (ProductFashion s : set) {
                sale += s.getSaleCount().intValue();
            }
        }
        return Integer.valueOf(sale);
    }

    public JSONObject convertToJson(String menthod) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("status", CommonUtils.parseInteger(getStatus()));
        json.put("weight", CommonUtils.parseInteger(getWeight()));
        json.put("score", CommonUtils.parseInteger(getScore()));
        json.put("coverImg", CommonUtils.parseStr(getCoverImg()));
        json.put("url", CommonUtils.parseStr(getAdminURl()));
        json.put("shareContent", CommonUtils.parseStr(getShareContent()));
        json.put("mtitle", CommonUtils.parseStr(getMtitle()));
        json.put("mdescription", CommonUtils.parseStr(getMdescription()));
        json.put("mkeywords", CommonUtils.parseStr(getMkeywords()));
        json.put("productKeywords", getKeywordArray() != null ? StringUtils.join(getKeywordArray(), ",") : "");
        json.put("categoryName", getCategory() == null ? "" : CommonUtils.parseStr(getCategory().getName()));
        json.put("categoryId", getCategory() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())));
        json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
        json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));

        json.put("salePrice", CommonUtils.parseDouble(getSalePrice()));
        json.put("marketPrice", CommonUtils.parseDouble(getMarketPrice()));
        json.put("costPrice", CommonUtils.parseDouble(getCostPrice()));
        json.put("stockCount", CommonUtils.parseInteger(getStockCount()));
        json.put("brandId", getBrand() != null ? CommonUtils.parseLong(getBrand().getId()) : "");
        json.put("brandName", getBrand() != null ? CommonUtils.parseStr(getBrand().getName()) : "");
        json.put("recommend", CommonUtils.parseBoolean(getRecommend()));
        json.put("special", CommonUtils.parseBoolean(getSpecial()));
        json.put("hotsale", CommonUtils.parseBoolean(getHotsale()));
        json.put("newProduct", CommonUtils.parseBoolean(getNewProduct()));
        json.put("alertInventory", CommonUtils.parseInteger(getAlertInventory()));

        if ("get".equals(menthod)) {
            json.put("text", CommonUtils.parseStr(getText()));
            json.put("text1", CommonUtils.parseStr(getText1()));
            JSONArray jsonArray = new JSONArray();
            if (getPictures() != null) {
                List<ProductPicture> picture = getPictures();
                for (ProductPicture pic : picture) {
                    jsonArray.put(CommonUtils.parseStr(pic.getPicturePath()));
                }
            }
            json.put("fashionSwitchPic", jsonArray);

            jsonArray = new JSONArray();
            List discontList = new ArrayList();
            if (getFashions() != null) {
                Set<ProductFashion> productFashion = getFashions();
                for (ProductFashion fashion : productFashion) {
                    JSONObject obj = new JSONObject();
                    obj.put("fashionIds", CommonUtils.parseId(fashion.getId()));
                    obj.put("nature", CommonUtils.parseId(fashion.getNature()));
                    obj.put("natureNames", CommonUtils.parseId(fashion.getAttitude()));
                    obj.put("stockCounts", CommonUtils.parseInteger(fashion.getStockCount()));
                    obj.put("salePrices", CommonUtils.parseDouble(fashion.getSalePrice()));
                    obj.put("marketPrices", CommonUtils.parseDouble(fashion.getMarketPrice()));
                    obj.put("costPrices", CommonUtils.parseDouble(fashion.getCostPrice()));
                    obj.put("isDefaults", CommonUtils.parseBoolean(fashion.getIsDefault()));
                    List standard = new ArrayList();

                    if (fashion.getStandards() != null) {
                        standard = fetchId(fashion.getStandards());
                        discontList.addAll(standard);
                    }
                    obj.put("fashion_standardId", standard);
                    jsonArray.put(obj);
                }
            }
            json.put("productFashion", jsonArray);

            if ((discontList != null) && (discontList.size() > 0)) {
                discontList = new ArrayList(new TreeSet(discontList));
            }
            json.put("standardId", discontList);
        }
        return json;
    }

    public List<Long> fetchId(Collection<Standard> standards) {
        List ids = new ArrayList();
        for (Standard sdt : standards) {
            ids.add(sdt.getId());
        }
        return ids;
    }

    public String[] fetchIds(Collection<Standard> standards) {
        String[] ids = new String[standards.size()];
        int i = 0;
        for (Standard sdt : standards) {
            ids[(i++)] = sdt.getName();
        }
        return ids;
    }

    public Product() {
    }

    public Product(Long id) {
        super(id);
    }

    public Product(Long id, ShopConfig config, Category category, ProductType type, Website website, String name, Double marketPrice, Double salePrice, Double costPrice, Long viewCount, Integer saleCount, Integer stockCount, Date createTime, Boolean special, Boolean recommend, Boolean hotsale, Boolean newProduct, Boolean relatedGoods, Integer status) {
        super(id,
                config,
                category,
                type,
                website,
                name,
                marketPrice,
                salePrice,
                costPrice,
                viewCount,
                saleCount,
                stockCount,
                createTime,
                special,
                recommend,
                hotsale,
                newProduct,
                relatedGoods,
                status);
    }
}

