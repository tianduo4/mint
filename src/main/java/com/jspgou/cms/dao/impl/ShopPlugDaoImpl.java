/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopPlugDao;
/*    */ import com.jspgou.cms.entity.ShopPlug;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopPlugDaoImpl extends HibernateBaseDao<ShopPlug, Long>
/*    */   implements ShopPlugDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Criteria crit = createCriteria(new Criterion[0]);
/* 19 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public List<ShopPlug> getList(String author, Boolean used)
/*    */   {
/* 25 */     Finder f = Finder.create("from ShopPlug plug where 1=1 ");
/* 26 */     if (StringUtils.isNotBlank(author)) {
/* 27 */       f.append("and plug.author=:author").setParam("author", author);
/*    */     }
/* 29 */     if (used != null) {
/* 30 */       if (used.booleanValue())
/* 31 */         f.append("and plug.used=true");
/*    */       else {
/* 33 */         f.append("and plug.used=false");
/*    */       }
/*    */     }
/* 36 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopPlug findById(Long id) {
/* 40 */     ShopPlug entity = (ShopPlug)get(id);
/* 41 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopPlug findByPath(String plugPath)
/*    */   {
/* 46 */     Finder f = Finder.create("from ShopPlug plug where plug.path=:path").setParam("path", plugPath);
/* 47 */     List list = find(f);
/* 48 */     if ((list != null) && (list.size() > 0)) {
/* 49 */       return (ShopPlug)list.get(0);
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public ShopPlug save(ShopPlug bean)
/*    */   {
/* 56 */     getSession().save(bean);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopPlug deleteById(Long id)
/*    */   {
/* 62 */     ShopPlug entity = (ShopPlug)super.get(id);
/* 63 */     if (entity != null) {
/* 64 */       getSession().delete(entity);
/*    */     }
/* 66 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopPlug> getEntityClass()
/*    */   {
/* 73 */     return ShopPlug.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopPlugDaoImpl
 * JD-Core Version:    0.6.0
 */