/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.LogisticsText;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseLogistics
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Logistics";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_WEB_URL = "webUrl";
/*  23 */   public static String PROP_PRIORITY = "priority";
/*  24 */   public static String PROP_LOGO_PATH = "logoPath";
/*     */ 
/*  58 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String webUrl;
/*     */   private String logoPath;
/*     */   private Integer priority;
/*     */   private Boolean courier;
/*     */   private String imgUrl;
/*     */   private Integer evenSpacing;
/*     */   private Double fnt;
/*     */   private Double fnz;
/*     */   private Double fat;
/*     */   private Double faz;
/*     */   private Double fpt;
/*     */   private Double fpz;
/*     */   private Double snt;
/*     */   private Double snz;
/*     */   private Double sat;
/*     */   private Double saz;
/*     */   private Double spt;
/*     */   private Double spz;
/*     */   private Double fnw;
/*     */   private Double fnh;
/*     */   private Double faw;
/*     */   private Double fah;
/*     */   private Double fpw;
/*     */   private Double fph;
/*     */   private Double snw;
/*     */   private Double snh;
/*     */   private Double saw;
/*     */   private Double sah;
/*     */   private Double spw;
/*     */   private Double sph;
/*     */   private String fnwh;
/*     */   private String fawh;
/*     */   private String fpwh;
/*     */   private String snwh;
/*     */   private String sawh;
/*     */   private String spwh;
/*     */   private Set<LogisticsText> logisticsTextSet;
/*     */ 
/*     */   public BaseLogistics()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseLogistics(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseLogistics(Long id, String name, Integer priority)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setName(name);
/*  50 */     setPriority(priority);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 117 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 125 */     this.id = id;
/* 126 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 136 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 144 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getWebUrl()
/*     */   {
/* 152 */     return this.webUrl;
/*     */   }
/*     */ 
/*     */   public void setWebUrl(String webUrl)
/*     */   {
/* 160 */     this.webUrl = webUrl;
/*     */   }
/*     */ 
/*     */   public String getLogoPath()
/*     */   {
/* 168 */     return this.logoPath;
/*     */   }
/*     */ 
/*     */   public void setLogoPath(String logoPath)
/*     */   {
/* 176 */     this.logoPath = logoPath;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 184 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 192 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getCourier()
/*     */   {
/* 197 */     return this.courier;
/*     */   }
/*     */   public void setCourier(Boolean courier) {
/* 200 */     this.courier = courier;
/*     */   }
/*     */ 
/*     */   public String getImgUrl() {
/* 204 */     return this.imgUrl;
/*     */   }
/*     */ 
/*     */   public void setImgUrl(String imgUrl) {
/* 208 */     this.imgUrl = imgUrl;
/*     */   }
/*     */ 
/*     */   public Integer getEvenSpacing() {
/* 212 */     return this.evenSpacing;
/*     */   }
/*     */ 
/*     */   public void setEvenSpacing(Integer evenSpacing) {
/* 216 */     this.evenSpacing = evenSpacing;
/*     */   }
/*     */ 
/*     */   public Double getFnt() {
/* 220 */     return this.fnt;
/*     */   }
/*     */ 
/*     */   public void setFnt(Double fnt) {
/* 224 */     this.fnt = fnt;
/*     */   }
/*     */ 
/*     */   public Double getFnz()
/*     */   {
/* 229 */     return this.fnz;
/*     */   }
/*     */ 
/*     */   public void setFnz(Double fnz) {
/* 233 */     this.fnz = fnz;
/*     */   }
/*     */ 
/*     */   public Double getFat() {
/* 237 */     return this.fat;
/*     */   }
/*     */ 
/*     */   public void setFat(Double fat) {
/* 241 */     this.fat = fat;
/*     */   }
/*     */ 
/*     */   public Double getFaz() {
/* 245 */     return this.faz;
/*     */   }
/*     */ 
/*     */   public void setFaz(Double faz) {
/* 249 */     this.faz = faz;
/*     */   }
/*     */ 
/*     */   public Double getFpt() {
/* 253 */     return this.fpt;
/*     */   }
/*     */ 
/*     */   public void setFpt(Double fpt) {
/* 257 */     this.fpt = fpt;
/*     */   }
/*     */ 
/*     */   public Double getFpz() {
/* 261 */     return this.fpz;
/*     */   }
/*     */ 
/*     */   public void setFpz(Double fpz) {
/* 265 */     this.fpz = fpz;
/*     */   }
/*     */ 
/*     */   public Double getSnt() {
/* 269 */     return this.snt;
/*     */   }
/*     */ 
/*     */   public void setSnt(Double snt) {
/* 273 */     this.snt = snt;
/*     */   }
/*     */ 
/*     */   public Double getSnz() {
/* 277 */     return this.snz;
/*     */   }
/*     */ 
/*     */   public void setSnz(Double snz) {
/* 281 */     this.snz = snz;
/*     */   }
/*     */ 
/*     */   public Double getSat() {
/* 285 */     return this.sat;
/*     */   }
/*     */ 
/*     */   public void setSat(Double sat) {
/* 289 */     this.sat = sat;
/*     */   }
/*     */ 
/*     */   public Double getSaz() {
/* 293 */     return this.saz;
/*     */   }
/*     */ 
/*     */   public void setSaz(Double saz) {
/* 297 */     this.saz = saz;
/*     */   }
/*     */ 
/*     */   public Double getSpt() {
/* 301 */     return this.spt;
/*     */   }
/*     */ 
/*     */   public void setSpt(Double spt) {
/* 305 */     this.spt = spt;
/*     */   }
/*     */ 
/*     */   public Double getSpz() {
/* 309 */     return this.spz;
/*     */   }
/*     */ 
/*     */   public void setSpz(Double spz) {
/* 313 */     this.spz = spz;
/*     */   }
/*     */ 
/*     */   public Double getFnw()
/*     */   {
/* 318 */     return this.fnw;
/*     */   }
/*     */ 
/*     */   public Double getFnh() {
/* 322 */     return this.fnh;
/*     */   }
/*     */ 
/*     */   public Double getFaw() {
/* 326 */     return this.faw;
/*     */   }
/*     */ 
/*     */   public Double getFah() {
/* 330 */     return this.fah;
/*     */   }
/*     */ 
/*     */   public Double getFpw() {
/* 334 */     return this.fpw;
/*     */   }
/*     */ 
/*     */   public Double getFph() {
/* 338 */     return this.fph;
/*     */   }
/*     */ 
/*     */   public Double getSnw() {
/* 342 */     return this.snw;
/*     */   }
/*     */ 
/*     */   public Double getSnh() {
/* 346 */     return this.snh;
/*     */   }
/*     */ 
/*     */   public Double getSaw() {
/* 350 */     return this.saw;
/*     */   }
/*     */ 
/*     */   public Double getSah() {
/* 354 */     return this.sah;
/*     */   }
/*     */ 
/*     */   public Double getSpw() {
/* 358 */     return this.spw;
/*     */   }
/*     */ 
/*     */   public Double getSph() {
/* 362 */     return this.sph;
/*     */   }
/*     */ 
/*     */   public void setFnw(Double fnw) {
/* 366 */     this.fnw = fnw;
/*     */   }
/*     */ 
/*     */   public void setFnh(Double fnh) {
/* 370 */     this.fnh = fnh;
/*     */   }
/*     */ 
/*     */   public void setFaw(Double faw) {
/* 374 */     this.faw = faw;
/*     */   }
/*     */ 
/*     */   public void setFah(Double fah) {
/* 378 */     this.fah = fah;
/*     */   }
/*     */ 
/*     */   public void setFpw(Double fpw) {
/* 382 */     this.fpw = fpw;
/*     */   }
/*     */ 
/*     */   public void setFph(Double fph) {
/* 386 */     this.fph = fph;
/*     */   }
/*     */ 
/*     */   public void setSnw(Double snw) {
/* 390 */     this.snw = snw;
/*     */   }
/*     */ 
/*     */   public void setSnh(Double snh) {
/* 394 */     this.snh = snh;
/*     */   }
/*     */ 
/*     */   public void setSaw(Double saw) {
/* 398 */     this.saw = saw;
/*     */   }
/*     */ 
/*     */   public void setSah(Double sah) {
/* 402 */     this.sah = sah;
/*     */   }
/*     */ 
/*     */   public void setSpw(Double spw) {
/* 406 */     this.spw = spw;
/*     */   }
/*     */ 
/*     */   public void setSph(Double sph) {
/* 410 */     this.sph = sph;
/*     */   }
/*     */ 
/*     */   public String getFnwh() {
/* 414 */     return this.fnwh;
/*     */   }
/*     */   public void setFnwh(String Fnwh) {
/* 417 */     this.fnwh = Fnwh;
/*     */   }
/*     */ 
/*     */   public String getFawh() {
/* 421 */     return this.fawh;
/*     */   }
/*     */   public void setFawh(String fawh) {
/* 424 */     this.fawh = fawh;
/*     */   }
/*     */   public String getFpwh() {
/* 427 */     return this.fpwh;
/*     */   }
/*     */   public void setFpwh(String fpwh) {
/* 430 */     this.fpwh = fpwh;
/*     */   }
/*     */   public String getSnwh() {
/* 433 */     return this.snwh;
/*     */   }
/*     */   public void setSnwh(String snwh) {
/* 436 */     this.snwh = snwh;
/*     */   }
/*     */ 
/*     */   public String getSawh() {
/* 440 */     return this.sawh;
/*     */   }
/*     */   public void setSawh(String sawh) {
/* 443 */     this.sawh = sawh;
/*     */   }
/*     */   public String getSpwh() {
/* 446 */     return this.spwh;
/*     */   }
/*     */   public void setSpwh(String spwh) {
/* 449 */     this.spwh = spwh;
/*     */   }
/*     */ 
/*     */   public Set<LogisticsText> getLogisticsTextSet()
/*     */   {
/* 456 */     return this.logisticsTextSet;
/*     */   }
/*     */ 
/*     */   public void setLogisticsTextSet(Set<LogisticsText> logisticsTextSet)
/*     */   {
/* 464 */     this.logisticsTextSet = logisticsTextSet;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 471 */     if (obj == null) return false;
/* 472 */     if (!(obj instanceof Logistics)) return false;
/*     */ 
/* 474 */     Logistics logistics = (Logistics)obj;
/* 475 */     if ((getId() == null) || (logistics.getId() == null)) return false;
/* 476 */     return getId().equals(logistics.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 482 */     if (-2147483648 == this.hashCode) {
/* 483 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 485 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 486 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 489 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 495 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseLogistics
 * JD-Core Version:    0.6.0
 */