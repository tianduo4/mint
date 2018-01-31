/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopMemberDao;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.entity.base.BaseShopMember;
/*     */ import com.jspgou.cms.manager.CartMng;
/*     */ import com.jspgou.cms.manager.CollectMng;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.CouponMng;
/*     */ import com.jspgou.cms.manager.DiscussMng;
/*     */ import com.jspgou.cms.manager.GiftExchangeMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopMoneyMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.AdminMng;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ShopMemberMngImpl
/*     */   implements ShopMemberMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private CartMng cartMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private MemberMng memberMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng shopMemberGroupMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @Autowired
/*     */   private CollectMng collectMng;
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng consultMng;
/*     */ 
/*     */   @Autowired
/*     */   private DiscussMng discussMng;
/*     */ 
/*     */   @Autowired
/*     */   private GiftExchangeMng giftExchangeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMoneyMng shopMoneyMng;
/*     */ 
/*     */   @Autowired
/*     */   private CouponMng couponMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private AdminMng adminMng;
/*     */ 
/*     */   public ShopMember getByUserId(Long webId, Long userId)
/*     */   {
/*  45 */     Member coreMember = this.memberMng.getByUserId(webId, userId);
/*  46 */     if (coreMember != null) {
/*  47 */       return findById(coreMember.getId());
/*     */     }
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   public ShopMember getByUsername(Long webId, String username)
/*     */   {
/*  55 */     Member coreMember = this.memberMng.getByUsername(webId, username);
/*  56 */     if (coreMember == null) {
/*  57 */       return null;
/*     */     }
/*  59 */     return findById(coreMember.getId());
/*     */   }
/*     */ 
/*     */   public ShopMember register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Long webId, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, ShopMember member)
/*     */   {
/*  67 */     Website web = this.websiteMng.findById(webId);
/*  68 */     if (member == null) {
/*  69 */       member = new ShopMember();
/*     */     }
/*  71 */     Member coreMember = this.memberMng.register(username, password, email, active, activationCode, ip, 
/*  72 */       disabled, web);
/*  73 */     member.setMember(coreMember);
/*  74 */     member.setWebsite(web);
/*  75 */     member.setFreezeScore(Integer.valueOf(0));
/*  76 */     if (groupId != null) {
/*  77 */       member.setGroup(this.shopMemberGroupMng.findById(groupId));
/*     */     }
/*     */ 
/*  80 */     if (userDegreeId != null) {
/*  81 */       member.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
/*     */     }
/*  83 */     if (degreeId != null) {
/*  84 */       member.setDegree(this.shopDictionaryMng.findById(degreeId));
/*     */     }
/*  86 */     if (incomeDescId != null) {
/*  87 */       member.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
/*     */     }
/*  89 */     if (workSeniorityId != null) {
/*  90 */       member.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
/*     */     }
/*  92 */     if (familyMembersId != null) {
/*  93 */       member.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
/*     */     }
/*  95 */     return save(member);
/*     */   }
/*     */ 
/*     */   public ShopMember join(String username, Long webId, Long groupId)
/*     */   {
/* 100 */     Website web = this.websiteMng.findById(webId);
/* 101 */     Member coreMember = this.memberMng.join(username, web);
/* 102 */     ShopMember member = new ShopMember();
/* 103 */     member.setMember(coreMember);
/* 104 */     member.setWebsite(web);
/* 105 */     member.setGroup(this.shopMemberGroupMng.findById(groupId));
/* 106 */     return save(member);
/*     */   }
/*     */ 
/*     */   public ShopMember join(Long userId, Long webId, ShopMemberGroup group)
/*     */   {
/* 111 */     User user = this.userMng.findById(userId);
/* 112 */     return join(user, webId, group);
/*     */   }
/*     */ 
/*     */   public ShopMember join(User user, Long webId, ShopMemberGroup group)
/*     */   {
/* 117 */     Website web = this.websiteMng.findById(webId);
/* 118 */     Member coreMember = this.memberMng.join(user, web);
/* 119 */     ShopMember member = new ShopMember();
/* 120 */     member.setMember(coreMember);
/* 121 */     member.setWebsite(web);
/* 122 */     member.setGroup(group);
/* 123 */     return save(member);
/*     */   }
/*     */ 
/*     */   public ShopMember register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Long webId, Long groupId)
/*     */   {
/* 129 */     return register(username, password, email, active, activationCode, ip, disabled, webId, 
/* 130 */       groupId, null, null, null, null, null, new ShopMember());
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, int pageNo, int pageSize) {
/* 136 */     Pagination page = this.dao.getPage(webId, pageNo, pageSize);
/* 137 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, int pageNo, int pageSize, String username) {
/* 143 */     Pagination page = this.dao.getPage(webId, pageNo, pageSize, username);
/* 144 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public ShopMember findById(Long id) {
/* 150 */     ShopMember entity = this.dao.findById(id);
/* 151 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopMember save(ShopMember bean)
/*     */   {
/* 156 */     bean.init();
/* 157 */     if (bean.getGroup() == null) {
/* 158 */       bean.setGroup(this.shopMemberGroupMng.findGroupByScore(bean.getWebsite()
/* 159 */         .getId(), bean.getScore().intValue()));
/*     */     }
/* 161 */     this.dao.save(bean);
/* 162 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopMember update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled)
/*     */   {
/* 172 */     Updater updater = new Updater(bean);
/* 173 */     ShopMember entity = this.dao.updateByUpdater(updater);
/*     */ 
/* 176 */     this.userMng.updateUser(entity.getMember().getUser().getId(), password, 
/* 177 */       email);
/*     */ 
/* 179 */     if (disabled != null) {
/* 180 */       entity.getMember().setDisabled(disabled);
/*     */     }
/* 182 */     if (groupId != null) {
/* 183 */       entity.setGroup(this.shopMemberGroupMng.findById(groupId));
/*     */     }
/*     */ 
/* 186 */     if (userDegreeId != null) {
/* 187 */       entity.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
/*     */     }
/* 189 */     if (degreeId != null) {
/* 190 */       entity.setDegree(this.shopDictionaryMng.findById(degreeId));
/*     */     }
/* 192 */     if (incomeDescId != null) {
/* 193 */       entity.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
/*     */     }
/* 195 */     if (workSeniorityId != null) {
/* 196 */       entity.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
/*     */     }
/* 198 */     if (familyMembersId != null) {
/* 199 */       entity.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
/*     */     }
/*     */ 
/* 204 */     this.dao.updateByUpdater(updater);
/*     */ 
/* 206 */     entity.setGroup(this.shopMemberGroupMng.findGroupByScore(entity.getWebsite()
/* 207 */       .getId(), entity.getScore().intValue()));
/* 208 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopMember update(ShopMember bean)
/*     */   {
/* 213 */     Updater updater = new Updater(bean);
/* 214 */     this.dao.updateByUpdater(updater);
/* 215 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopMember updateScore(ShopMember bean, Integer score)
/*     */   {
/* 220 */     Integer scoreDb = this.dao.findById(bean.getId()).getScore();
/* 221 */     Integer scoreTotal = Integer.valueOf(scoreDb.intValue() + score.intValue());
/* 222 */     bean.setScore(scoreTotal);
/* 223 */     bean.setGroup(this.shopMemberGroupMng.findGroupByScore(bean.getWebsite()
/* 224 */       .getId(), scoreTotal.intValue()));
/* 225 */     Updater updater = new Updater(bean);
/* 226 */     this.dao.updateByUpdater(updater);
/* 227 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopMember update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId)
/*     */   {
/* 237 */     ShopMember entity = findById(bean.getId());
/*     */ 
/* 239 */     if (userDegreeId != null) {
/* 240 */       entity.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
/*     */     }
/* 242 */     if (degreeId != null) {
/* 243 */       entity.setDegree(this.shopDictionaryMng.findById(degreeId));
/*     */     }
/* 245 */     if (incomeDescId != null) {
/* 246 */       entity.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
/*     */     }
/* 248 */     if (workSeniorityId != null) {
/* 249 */       entity.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
/*     */     }
/* 251 */     if (familyMembersId != null) {
/* 252 */       entity.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
/*     */     }
/* 254 */     Updater updater = new Updater(bean);
/* 255 */     updater.exclude(BaseShopMember.PROP_SCORE);
/* 256 */     this.dao.updateByUpdater(updater);
/* 257 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopMember deleteById(Long id)
/*     */   {
/* 263 */     this.shopMemberAddressMng.deleteByMemberId(id);
/*     */ 
/* 265 */     this.cartMng.deleteById(id);
/*     */ 
/* 267 */     this.collectMng.deleteByMemberId(id);
/*     */ 
/* 269 */     this.consultMng.deleteByMemberId(id);
/*     */ 
/* 271 */     this.discussMng.deleteByMemberId(id);
/*     */ 
/* 273 */     this.shopScoreMng.deleteByMemberId(id);
/*     */ 
/* 275 */     this.giftExchangeMng.deleteByMemberId(id);
/*     */ 
/* 277 */     this.couponMng.deleteByMemberId(id);
/*     */ 
/* 281 */     this.shopMoneyMng.deleteByMemberId(id);
/*     */ 
/* 283 */     ShopMember bean = this.dao.deleteById(id);
/*     */ 
/* 288 */     if (this.adminMng.getByUsername(bean.getUsername()) == null) {
/* 289 */       this.userMng.deleteById(bean.getMember().getUser().getId());
/*     */     }
/* 291 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopMember[] deleteByIds(Long[] ids)
/*     */   {
/* 296 */     ShopMember[] beans = new ShopMember[ids.length];
/* 297 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 298 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 300 */     return beans;
/*     */   }
/*     */ 
/*     */   public ShopMember findUsername(String username) {
/* 304 */     return this.dao.findUsername(username);
/*     */   }
/*     */   @Transactional(readOnly=true)
/*     */   public ShopMember findByUsername(String username) {
/* 309 */     ShopMember entity = this.dao.findByUsername(username);
/* 310 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<ShopMember> getList(String realName, Long groupId) {
/* 314 */     return this.dao.getList(realName, groupId);
/*     */   }
/*     */ 
/*     */   public boolean usernameNotExist(String username) {
/* 318 */     return this.dao.countByUsername(username) <= 0;
/*     */   }
/*     */ 
/*     */   public Long getMemberCount(Long webId)
/*     */   {
/* 324 */     return this.dao.getMemberCount(webId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopMemberMngImpl
 * JD-Core Version:    0.6.0
 */