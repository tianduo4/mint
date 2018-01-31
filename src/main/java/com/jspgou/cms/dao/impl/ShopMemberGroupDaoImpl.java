/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMemberGroupDao;
/*    */ import com.jspgou.cms.entity.ShopMemberGroup;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopMemberGroupDaoImpl extends HibernateBaseDao<ShopMemberGroup, Long>
/*    */   implements ShopMemberGroupDao
/*    */ {
/*    */   public List<ShopMemberGroup> getList(Long webId, boolean cacheable)
/*    */   {
/* 21 */     String hql = "from ShopMemberGroup bean where bean.website.id=:webId order by bean.score";
/* 22 */     return getSession().createQuery(hql).setParameter("webId", webId)
/* 23 */       .setCacheable(cacheable).list();
/*    */   }
/*    */ 
/*    */   public List<ShopMemberGroup> getList()
/*    */   {
/* 29 */     String hql = "from ShopMemberGroup bean order by bean.id asc";
/* 30 */     return find(hql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup findById(Long id)
/*    */   {
/* 37 */     ShopMemberGroup entity = (ShopMemberGroup)get(id);
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup save(ShopMemberGroup bean)
/*    */   {
/* 43 */     getSession().save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup deleteById(Long id)
/*    */   {
/* 49 */     ShopMemberGroup entity = (ShopMemberGroup)super.get(id);
/* 50 */     if (entity != null) {
/* 51 */       getSession().delete(entity);
/*    */     }
/* 53 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopMemberGroup> getEntityClass()
/*    */   {
/* 58 */     return ShopMemberGroup.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopMemberGroupDaoImpl
 * JD-Core Version:    0.6.0
 */