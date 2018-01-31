/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.AdvertiseDao;
/*    */ import com.jspgou.cms.entity.Advertise;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class AdvertiseDaoImpl extends HibernateBaseDao<Advertise, Integer>
/*    */   implements AdvertiseDao
/*    */ {
/*    */   public Pagination getPage(Integer adspaceId, Boolean enabled, int pageNo, int pageSize)
/*    */   {
/* 23 */     Finder f = Finder.create("from Advertise bean where 1=1");
/* 24 */     if (adspaceId != null) {
/* 25 */       f.append(" and bean.adspace.id=:adspaceId");
/* 26 */       f.setParam("adspaceId", adspaceId);
/*    */     }
/* 28 */     if (enabled != null) {
/* 29 */       f.append(" and bean.enabled=:enabled");
/* 30 */       f.setParam("enabled", enabled);
/*    */     }
/* 32 */     f.append(" order by bean.id desc");
/* 33 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<Advertise> getList(Integer adspaceId, Boolean enabled)
/*    */   {
/* 39 */     Finder f = Finder.create("from Advertise bean where 1=1");
/* 40 */     if (adspaceId != null) {
/* 41 */       f.append(" and bean.adspace.id=:adspaceId");
/* 42 */       f.setParam("adspaceId", adspaceId);
/*    */     }
/* 44 */     if (enabled != null) {
/* 45 */       f.append(" and bean.enabled=:enabled");
/* 46 */       f.setParam("enabled", enabled);
/*    */     }
/* 48 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Advertise findById(Integer id)
/*    */   {
/* 53 */     Advertise entity = (Advertise)get(id);
/* 54 */     return entity;
/*    */   }
/*    */ 
/*    */   public Advertise save(Advertise bean)
/*    */   {
/* 59 */     getSession().save(bean);
/* 60 */     return bean;
/*    */   }
/*    */ 
/*    */   public Advertise deleteById(Integer id)
/*    */   {
/* 65 */     Advertise entity = (Advertise)super.get(id);
/* 66 */     if (entity != null) {
/* 67 */       getSession().delete(entity);
/*    */     }
/* 69 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Advertise> getEntityClass()
/*    */   {
/* 74 */     return Advertise.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.AdvertiseDaoImpl
 * JD-Core Version:    0.6.0
 */