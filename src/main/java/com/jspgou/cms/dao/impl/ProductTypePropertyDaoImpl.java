/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductTypePropertyDao;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ProductTypePropertyDaoImpl extends HibernateBaseDao<ProductTypeProperty, Long>
/*     */   implements ProductTypePropertyDao
/*     */ {
/*     */   public ProductTypeProperty deleteById(Long id)
/*     */   {
/*  26 */     ProductTypeProperty entity = (ProductTypeProperty)super.get(id);
/*  27 */     if (entity != null) {
/*  28 */       getSession().delete(entity);
/*     */     }
/*  30 */     return entity;
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty findById(Long id)
/*     */   {
/*  35 */     ProductTypeProperty entity = (ProductTypeProperty)get(id);
/*  36 */     return entity;
/*     */   }
/*     */ 
/*     */   public Pagination getList(int pageNo, int pageSize, Long typeid, Boolean isCategory, String searchType, String searchContent)
/*     */   {
/*  42 */     Finder f = Finder.create("from ProductTypeProperty bean where 1=1 ");
/*  43 */     if (typeid != null) {
/*  44 */       f.append(" and bean.propertyType.id = :typeid").setParam("typeid", typeid);
/*     */     }
/*  46 */     if (searchType != null) {
/*  47 */       if ("1".equals(searchType)) {
/*  48 */         f.append(" and bean.propertyType.name like :searchContent ");
/*     */       }
/*  51 */       else if ("2".equals(searchType)) {
/*  52 */         f.append(" and bean.propertyName like :searchContent ");
/*     */       }
/*  54 */       f.setParam("searchContent", "%" + searchContent + "%");
/*     */     }
/*  56 */     f.append(" and bean.category=:isCategory").setParam("isCategory", isCategory);
/*  57 */     f.append(" order by bean.propertyType.id,bean.sort");
/*  58 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getList(Long typeId, Boolean isCategory)
/*     */   {
/*  64 */     Finder f = Finder.create("from ProductTypeProperty bean where 1=1 ");
/*  65 */     if (typeId != null) {
/*  66 */       f.append(" and bean.propertyType.id = :typeId").setParam("typeId", typeId);
/*     */     }
/*  68 */     f.append(" and bean.category=:isCategory").setParam("isCategory", isCategory);
/*  69 */     f.append(" order by bean.sort asc");
/*  70 */     return find(f);
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty save(ProductTypeProperty bean)
/*     */   {
/*  75 */     getSession().save(bean);
/*  76 */     return bean;
/*     */   }
/*     */ 
/*     */   protected Class<ProductTypeProperty> getEntityClass()
/*     */   {
/*  81 */     return ProductTypeProperty.class;
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> findBySearch(String searchType, String searchContent)
/*     */   {
/*  88 */     String hql = "from ProductTypeProperty bean where 1 = 1 ";
/*     */ 
/*  90 */     if ("1".equals(searchType)) {
/*  91 */       hql = hql + " and bean.propertyType.name like :searchContent ";
/*     */     }
/*  94 */     else if ("2".equals(searchType)) {
/*  95 */       hql = hql + " and bean.propertyName like :searchContent ";
/*     */     }
/*  97 */     searchContent = "%" + searchContent + "%";
/*  98 */     return getSession().createQuery(hql).setString("searchContent", searchContent).setCacheable(false).list();
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> findListByTypeId(Long typeId)
/*     */   {
/* 104 */     String hql = "from ProductTypeProperty bean where bean.propertyType.id = :typeId ";
/* 105 */     return getSession().createQuery(hql).setLong("typeId", typeId.longValue()).setCacheable(false).list();
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getList(Long typeId, boolean isCategory)
/*     */   {
/* 111 */     Finder f = Finder.create("from ProductTypeProperty bean where 1=1");
/* 112 */     f.append(" and bean.propertyType.id=:typeId").setParam("typeId", typeId);
/* 113 */     f.append(" and bean.category=:isCategory").setParam("isCategory", Boolean.valueOf(isCategory));
/* 114 */     f.append(" order by bean.sort asc");
/* 115 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles)
/*     */   {
/* 120 */     List list = new ArrayList();
/*     */ 
/* 122 */     int i = 0; for (int len = fields.length; i < len; i++) {
/* 123 */       if (!StringUtils.isBlank(fields[i])) {
/* 124 */         ProductTypeProperty item = new ProductTypeProperty();
/* 125 */         item.setCustom(Boolean.valueOf(false));
/* 126 */         item.setPropertyType(model);
/* 127 */         item.setCategory(Boolean.valueOf(isCategory));
/*     */ 
/* 129 */         item.setField(fields[i]);
/* 130 */         item.setPropertyName(propertyNames[i]);
/* 131 */         item.setSort(sorts[i]);
/* 132 */         item.setDataType(dataTypes[i]);
/* 133 */         item.setSingle(singles[i]);
/* 134 */         list.add(item);
/*     */       }
/*     */     }
/* 137 */     return list;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductTypePropertyDaoImpl
 * JD-Core Version:    0.6.0
 */