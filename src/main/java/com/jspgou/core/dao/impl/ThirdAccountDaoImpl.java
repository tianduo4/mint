/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.core.dao.ThirdAccountDao;
/*    */ import com.jspgou.core.entity.ThirdAccount;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ThirdAccountDaoImpl extends HibernateBaseDao<ThirdAccount, Long>
/*    */   implements ThirdAccountDao
/*    */ {
/*    */   public Pagination getPage(String username, String source, int pageNo, int pageSize)
/*    */   {
/* 23 */     String hql = "from ThirdAccount bean where 1=1 ";
/* 24 */     Finder f = Finder.create(hql);
/* 25 */     if (StringUtils.isNotBlank(username)) {
/* 26 */       f.append(" and bean.username like :username").setParam("username", "%" + username + "%");
/*    */     }
/* 28 */     if (StringUtils.isNotBlank(source)) {
/* 29 */       f.append(" and bean.source=:source").setParam("source", source);
/*    */     }
/* 31 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public ThirdAccount findById(Long id) {
/* 35 */     ThirdAccount entity = (ThirdAccount)get(id);
/* 36 */     return entity;
/*    */   }
/*    */ 
/*    */   public ThirdAccount findByKey(String key)
/*    */   {
/* 41 */     String hql = "from ThirdAccount bean where bean.accountKey=:accountKey";
/* 42 */     Finder f = Finder.create(hql).setParam("accountKey", key);
/* 43 */     List li = find(f);
/* 44 */     if (li.size() > 0) {
/* 45 */       return (ThirdAccount)li.get(0);
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   public ThirdAccount save(ThirdAccount bean)
/*    */   {
/* 52 */     getSession().save(bean);
/* 53 */     return bean;
/*    */   }
/*    */ 
/*    */   public ThirdAccount deleteById(Long id) {
/* 57 */     ThirdAccount entity = (ThirdAccount)super.get(id);
/* 58 */     if (entity != null) {
/* 59 */       getSession().delete(entity);
/*    */     }
/* 61 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ThirdAccount> getEntityClass()
/*    */   {
/* 66 */     return ThirdAccount.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.ThirdAccountDaoImpl
 * JD-Core Version:    0.6.0
 */