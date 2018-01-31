/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.core.dao.MemberDao;
/*    */ import com.jspgou.core.entity.Member;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class MemberDaoImpl extends HibernateBaseDao<Member, Long>
/*    */   implements MemberDao
/*    */ {
/*    */   public Member getByUsername(String username)
/*    */   {
/* 23 */     String f = "from Member bean where bean.user.username=:username";
/* 24 */     return (Member)getSession().createQuery(f).setParameter("username", username).uniqueResult();
/*    */   }
/*    */ 
/*    */   public Member getByUserId(Long webId, Long userId)
/*    */   {
/* 30 */     String hql = "from Member bean where bean.user.id=:userId";
/*    */ 
/* 32 */     Finder finder = Finder.create(hql).setParam("userId", userId);
/* 33 */     if (webId != null) {
/* 34 */       finder.append(" and bean.website.id=:webId").setParam("webId", webId);
/*    */     }
/* 36 */     List li = find(finder);
/* 37 */     if (li.size() > 0) {
/* 38 */       return (Member)li.get(0);
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   public Member getByUserIdAndActive(String activationCode, Long userId)
/*    */   {
/* 46 */     String s = "from Member bean where bean.activationCode=? and bean.user.id=?";
/* 47 */     return (Member)findUnique(s, new Object[] { 
/* 48 */       activationCode, userId });
/*    */   }
/*    */ 
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 54 */     Criteria criteria = createCriteria(new Criterion[0]);
/* 55 */     Pagination pagination = findByCriteria(criteria, pageNo, pageSize);
/* 56 */     return pagination;
/*    */   }
/*    */ 
/*    */   public Member findById(Long id)
/*    */   {
/* 61 */     Member entity = (Member)get(id);
/* 62 */     return entity;
/*    */   }
/*    */ 
/*    */   public Member save(Member bean)
/*    */   {
/* 67 */     getSession().save(bean);
/* 68 */     return bean;
/*    */   }
/*    */ 
/*    */   public Member deleteById(Long id)
/*    */   {
/* 73 */     Member entity = (Member)super.get(id);
/* 74 */     if (entity != null)
/* 75 */       getSession().delete(entity);
/* 76 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Member> getEntityClass()
/*    */   {
/* 81 */     return Member.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.MemberDaoImpl
 * JD-Core Version:    0.6.0
 */