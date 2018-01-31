/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopMemberDao;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ShopMemberDaoImpl extends HibernateBaseDao<ShopMember, Long>
/*     */   implements ShopMemberDao
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private MemberMng memberMng;
/*     */ 
/*     */   public Pagination getPage(Long webId, int pageNo, int pageSize)
/*     */   {
/*  27 */     Finder f = 
/*  28 */       Finder.create("from ShopMember bean where bean.website.id=:webId order by bean.id desc");
/*  29 */     f.setParam("webId", webId);
/*  30 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long webId, int pageNo, int pageSize, String username)
/*     */   {
/*  35 */     Finder f = 
/*  36 */       Finder.create("from ShopMember bean where bean.website.id=:webId ");
/*  37 */     f.setParam("webId", webId);
/*  38 */     if (StringUtils.isNotEmpty(username)) {
/*  39 */       f.append(" and bean.member.user.username like :username");
/*  40 */       f.setParam("username", "%" + username + "%");
/*     */     }
/*  42 */     f.append(" order by bean.id desc");
/*  43 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public ShopMember getByUserId(Long webId, Long userId)
/*     */   {
/*  48 */     Member coreMember = this.memberMng.getByUserId(webId, userId);
/*  49 */     if (coreMember != null) {
/*  50 */       return findById(coreMember.getId());
/*     */     }
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public ShopMember findById(Long id)
/*     */   {
/*  58 */     ShopMember entity = (ShopMember)get(id);
/*  59 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopMember save(ShopMember bean)
/*     */   {
/*  64 */     getSession().save(bean);
/*  65 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopMember deleteById(Long id)
/*     */   {
/*  70 */     ShopMember entity = (ShopMember)super.get(id);
/*  71 */     if (entity != null) {
/*  72 */       getSession().delete(entity);
/*     */     }
/*  74 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<ShopMember> getEntityClass()
/*     */   {
/*  79 */     return ShopMember.class;
/*     */   }
/*     */ 
/*     */   public ShopMember findUsername(String username) {
/*  83 */     String hql = "from ShopMember bean where bean.member.user.username=:realName";
/*  84 */     Query query = getSession().createQuery(hql);
/*  85 */     query.setParameter("realName", username);
/*  86 */     query.setMaxResults(1);
/*  87 */     return (ShopMember)query.uniqueResult();
/*     */   }
/*     */ 
/*     */   public ShopMember findByUsername(String realName)
/*     */   {
/*  94 */     return (ShopMember)findUniqueByProperty("realName", realName);
/*     */   }
/*     */ 
/*     */   public List<ShopMember> getList(String realName, Long groupId)
/*     */   {
/* 100 */     Finder f = Finder.create("select bean from ShopMember bean where 1=1");
/* 101 */     if (!StringUtils.isBlank(realName)) {
/* 102 */       f.append(" and bean.realName like :realName");
/* 103 */       f.setParam("realName", "%" + realName + "%");
/*     */     }
/* 105 */     if (groupId != null) {
/* 106 */       f.append(" and bean.group.id=:groupId");
/* 107 */       f.setParam("groupId", groupId);
/*     */     }
/* 109 */     f.append(" order by bean.id desc");
/* 110 */     return find(f);
/*     */   }
/*     */ 
/*     */   public int countByUsername(String realName)
/*     */   {
/* 115 */     String hql = "select count(*) from ShopMember bean where bean.member.user.username=:realName";
/* 116 */     Query query = getSession().createQuery(hql);
/* 117 */     query.setParameter("realName", realName);
/* 118 */     return ((Number)query.iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   public Long getMemberCount(Long webId)
/*     */   {
/* 125 */     Finder finder = Finder.create("select count(1) from ShopMember bean where  bean.website.id=:webId");
/* 126 */     finder.setParam("webId", webId);
/* 127 */     List list = find(finder);
/* 128 */     return (Long)list.get(0);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopMemberDaoImpl
 * JD-Core Version:    0.6.0
 */