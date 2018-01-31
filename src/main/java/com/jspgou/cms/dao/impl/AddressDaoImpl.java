/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.AddressDao;
/*    */ import com.jspgou.cms.entity.Address;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class AddressDaoImpl extends HibernateBaseDao<Address, Long>
/*    */   implements AddressDao
/*    */ {
/*    */   public List<Address> getListById(Long parentId)
/*    */   {
/* 23 */     Finder f = Finder.create("from Address bean where 1=1 ");
/* 24 */     if (parentId == null) {
/* 25 */       f.append(" and bean.parent.id is null");
/*    */     } else {
/* 27 */       f.append(" and bean.parent.id=:parentId");
/* 28 */       f.setParam("parentId", parentId);
/*    */     }
/* 30 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 35 */     Criteria crit = createCriteria(new Criterion[0]);
/* 36 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 37 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPageByParentId(Long parentId, int pageNo, int pageSize)
/*    */   {
/* 42 */     Finder f = Finder.create("from Address bean where 1=1 ");
/* 43 */     if (parentId != null) {
/* 44 */       f.append(" and bean.parent.id=:id");
/* 45 */       f.setParam("id", parentId);
/*    */     } else {
/* 47 */       f.append(" and bean.parent.id is null");
/*    */     }
/* 49 */     f.append(" order by bean.priority");
/* 50 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Address findById(Long id)
/*    */   {
/* 55 */     Address entity = (Address)get(id);
/* 56 */     return entity;
/*    */   }
/*    */ 
/*    */   public Address save(Address bean)
/*    */   {
/* 61 */     getSession().save(bean);
/* 62 */     return bean;
/*    */   }
/*    */ 
/*    */   public Address deleteById(Long id)
/*    */   {
/* 67 */     Address entity = (Address)super.get(id);
/* 68 */     if (entity != null) {
/* 69 */       getSession().delete(entity);
/*    */     }
/* 71 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Address> getEntityClass()
/*    */   {
/* 76 */     return Address.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.AddressDaoImpl
 * JD-Core Version:    0.6.0
 */