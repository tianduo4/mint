/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopAdminDao;
/*    */ import com.jspgou.cms.entity.ShopAdmin;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopAdminDaoImpl extends HibernateBaseDao<ShopAdmin, Long>
/*    */   implements ShopAdminDao
/*    */ {
/*    */   public Pagination getPage(Long webId, int pageNo, int pageSize)
/*    */   {
/* 20 */     Finder f = 
/* 21 */       Finder.create("from ShopAdmin bean where bean.website.id=:webId  order by bean.id desc");
/* 22 */     f.setParam("webId", webId);
/* 23 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public ShopAdmin findById(Long id)
/*    */   {
/* 28 */     ShopAdmin entity = (ShopAdmin)get(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopAdmin save(ShopAdmin bean)
/*    */   {
/* 34 */     getSession().save(bean);
/* 35 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopAdmin deleteById(Long id)
/*    */   {
/* 40 */     ShopAdmin entity = (ShopAdmin)super.get(id);
/* 41 */     if (entity != null) {
/* 42 */       getSession().delete(entity);
/*    */     }
/* 44 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopAdmin> getEntityClass()
/*    */   {
/* 49 */     return ShopAdmin.class;
/*    */   }
/*    */ 
/*    */   public ShopAdmin getByUsername(String username)
/*    */   {
/* 54 */     String hql = "from ShopAdmin bean where bean.admin.user.username=:username";
/* 55 */     return (ShopAdmin)getSession().createQuery(hql).setParameter("username", username).uniqueResult();
/*    */   }
/*    */ 
/*    */   public ShopAdmin findByName(String name)
/*    */   {
/* 61 */     String sql = "from ShopAdmin bean where bean.admin.user.username=:username";
/* 62 */     return (ShopAdmin)getSession().createQuery(sql).setParameter("username", name).uniqueResult();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopAdminDaoImpl
 * JD-Core Version:    0.6.0
 */