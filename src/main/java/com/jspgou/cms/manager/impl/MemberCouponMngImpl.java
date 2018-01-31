/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.MemberCouponDao;
/*    */ import com.jspgou.cms.entity.MemberCoupon;
/*    */ import com.jspgou.cms.manager.MemberCouponMng;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class MemberCouponMngImpl
/*    */   implements MemberCouponMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private MemberCouponDao dao;
/*    */ 
/*    */   public List<MemberCoupon> getList(Long memberId)
/*    */   {
/* 23 */     List list = this.dao.getList(memberId);
/* 24 */     return list;
/*    */   }
/*    */ 
/*    */   public MemberCoupon findById(Long id)
/*    */   {
/* 29 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<MemberCoupon> getList(Long memberId, BigDecimal price) {
/* 35 */     return this.dao.getList(memberId, new Date(), price);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public MemberCoupon findByCoupon(Long memberId, Long couponId) {
/* 41 */     return this.dao.findByCoupon(memberId, couponId);
/*    */   }
/*    */ 
/*    */   public MemberCoupon save(MemberCoupon bean)
/*    */   {
/* 46 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public MemberCoupon update(MemberCoupon bean)
/*    */   {
/* 51 */     return this.dao.update(bean);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.MemberCouponMngImpl
 * JD-Core Version:    0.6.0
 */