/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CustomerServiceDao;
/*    */ import com.jspgou.cms.entity.CustomerService;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class CustomerServiceDaoImpl extends HibernateBaseDao<CustomerService, Long>
/*    */   implements CustomerServiceDao
/*    */ {
/*    */   public CustomerService findById(Long id)
/*    */   {
/* 23 */     CustomerService entity = (CustomerService)get(id);
/* 24 */     return entity;
/*    */   }
/*    */ 
/*    */   public Pagination getPagination(Boolean disable, int pageNo, int pageSize)
/*    */   {
/* 29 */     Finder f = Finder.create("from CustomerService bean where 1=1");
/* 30 */     if (disable != null) {
/* 31 */       f.append(" and bean.disable=:disable").setParam("disable", disable);
/*    */     }
/* 33 */     f.append(" order by bean.priority");
/* 34 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<CustomerService> getList(Boolean disable)
/*    */   {
/* 40 */     Finder f = Finder.create("from CustomerService bean where 1=1");
/*    */ 
/* 42 */     if (disable != null) {
/* 43 */       f.append(" and bean.disable=:disable").setParam("disable", disable);
/*    */     }
/* 45 */     f.append(" order by bean.priority");
/* 46 */     return find(f);
/*    */   }
/*    */ 
/*    */   public CustomerService save(CustomerService bean)
/*    */   {
/* 51 */     getSession().save(bean);
/* 52 */     return bean;
/*    */   }
/*    */ 
/*    */   public CustomerService deleteById(Long id)
/*    */   {
/* 57 */     CustomerService entity = (CustomerService)super.get(id);
/* 58 */     if (entity != null) {
/* 59 */       getSession().delete(entity);
/*    */     }
/* 61 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<CustomerService> getEntityClass()
/*    */   {
/* 69 */     return CustomerService.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CustomerServiceDaoImpl
 * JD-Core Version:    0.6.0
 */