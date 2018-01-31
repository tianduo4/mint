/*    */ package com.jspgou.plug.weixin.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinDao;
/*    */ import com.jspgou.plug.weixin.entity.Weixin;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class WeixinDaoImpl extends HibernateBaseDao<Weixin, Integer>
/*    */   implements WeixinDao
/*    */ {
/*    */   public Pagination getPage(Integer siteId, int pageNo, int pageSize)
/*    */   {
/* 14 */     Finder f = Finder.create(" from Weixin bean where 1=1");
/* 15 */     f.append(" and bean.site.id=:siteId");
/* 16 */     f.setParam("siteId", siteId);
/* 17 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Weixin findById(Integer id) {
/* 21 */     return (Weixin)get(id);
/*    */   }
/*    */ 
/*    */   public Weixin findBy()
/*    */   {
/* 26 */     Finder f = Finder.create(" from Weixin bean where 1=1");
/* 27 */     List list = find(f);
/* 28 */     if ((list != null) && (list.size() > 0)) {
/* 29 */       return (Weixin)list.get(0);
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public Weixin find(Long siteId) {
/* 35 */     Finder f = Finder.create(" from Weixin bean where 1=1");
/* 36 */     f.append(" and bean.site.id=:siteId").setParam("siteId", siteId);
/* 37 */     List list = find(f);
/* 38 */     if ((list != null) && (list.size() > 0)) {
/* 39 */       return (Weixin)list.get(0);
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   public Weixin deleteById(Integer id) {
/* 45 */     Weixin entity = (Weixin)get(id);
/* 46 */     if (entity != null) {
/* 47 */       getSession().delete(entity);
/* 48 */       return entity;
/*    */     }
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public Weixin save(Weixin bean) {
/* 54 */     getSession().save(bean);
/* 55 */     return bean;
/*    */   }
/*    */ 
/*    */   protected Class<Weixin> getEntityClass()
/*    */   {
/* 60 */     return Weixin.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.impl.WeixinDaoImpl
 * JD-Core Version:    0.6.0
 */