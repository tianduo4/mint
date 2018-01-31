/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductDao;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductTag;
/*     */ import com.jspgou.cms.lucene.LuceneProduct;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.index.CorruptIndexException;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.hibernate.CacheMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.ScrollMode;
/*     */ import org.hibernate.ScrollableResults;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ProductDaoImpl extends HibernateBaseDao<Product, Long>
/*     */   implements ProductDao
/*     */ {
/*     */   public Pagination getPageByCategory(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  41 */     Finder f = getFinderForCategory(ctgId, productName, brandName, status, 
/*  42 */       isRecommend, isSpecial, isHotsale, isNewProduct, typeId, startSalePrice, endSalePrice, 
/*  43 */       startStock, endStock, cacheable);
/*  44 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByCategoryChannel(String brandId, Integer ctgId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  51 */     Finder f = getFinderForCategoryChannel(brandId, ctgId, isRecommend, 
/*  52 */       names, values, isSpecial, orderBy, startPrice, endPrice, cacheable);
/*  53 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByStockWarning(Long webId, Integer count, Boolean status, int pageNo, int pageSize)
/*     */   {
/*  59 */     Finder f = Finder.create("select bean from Product bean");
/*  60 */     f.append(" where bean.website.id=:webId").setParam("webId", webId);
/*  61 */     f.append(" and bean.stockCount <=:count").setParam("count", count);
/*  62 */     f.append(" and bean.lackRemind=:status").setParam("status", status);
/*  63 */     f.append(" order by bean.stockCount asc");
/*  64 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Product> getHistoryProduct(HashSet<Long> set, Integer count)
/*     */   {
/*  70 */     if (set.size() > 0) {
/*  71 */       return getSession().createQuery("from Product bean where bean.id in (:ids)")
/*  72 */         .setParameterList("ids", set).setMaxResults(count.intValue()).list();
/*     */     }
/*  74 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public Pagination getPageByWebsite(Long webId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  84 */     Finder f = getFinderForWebsite(webId, productName, brandName, status, 
/*  85 */       isRecommend, isSpecial, isHotsale, isNewProduct, typeId, startSalePrice, endSalePrice, 
/*  86 */       startStock, endStock, cacheable);
/*  87 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  93 */     Finder f = getFinderForTag(tagId, ctgId, isRecommend, isSpecial, 
/*  94 */       cacheable);
/*  95 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Product> getListByCategory(Integer ctgId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/* 103 */     Finder f = getFinderForCategory(ctgId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial, 
/* 104 */       null, null, null, null, null, null, null, cacheable);
/* 105 */     f.setFirstResult(firstResult);
/* 106 */     f.setMaxResults(maxResults);
/* 107 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Product> getListByWebsite(Long webId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/* 115 */     Finder f = getFinderForWebsite(webId, null, null, null, isRecommend, isSpecial, 
/* 116 */       null, null, null, null, null, null, null, cacheable);
/* 117 */     f.setFirstResult(firstResult);
/* 118 */     f.setMaxResults(maxResults);
/* 119 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Product> getListByWebsite1(Long webId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/* 127 */     Finder f = getFinderForWebsite1(webId, isRecommend, isSpecial, isHostSale, isNewProduct, cacheable);
/* 128 */     f.setFirstResult(firstResult);
/* 129 */     f.setMaxResults(maxResults);
/* 130 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Product> getListByTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/* 138 */     Finder f = getFinderForTag(tagId, ctgId, isRecommend, isSpecial, cacheable);
/* 139 */     f.setFirstResult(firstResult);
/* 140 */     f.setMaxResults(maxResults);
/* 141 */     return find(f);
/*     */   }
/*     */ 
/*     */   private Finder getFinderForCategory(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, boolean cacheable)
/*     */   {
/* 149 */     Finder f = Finder.create("select bean from Product bean");
/* 150 */     f.append(" inner join bean.category node,Category parent");
/* 151 */     f.append(" where node.lft between parent.lft and parent.rgt");
/* 152 */     f.append(" and parent.id=:ctgId");
/* 153 */     f.append(" and ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
/* 154 */     if (!StringUtils.isBlank(productName)) {
/* 155 */       f.append(" and bean.name like:productName");
/* 156 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 158 */     if (!StringUtils.isBlank(brandName)) {
/* 159 */       f.append(" and bean.brand.name like:brandName");
/* 160 */       f.setParam("brandName", "%" + brandName + "%");
/*     */     }
/* 162 */     if (status != null) {
/* 163 */       f.append(" and bean.status=:status");
/* 164 */       f.setParam("status", status);
/*     */     }
/* 166 */     if (isRecommend != null) {
/* 167 */       f.append(" and bean.recommend=:isRecommend");
/* 168 */       f.setParam("isRecommend", isRecommend);
/*     */     }
/* 170 */     if (isSpecial != null) {
/* 171 */       f.append(" and bean.special=:isSpecial");
/* 172 */       f.setParam("isSpecial", isSpecial);
/*     */     }
/* 174 */     if (isHotsale != null) {
/* 175 */       f.append(" and bean.hotsale=:isHotsale");
/* 176 */       f.setParam("isHotsale", isHotsale);
/*     */     }
/* 178 */     if (isNewProduct != null) {
/* 179 */       f.append(" and bean.newProduct=:isNewProduct");
/* 180 */       f.setParam("isNewProduct", isNewProduct);
/*     */     }
/* 182 */     if (typeId != null) {
/* 183 */       f.append(" and bean.type.id=:typeId");
/* 184 */       f.setParam("typeId", typeId);
/*     */     }
/* 186 */     if (startSalePrice != null) {
/* 187 */       f.append(" and bean.salePrice>:startSalePrice");
/* 188 */       f.setParam("startSalePrice", startSalePrice);
/*     */     }
/* 190 */     if (endSalePrice != null) {
/* 191 */       f.append(" and bean.salePrice<:endSalePrice");
/* 192 */       f.setParam("endSalePrice", endSalePrice);
/*     */     }
/* 194 */     if (startStock != null) {
/* 195 */       f.append(" and bean.stockCount>:startStock");
/* 196 */       f.setParam("startStock", startStock);
/*     */     }
/* 198 */     if (endStock != null) {
/* 199 */       f.append(" and bean.stockCount<:endStock");
/* 200 */       f.setParam("endStock", endStock);
/*     */     }
/* 202 */     f.append(" order by bean.id desc");
/* 203 */     f.setParam("ctgId", ctgId);
/* 204 */     f.setCacheable(cacheable);
/* 205 */     return f;
/*     */   }
/*     */ 
/*     */   public List<Product> getList(Long typeId, Long brandId, String productName, boolean cacheable)
/*     */   {
/* 211 */     Finder f = Finder.create("from Product bean where 1=1");
/* 212 */     if (typeId != null) {
/* 213 */       f.append(" and bean.type.id=:typeId");
/* 214 */       f.setParam("typeId", typeId);
/*     */     }
/* 216 */     if (brandId != null) {
/* 217 */       f.append(" and bean.brand.id=:brandId");
/* 218 */       f.setParam("brandId", brandId);
/*     */     }
/* 220 */     if (!StringUtils.isBlank(productName)) {
/* 221 */       f.append(" and bean.name like:productName");
/* 222 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 224 */     f.setCacheable(cacheable);
/* 225 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(int orderBy, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/* 230 */     Finder f = Finder.create("from Product bean where 1=1");
/* 231 */     appendOrder(f, orderBy);
/* 232 */     f.setCacheable(cacheable);
/* 233 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage1(Long typeId, int orderBy, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/* 238 */     Finder f = Finder.create("from Product bean where 1=1");
/* 239 */     if (typeId != null) {
/* 240 */       f.append(" and bean.category.id=:typeId");
/* 241 */       f.setParam("typeId", typeId);
/*     */     }
/* 243 */     appendOrder(f, orderBy);
/* 244 */     f.setCacheable(cacheable);
/* 245 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Product> findAll()
/*     */   {
/* 251 */     Finder f = Finder.create("from Product bean ");
/* 252 */     return find(f);
/*     */   }
/*     */ 
/*     */   private Finder getFinderForCategoryChannel(String brandId, Integer ctgId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, boolean cacheable)
/*     */   {
/* 257 */     Finder f = Finder.create("select distinct bean from Product bean");
/* 258 */     f.append(" join bean.category node,Category parent");
/* 259 */     String[] exendeds = null;
/* 260 */     if (names != null) {
/* 261 */       exendeds = new String[names.length];
/* 262 */       int i = 0; for (int j = names.length; i < j; i++) {
/* 263 */         String exended = "exended" + i;
/* 264 */         exendeds[i] = exended;
/* 265 */         f.append(" inner join bean.exendeds " + exended);
/*     */       }
/*     */     }
/* 268 */     f.append(" where node.lft between parent.lft and parent.rgt and bean.status=" + Product.ON_SALE_STATUS);
/* 269 */     f.append(" and parent.id=:ctgId");
/* 270 */     f.setParam("ctgId", ctgId);
/* 271 */     if (isRecommend != null) {
/* 272 */       f.append(" and node.recommend=:recommend");
/* 273 */       f.setParam("recommend", isRecommend);
/*     */     }
/* 275 */     if (isSpecial != null) {
/* 276 */       f.append(" and node.special=:special");
/* 277 */       f.setParam("special", isSpecial);
/*     */     }
/*     */ 
/* 284 */     if ((brandId != null) && (!brandId.equals(""))) {
/* 285 */       f.append(" and bean.brand.id in(" + brandId + ")");
/*     */     }
/* 287 */     if (names != null) {
/* 288 */       for (int i = 0; i < names.length; i++) {
/* 289 */         String an = "attr_name" + i;
/* 290 */         String av = "attr_value" + i;
/* 291 */         if (!values[i].equals("0")) {
/* 292 */           f.append(" and " + exendeds[i] + ".name=:" + an).setParam(an, names[i]);
/* 293 */           f.append(" and " + exendeds[i] + ".value=:" + av).setParam(av, values[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 298 */     if (startPrice.doubleValue() > 0.0D) {
/* 299 */       f.append(" and bean.salePrice>=:startPrice");
/* 300 */       f.setParam("startPrice", startPrice);
/*     */     }
/* 302 */     if (endPrice.doubleValue() > 0.0D) {
/* 303 */       f.append(" and bean.salePrice<=:endPrice");
/* 304 */       f.setParam("endPrice", endPrice);
/*     */     }
/* 306 */     appendOrder(f, orderBy);
/* 307 */     f.setCacheable(cacheable);
/* 308 */     return f;
/*     */   }
/*     */ 
/*     */   private void appendOrder(Finder f, int orderBy)
/*     */   {
/* 313 */     switch (orderBy)
/*     */     {
/*     */     case 1:
/* 316 */       f.append(" order by bean.id asc");
/* 317 */       break;
/*     */     case 2:
/* 320 */       f.append(" order by bean.createTime desc");
/* 321 */       break;
/*     */     case 3:
/* 324 */       f.append(" order by bean.createTime asc");
/* 325 */       break;
/*     */     case 4:
/* 328 */       f.append(" order by bean.saleCount desc, bean.createTime desc");
/* 329 */       break;
/*     */     case 5:
/* 332 */       f.append(" order by bean.saleCount desc, bean.createTime asc");
/* 333 */       break;
/*     */     case 6:
/* 336 */       f.append(" order by bean.salePrice desc, bean.id desc");
/* 337 */       break;
/*     */     case 7:
/* 340 */       f.append(" order by bean.salePrice asc,bean.id desc");
/* 341 */       break;
/*     */     case 8:
/* 344 */       f.append(" order by bean.liRun desc");
/* 345 */       break;
/*     */     case 9:
/* 348 */       f.append(" order by bean.viewCount desc");
/* 349 */       break;
/*     */     default:
/* 352 */       f.append(" order by bean.id desc");
/*     */     }
/*     */   }
/*     */ 
/*     */   private Finder getFinderForWebsite(Long webId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, boolean cacheable)
/*     */   {
/* 361 */     Finder f = Finder.create("select bean from Product bean");
/* 362 */     f.append(" where bean.website.id=:webId and ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
/* 363 */     if (!StringUtils.isBlank(productName)) {
/* 364 */       f.append(" and bean.name like:productName");
/* 365 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 367 */     if (!StringUtils.isBlank(brandName)) {
/* 368 */       f.append(" and bean.brand.name like:brandName");
/* 369 */       f.setParam("brandName", "%" + brandName + "%");
/*     */     }
/* 371 */     if (status != null) {
/* 372 */       f.append(" and bean.status=:status");
/* 373 */       f.setParam("status", status);
/*     */     }
/* 375 */     if (isRecommend != null) {
/* 376 */       f.append(" and bean.recommend=:isRecommend");
/* 377 */       f.setParam("isRecommend", isRecommend);
/*     */     }
/* 379 */     if (isSpecial != null) {
/* 380 */       f.append(" and bean.special=:isSpecial");
/* 381 */       f.setParam("isSpecial", isSpecial);
/*     */     }
/* 383 */     if (isHotsale != null) {
/* 384 */       f.append(" and bean.hotsale=:isHotsale");
/* 385 */       f.setParam("isHotsale", isHotsale);
/*     */     }
/* 387 */     if (isNewProduct != null) {
/* 388 */       f.append(" and bean.newProduct=:isNewProduct");
/* 389 */       f.setParam("isNewProduct", isNewProduct);
/*     */     }
/* 391 */     if (typeId != null) {
/* 392 */       f.append(" and bean.type.id=:typeId");
/* 393 */       f.setParam("typeId", typeId);
/*     */     }
/* 395 */     if (startSalePrice != null) {
/* 396 */       f.append(" and bean.salePrice>:startSalePrice");
/* 397 */       f.setParam("startSalePrice", startSalePrice);
/*     */     }
/* 399 */     if (endSalePrice != null) {
/* 400 */       f.append(" and bean.salePrice<:endSalePrice");
/* 401 */       f.setParam("endSalePrice", endSalePrice);
/*     */     }
/* 403 */     if (startStock != null) {
/* 404 */       f.append(" and bean.stockCount>:startStock");
/* 405 */       f.setParam("startStock", startStock);
/*     */     }
/* 407 */     if (endStock != null) {
/* 408 */       f.append(" and bean.stockCount<:endStock");
/* 409 */       f.setParam("endStock", endStock);
/*     */     }
/* 411 */     f.append(" order by bean.id desc");
/* 412 */     f.setParam("webId", webId);
/* 413 */     f.setCacheable(cacheable);
/* 414 */     return f;
/*     */   }
/*     */ 
/*     */   public List<Product> getIsRecommend(Boolean isRecommend, int count)
/*     */   {
/* 420 */     Finder f = Finder.create("from Product bean where bean.recommend=:recommend").setParam("recommend", isRecommend);
/* 421 */     f.setMaxResults(count);
/* 422 */     return find(f);
/*     */   }
/*     */ 
/*     */   private Finder getFinderForWebsite1(Long webId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, boolean cacheable)
/*     */   {
/* 427 */     Finder f = Finder.create("from Product bean");
/* 428 */     f.append(" where bean.website.id=:webId and  ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
/* 429 */     if (isRecommend != null) {
/* 430 */       f.append(" and bean.recommend=:recommend");
/* 431 */       f.setParam("recommend", isRecommend);
/*     */     }
/* 433 */     if (isSpecial != null) {
/* 434 */       f.append(" and bean.special=:special");
/* 435 */       f.setParam("special", isSpecial);
/*     */     }
/* 437 */     if (isHostSale != null) {
/* 438 */       f.append(" and bean.hotsale=:hotsale");
/* 439 */       f.setParam("hotsale", isHostSale);
/*     */     }
/* 441 */     if (isNewProduct != null) {
/* 442 */       f.append(" and bean.newProduct=:newProduct");
/* 443 */       f.setParam("newProduct", isNewProduct);
/*     */     }
/* 445 */     f.append(" order by bean.id desc");
/* 446 */     f.setParam("webId", webId);
/* 447 */     f.setCacheable(cacheable);
/* 448 */     return f;
/*     */   }
/*     */ 
/*     */   private Finder getFinderForTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, boolean cacheable)
/*     */   {
/* 453 */     Finder f = Finder.create("select bean from Product bean");
/* 454 */     f.append(" inner join bean.tags tag with tag.id=:tagId");
/* 455 */     f.append(" where   ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
/* 456 */     f.setParam("tagId", tagId);
/* 457 */     if (ctgId != null) {
/* 458 */       f.append(" and bean.category.id=:ctgId");
/* 459 */       f.setParam("ctgId", ctgId);
/*     */     }
/* 461 */     if (isRecommend != null) {
/* 462 */       f.append(" and bean.recommend=:recommend");
/* 463 */       f.setParam("recommend", isRecommend);
/*     */     }
/* 465 */     if (isSpecial != null) {
/* 466 */       f.append(" and bean.special=:special");
/* 467 */       f.setParam("special", isSpecial);
/*     */     }
/* 469 */     f.append(" order by bean.id desc");
/* 470 */     f.setCacheable(cacheable);
/* 471 */     return f;
/*     */   }
/*     */ 
/*     */   public int luceneWriteIndex(IndexWriter writer, Long webId, Date start, Date end)
/*     */     throws CorruptIndexException, IOException
/*     */   {
/* 477 */     Session session = getSession();
/* 478 */     Finder f = Finder.create("from Product bean where 1=1 and bean.status=" + Product.ON_SALE_STATUS);
/* 479 */     if (webId != null) {
/* 480 */       f.append(" and bean.website.id=:webId");
/* 481 */       f.setParam("webId", webId);
/*     */     }
/* 483 */     if (start != null) {
/* 484 */       f.append(" and bean.createTime >= :start");
/* 485 */       f.setParam("start", start);
/*     */     }
/* 487 */     if (end != null) {
/* 488 */       f.append(" and bean.createTime >= :end");
/* 489 */       f.setParam("end", end);
/*     */     }
/*     */ 
/* 492 */     ScrollableResults products = f.createQuery(session).setCacheMode(
/* 493 */       CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
/*     */ 
/* 495 */     int count = 0;
/* 496 */     while (products.next()) {
/* 497 */       Product product = (Product)products.get(0);
/* 498 */       writer.addDocument(LuceneProduct.createDocument(product));
/* 499 */       count++; if (count % 20 == 0) {
/* 500 */         session.clear();
/*     */       }
/*     */     }
/* 503 */     return count;
/*     */   }
/*     */ 
/*     */   public int deleteTagAssociation(ProductTag[] tags)
/*     */   {
/* 508 */     Long[] tagIds = new Long[tags.length];
/* 509 */     int i = 0; for (int len = tags.length; i < len; i++) {
/* 510 */       tagIds[i] = tags[i].getId();
/*     */     }
/* 512 */     Session session = getSession();
/* 513 */     String hql = "from Product bean inner join bean.tags tag where tag.id in (:tagIds)";
/* 514 */     ScrollableResults products = session.createQuery(hql).setParameterList(
/* 515 */       "tagIds", tagIds).setCacheMode(CacheMode.IGNORE).scroll(
/* 516 */       ScrollMode.FORWARD_ONLY);
/* 517 */     int count = 0;
/* 518 */     while (products.next()) {
/* 519 */       Product product = (Product)products.get(0);
/* 520 */       product.getTags().removeAll(Arrays.asList(tags));
/* 521 */       count++; if (count % 20 == 0) {
/* 522 */         session.flush();
/* 523 */         session.clear();
/*     */       }
/*     */     }
/* 526 */     return count;
/*     */   }
/*     */ 
/*     */   public Integer[] getProductByTag(Long webId)
/*     */   {
/* 531 */     Long newProduct = (Long)getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId  and bean.newProduct=true")
/* 532 */       .setParameter("webId", webId).uniqueResult();
/* 533 */     Long hotProduct = (Long)getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId  and bean.hotsale=true")
/* 534 */       .setParameter("webId", webId).uniqueResult();
/* 535 */     Long speProduct = (Long)getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId   and bean.special=true")
/* 536 */       .setParameter("webId", webId).uniqueResult();
/* 537 */     Integer[] p = new Integer[3];
/* 538 */     p[0] = Integer.valueOf(newProduct.intValue());
/* 539 */     p[1] = Integer.valueOf(hotProduct.intValue());
/* 540 */     p[2] = Integer.valueOf(speProduct.intValue());
/* 541 */     return p;
/*     */   }
/*     */ 
/*     */   public Long getProductCount(Long webId)
/*     */   {
/* 548 */     Finder finder = Finder.create("select count(1) from Product bean where bean.status = " + Product.ON_SALE_STATUS);
/* 549 */     finder.append(" and bean.website.id=:webId").setParam("webId", webId);
/* 550 */     List list = find(finder);
/* 551 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public Long getOverStock(Long webId)
/*     */   {
/* 557 */     Finder finder = Finder.create("select count(1) from Product bean  where bean.stockCount < bean.alertInventory and bean.status = " + Product.ON_SALE_STATUS);
/* 558 */     finder.append(" and bean.website.id=:webId").setParam("webId", webId);
/* 559 */     List list = find(finder);
/* 560 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public Pagination getPageOverStockList(Long webId, int pageNo, int pageSize)
/*     */   {
/* 565 */     Finder finder = Finder.create(" from Product bean  where bean.stockCount < bean.alertInventory and bean.status = " + Product.ON_SALE_STATUS);
/* 566 */     finder.append("  and bean.website.id=:webId").setParam("webId", webId);
/* 567 */     finder.append(" order by bean.stockCount asc");
/* 568 */     return find(finder, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Product findById(Long id)
/*     */   {
/* 573 */     Product entity = (Product)get(id);
/* 574 */     return entity;
/*     */   }
/*     */ 
/*     */   public Product save(Product bean)
/*     */   {
/* 579 */     getSession().save(bean);
/* 580 */     return bean;
/*     */   }
/*     */ 
/*     */   public Product deleteById(Long id)
/*     */   {
/* 585 */     Product entity = (Product)super.get(id);
/* 586 */     if (entity != null) {
/* 587 */       getSession().delete(entity);
/*     */     }
/* 589 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<Product> getEntityClass()
/*     */   {
/* 594 */     return Product.class;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductDaoImpl
 * JD-Core Version:    0.6.0
 */