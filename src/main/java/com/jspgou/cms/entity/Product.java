/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseProduct;
/*     */ import com.jspgou.cms.web.threadvariable.GroupThread;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Product extends BaseProduct
/*     */   implements ProductInfo
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  36 */   public static int DEL_STATUS = 0;
/*     */ 
/*  40 */   public static int ON_SALE_STATUS = 1;
/*     */ 
/*  44 */   public static int NOT_SALE_STATUS = 2;
/*     */ 
/*  48 */   public static int RECLE_STATUS = 3;
/*     */ 
/*  53 */   public static String DETAIL_SUFFIX = "_d";
/*     */ 
/*  57 */   public static String LIST_SUFFIX = "_l";
/*     */ 
/*  61 */   public static String MIN_SUFFIX = "_m";
/*     */ 
/*     */   public Double getMemberPrice()
/*     */   {
/*  69 */     ShopMemberGroup group = GroupThread.get();
/*  70 */     if (group == null) {
/*  71 */       return getSalePrice();
/*     */     }
/*  73 */     return getMemberPrice(group);
/*     */   }
/*     */ 
/*     */   public Double getMemberPrice(ShopMemberGroup group)
/*     */   {
/*  86 */     return Double.valueOf(getSalePrice().doubleValue() * group.getDiscount().intValue() / 100.0D);
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/*  96 */     return 
/*  97 */       "/" + 
/*  98 */       getCategory().getPath() + 
/*  99 */       "/" + 
/* 100 */       getId() + 
/* 101 */       getWebsite().getSuffix();
/*     */   }
/*     */ 
/*     */   public String getAdminURl() {
/* 105 */     return "/" + 
/* 106 */       getCategory().getPath() + 
/* 107 */       "/" + 
/* 108 */       getId() + 
/* 109 */       getWebsite().getSuffix();
/*     */   }
/*     */ 
/*     */   public String getDetailImgUrl()
/*     */   {
/* 120 */     return getImageUrl(getDetailImg());
/*     */   }
/*     */ 
/*     */   public String getListImgUrl()
/*     */   {
/* 130 */     return getImageUrl(getListImg());
/*     */   }
/*     */ 
/*     */   public String getCoverImgUrl() {
/* 134 */     ProductExt ext = getProductExt();
/* 135 */     if (ext != null) {
/* 136 */       return ext.getCoverImg();
/*     */     }
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */   public Double getPrice() {
/* 142 */     ProductFashion fashion = getProductFashion();
/* 143 */     if (fashion != null) {
/* 144 */       return fashion.getSalePrice();
/*     */     }
/* 146 */     return getSalePrice();
/*     */   }
/*     */ 
/*     */   public String getMinImgUrl()
/*     */   {
/* 156 */     return getImageUrl(getMinImg());
/*     */   }
/*     */ 
/*     */   public ProductFashion getProductFashion() {
/* 160 */     Set set = getFashions();
/* 161 */     for (ProductFashion p : set) {
/* 162 */       if (p.getIsDefault().booleanValue()) {
/* 163 */         return p;
/*     */       }
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */   private String getImageUrl(String img)
/*     */   {
/* 171 */     if (StringUtils.isBlank(img)) {
/* 172 */       return null;
/*     */     }
/* 174 */     return getWebsite().getUploadUrl(img);
/*     */   }
/*     */ 
/*     */   public List<Category> getCategoryList() {
/* 178 */     return getCategory().getCategoryList();
/*     */   }
/*     */ 
/*     */   public void addToTags(ProductTag tag)
/*     */   {
/* 187 */     Set set = getTags();
/* 188 */     if (set == null) {
/* 189 */       set = new HashSet();
/* 190 */       setTags(set);
/*     */     }
/* 192 */     tag.increaseCount();
/* 193 */     set.add(tag);
/*     */   }
/*     */ 
/*     */   public void removeFromTags(ProductTag tag)
/*     */   {
/* 202 */     Set set = getTags();
/* 203 */     if (set != null) {
/* 204 */       tag.reduceCount();
/* 205 */       set.remove(tag);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAllTags()
/*     */   {
/* 213 */     Set set = getTags();
/* 214 */     for (ProductTag tag : set) {
/* 215 */       tag.reduceCount();
/*     */     }
/* 217 */     set.clear();
/*     */   }
/*     */ 
/*     */   public Long[] getTagIds()
/*     */   {
/* 226 */     Set set = getTags();
/* 227 */     if (set == null) {
/* 228 */       return null;
/*     */     }
/* 230 */     Long[] tagIds = new Long[set.size()];
/* 231 */     int i = 0;
/* 232 */     for (ProductTag tag : set) {
/* 233 */       tagIds[(i++)] = tag.getId();
/*     */     }
/* 235 */     return tagIds;
/*     */   }
/*     */ 
/*     */   public void addToExendeds(String name, String value)
/*     */   {
/* 240 */     List list = getExendeds();
/* 241 */     if (list == null) {
/* 242 */       list = new ArrayList();
/* 243 */       setExendeds(list);
/*     */     }
/* 245 */     ProductExended cp = new ProductExended();
/* 246 */     cp.setName(name);
/* 247 */     cp.setValue(value);
/* 248 */     list.add(cp);
/*     */   }
/*     */ 
/*     */   public void addToPictures(String fashionSwitchPic, String fashionBigPic, String fashionAmpPic) {
/* 252 */     List list = getPictures();
/* 253 */     if (list == null) {
/* 254 */       list = new ArrayList();
/* 255 */       setPictures(list);
/*     */     }
/* 257 */     ProductPicture pp = new ProductPicture();
/* 258 */     pp.setPicturePath(fashionSwitchPic);
/* 259 */     pp.setBigPicturePath(fashionBigPic);
/* 260 */     pp.setAppPicturePath(fashionAmpPic);
/* 261 */     list.add(pp);
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 270 */     ProductText productText = getProductText();
/* 271 */     if (productText != null) {
/* 272 */       return productText.getText();
/*     */     }
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */   public void setText(String s)
/*     */   {
/* 282 */     ProductText productText = getProductText();
/* 283 */     if (productText != null) {
/* 284 */       productText.setText(s);
/*     */     } else {
/* 286 */       productText = new ProductText();
/* 287 */       productText.setText(s);
/* 288 */       setProductText(productText);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getText1()
/*     */   {
/* 298 */     ProductText productText = getProductText();
/* 299 */     if (productText != null) {
/* 300 */       return productText.getText1();
/*     */     }
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */   public void setText1(String s)
/*     */   {
/* 310 */     ProductText productText = getProductText();
/* 311 */     if (productText != null) {
/* 312 */       productText.setText1(s);
/*     */     } else {
/* 314 */       productText = new ProductText();
/* 315 */       productText.setText1(s);
/* 316 */       setProductText(productText);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getText2()
/*     */   {
/* 326 */     ProductText productText = getProductText();
/* 327 */     if (productText != null) {
/* 328 */       return productText.getText2();
/*     */     }
/* 330 */     return null;
/*     */   }
/*     */ 
/*     */   public void setText2(String s)
/*     */   {
/* 338 */     ProductText productText = getProductText();
/* 339 */     if (productText != null) {
/* 340 */       productText.setText2(s);
/*     */     } else {
/* 342 */       productText = new ProductText();
/* 343 */       productText.setText2(s);
/* 344 */       setProductText(productText);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getWeixinProductUrl()
/*     */   {
/* 351 */     String sourceUrl = getUrl();
/* 352 */     StringBuilder url = new StringBuilder();
/* 353 */     if (!sourceUrl.startsWith(getWebsite().getContextPath())) {
/* 354 */       Website site = getWebsite();
/* 355 */       url.append(site.getContextPath()).append(site.getDomain());
/* 356 */       if (site.getPort() != null) {
/* 357 */         url.append(":").append(site.getPort());
/*     */       }
/* 359 */       url.append(sourceUrl);
/* 360 */       sourceUrl = url.toString();
/*     */     }
/* 362 */     return sourceUrl;
/*     */   }
/*     */ 
/*     */   public Collection<String> getKeywordArray()
/*     */   {
/* 370 */     return getKeywords();
/*     */   }
/*     */ 
/*     */   public Collection<String> getTagArray()
/*     */   {
/* 375 */     Set tags = getTags();
/* 376 */     if (tags != null) {
/* 377 */       List list = new ArrayList(tags.size());
/* 378 */       for (ProductTag tag : tags) {
/* 379 */         list.add(tag.getName());
/*     */       }
/* 381 */       return list;
/*     */     }
/* 383 */     return null;
/*     */   }
/*     */ 
/*     */   public String getBrandName()
/*     */   {
/* 389 */     Brand brand = getBrand();
/* 390 */     if (brand != null) {
/* 391 */       return brand.getName();
/*     */     }
/* 393 */     return null;
/*     */   }
/*     */ 
/*     */   public String getBrandId()
/*     */   {
/* 398 */     Brand brand = getBrand();
/* 399 */     if (brand != null) {
/* 400 */       return brand.getId().toString();
/*     */     }
/* 402 */     return null;
/*     */   }
/*     */ 
/*     */   public Collection<String> getCategoryNameArray()
/*     */   {
/* 408 */     List list = getCategoryList();
/* 409 */     List nameList = new ArrayList(list.size());
/* 410 */     for (Category c : list) {
/* 411 */       nameList.add(c.getName());
/*     */     }
/* 413 */     return nameList;
/*     */   }
/*     */ 
/*     */   public Collection<Integer> getCategoryIdArray()
/*     */   {
/* 418 */     List list = getCategoryList();
/* 419 */     List nameList = new ArrayList(list.size());
/* 420 */     for (Category c : list) {
/* 421 */       nameList.add(c.getId());
/*     */     }
/* 423 */     return nameList;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 430 */     if (getLackRemind() == null) {
/* 431 */       setLackRemind(Boolean.valueOf(true));
/*     */     }
/*     */ 
/* 436 */     if (getStatus() == null) {
/* 437 */       setStatus(Integer.valueOf(ON_SALE_STATUS));
/*     */     }
/* 439 */     if (getViewCount() == null) {
/* 440 */       setViewCount(Long.valueOf(0L));
/*     */     }
/* 442 */     if (getSaleCount() == null) {
/* 443 */       setSaleCount(Integer.valueOf(0));
/*     */     }
/* 445 */     if (getStockCount() == null) {
/* 446 */       setStockCount(Integer.valueOf(10));
/*     */     }
/* 448 */     if (getMarketPrice() == null) {
/* 449 */       setMarketPrice(Double.valueOf(0.0D));
/*     */     }
/* 451 */     if (getSalePrice() == null) {
/* 452 */       setSalePrice(Double.valueOf(0.0D));
/*     */     }
/* 454 */     if (getCostPrice() == null) {
/* 455 */       setCostPrice(Double.valueOf(0.0D));
/*     */     }
/* 457 */     if (getRecommend() == null) {
/* 458 */       setRecommend(Boolean.valueOf(false));
/*     */     }
/* 460 */     if (getSpecial() == null) {
/* 461 */       setSpecial(Boolean.valueOf(false));
/*     */     }
/* 463 */     if (getScore() == null) {
/* 464 */       setScore(Integer.valueOf(0));
/*     */     }
/* 466 */     if (getLiRun() == null) {
/* 467 */       setLiRun(Double.valueOf(0.0D));
/*     */     }
/* 469 */     setCreateTime(new Timestamp(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public String getMtitle() {
/* 473 */     ProductExt ext = getProductExt();
/* 474 */     if (ext != null) {
/* 475 */       return ext.getMtitle();
/*     */     }
/* 477 */     return null;
/*     */   }
/*     */ 
/*     */   public String getMkeywords()
/*     */   {
/* 482 */     ProductExt ext = getProductExt();
/* 483 */     if (ext != null) {
/* 484 */       return ext.getMkeywords();
/*     */     }
/* 486 */     return null;
/*     */   }
/*     */ 
/*     */   public String getMdescription()
/*     */   {
/* 491 */     ProductExt ext = getProductExt();
/* 492 */     if (ext != null) {
/* 493 */       return ext.getMdescription();
/*     */     }
/* 495 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDetailImg()
/*     */   {
/* 500 */     ProductExt ext = getProductExt();
/* 501 */     if (ext != null) {
/* 502 */       return ext.getDetailImg();
/*     */     }
/* 504 */     return null;
/*     */   }
/*     */ 
/*     */   public String getListImg()
/*     */   {
/* 509 */     ProductExt ext = getProductExt();
/* 510 */     if (ext != null) {
/* 511 */       return ext.getListImg();
/*     */     }
/* 513 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCoverImg()
/*     */   {
/* 518 */     ProductExt ext = getProductExt();
/* 519 */     if (ext != null) {
/* 520 */       return ext.getCoverImg();
/*     */     }
/* 522 */     return null;
/*     */   }
/*     */ 
/*     */   public String getMinImg()
/*     */   {
/* 527 */     ProductExt ext = getProductExt();
/* 528 */     if (ext != null) {
/* 529 */       return ext.getMinImg();
/*     */     }
/* 531 */     return null;
/*     */   }
/*     */ 
/*     */   public Integer getWeight()
/*     */   {
/* 536 */     ProductExt ext = getProductExt();
/* 537 */     if (ext != null) {
/* 538 */       return ext.getWeight();
/*     */     }
/* 540 */     return null;
/*     */   }
/*     */ 
/*     */   public String getUnit()
/*     */   {
/* 545 */     ProductExt ext = getProductExt();
/* 546 */     if (ext != null) {
/* 547 */       return ext.getUnit();
/*     */     }
/* 549 */     return null;
/*     */   }
/*     */ 
/*     */   public Integer getProductTotalStockCount()
/*     */   {
/* 555 */     Set set = getFashions();
/* 556 */     int store = 0;
/* 557 */     if (set != null) {
/* 558 */       for (ProductFashion s : set) {
/* 559 */         store += s.getStockCount().intValue();
/*     */       }
/*     */     }
/*     */ 
/* 563 */     return Integer.valueOf(store);
/*     */   }
/*     */ 
/*     */   public Integer getProductTotalSaleCount() {
/* 567 */     Set set = getFashions();
/* 568 */     int sale = 0;
/* 569 */     if (set != null) {
/* 570 */       for (ProductFashion s : set) {
/* 571 */         sale += s.getSaleCount().intValue();
/*     */       }
/*     */     }
/* 574 */     return Integer.valueOf(sale);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson(String menthod) throws JSONException
/*     */   {
/* 579 */     JSONObject json = new JSONObject();
/* 580 */     json.put("id", CommonUtils.parseId(getId()));
/* 581 */     json.put("name", CommonUtils.parseStr(getName()));
/* 582 */     json.put("status", CommonUtils.parseInteger(getStatus()));
/* 583 */     json.put("weight", CommonUtils.parseInteger(getWeight()));
/* 584 */     json.put("score", CommonUtils.parseInteger(getScore()));
/* 585 */     json.put("coverImg", CommonUtils.parseStr(getCoverImg()));
/* 586 */     json.put("url", CommonUtils.parseStr(getAdminURl()));
/* 587 */     json.put("shareContent", CommonUtils.parseStr(getShareContent()));
/* 588 */     json.put("mtitle", CommonUtils.parseStr(getMtitle()));
/* 589 */     json.put("mdescription", CommonUtils.parseStr(getMdescription()));
/* 590 */     json.put("mkeywords", CommonUtils.parseStr(getMkeywords()));
/* 591 */     json.put("productKeywords", getKeywordArray() != null ? StringUtils.join(getKeywordArray(), ",") : "");
/* 592 */     json.put("categoryName", getCategory() == null ? "" : CommonUtils.parseStr(getCategory().getName()));
/* 593 */     json.put("categoryId", getCategory() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())));
/* 594 */     json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
/* 595 */     json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
/*     */ 
/* 597 */     json.put("salePrice", CommonUtils.parseDouble(getSalePrice()));
/* 598 */     json.put("marketPrice", CommonUtils.parseDouble(getMarketPrice()));
/* 599 */     json.put("costPrice", CommonUtils.parseDouble(getCostPrice()));
/* 600 */     json.put("stockCount", CommonUtils.parseInteger(getStockCount()));
/* 601 */     json.put("brandId", getBrand() != null ? CommonUtils.parseLong(getBrand().getId()) : "");
/* 602 */     json.put("brandName", getBrand() != null ? CommonUtils.parseStr(getBrand().getName()) : "");
/* 603 */     json.put("recommend", CommonUtils.parseBoolean(getRecommend()));
/* 604 */     json.put("special", CommonUtils.parseBoolean(getSpecial()));
/* 605 */     json.put("hotsale", CommonUtils.parseBoolean(getHotsale()));
/* 606 */     json.put("newProduct", CommonUtils.parseBoolean(getNewProduct()));
/* 607 */     json.put("alertInventory", CommonUtils.parseInteger(getAlertInventory()));
/*     */ 
/* 609 */     if ("get".equals(menthod)) {
/* 610 */       json.put("text", CommonUtils.parseStr(getText()));
/* 611 */       json.put("text1", CommonUtils.parseStr(getText1()));
/* 612 */       JSONArray jsonArray = new JSONArray();
/* 613 */       if (getPictures() != null) {
/* 614 */         List picture = getPictures();
/* 615 */         for (ProductPicture pic : picture) {
/* 616 */           jsonArray.put(CommonUtils.parseStr(pic.getPicturePath()));
/*     */         }
/*     */       }
/* 619 */       json.put("fashionSwitchPic", jsonArray);
/*     */ 
/* 621 */       jsonArray = new JSONArray();
/* 622 */       List discontList = new ArrayList();
/* 623 */       if (getFashions() != null) {
/* 624 */         Set productFashion = getFashions();
/* 625 */         for (ProductFashion fashion : productFashion) {
/* 626 */           JSONObject obj = new JSONObject();
/* 627 */           obj.put("fashionIds", CommonUtils.parseId(fashion.getId()));
/* 628 */           obj.put("nature", CommonUtils.parseId(fashion.getNature()));
/* 629 */           obj.put("natureNames", CommonUtils.parseId(fashion.getAttitude()));
/* 630 */           obj.put("stockCounts", CommonUtils.parseInteger(fashion.getStockCount()));
/* 631 */           obj.put("salePrices", CommonUtils.parseDouble(fashion.getSalePrice()));
/* 632 */           obj.put("marketPrices", CommonUtils.parseDouble(fashion.getMarketPrice()));
/* 633 */           obj.put("costPrices", CommonUtils.parseDouble(fashion.getCostPrice()));
/* 634 */           obj.put("isDefaults", CommonUtils.parseBoolean(fashion.getIsDefault()));
/* 635 */           List standard = new ArrayList();
/*     */ 
/* 637 */           if (fashion.getStandards() != null) {
/* 638 */             standard = fetchId(fashion.getStandards());
/* 639 */             discontList.addAll(standard);
/*     */           }
/* 641 */           obj.put("fashion_standardId", standard);
/* 642 */           jsonArray.put(obj);
/*     */         }
/*     */       }
/* 645 */       json.put("productFashion", jsonArray);
/*     */ 
/* 647 */       if ((discontList != null) && (discontList.size() > 0)) {
/* 648 */         discontList = new ArrayList(new TreeSet(discontList));
/*     */       }
/* 650 */       json.put("standardId", discontList);
/*     */     }
/* 652 */     return json;
/*     */   }
/*     */   public List<Long> fetchId(Collection<Standard> standards) {
/* 655 */     List ids = new ArrayList();
/* 656 */     for (Standard sdt : standards) {
/* 657 */       ids.add(sdt.getId());
/*     */     }
/* 659 */     return ids;
/*     */   }
/*     */ 
/*     */   public String[] fetchIds(Collection<Standard> standards) {
/* 663 */     String[] ids = new String[standards.size()];
/* 664 */     int i = 0;
/* 665 */     for (Standard sdt : standards) {
/* 666 */       ids[(i++)] = sdt.getName();
/*     */     }
/* 668 */     return ids;
/*     */   }
/*     */ 
/*     */   public Product()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Product(Long id)
/*     */   {
/* 680 */     super(id);
/*     */   }
/*     */ 
/*     */   public Product(Long id, ShopConfig config, Category category, ProductType type, Website website, String name, Double marketPrice, Double salePrice, Double costPrice, Long viewCount, Integer saleCount, Integer stockCount, Date createTime, Boolean special, Boolean recommend, Boolean hotsale, Boolean newProduct, Boolean relatedGoods, Integer status)
/*     */   {
/* 727 */     super(id, 
/* 710 */       config, 
/* 711 */       category, 
/* 712 */       type, 
/* 713 */       website, 
/* 714 */       name, 
/* 715 */       marketPrice, 
/* 716 */       salePrice, 
/* 717 */       costPrice, 
/* 718 */       viewCount, 
/* 719 */       saleCount, 
/* 720 */       stockCount, 
/* 721 */       createTime, 
/* 722 */       special, 
/* 723 */       recommend, 
/* 724 */       hotsale, 
/* 725 */       newProduct, 
/* 726 */       relatedGoods, 
/* 727 */       status);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Product
 * JD-Core Version:    0.6.0
 */