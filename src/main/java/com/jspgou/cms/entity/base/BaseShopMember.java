/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseShopMember
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   public static String REF = "ShopMember";
/*  25 */   public static String PROP_STREET = "street";
/*  26 */   public static String PROP_MEMBER = "member";
/*  27 */   public static String PROP_WEBSITE = "website";
/*  28 */   public static String PROP_FAX = "fax";
/*  29 */   public static String PROP_AVATAR = "avatar";
/*  30 */   public static String PROP_REAL_NAME = "realName";
/*  31 */   public static String PROP_CITY = "city";
/*  32 */   public static String PROP_GROUP = "group";
/*  33 */   public static String PROP_BIRTHDAY = "birthday";
/*  34 */   public static String PROP_STATE = "state";
/*  35 */   public static String PROP_DISTRICT = "district";
/*  36 */   public static String PROP_GENDER = "gender";
/*  37 */   public static String PROP_SUBURB = "suburb";
/*  38 */   public static String PROP_MOBILE = "mobile";
/*  39 */   public static String PROP_FIRSTNAME = "firstname";
/*  40 */   public static String PROP_LASTNAME = "lastname";
/*  41 */   public static String PROP_ID = "id";
/*  42 */   public static String PROP_ZIP = "zip";
/*  43 */   public static String PROP_COMPANY = "company";
/*  44 */   public static String PROP_SCORE = "score";
/*  45 */   public static String PROP_TEL = "tel";
/*  46 */   public static String PROP_WECHAT_OPPENID = "wechatOppenid";
/*     */ 
/*  82 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String realName;
/*     */   private Boolean gender;
/*     */   private Integer score;
/*     */   private Integer freezeScore;
/*     */   private Double money;
/*     */   private Date birthday;
/*     */   private String tel;
/*     */   private String mobile;
/*     */   private String avatar;
/*     */   private Boolean marriage;
/*     */   private String company;
/*     */   private Boolean isCar;
/*     */   private String position;
/*     */   private String address;
/*     */   private String schoolTag;
/*     */   private Date schoolTagDate;
/*     */   private String favoriteBrand;
/*     */   private String favoriteStar;
/*     */   private String favoriteMovie;
/*     */   private String favoritePersonage;
/*     */   private String wechatOppenid;
/*     */   private Member member;
/*     */   private Website website;
/*     */   private ShopMemberGroup group;
/*     */   private ShopDictionary userDegree;
/*     */   private ShopDictionary familyMembers;
/*     */   private ShopDictionary incomeDesc;
/*     */   private ShopDictionary workSeniority;
/*     */   private ShopDictionary degree;
/*     */   private Set<Product> favorites;
/*     */ 
/*     */   public BaseShopMember()
/*     */   {
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMember(Long id)
/*     */   {
/*  58 */     setId(id);
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMember(Long id, Website website, ShopMemberGroup group, Integer score)
/*     */   {
/*  71 */     setId(id);
/*  72 */     setWebsite(website);
/*  73 */     setGroup(group);
/*  74 */     setScore(score);
/*  75 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 137 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 145 */     this.id = id;
/* 146 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getWechatOppenid() {
/* 150 */     return this.wechatOppenid;
/*     */   }
/*     */ 
/*     */   public void setWechatOppenid(String wechatOppenid) {
/* 154 */     this.wechatOppenid = wechatOppenid;
/*     */   }
/*     */ 
/*     */   public String getRealName()
/*     */   {
/* 163 */     return this.realName;
/*     */   }
/*     */ 
/*     */   public void setRealName(String realName)
/*     */   {
/* 171 */     this.realName = realName;
/*     */   }
/*     */ 
/*     */   public Boolean getGender()
/*     */   {
/* 177 */     return this.gender;
/*     */   }
/*     */ 
/*     */   public void setGender(Boolean gender)
/*     */   {
/* 185 */     this.gender = gender;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 191 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 199 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Integer getFreezeScore() {
/* 203 */     return this.freezeScore;
/*     */   }
/*     */ 
/*     */   public void setFreezeScore(Integer freezeScore) {
/* 207 */     this.freezeScore = freezeScore;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/* 211 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(Double money) {
/* 215 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public Date getBirthday()
/*     */   {
/* 222 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Date birthday)
/*     */   {
/* 229 */     this.birthday = birthday;
/*     */   }
/*     */ 
/*     */   public String getTel()
/*     */   {
/* 235 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel)
/*     */   {
/* 243 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 249 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile)
/*     */   {
/* 256 */     this.mobile = mobile;
/*     */   }
/*     */ 
/*     */   public Boolean getMarriage()
/*     */   {
/* 262 */     return this.marriage;
/*     */   }
/*     */ 
/*     */   public void setMarriage(Boolean marriage)
/*     */   {
/* 269 */     this.marriage = marriage;
/*     */   }
/*     */ 
/*     */   public String getCompany()
/*     */   {
/* 275 */     return this.company;
/*     */   }
/*     */ 
/*     */   public void setCompany(String company)
/*     */   {
/* 282 */     this.company = company;
/*     */   }
/*     */ 
/*     */   public String getAvatar()
/*     */   {
/* 288 */     return this.avatar;
/*     */   }
/*     */ 
/*     */   public void setAvatar(String avatar)
/*     */   {
/* 295 */     this.avatar = avatar;
/*     */   }
/*     */ 
/*     */   public Boolean getIsCar()
/*     */   {
/* 301 */     return this.isCar;
/*     */   }
/*     */ 
/*     */   public void setIsCar(Boolean isCar)
/*     */   {
/* 309 */     this.isCar = isCar;
/*     */   }
/*     */ 
/*     */   public String getPosition()
/*     */   {
/* 316 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(String position)
/*     */   {
/* 323 */     this.position = position;
/*     */   }
/*     */ 
/*     */   public String getSchoolTag()
/*     */   {
/* 329 */     return this.schoolTag;
/*     */   }
/*     */ 
/*     */   public void setSchoolTag(String schoolTag)
/*     */   {
/* 336 */     this.schoolTag = schoolTag;
/*     */   }
/*     */ 
/*     */   public Date getSchoolTagDate()
/*     */   {
/* 342 */     return this.schoolTagDate;
/*     */   }
/*     */ 
/*     */   public void setSchoolTagDate(Date schoolTagDate)
/*     */   {
/* 349 */     this.schoolTagDate = schoolTagDate;
/*     */   }
/*     */ 
/*     */   public String getFavoriteBrand()
/*     */   {
/* 355 */     return this.favoriteBrand;
/*     */   }
/*     */ 
/*     */   public void setFavoriteBrand(String favoriteBrand)
/*     */   {
/* 362 */     this.favoriteBrand = favoriteBrand;
/*     */   }
/*     */ 
/*     */   public String getFavoriteStar()
/*     */   {
/* 369 */     return this.favoriteStar;
/*     */   }
/*     */ 
/*     */   public void setFavoriteStar(String favoriteStar)
/*     */   {
/* 376 */     this.favoriteStar = favoriteStar;
/*     */   }
/*     */ 
/*     */   public String getFavoriteMovie()
/*     */   {
/* 383 */     return this.favoriteMovie;
/*     */   }
/*     */ 
/*     */   public void setFavoriteMovie(String favoriteMovie)
/*     */   {
/* 390 */     this.favoriteMovie = favoriteMovie;
/*     */   }
/*     */ 
/*     */   public String getFavoritePersonage()
/*     */   {
/* 397 */     return this.favoritePersonage;
/*     */   }
/*     */ 
/*     */   public void setFavoritePersonage(String favoritePersonage)
/*     */   {
/* 404 */     this.favoritePersonage = favoritePersonage;
/*     */   }
/*     */ 
/*     */   public Member getMember()
/*     */   {
/* 411 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member)
/*     */   {
/* 419 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 427 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 435 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public ShopMemberGroup getGroup()
/*     */   {
/* 443 */     return this.group;
/*     */   }
/*     */ 
/*     */   public void setGroup(ShopMemberGroup group)
/*     */   {
/* 451 */     this.group = group;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 456 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address) {
/* 460 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public int getHashCode() {
/* 464 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public void setHashCode(int hashCode) {
/* 468 */     this.hashCode = hashCode;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getUserDegree() {
/* 472 */     return this.userDegree;
/*     */   }
/*     */ 
/*     */   public void setUserDegree(ShopDictionary userDegree) {
/* 476 */     this.userDegree = userDegree;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getFamilyMembers() {
/* 480 */     return this.familyMembers;
/*     */   }
/*     */ 
/*     */   public void setFamilyMembers(ShopDictionary familyMembers) {
/* 484 */     this.familyMembers = familyMembers;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getIncomeDesc() {
/* 488 */     return this.incomeDesc;
/*     */   }
/*     */ 
/*     */   public void setIncomeDesc(ShopDictionary incomeDesc) {
/* 492 */     this.incomeDesc = incomeDesc;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getWorkSeniority() {
/* 496 */     return this.workSeniority;
/*     */   }
/*     */ 
/*     */   public void setWorkSeniority(ShopDictionary workSeniority) {
/* 500 */     this.workSeniority = workSeniority;
/*     */   }
/*     */ 
/*     */   public ShopDictionary getDegree() {
/* 504 */     return this.degree;
/*     */   }
/*     */ 
/*     */   public void setDegree(ShopDictionary degree) {
/* 508 */     this.degree = degree;
/*     */   }
/*     */ 
/*     */   public Set<Product> getFavorites()
/*     */   {
/* 515 */     return this.favorites;
/*     */   }
/*     */ 
/*     */   public void setFavorites(Set<Product> favorites)
/*     */   {
/* 523 */     this.favorites = favorites;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 530 */     if (obj == null) return false;
/* 531 */     if (!(obj instanceof ShopMember)) return false;
/*     */ 
/* 533 */     ShopMember shopMember = (ShopMember)obj;
/* 534 */     if ((getId() == null) || (shopMember.getId() == null)) return false;
/* 535 */     return getId().equals(shopMember.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 541 */     if (-2147483648 == this.hashCode) {
/* 542 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 544 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 545 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 548 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 554 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopMember
 * JD-Core Version:    0.6.0
 */