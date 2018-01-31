/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopMemberAddress
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopMemberAddress";
/*  24 */   public static String PROP_PHONE = "phone";
/*  25 */   public static String PROP_MEMBER = "member";
/*  26 */   public static String PROP_IS_DEFAULT = "isDefault";
/*  27 */   public static String PROP_PROVINCE = "province";
/*  28 */   public static String PROP_COUNTRY = "country";
/*  29 */   public static String PROP_CITY = "city";
/*  30 */   public static String PROP_PROVINCEID = "provinceId";
/*  31 */   public static String PROP_COUNTRYID = "countryId";
/*  32 */   public static String PROP_CITYID = "cityId";
/*  33 */   public static String PROP_AREA_CODE = "areaCode";
/*  34 */   public static String PROP_DETAILADDRESS = "detailaddress";
/*  35 */   public static String PROP_POST_CODE = "postCode";
/*  36 */   public static String PROP_USERNAME = "username";
/*  37 */   public static String PROP_GENDER = "gender";
/*  38 */   public static String PROP_EXT_NUMBER = "extNumber";
/*  39 */   public static String PROP_ID = "id";
/*  40 */   public static String PROP_TEL = "tel";
/*     */ 
/*  90 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String username;
/*     */   private boolean gender;
/*     */   private String detailaddress;
/*     */   private String postCode;
/*     */   private String tel;
/*     */   private String areaCode;
/*     */   private String phone;
/*     */   private String extNumber;
/*     */   private boolean isDefault;
/*     */   private ShopMember member;
/*     */   private String province;
/*     */   private String city;
/*     */   private String country;
/*     */   private Long provinceId;
/*     */   private Long cityId;
/*     */   private Long countryId;
/*     */ 
/*     */   public BaseShopMemberAddress()
/*     */   {
/*  45 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMemberAddress(Long id)
/*     */   {
/*  52 */     setId(id);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopMemberAddress(Long id, ShopMember member, String province, String city, String country, Long provinceId, Long cityId, Long countryId, String username, String detailaddress, boolean isDefault)
/*     */   {
/*  72 */     setId(id);
/*  73 */     setMember(member);
/*  74 */     setProvince(province);
/*  75 */     setCity(city);
/*  76 */     setCountry(country);
/*  77 */     setProvinceId(provinceId);
/*  78 */     setCityId(cityId);
/*  79 */     setCountryId(countryId);
/*  80 */     setUsername(username);
/*  81 */     setDetailaddress(detailaddress);
/*  82 */     setIsDefault(isDefault);
/*  83 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 124 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 132 */     this.id = id;
/* 133 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 143 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 151 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public boolean getGender()
/*     */   {
/* 159 */     return this.gender;
/*     */   }
/*     */ 
/*     */   public void setGender(boolean gender)
/*     */   {
/* 167 */     this.gender = gender;
/*     */   }
/*     */ 
/*     */   public String getDetailaddress()
/*     */   {
/* 175 */     return this.detailaddress;
/*     */   }
/*     */ 
/*     */   public void setDetailaddress(String detailaddress)
/*     */   {
/* 183 */     this.detailaddress = detailaddress;
/*     */   }
/*     */ 
/*     */   public String getPostCode()
/*     */   {
/* 191 */     return this.postCode;
/*     */   }
/*     */ 
/*     */   public void setPostCode(String postCode)
/*     */   {
/* 199 */     this.postCode = postCode;
/*     */   }
/*     */ 
/*     */   public String getTel()
/*     */   {
/* 207 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel)
/*     */   {
/* 215 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getAreaCode()
/*     */   {
/* 223 */     return this.areaCode;
/*     */   }
/*     */ 
/*     */   public void setAreaCode(String areaCode)
/*     */   {
/* 231 */     this.areaCode = areaCode;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 239 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String phone)
/*     */   {
/* 247 */     this.phone = phone;
/*     */   }
/*     */ 
/*     */   public String getExtNumber()
/*     */   {
/* 255 */     return this.extNumber;
/*     */   }
/*     */ 
/*     */   public void setExtNumber(String extNumber)
/*     */   {
/* 263 */     this.extNumber = extNumber;
/*     */   }
/*     */ 
/*     */   public boolean getIsDefault()
/*     */   {
/* 271 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(boolean isDefault)
/*     */   {
/* 279 */     this.isDefault = isDefault;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 287 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 295 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public String getProvince()
/*     */   {
/* 303 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province)
/*     */   {
/* 311 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getCity()
/*     */   {
/* 319 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String city)
/*     */   {
/* 327 */     this.city = city;
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 335 */     return this.country;
/*     */   }
/*     */ 
/*     */   public void setCountry(String country)
/*     */   {
/* 343 */     this.country = country;
/*     */   }
/*     */ 
/*     */   public Long getProvinceId()
/*     */   {
/* 351 */     return this.provinceId;
/*     */   }
/*     */ 
/*     */   public void setProvinceId(Long provinceId)
/*     */   {
/* 359 */     this.provinceId = provinceId;
/*     */   }
/*     */ 
/*     */   public Long getCityId()
/*     */   {
/* 367 */     return this.cityId;
/*     */   }
/*     */ 
/*     */   public void setCityId(Long cityId)
/*     */   {
/* 375 */     this.cityId = cityId;
/*     */   }
/*     */ 
/*     */   public Long getCountryId()
/*     */   {
/* 383 */     return this.countryId;
/*     */   }
/*     */ 
/*     */   public void setCountryId(Long countryId)
/*     */   {
/* 391 */     this.countryId = countryId;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 397 */     if (obj == null) return false;
/* 398 */     if (!(obj instanceof ShopMemberAddress)) return false;
/*     */ 
/* 400 */     ShopMemberAddress shopMemberAddress = (ShopMemberAddress)obj;
/* 401 */     if ((getId() == null) || (shopMemberAddress.getId() == null)) return false;
/* 402 */     return getId().equals(shopMemberAddress.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 408 */     if (-2147483648 == this.hashCode) {
/* 409 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 411 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 412 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 415 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 421 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopMemberAddress
 * JD-Core Version:    0.6.0
 */