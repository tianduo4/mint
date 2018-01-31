/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseShopMember;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.Date;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ public class ShopMember extends BaseShopMember
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public ShopMember()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ShopMember(Long id)
/*     */   {
/*  26 */     super(id);
/*     */   }
/*     */ 
/*     */   public ShopMember(Long id, Website website, ShopMemberGroup group, Integer score)
/*     */   {
/*  42 */     super(id, 
/*  40 */       website, 
/*  41 */       group, 
/*  42 */       score);
/*     */   }
/*     */ 
/*     */   public void init() {
/*  46 */     if (getScore() == null)
/*  47 */       setScore(Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/*  52 */     return getMember() != null ? getMember().getCreateTime() : null;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled() {
/*  56 */     return getMember() != null ? getMember().getDisabled() : null;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/*  60 */     return getMember() != null ? getMember().getUsername() : "";
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  64 */     return getMember() != null ? getMember().getEmail() : "";
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  68 */     return getMember() != null ? getMember().getPassword() : "";
/*     */   }
/*     */ 
/*     */   public Long getLoginCount() {
/*  72 */     return Long.valueOf(getMember() != null ? getMember().getLoginCount().longValue() : 0L);
/*     */   }
/*     */ 
/*     */   public String getRegisterIp() {
/*  76 */     return getMember() != null ? getMember().getRegisterIp() : "";
/*     */   }
/*     */ 
/*     */   public Date getLastLoginTime() {
/*  80 */     return getMember() != null ? getMember().getLastLoginTime() : null;
/*     */   }
/*     */ 
/*     */   public String getLastLoginIp() {
/*  84 */     return getMember() != null ? getMember().getLastLoginIp() : "";
/*     */   }
/*     */ 
/*     */   public Date getCurrentLoginTime() {
/*  88 */     return getMember() != null ? getMember().getCurrentLoginTime() : null;
/*     */   }
/*     */ 
/*     */   public String getCurrentLoginIp() {
/*  92 */     return getMember() != null ? getMember().getCurrentLoginIp() : "";
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() {
/*  96 */     JSONObject json = new JSONObject();
/*  97 */     json.put("id", CommonUtils.parseId(getId()));
/*  98 */     json.put("username", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
/*  99 */     json.put("email", (getMember() != null) && (getMember().getUser() != null) ? CommonUtils.parseStr(getMember().getUser().getEmail()) : "");
/* 100 */     json.put("groupId", getGroup() == null ? "" : CommonUtils.parseLong(getGroup().getId()));
/* 101 */     json.put("groupName", getGroup() == null ? "" : CommonUtils.parseStr(getGroup().getName()));
/* 102 */     json.put("score", Integer.valueOf(CommonUtils.parseInteger(getScore())));
/* 103 */     json.put("realName", CommonUtils.parseStr(getRealName()));
/* 104 */     json.put("gender", CommonUtils.parseBoolean(getGender()));
/* 105 */     json.put("birthday", CommonUtils.parseDate(getBirthday(), DateUtils.COMMON_FORMAT_STR));
/* 106 */     json.put("marriage", CommonUtils.parseBoolean(getMarriage()));
/* 107 */     json.put("mobile", CommonUtils.parseStr(getMobile()));
/* 108 */     json.put("tel", CommonUtils.parseStr(getTel()));
/* 109 */     json.put("schoolTag", CommonUtils.parseStr(getSchoolTag()));
/* 110 */     json.put("schoolTagDate", CommonUtils.parseDate(getSchoolTagDate(), DateUtils.COMMON_FORMAT_SHORT));
/* 111 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/* 112 */     json.put("isCar", CommonUtils.parseBoolean(getIsCar()));
/* 113 */     json.put("position", CommonUtils.parseStr(getPosition()));
/* 114 */     json.put("userDegreeId", getUserDegree() == null ? "" : CommonUtils.parseLong(getUserDegree().getId()));
/* 115 */     json.put("userDegreeName", getUserDegree() == null ? "" : CommonUtils.parseStr(getUserDegree().getName()));
/* 116 */     json.put("degreeId", getDegree() == null ? "" : CommonUtils.parseLong(getDegree().getId()));
/* 117 */     json.put("degreeName", getDegree() == null ? "" : CommonUtils.parseStr(getDegree().getName()));
/* 118 */     json.put("incomeDescId", getIncomeDesc() == null ? "" : CommonUtils.parseLong(getIncomeDesc().getId()));
/* 119 */     json.put("incomeDescName", getIncomeDesc() == null ? "" : CommonUtils.parseStr(getIncomeDesc().getName()));
/* 120 */     json.put("workSeniorityId", getWorkSeniority() == null ? "" : CommonUtils.parseLong(getWorkSeniority().getId()));
/* 121 */     json.put("workSeniorityName", getWorkSeniority() == null ? "" : CommonUtils.parseStr(getWorkSeniority().getName()));
/* 122 */     json.put("familyMembersId", getFamilyMembers() == null ? "" : CommonUtils.parseLong(getFamilyMembers().getId()));
/* 123 */     json.put("familyMembersName", getFamilyMembers() == null ? "" : CommonUtils.parseStr(getFamilyMembers().getName()));
/* 124 */     json.put("avatar", CommonUtils.parseStr(getAvatar()));
/* 125 */     json.put("company", CommonUtils.parseStr(getCompany()));
/* 126 */     json.put("address", CommonUtils.parseStr(getAddress()));
/* 127 */     json.put("favoriteBrand", CommonUtils.parseStr(getFavoriteBrand()));
/* 128 */     json.put("favoriteStar", CommonUtils.parseStr(getFavoriteStar()));
/* 129 */     json.put("favoriteMovie", CommonUtils.parseStr(getFavoriteMovie()));
/* 130 */     json.put("loginTime", (getMember() != null) && (getMember().getUser() != null) ? CommonUtils.parseDate(getMember().getUser().getCurrentLoginTime(), DateUtils.COMMON_FORMAT_STR) : "");
/* 131 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopMember
 * JD-Core Version:    0.6.0
 */