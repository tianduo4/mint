/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.CategoryDao;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class CategoryDaoImpl extends HibernateBaseDao<Category, Integer>
/*     */   implements CategoryDao
/*     */ {
/*     */   public List<Category> getAll(Long webId)
/*     */   {
/*  25 */     String hql = "from Category bean where bean.website.id=? order by bean.priority";
/*  26 */     return createQuery(hql, new Object[] { webId }).setCacheable(false).list();
/*     */   }
/*     */ 
/*     */   public Category getByPath(Long webId, String path, boolean cacheable)
/*     */   {
/*  31 */     String hql = "from Category bean where bean.website.id=? and bean.path=?";
/*  32 */     return (Category)createQuery(hql, new Object[] { webId, path }).setCacheable(cacheable)
/*  33 */       .uniqueResult();
/*     */   }
/*     */ 
/*     */   public List<Category> getListForParent(Long webId, Integer ctgId)
/*     */   {
/*  39 */     Finder f = Finder.create("select node");
/*  40 */     f.append(" from Category node,Category exclude");
/*  41 */     f.append(" where ex.id=:ctgId and node.website.id=?");
/*  42 */     f.append(" and node.lft<exclude.lft and node.rgt>exclude.rgt");
/*  43 */     f.append(" order by node.priority");
/*  44 */     f.setParam("webId", webId);
/*  45 */     f.setParam("ctgId", ctgId);
/*  46 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Category> getListForChild(Long webId, Integer ctgId)
/*     */   {
/*  52 */     Finder f = Finder.create("select node");
/*  53 */     f.append(" from Category node, Category parent");
/*  54 */     f.append(" where parent.id=:ctgId and node.website.id=:webId");
/*  55 */     f.append(" and node.lft>=parent.lft and node.rgt<=parent.rgt");
/*  56 */     f.setParam("webId", webId);
/*  57 */     f.setParam("ctgId", ctgId);
/*  58 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Category> getTopList(Long webId, boolean cacheable)
/*     */   {
/*  64 */     String hql = "from Category bean where bean.website.id=? and bean.parent.id is null order by bean.priority";
/*  65 */     return createQuery(hql, new Object[] { webId }).setCacheable(cacheable).list();
/*     */   }
/*     */ 
/*     */   public List<Category> getChildList(Long webId, Integer parentId)
/*     */   {
/*  71 */     Finder f = Finder.create("from Category bean");
/*  72 */     f.append(" where bean.parent.id=:parentId");
/*  73 */     f.setParam("parentId", parentId);
/*  74 */     f.append(" order by bean.priority asc,bean.id asc");
/*  75 */     return find(f);
/*     */   }
/*     */ 
/*     */   public int countPath(Long webId, String path)
/*     */   {
/*  80 */     String hql = "select count(*) from Category bean where bean.website.id=:webId and bean.path=:path";
/*  81 */     return 
/*  82 */       ((Number)getSession().createQuery(hql).setParameter("webId", 
/*  82 */       webId).setParameter("path", path).iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   public List<Category> getListByptype(Long webId, Long pTypeId, Integer count)
/*     */   {
/*  89 */     String hql = "from Category bean where bean.website.id=? and bean.type.id=?";
/*  90 */     if ((count != null) && (count.intValue() != 0))
/*     */     {
/*  92 */       return getSession().createQuery(hql).setParameter(0, webId).setParameter(1, pTypeId).setFirstResult(0)
/*  93 */         .setMaxResults(count.intValue()).list();
/*     */     }
/*  95 */     return getSession().createQuery(hql).setParameter(0, webId).setParameter(1, pTypeId).list();
/*     */   }
/*     */ 
/*     */   public Category findById(Integer id)
/*     */   {
/* 100 */     Category entity = (Category)get(id);
/* 101 */     return entity;
/*     */   }
/*     */ 
/*     */   public Category save(Category bean)
/*     */   {
/* 106 */     getSession().save(bean);
/* 107 */     return bean;
/*     */   }
/*     */ 
/*     */   public Category deleteById(Integer id)
/*     */   {
/* 112 */     Category entity = (Category)super.get(id);
/* 113 */     if (entity != null) {
/* 114 */       getSession().delete(entity);
/*     */     }
/* 116 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<Category> getEntityClass()
/*     */   {
/* 121 */     return Category.class;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CategoryDaoImpl
 * JD-Core Version:    0.6.0
 */