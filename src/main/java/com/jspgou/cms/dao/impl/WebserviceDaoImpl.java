/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.WebserviceDao;
/*    */ import com.jspgou.cms.entity.Webservice;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class WebserviceDaoImpl extends HibernateBaseDao<Webservice, Integer>
/*    */   implements WebserviceDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 19 */     String hql = "from Webservice bean";
/* 20 */     Finder f = Finder.create(hql);
/* 21 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<Webservice> getList(String type)
/*    */   {
/* 26 */     String hql = "from Webservice bean where bean.type=:type";
/* 27 */     Finder f = Finder.create(hql).setParam("type", type);
/* 28 */     f.setCacheable(true);
/* 29 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Webservice findById(Integer id)
/*    */   {
/* 34 */     Webservice entity = (Webservice)get(id);
/* 35 */     return entity;
/*    */   }
/*    */ 
/*    */   public Webservice save(Webservice bean)
/*    */   {
/* 40 */     getSession().save(bean);
/* 41 */     return bean;
/*    */   }
/*    */ 
/*    */   public Webservice deleteById(Integer id)
/*    */   {
/* 46 */     Webservice entity = (Webservice)super.get(id);
/* 47 */     if (entity != null) {
/* 48 */       getSession().delete(entity);
/*    */     }
/* 50 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Webservice> getEntityClass()
/*    */   {
/* 55 */     return Webservice.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.WebserviceDaoImpl
 * JD-Core Version:    0.6.0
 */