/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductDao;
/*     */ import com.jspgou.cms.dao.ProductFashionDao;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Collect;
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ProductStandard;
/*     */ import com.jspgou.cms.entity.ProductTag;
/*     */ import com.jspgou.cms.entity.ProductText;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.base.BaseProduct;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CartItemMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.CollectMng;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.ProductExtMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductStandardMng;
/*     */ import com.jspgou.cms.manager.ProductTagMng;
/*     */ import com.jspgou.cms.manager.ProductTextMng;
/*     */ import com.jspgou.cms.manager.RelatedgoodsMng;
/*     */ import com.jspgou.cms.manager.ShopConfigMng;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.common.file.FileNameUtils;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.image.ImageScale;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ProductMngImpl
/*     */   implements ProductMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionDao fashDao;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private CollectMng collectMng;
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng consultMng;
/*     */ 
/*     */   @Autowired
/*     */   private CartItemMng cartItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTagMng productTagMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTextMng productTextMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopConfigMng shopConfigMng;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private ImageScale imageScale;
/*     */ 
/*     */   @Autowired
/*     */   private ProductExtMng productExtMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductStandardMng productStandardMng;
/*     */ 
/*     */   @Autowired
/*     */   private RelatedgoodsMng relatedgoodsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng fashMng;
/*     */ 
/*     */   public List<Product> getList(Long typeId, Long brandId, String productName)
/*     */   {
/*  72 */     return this.dao.getList(typeId, brandId, productName, true);
/*     */   }
/*     */ 
/*     */   public void resetSaleTop()
/*     */   {
/*  77 */     List<Product> list = getList(null, null, null);
/*  78 */     for (Product product : list) {
/*  79 */       product.setSaleCount(Integer.valueOf(0));
/*  80 */       update(product);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resetProfitTop()
/*     */   {
/*  86 */     List<Product> list = getList(null, null, null);
/*  87 */     for (Product product : list) {
/*  88 */       product.setLiRun(Double.valueOf(0.0D));
/*  89 */       update(product);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateViewCount(Product product)
/*     */   {
/*  95 */     product.setViewCount(Long.valueOf(product.getViewCount().longValue() + 1L));
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize)
/*     */   {
/*     */     Pagination page;
/* 105 */     if (ctgId != null)
/* 106 */       page = this.dao.getPageByCategory(ctgId, productName, brandName, status, 
/* 107 */         isRecommend, isSpecial, isHotsale, isNewProduct, typeId, 
/* 108 */         startSalePrice, endSalePrice, startStock, endStock, pageNo, pageSize, false);
/*     */     else {
/* 110 */       page = this.dao.getPageByWebsite(webId, productName, brandName, status, 
/* 111 */         isRecommend, isSpecial, isHotsale, isNewProduct, typeId, 
/* 112 */         startSalePrice, endSalePrice, startStock, endStock, pageNo, 
/* 113 */         pageSize, false);
/*     */     }
/* 115 */     return page;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(int orderBy, int pageNo, int pageSize)
/*     */   {
/* 121 */     return this.dao.getPage(orderBy, pageNo, pageSize, true);
/*     */   }
/*     */ 
/*     */   public Pagination getPage1(Long typeId, int orderBy, int pageNo, int pageSize)
/*     */   {
/* 126 */     return this.dao.getPage1(typeId, orderBy, pageNo, pageSize, true);
/*     */   }
/*     */ 
/*     */   public List<Product> findAll()
/*     */   {
/* 131 */     return this.dao.findAll();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPageForTag(Long webId, Integer ctgId, Long tagId, Boolean isRecommend, Boolean isSpecial, int pageNo, int pageSize)
/*     */   {
/*     */     Pagination page;
/* 139 */     if (tagId != null) {
/* 140 */       page = this.dao.getPageByTag(tagId, ctgId, isRecommend, isSpecial, 
/* 141 */         pageNo, pageSize, true);
/*     */     }
/*     */     else
/*     */     {
/* 143 */       if (ctgId != null)
/* 144 */         page = this.dao.getPageByCategory(ctgId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial, 
/* 145 */           null, null, null, null, null, null, null, pageNo, pageSize, true);
/*     */       else {
/* 147 */         page = this.dao.getPageByWebsite(webId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial, 
/* 148 */           null, null, null, null, null, null, null, pageNo, pageSize, true);
/*     */       }
/*     */     }
/* 151 */     return page;
/*     */   }
/*     */ 
/*     */   public Pagination getPageByStockWarning(Long webId, Integer count, Boolean status, int pageNo, int pageSize)
/*     */   {
/* 156 */     return this.dao.getPageByStockWarning(webId, count, status, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPageForTagChannel(String brandId, Long webId, Integer ctgId, Long tagId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int pageSize)
/*     */   {
/*     */     Pagination page;
/* 164 */     if (tagId != null) {
/* 165 */       page = this.dao.getPageByTag(tagId, ctgId, isRecommend, isSpecial, pageNo, pageSize, true);
/*     */     }
/*     */     else
/*     */     {
/* 167 */       if (ctgId != null)
/* 168 */         page = this.dao.getPageByCategoryChannel(brandId, ctgId, isRecommend, names, values, isSpecial, orderBy, startPrice, endPrice, pageNo, pageSize, true);
/*     */       else {
/* 170 */         page = this.dao.getPageByWebsite(webId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial, null, null, null, null, null, null, null, pageNo, pageSize, true);
/*     */       }
/*     */     }
/* 173 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Product> getListForTag(Long webId, Integer ctgId, Long tagId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, int firstResult, int maxResults)
/*     */   {
/*     */     List list;
/* 179 */     if (tagId != null) {
/* 180 */       list = this.dao.getListByTag(tagId, ctgId, isRecommend, isSpecial, firstResult, maxResults, true);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       if (ctgId != null)
/* 183 */         list = this.dao.getListByCategory(ctgId, isRecommend, isSpecial, firstResult, maxResults, true);
/*     */       else {
/* 185 */         list = this.dao.getListByWebsite1(webId, isRecommend, isSpecial, isHostSale, isNewProduct, firstResult, maxResults, true);
/*     */       }
/*     */     }
/* 188 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Product> getIsRecommend(Boolean isRecommend, int count)
/*     */   {
/* 193 */     return this.dao.getIsRecommend(isRecommend, count);
/*     */   }
/*     */ 
/*     */   public Integer[] getProductByTag(Long webId)
/*     */   {
/* 198 */     return this.dao.getProductByTag(webId);
/*     */   }
/*     */ 
/*     */   public int deleteTagAssociation(ProductTag[] tags)
/*     */   {
/* 203 */     if (ArrayUtils.isEmpty(tags)) {
/* 204 */       return 0;
/*     */     }
/* 206 */     return this.dao.deleteTagAssociation(tags);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Product findById(Long id) {
/* 212 */     Product entity = this.dao.findById(id);
/* 213 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product save(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, Long[] tagIds, String[] keywords, String[] names, String[] values, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, MultipartFile file)
/*     */   {
/* 220 */     ProductText text = bean.getProductText();
/* 221 */     if (text != null) {
/* 222 */       text.setProduct(bean);
/*     */     }
/* 224 */     Category category = this.categoryMng.findById(categoryId);
/* 225 */     if (brandId != null) {
/* 226 */       bean.setBrand(this.brandMng.findById(brandId));
/*     */     }
/* 228 */     Website web = this.websiteMng.findById(webId);
/* 229 */     bean.setWebsite(web);
/* 230 */     bean.setConfig(this.shopConfigMng.findById(webId));
/* 231 */     bean.setCategory(category);
/* 232 */     bean.setType(category.getType());
/* 233 */     if (!ArrayUtils.isEmpty(tagIds)) {
/* 234 */       for (Long tagId : tagIds) {
/* 235 */         bean.addToTags(this.productTagMng.findById(tagId));
/*     */       }
/*     */     }
/* 238 */     if (!ArrayUtils.isEmpty(keywords)) {
/* 239 */       bean.setKeywords(Arrays.asList(keywords));
/*     */     }
/* 241 */     bean.init();
/* 242 */     this.dao.save(bean);
/*     */ 
/* 244 */     if ((names != null) && (names.length > 0)) {
/* 245 */       int i = 0; for (int len = names.length; i < len; i++) {
/* 246 */         if (!StringUtils.isBlank(names[i])) {
/* 247 */           bean.addToExendeds(names[i], values[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 252 */     if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
/* 253 */       int i = 0; for (int len = fashionSwitchPic.length; i < len; i++) {
/* 254 */         if (!StringUtils.isBlank(fashionSwitchPic[i])) {
/* 255 */           bean.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
/*     */         }
/*     */       }
/*     */     }
/* 259 */     String uploadPath = this.realPathResolver.get(web.getUploadRel());
/* 260 */     saveImage(bean, ext, category.getType(), file, uploadPath);
/* 261 */     this.productExtMng.save(ext, bean);
/* 262 */     return bean;
/*     */   }
/*     */ 
/*     */   public Product save1(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, String[] keywords, String[] names, String[] values, String[] fashionSwitchPic)
/*     */   {
/* 271 */     ProductText text = bean.getProductText();
/* 272 */     if (text != null) {
/* 273 */       text.setProduct(bean);
/*     */     }
/* 275 */     Category category = this.categoryMng.findById(categoryId);
/* 276 */     if (brandId != null) {
/* 277 */       bean.setBrand(this.brandMng.findById(brandId));
/*     */     }
/* 279 */     Website web = this.websiteMng.findById(webId);
/* 280 */     bean.setWebsite(web);
/* 281 */     bean.setConfig(this.shopConfigMng.findById(webId));
/* 282 */     bean.setCategory(category);
/* 283 */     bean.setType(category.getType());
/* 284 */     bean.init();
/*     */ 
/* 286 */     if (!ArrayUtils.isEmpty(keywords)) {
/* 287 */       bean.setKeywords(Arrays.asList(keywords));
/*     */     }
/*     */ 
/* 290 */     this.dao.save(bean);
/*     */ 
/* 292 */     if ((names != null) && (names.length > 0)) {
/* 293 */       int i = 0; for (int len = names.length; i < len; i++) {
/* 294 */         if (!StringUtils.isBlank(names[i])) {
/* 295 */           bean.addToExendeds(names[i], values[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 300 */     if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
/* 301 */       int i = 0; for (int len = fashionSwitchPic.length; i < len; i++) {
/* 302 */         if (!StringUtils.isBlank(fashionSwitchPic[i])) {
/* 303 */           bean.addToPictures(fashionSwitchPic[i], fashionSwitchPic[i], fashionSwitchPic[i]);
/* 304 */           if (i == 0) {
/* 305 */             ext.setCoverImg(fashionSwitchPic[0]);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 312 */     this.productExtMng.save(ext, bean);
/* 313 */     return bean;
/*     */   }
/*     */ 
/*     */   public Product updateByUpdater(Product bean)
/*     */   {
/* 318 */     Updater updater = new Updater(bean);
/* 319 */     Product entity = this.dao.updateByUpdater(updater);
/* 320 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product update(Product bean, ProductExt ext, Integer ctgId, Long brandId, Long[] tagIds, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, MultipartFile file)
/*     */   {
/* 329 */     Product entity = findById(bean.getId());
/* 330 */     ProductText text = bean.getProductText();
/*     */ 
/* 332 */     if (text != null) {
/* 333 */       ProductText ptext = entity.getProductText();
/* 334 */       if (ptext != null) {
/* 335 */         ptext.setText(text.getText());
/* 336 */         ptext.setText1(text.getText1());
/* 337 */         ptext.setText2(text.getText2());
/*     */       } else {
/* 339 */         text.setId(bean.getId());
/* 340 */         text.setProduct(entity);
/* 341 */         entity.setProductText(text);
/* 342 */         this.productTextMng.save(text);
/*     */       }
/*     */     }
/*     */ 
/* 346 */     entity.removeAllTags();
/*     */ 
/* 348 */     Category category = this.categoryMng.findById(ctgId);
/* 349 */     entity.setCategory(category);
/* 350 */     entity.setType(category.getType());
/* 351 */     if (entity.getStatus() == null) {
/* 352 */       entity.setStatus(Integer.valueOf(Product.DEL_STATUS));
/*     */     }
/*     */ 
/* 355 */     if (brandId != null)
/* 356 */       entity.setBrand(this.brandMng.findById(brandId));
/*     */     else {
/* 358 */       entity.setBrand(null);
/*     */     }
/*     */ 
/* 361 */     if (!ArrayUtils.isEmpty(tagIds)) {
/* 362 */       for (Long tagId : tagIds) {
/* 363 */         entity.addToTags(this.productTagMng.findById(tagId));
/*     */       }
/*     */     }
/*     */ 
/* 367 */     if (keywords != null) {
/* 368 */       List kw = entity.getKeywords();
/* 369 */       kw.clear();
/* 370 */       kw.addAll(Arrays.asList(keywords));
/*     */     } else {
/* 372 */       entity.getKeywords().clear();
/*     */     }
/*     */ 
/* 376 */     Updater updater = new Updater(bean);
/* 377 */     updater.exclude(BaseProduct.PROP_WEBSITE);
/* 378 */     updater.exclude(BaseProduct.PROP_PRODUCT_TEXT);
/* 379 */     entity = this.dao.updateByUpdater(updater);
/*     */ 
/* 381 */     if (attr != null) {
/* 382 */       Map attrOrig = entity.getAttr();
/* 383 */       attrOrig.clear();
/* 384 */       attrOrig.putAll(attr);
/*     */     }
/*     */ 
/* 387 */     entity.getExendeds().clear();
/* 388 */     if ((names != null) && (names.length > 0)) {
/* 389 */       int i = 0; for (int len = names.length; i < len; i++) {
/* 390 */         if (!StringUtils.isBlank(names[i])) {
/* 391 */           entity.addToExendeds(names[i], values[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 396 */     entity.getPictures().clear();
/* 397 */     if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
/* 398 */       int i = 0; for (int len = fashionSwitchPic.length; i < len; i++) {
/* 399 */         if (!StringUtils.isBlank(fashionSwitchPic[i])) {
/* 400 */           entity.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
/*     */         }
/*     */       }
/*     */     }
/* 404 */     String uploadPath = this.realPathResolver.get(entity.getWebsite().getUploadRel());
/* 405 */     saveImage(entity, ext, category.getType(), file, uploadPath);
/* 406 */     this.productExtMng.saveOrUpdate(ext, entity);
/* 407 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product update1(Product bean, ProductExt ext, Integer ctgId, Long brandId, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic)
/*     */   {
/* 419 */     Product entity = findById(bean.getId());
/* 420 */     ProductText text = bean.getProductText();
/*     */ 
/* 422 */     if (text != null) {
/* 423 */       ProductText ptext = entity.getProductText();
/* 424 */       if (ptext != null) {
/* 425 */         ptext.setText(text.getText());
/* 426 */         ptext.setText1(text.getText1());
/* 427 */         ptext.setText2(text.getText2());
/*     */       } else {
/* 429 */         text.setId(bean.getId());
/* 430 */         text.setProduct(entity);
/* 431 */         entity.setProductText(text);
/* 432 */         this.productTextMng.save(text);
/*     */       }
/*     */     }
/*     */ 
/* 436 */     entity.removeAllTags();
/*     */ 
/* 438 */     Category category = this.categoryMng.findById(ctgId);
/* 439 */     entity.setCategory(category);
/* 440 */     entity.setType(category.getType());
/* 441 */     if (entity.getStatus() == null) {
/* 442 */       entity.setStatus(Integer.valueOf(0));
/*     */     }
/*     */ 
/* 445 */     if (brandId != null)
/* 446 */       entity.setBrand(this.brandMng.findById(brandId));
/*     */     else {
/* 448 */       entity.setBrand(null);
/*     */     }
/*     */ 
/* 451 */     if (!ArrayUtils.isEmpty(keywords)) {
/* 452 */       bean.setKeywords(Arrays.asList(keywords));
/*     */     }
/*     */ 
/* 456 */     Updater updater = new Updater(bean);
/* 457 */     updater.exclude(BaseProduct.PROP_WEBSITE);
/* 458 */     updater.exclude(BaseProduct.PROP_PRODUCT_TEXT);
/* 459 */     entity = this.dao.updateByUpdater(updater);
/*     */ 
/* 461 */     if (attr != null) {
/* 462 */       Map attrOrig = entity.getAttr();
/* 463 */       attrOrig.clear();
/* 464 */       attrOrig.putAll(attr);
/*     */     }
/*     */ 
/* 467 */     entity.getExendeds().clear();
/* 468 */     if ((names != null) && (names.length > 0)) {
/* 469 */       int i = 0; for (int len = names.length; i < len; i++) {
/* 470 */         if (!StringUtils.isBlank(names[i])) {
/* 471 */           entity.addToExendeds(names[i], values[i]);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 477 */     entity.getPictures().clear();
/* 478 */     if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
/* 479 */       int i = 0; for (int len = fashionSwitchPic.length; i < len; i++) {
/* 480 */         if (!StringUtils.isBlank(fashionSwitchPic[i])) {
/* 481 */           entity.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
/* 482 */           if (i == 0) {
/* 483 */             ext.setCoverImg(fashionSwitchPic[0]);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 490 */     this.productExtMng.saveOrUpdate(ext, entity);
/* 491 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product update(Product bean)
/*     */   {
/* 496 */     Updater updater = new Updater(bean);
/* 497 */     Product entity = this.dao.updateByUpdater(updater);
/* 498 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product[] deleteByUpIds(Long[] ids)
/*     */   {
/* 503 */     Product[] beans = new Product[ids.length];
/*     */ 
/* 505 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 506 */       Product product = this.dao.findById(ids[i]);
/* 507 */       product.setStatus(Integer.valueOf(Product.DEL_STATUS));
/* 508 */       update(product);
/* 509 */       beans[i] = product;
/*     */     }
/* 511 */     return beans;
/*     */   }
/*     */ 
/*     */   public Product[] deleteByIds(Long[] ids)
/*     */   {
/* 516 */     Product[] beans = new Product[ids.length];
/* 517 */     int i = 0;
/*     */     List<Collect> clist;
/*     */     List<Consult> consultList;
/* 517 */     for (int len = ids.length; i < len; i++) {
/* 518 */       this.cartItemMng.deleteByProductId(ids[i]);
/* 519 */       clist = this.collectMng.findByProductId(ids[i]);
/* 520 */       for (Collect collect : clist) {
/* 521 */         this.collectMng.deleteById(collect.getId());
/*     */       }
/* 523 */       consultList = this.consultMng.findByProductId(ids[i]);
/* 524 */       for (Consult consult : consultList) {
/* 525 */         this.consultMng.deleteById(consult.getId());
/*     */       }
/* 527 */       List<ProductStandard> psList = this.productStandardMng.findByProductId(ids[i]);
/* 528 */       for (ProductStandard ps : psList) {
/* 529 */         this.productStandardMng.deleteById(ps.getId());
/*     */       }
/*     */ 
/* 532 */       if (this.relatedgoodsMng.findByIdProductId(ids[i]) != null) {
/* 533 */         this.relatedgoodsMng.deleteProduct(ids[i]);
/*     */       }
/* 535 */       Product product = findById(ids[i]);
/* 536 */       product.getTags().clear();
/* 537 */       product.getFashions().clear();
/* 538 */       product.getKeywords().clear();
/* 539 */       product.getPopularityGroups().clear();
/* 540 */       beans[i] = this.dao.deleteById(ids[i]);
/*     */     }
/* 542 */     for (Product p : beans) {
/* 543 */       p.removeAllTags();
/*     */     }
/* 545 */     return beans;
/*     */   }
/*     */ 
/*     */   private boolean saveImage(Product product, ProductExt bean, ProductType type, MultipartFile file, String uploadPath)
/*     */   {
/* 551 */     if ((file == null) || (file.isEmpty())) {
/* 552 */       return false;
/*     */     }
/*     */ 
/* 555 */     deleteImage(product, uploadPath);
/*     */ 
/* 557 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/*     */ 
/* 559 */     if (!ImageUtils.isImage(ext)) {
/* 560 */       return false;
/*     */     }
/*     */ 
/* 563 */     String dateDir = FileNameUtils.genPathName();
/*     */ 
/* 565 */     File root = new File(uploadPath, dateDir);
/*     */ 
/* 567 */     String relPath = "/" + dateDir + "/";
/* 568 */     if (!root.exists()) {
/* 569 */       root.mkdirs();
/*     */     }
/*     */ 
/* 572 */     String name = FileNameUtils.genFileName();
/*     */ 
/* 574 */     File tempFile = new File(root, name);
/*     */     try {
/* 576 */       file.transferTo(tempFile);
/*     */     } catch (Exception e) {
/* 578 */       throw new RuntimeException(e);
/*     */     }
/*     */     try
/*     */     {
/* 582 */       String detailName = name + Product.DETAIL_SUFFIX + "." + ext;
/* 583 */       File detailFile = new File(root, detailName);
/* 584 */       this.imageScale.resizeFix(tempFile, detailFile, 
/* 585 */         type.getDetailImgWidth().intValue(), type.getDetailImgHeight().intValue());
/* 586 */       bean.setDetailImg(relPath + detailName);
/*     */ 
/* 588 */       String listName = name + Product.LIST_SUFFIX + "." + ext;
/* 589 */       File listFile = new File(root, listName);
/* 590 */       this.imageScale.resizeFix(tempFile, listFile, type.getListImgWidth().intValue(), 
/* 591 */         type.getListImgHeight().intValue());
/* 592 */       bean.setListImg(relPath + listName);
/*     */ 
/* 594 */       String minName = name + Product.MIN_SUFFIX + "." + ext;
/* 595 */       File minFile = new File(root, minName);
/* 596 */       this.imageScale.resizeFix(tempFile, minFile, type.getMinImgWidth().intValue(), type
/* 597 */         .getMinImgHeight().intValue());
/* 598 */       bean.setMinImg(relPath + minName);
/*     */     } catch (Exception e) {
/* 600 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 603 */     tempFile.delete();
/* 604 */     return true;
/*     */   }
/*     */ 
/*     */   public void deleteImage(Product entity, String uploadPath) {
/* 608 */     String detail = entity.getDetailImg();
/* 609 */     if (!StringUtils.isBlank(detail)) {
/* 610 */       new File(uploadPath + detail).delete();
/*     */     }
/* 612 */     String list = entity.getListImg();
/* 613 */     if (!StringUtils.isBlank(list)) {
/* 614 */       new File(uploadPath + list).delete();
/*     */     }
/* 616 */     String min = entity.getMinImg();
/* 617 */     if (!StringUtils.isBlank(min))
/* 618 */       new File(uploadPath + min).delete();
/*     */   }
/*     */ 
/*     */   public Integer getStoreByProductPattern(Long id, Long fashId)
/*     */   {
/* 624 */     ProductFashion bean = this.fashDao.getPfashion(id, fashId);
/* 625 */     return bean.getStockCount();
/*     */   }
/*     */ 
/*     */   public List<Product> getHistoryProduct(HashSet<Long> set, Integer count)
/*     */   {
/* 630 */     return this.dao.getHistoryProduct(set, count);
/*     */   }
/*     */ 
/*     */   public Integer getTotalStore(Long productId)
/*     */   {
/* 635 */     Product bean = this.dao.findById(productId);
/* 636 */     Set<ProductFashion> set = bean.getFashions();
/* 637 */     Integer store = Integer.valueOf(0);
/* 638 */     if (set != null) {
/* 639 */       for (ProductFashion f : set) {
/* 640 */         store = Integer.valueOf(store.intValue() + f.getStockCount().intValue());
/*     */       }
/*     */     }
/*     */ 
/* 644 */     return store;
/*     */   }
/*     */ 
/*     */   public String getTipFile(String key)
/*     */   {
/* 649 */     String TipFile = "/WEB-INF/languages/jspgou_admin/messages_zh_CN.properties";
/* 650 */     String TipFileText = null;
/*     */     try
/*     */     {
/* 653 */       InputStream in = new FileInputStream(this.realPathResolver.get(TipFile));
/* 654 */       Properties p = new Properties();
/* 655 */       p.load(in);
/* 656 */       TipFileText = p.getProperty(key);
/*     */     } catch (FileNotFoundException e) {
/* 658 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 660 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception e) {
/* 663 */       e.printStackTrace();
/*     */     }
/* 665 */     return TipFileText;
/*     */   }
/*     */ 
/*     */   public Product saveByApi(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, String[] keywords, String[] names, String[] values, String[] fashionSwitchPics, Long[] pictures, String[] colorImgs, Long[] characters, String[] natures, Boolean[] isDefault, Integer[] stockCount, Double[] salePrice, Double[] marketPrice, Double[] costPrice)
/*     */     throws Exception
/*     */   {
/* 673 */     bean = save1(bean, ext, webId, categoryId, brandId, keywords, names, values, fashionSwitchPics);
/*     */ 
/* 675 */     if (pictures != null) {
/* 676 */       for (int i = 0; i < pictures.length; i++) {
/* 677 */         ProductStandard ps = new ProductStandard();
/* 678 */         ps.setImgPath(colorImgs[i]);
/* 679 */         ps.setType(this.standardMng.findById(pictures[i]).getType());
/* 680 */         ps.setProduct(bean);
/* 681 */         ps.setStandard(this.standardMng.findById(pictures[i]));
/* 682 */         ps.setDataType(true);
/* 683 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/*     */ 
/* 687 */     if (characters != null) {
/* 688 */       for (int i = 0; i < characters.length; i++) {
/* 689 */         if ((pictures != null) && (pictures.length > 0) && (Arrays.asList(pictures).contains(characters[i]))) {
/*     */           continue;
/*     */         }
/* 692 */         ProductStandard ps = new ProductStandard();
/* 693 */         ps.setType(this.standardMng.findById(characters[i])
/* 694 */           .getType());
/* 695 */         ps.setProduct(bean);
/* 696 */         ps.setStandard(this.standardMng.findById(characters[i]));
/* 697 */         ps.setDataType(false);
/* 698 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 701 */     saveProductFash(bean, natures, isDefault, stockCount, 
/* 702 */       salePrice, marketPrice, costPrice);
/* 703 */     return bean;
/*     */   }
/*     */ 
/*     */   public Product updateByApi(Product bean, ProductExt ext, Long brandId, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Integer categoryId, String[] fashionSwitchPics, Long[] pictures, String[] colorImgs, Long[] characters, Long[] fashionIds, String[] natures, Boolean[] isDefault, Integer[] stockCount, Double[] salePrice, Double[] marketPrice, Double[] costPrice)
/*     */     throws Exception
/*     */   {
/* 713 */     bean = update1(bean, ext, categoryId, brandId, keywords, names, values, attr, fashionSwitchPics, 
/* 714 */       fashionSwitchPics, fashionSwitchPics);
/* 715 */     List pcList = this.productStandardMng
/* 716 */       .findByProductId(bean.getId());
/* 717 */     for (int j = 0; j < pcList.size(); j++) {
/* 718 */       this.productStandardMng.deleteById(((ProductStandard)pcList.get(j)).getId());
/*     */     }
/* 720 */     if (pictures != null) {
/* 721 */       for (int i = 0; i < pictures.length; i++) {
/* 722 */         ProductStandard ps = new ProductStandard();
/* 723 */         ps.setImgPath(colorImgs[i]);
/* 724 */         ps.setType(this.standardMng.findById(pictures[i]).getType());
/* 725 */         ps.setProduct(bean);
/* 726 */         ps.setStandard(this.standardMng.findById(pictures[i]));
/* 727 */         ps.setDataType(true);
/* 728 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 731 */     if (characters != null) {
/* 732 */       for (int i = 0; i < characters.length; i++) {
/* 733 */         if ((pictures != null) && (pictures.length > 0) && (Arrays.asList(pictures).contains(characters[i]))) {
/*     */           continue;
/*     */         }
/* 736 */         ProductStandard ps = new ProductStandard();
/* 737 */         ps.setType(this.standardMng.findById(characters[i])
/* 738 */           .getType());
/* 739 */         ps.setProduct(bean);
/* 740 */         ps.setStandard(this.standardMng.findById(characters[i]));
/* 741 */         ps.setDataType(false);
/* 742 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 745 */     if (bean.getCategory().getColorsize().booleanValue()) {
/* 746 */       Set<ProductFashion> pfList = bean
/* 747 */         .getFashions();
/*     */ 
/* 749 */       if (fashionIds != null)
/* 750 */         for (ProductFashion ps : pfList)
/*     */         {
/* 752 */           if (!Arrays.asList(fashionIds).contains(
/* 752 */             ps.getId())) {
/* 753 */             this.fashMng.deleteById(ps.getId());
/* 754 */             this.cartItemMng.deleteByProductFactionId(ps
/* 755 */               .getId());
/*     */           }
/*     */         }
/*     */       else {
/* 759 */         for (ProductFashion ps : pfList) {
/* 760 */           this.fashMng.deleteById(ps.getId());
/* 761 */           this.cartItemMng.deleteByProductFactionId(ps.getId());
/*     */         }
/*     */       }
/* 764 */       updateProductFash(bean, fashionIds, natures, isDefault, 
/* 765 */         stockCount, salePrice, marketPrice, costPrice);
/*     */     }
/* 767 */     return bean;
/*     */   }
/*     */ 
/*     */   private void saveProductFash(Product bean, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices)
/*     */   {
/* 775 */     if (nature != null)
/* 776 */       for (int i = 0; i < nature.length; i++)
/*     */       {
/* 778 */         ProductFashion pfash = new ProductFashion();
/* 779 */         pfash.setCreateTime(new Date());
/* 780 */         pfash.setIsDefault(isDefaults[i]);
/* 781 */         pfash.setStockCount(stockCounts[i]);
/* 782 */         pfash.setMarketPrice(marketPrices[i]);
/* 783 */         pfash.setSalePrice(salePrices[i]);
/* 784 */         pfash.setCostPrice(costPrices[i]);
/* 785 */         pfash.setProductId(bean);
/* 786 */         pfash.setNature(nature[i]);
/* 787 */         String[] arr = nature[i].split("_");
/* 788 */         ProductFashion fashion = this.productFashionMng.save(pfash, arr);
/* 789 */         this.productFashionMng.addStandard(fashion, arr);
/* 790 */         if (isDefaults[i].booleanValue()) {
/* 791 */           bean.setCostPrice(costPrices[i]);
/* 792 */           bean.setMarketPrice(marketPrices[i]);
/* 793 */           bean.setSalePrice(salePrices[i]);
/* 794 */           update(bean);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private void updateProductFash(Product bean, Long[] fashId, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices)
/*     */   {
/* 803 */     if (nature != null)
/* 804 */       for (int i = 0; i < nature.length; i++)
/*     */       {
/* 807 */         if ((fashId != null) && (i < fashId.length) && (fashId[i].longValue() > 0L)) {
/* 808 */           ProductFashion pfash = this.productFashionMng.findById(fashId[i]);
/* 809 */           pfash.setCreateTime(new Date());
/* 810 */           pfash.setIsDefault(isDefaults[i]);
/* 811 */           pfash.setStockCount(stockCounts[i]);
/* 812 */           pfash.setMarketPrice(marketPrices[i]);
/* 813 */           pfash.setSalePrice(salePrices[i]);
/* 814 */           pfash.setCostPrice(costPrices[i]);
/* 815 */           pfash.setProductId(bean);
/* 816 */           pfash.setNature(nature[i]);
/* 817 */           String[] arr = nature[i].split("_");
/* 818 */           this.productFashionMng.updateStandard(pfash, arr);
/*     */ 
/* 821 */           if (isDefaults[i].booleanValue()) {
/* 822 */             bean.setCostPrice(costPrices[i]);
/* 823 */             bean.setMarketPrice(marketPrices[i]);
/* 824 */             bean.setSalePrice(salePrices[i]);
/* 825 */             update(bean);
/*     */           }
/*     */         } else {
/* 828 */           ProductFashion pfash = new ProductFashion();
/* 829 */           pfash.setCreateTime(new Date());
/* 830 */           pfash.setIsDefault(isDefaults[i]);
/* 831 */           pfash.setStockCount(stockCounts[i]);
/* 832 */           pfash.setMarketPrice(marketPrices[i]);
/* 833 */           pfash.setSalePrice(salePrices[i]);
/* 834 */           pfash.setCostPrice(costPrices[i]);
/* 835 */           pfash.setProductId(bean);
/* 836 */           pfash.setNature(nature[i]);
/* 837 */           String[] arr = nature[i].split("_");
/* 838 */           ProductFashion fashion = this.productFashionMng.save(pfash, arr);
/* 839 */           this.productFashionMng.addStandard(fashion, arr);
/* 840 */           if (isDefaults[i].booleanValue()) {
/* 841 */             bean.setCostPrice(costPrices[i]);
/* 842 */             bean.setMarketPrice(marketPrices[i]);
/* 843 */             bean.setSalePrice(salePrices[i]);
/* 844 */             update(bean);
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public Long getProductCount(Long webId)
/*     */   {
/* 855 */     return this.dao.getProductCount(webId);
/*     */   }
/*     */ 
/*     */   public Long getOverStock(Long webId)
/*     */   {
/* 860 */     return this.dao.getOverStock(webId);
/*     */   }
/*     */ 
/*     */   public Pagination getPageOverStockList(Long webId, int pageNo, int pageSize)
/*     */   {
/* 865 */     return this.dao.getPageOverStockList(webId, pageNo, pageSize);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductMngImpl
 * JD-Core Version:    0.6.0
 */