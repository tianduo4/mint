/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMemberAddressDao;
/*    */ import com.jspgou.cms.entity.ShopMemberAddress;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopMemberAddressDaoImpl extends HibernateBaseDao<ShopMemberAddress, Long>
/*    */   implements ShopMemberAddressDao
/*    */ {
/*    */   public List<ShopMemberAddress> getList(Long memberId)
/*    */   {
/* 22 */     String hql = "from ShopMemberAddress bean where bean.member.id=? order by bean.isDefault desc";
/* 23 */     return find(hql, new Object[] { memberId });
/*    */   }
/*    */ 
/*    */   public List<ShopMemberAddress> findByMemberDefault(Long memberId, Boolean isDefault)
/*    */   {
/* 29 */     Finder f = Finder.create("from ShopMemberAddress bean where 1=1");
/* 30 */     if (memberId != null) {
/* 31 */       f.append(" and bean.member.id=:memberId");
/* 32 */       f.setParam("memberId", memberId);
/*    */     }
/* 34 */     if (isDefault != null) {
/* 35 */       f.append(" and bean.isDefault=:isDefault ");
/* 36 */       f.setParam("isDefault", isDefault);
/*    */     }
/* 38 */     f.append(" order by bean.isDefault desc");
/* 39 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress findById(Long id)
/*    */   {
/* 44 */     ShopMemberAddress entity = (ShopMemberAddress)get(id);
/* 45 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress save(ShopMemberAddress bean)
/*    */   {
/* 50 */     getSession().save(bean);
/* 51 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress deleteById(Long id)
/*    */   {
/* 56 */     ShopMemberAddress entity = (ShopMemberAddress)super.get(id);
/* 57 */     if (entity != null) {
/* 58 */       getSession().delete(entity);
/*    */     }
/* 60 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopMemberAddress> getEntityClass()
/*    */   {
/* 65 */     return ShopMemberAddress.class;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 70 */     String sql = "delete from jc_shop_member_address where member_id = " + memberId;
/* 71 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopMemberAddressDaoImpl
 * JD-Core Version:    0.6.0
 */