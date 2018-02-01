/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.BrandDao;
/*     */ import com.jspgou.cms.dao.CategoryDao;
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.entity.base.BaseCategory;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class CategoryMngImpl
/*     */   implements CategoryMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private BrandDao brandDao;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */   private ProductTypeMng productTypeMng;
/*     */   private CategoryDao dao;
/*     */ 
/*     */   public List<Category> getAll(Long webId)
/*     */   {
/*  36 */     return this.dao.getAll(webId);
/*     */   }
/*     */ 
/*     */   public Category getByPath(Long webId, String path) {
/*  40 */     return this.dao.getByPath(webId, path, false);
/*     */   }
/*     */ 
/*     */   public Category getByPathForTag(Long webId, String path)
/*     */   {
/*  45 */     return this.dao.getByPath(webId, path, true);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Category> getListForParent(Long webId, Integer ctgId)
/*     */   {
/*  54 */     List allList = getList(webId);
/*  55 */     if (ctgId != null) {
/*  56 */       List list = this.dao.getListForChild(webId, ctgId);
/*  57 */       allList.removeAll(list);
/*     */     }
/*  59 */     return allList;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Category> getListForProduct(Long webId, Integer ctgId)
/*     */   {
/*  68 */     List list = new ArrayList();
/*  69 */     Category category = findById(ctgId);
/*  70 */     addAllChildToList(list, Arrays.asList(new Category[] { category }), category.getType()
/*  71 */       .getId(), null);
/*  72 */     return list;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Category> getTopList(Long webId)
/*     */   {
/*  81 */     return this.dao.getTopList(webId, false);
/*     */   }
/*     */ 
/*     */   public List<Category> getChildList(Long wegId, Integer parentId)
/*     */   {
/*  86 */     return this.dao.getChildList(wegId, parentId);
/*     */   }
/*     */ 
/*     */   public List<Category> getTopListForTag(Long webId)
/*     */   {
/*  94 */     return this.dao.getTopList(webId, true);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Category> getList(Long webId)
/*     */   {
/* 103 */     List list = this.dao.getTopList(webId, false);
/* 104 */     List allList = new ArrayList();
/* 105 */     addAllChildToList(allList, list, null, Integer.valueOf(1));
/* 106 */     return allList;
/*     */   }
/*     */ 
/*     */   public boolean checkPath(Long webId, String path)
/*     */   {
/* 114 */     return this.dao.countPath(webId, path) <= 0;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Category findById(Integer id)
/*     */   {
/* 123 */     Category entity = this.dao.findById(id);
/* 124 */     return entity;
/*     */   }
/*     */ 
/*     */   public Category save(Category bean, Integer parentId, Long typeId, Long[] brandIds, Long[] standardTypeIds)
/*     */   {
/* 132 */     Category parent = null;
/* 133 */     if (parentId != null) {
/* 134 */       parent = findById(parentId);
/* 135 */       bean.setParent(parent);
/*     */     }
/* 137 */     if (typeId != null) {
/* 138 */       bean.setType(this.productTypeMng.findById(typeId));
/*     */     }
/* 140 */     Category entity = this.dao.save(bean);
/* 141 */     if ((brandIds != null) && (brandIds.length > 0)) {
/* 142 */       for (Long brandId : brandIds)
/* 143 */         entity.addToBrands(this.brandMng.findById(brandId));
/*     */     }
/*     */     else {
/* 146 */       entity.setBrands(new HashSet());
/*     */     }
/* 148 */     if (parent != null) {
/* 149 */       parent.addToChild(bean);
/*     */     }
/* 151 */     if ((standardTypeIds != null) && (standardTypeIds.length > 0)) {
/* 152 */       for (Long sid : standardTypeIds) {
/* 153 */         entity.addToStandardTypes(this.standardTypeMng.findById(sid));
/*     */       }
/*     */     }
/* 156 */     return bean;
/*     */   }
/*     */ 
/*     */   public List<Brand> getBrandByCate(Integer categoryId)
/*     */   {
/* 161 */     return this.brandDao.getListByCate(categoryId);
/*     */   }
/*     */ 
/*     */   public Category update(Category bean, Integer parentId, Long typeId, Long[] brandIds, Map<String, String> attr, Long[] standardTypeIds)
/*     */   {
/* 169 */     Assert.notNull(bean);
/* 170 */     Category entity = findById(bean.getId());
/* 171 */     Category origParent = entity.getParent();
/* 172 */     Category parent = null;
/* 173 */     if (parentId != null) {
/* 174 */       parent = findById(parentId);
/* 175 */       bean.setParent(parent);
/*     */     } else {
/* 177 */       bean.setParent(null);
/*     */     }
/* 179 */     Updater updater = new Updater(bean);
/* 180 */     updater.include(BaseCategory.PROP_PARENT);
/* 181 */     entity = this.dao.updateByUpdater(updater);
/* 182 */     if (origParent != null) {
/* 183 */       origParent.removeFromChild(entity);
/*     */     }
/* 185 */     if (parent != null) {
/* 186 */       parent.addToChild(entity);
/*     */     }
/*     */ 
/* 189 */     Set<Brand> brands = entity.getBrands();
/* 190 */     for (Brand brand : brands) {
/* 191 */       brand.removeFromCategorys(entity);
/*     */     }
/* 193 */     brands.clear();
/* 194 */     if ((brandIds != null) && (brandIds.length > 0)) {
/* 195 */       for (Long brandId : brandIds)
/* 196 */         entity.addToBrands(this.brandMng.findById(brandId));
/*     */     }
/*     */     else {
/* 199 */       entity.setBrands(new HashSet());
/*     */     }
/* 201 */     Set<StandardType> standardTypes = entity.getStandardType();
/* 202 */     for (StandardType t : standardTypes) {
/* 203 */       t.removeFromCategorys(entity);
/*     */     }
/* 205 */     standardTypes.clear();
/* 206 */     if ((standardTypeIds != null) && (standardTypeIds.length > 0)) {
/* 207 */       for (Long sid : standardTypeIds) {
/* 208 */         entity.addToStandardTypes(this.standardTypeMng.findById(sid));
/*     */       }
/*     */     }
/*     */ 
/* 212 */     if (attr != null) {
/* 213 */       Map attrOrig = entity.getAttr();
/* 214 */       attrOrig.clear();
/* 215 */       attrOrig.putAll(attr);
/*     */     }
/*     */ 
/* 218 */     return entity;
/*     */   }
/*     */ 
/*     */   public Category deleteById(Integer id)
/*     */   {
/* 226 */     Category parent = findById(id).getParent();
/* 227 */     Category bean = this.dao.deleteById(id);
/* 228 */     if (parent != null) {
/* 229 */       parent.removeFromChild(bean);
/*     */     }
/* 231 */     return bean;
/*     */   }
/*     */ 
/*     */   public Category[] deleteByIds(Integer[] ids)
/*     */   {
/* 239 */     Category[] beans = new Category[ids.length];
/* 240 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 241 */       beans[i] = this.dao.deleteById(ids[i]);
/*     */     }
/*     */ 
/* 244 */     for (Category bean : beans) {
/* 245 */       Category parent = bean.getParent();
/* 246 */       if (parent != null) {
/* 247 */         parent.removeFromChild(bean);
/*     */       }
/*     */     }
/* 250 */     return beans;
/*     */   }
/*     */ 
/*     */   public Category[] updatePriority(Integer[] ids, Integer[] priority)
/*     */   {
/* 255 */     Category[] beans = new Category[ids.length];
/* 256 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 257 */       beans[i] = findById(ids[i]);
/* 258 */       beans[i].setPriority(priority[i]);
/*     */     }
/* 260 */     return beans;
/*     */   }
/*     */ 
/*     */   private void addAllChildToList(List<Category> allList, Collection<Category> coll, Long typeId, Integer leval)
/*     */   {
/* 274 */     for (Category ctg : coll) {
/* 275 */       if (leval != null) {
/* 276 */         ctg.setLeval(leval.intValue());
/*     */       }
/*     */ 
/* 279 */       if (typeId != null) {
/* 280 */         if (typeId.equals(ctg.getType().getId()))
/* 281 */           allList.add(ctg);
/*     */       }
/*     */       else {
/* 284 */         allList.add(ctg);
/*     */       }
/* 286 */       Collection child = ctg.getChild();
/* 287 */       if ((child != null) && (child.size() > 0)) {
/* 288 */         if (leval != null) {
/* 289 */           leval = Integer.valueOf(leval.intValue() + 1);
/*     */         }
/* 291 */         addAllChildToList(allList, child, typeId, leval);
/* 292 */         if (leval != null)
/* 293 */           leval = Integer.valueOf(leval.intValue() - 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Category> getListBypType(Long webId, Long typeId, Integer count)
/*     */   {
/* 301 */     return this.dao.getListByptype(webId, typeId, count);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setProductTypeMng(ProductTypeMng productMng)
/*     */   {
/* 316 */     this.productTypeMng = productMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(CategoryDao dao) {
/* 321 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CategoryMngImpl
 * JD-Core Version:    0.6.0
 */