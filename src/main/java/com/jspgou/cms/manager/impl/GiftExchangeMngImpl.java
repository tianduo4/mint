/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.GiftExchangeDao;
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import com.jspgou.cms.entity.GiftExchange;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.entity.ShopScore.ScoreTypes;
/*     */ import com.jspgou.cms.manager.GiftExchangeMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class GiftExchangeMngImpl
/*     */   implements GiftExchangeMng
/*     */ {
/*     */   private GiftExchangeDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  30 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  31 */     return page;
/*     */   }
/*     */ 
/*     */   public List<GiftExchange> getlist(Long memberId)
/*     */   {
/*  36 */     return this.dao.getlist(memberId);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public GiftExchange findById(Long id) {
/*  42 */     GiftExchange entity = this.dao.findById(id);
/*  43 */     return entity;
/*     */   }
/*     */ 
/*     */   public GiftExchange save(GiftExchange bean)
/*     */   {
/*  48 */     this.dao.save(bean);
/*  49 */     return bean;
/*     */   }
/*     */ 
/*     */   public GiftExchange save(Gift gift, ShopMemberAddress shopMemberAddress, ShopMember member, Integer count)
/*     */   {
/*  54 */     GiftExchange giftExchange = new GiftExchange();
/*  55 */     Date now = new Timestamp(System.currentTimeMillis());
/*  56 */     giftExchange.setAmount(count);
/*  57 */     giftExchange.setCreateTime(now);
/*  58 */     if ((StringUtils.isBlank(shopMemberAddress.getTel())) && (StringUtils.isNotBlank(shopMemberAddress.getPhone())))
/*  59 */       giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",固话：" + shopMemberAddress.getPhone() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
/*  60 */     else if ((StringUtils.isBlank(shopMemberAddress.getPhone())) && (StringUtils.isNotBlank(shopMemberAddress.getTel())))
/*  61 */       giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",移动电话：" + shopMemberAddress.getTel() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
/*  62 */     else if ((StringUtils.isNotBlank(shopMemberAddress.getTel())) && (StringUtils.isNotBlank(shopMemberAddress.getPhone()))) {
/*  63 */       giftExchange.setDetailaddress(shopMemberAddress.getUsername() + ",移动电话：" + shopMemberAddress.getTel() + "," + shopMemberAddress.getDetailaddress() + "," + shopMemberAddress.getPostCode());
/*     */     }
/*  65 */     giftExchange.setGift(gift);
/*  66 */     giftExchange.setMember(member);
/*  67 */     giftExchange.setScore(gift.getGiftScore());
/*  68 */     giftExchange.setTotalScore(Integer.valueOf(gift.getGiftScore().intValue() * count.intValue()));
/*  69 */     giftExchange.setStatus(Integer.valueOf(1));
/*     */ 
/*  71 */     ShopScore shopScore = new ShopScore();
/*  72 */     shopScore.setMember(member);
/*  73 */     shopScore.setName(gift.getGiftName());
/*  74 */     shopScore.setScoreTime(new Date());
/*  75 */     shopScore.setStatus(true);
/*  76 */     shopScore.setUseStatus(true);
/*  77 */     shopScore.setScoreType(Integer.valueOf(ShopScore.ScoreTypes.ORDER_SCORE.getValue()));
/*  78 */     shopScore.setScore(Integer.valueOf(gift.getGiftScore().intValue() * count.intValue()));
/*  79 */     this.shopScoreMng.save(shopScore);
/*  80 */     member.setScore(Integer.valueOf(member.getScore().intValue() - gift.getGiftScore().intValue() * count.intValue()));
/*  81 */     return save(giftExchange);
/*     */   }
/*     */ 
/*     */   public GiftExchange update(GiftExchange bean)
/*     */   {
/*  86 */     Updater updater = new Updater(bean);
/*  87 */     GiftExchange entity = this.dao.updateByUpdater(updater);
/*  88 */     return entity;
/*     */   }
/*     */ 
/*     */   public GiftExchange deleteById(Long id)
/*     */   {
/*  93 */     GiftExchange bean = this.dao.deleteById(id);
/*  94 */     return bean;
/*     */   }
/*     */ 
/*     */   public GiftExchange[] deleteByIds(Long[] ids)
/*     */   {
/*  99 */     GiftExchange[] beans = new GiftExchange[ids.length];
/* 100 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 101 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 103 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(GiftExchangeDao dao)
/*     */   {
/* 110 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 118 */     this.dao.deleteByMemberId(memberId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.GiftExchangeMngImpl
 * JD-Core Version:    0.6.0
 */