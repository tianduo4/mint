/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseWebsite
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   public static String REF = "Website";
/*  25 */   public static String PROP_CONTROL_NAME_MINLEN = "controlNameMinlen";
/*  26 */   public static String PROP_RGT = "rgt";
/*  27 */   public static String PROP_CONTROL_ADMIN_IPS = "controlAdminIps";
/*  28 */   public static String PROP_LOCALE_ADMIN = "localeAdmin";
/*  29 */   public static String PROP_CREATE_TIME = "createTime";
/*  30 */   public static String PROP_CLOSE = "close";
/*  31 */   public static String PROP_CURRENT_SYSTEM = "currentSystem";
/*  32 */   public static String PROP_SUFFIX = "suffix";
/*  33 */   public static String PROP_FRONT_CONTENT_TYPE = "frontContentType";
/*  34 */   public static String PROP_PASSWORD = "password";
/*  35 */   public static String PROP_MOBILE = "mobile";
/*  36 */   public static String PROP_DOMAIN_ALIAS = "domainAlias";
/*  37 */   public static String PROP_LOCALE_FRONT = "localeFront";
/*  38 */   public static String PROP_NAME = "name";
/*  39 */   public static String PROP_CONTROL_FRONT_IPS = "controlFrontIps";
/*  40 */   public static String PROP_GLOBAL = "global";
/*  41 */   public static String PROP_HOST = "host";
/*  42 */   public static String PROP_DOMAIN = "domain";
/*  43 */   public static String PROP_RES_PATH = "resPath";
/*  44 */   public static String PROP_BASE_DOMAIN = "baseDomain";
/*  45 */   public static String PROP_PHONE = "phone";
/*  46 */   public static String PROP_CLOSE_REASON = "closeReason";
/*  47 */   public static String PROP_COPYRIGHT = "copyright";
/*  48 */   public static String PROP_RECORD_CODE = "recordCode";
/*  49 */   public static String PROP_EMAIL = "email";
/*  50 */   public static String PROP_ENCODING = "encoding";
/*  51 */   public static String PROP_FRONT_ENCODING = "frontEncoding";
/*  52 */   public static String PROP_SHORT_NAME = "shortName";
/*  53 */   public static String PROP_LFT = "lft";
/*  54 */   public static String PROP_PARENT = "parent";
/*  55 */   public static String PROP_COMPANY = "company";
/*  56 */   public static String PROP_PERSONAL = "personal";
/*  57 */   public static String PROP_CONTROL_RESERVED = "controlReserved";
/*  58 */   public static String PROP_ADMIN = "admin";
/*  59 */   public static String PROP_ID = "id";
/*  60 */   public static String PROP_USERNAME = "username";
/*  61 */   public static String PROP_RELATIVE_PATH = "relativePath";
/*  62 */   public static String PROP_UPLOAD_FTP = "uploadFtp";
/*  63 */   public static String PROP_RESOURCES_PATH = "resourcesPath";
/*     */ 
/* 114 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String domain;
/*     */   private String name;
/*     */   private String additionalTitle;
/*     */   private String currentSystem;
/*     */   private String suffix;
/*     */   private Integer lft;
/*     */   private Integer rgt;
/*     */   private Date createTime;
/*     */   private String baseDomain;
/*     */   private String domainAlias;
/*     */   private String shortName;
/*     */   private String closeReason;
/*     */   private Boolean close;
/*     */   private Boolean relativePath;
/*     */   private String frontEncoding;
/*     */   private String frontContentType;
/*     */   private String localeFront;
/*     */   private String localeAdmin;
/*     */   private String controlReserved;
/*     */   private Integer controlNameMinlen;
/*     */   private String controlFrontIps;
/*     */   private String controlAdminIps;
/*     */   private String company;
/*     */   private String copyright;
/*     */   private String recordCode;
/*     */   private String email;
/*     */   private String phone;
/*     */   private String mobile;
/*     */   private String version;
/*     */   private Boolean restart;
/*     */   private Boolean pageSync;
/*     */   private Boolean resouceSync;
/*     */   EmailSender emailSender;
/*     */   private Admin admin;
/*     */   private Website parent;
/*     */   private Global global;
/*     */   private Set<Website> child;
/*     */   private String tplMobileSolution;
/*     */   private String tplSolution;
/*     */   private String resourcesPath;
/*     */   private Ftp uploadFtp;
/*     */   private Ftp syncPageFtp;
/*     */   private Map<String, String> attr;
/*     */   private Map<String, MessageTemplate> messages;
/*     */ 
/*     */   public BaseWebsite()
/*     */   {
/*  66 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebsite(Long id)
/*     */   {
/*  73 */     setId(id);
/*  74 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebsite(Long id, Global global, String domain, String name, String currentSystem, String suffix, Integer lft, Integer rgt, Date createTime, Boolean close, Boolean relativePath, String frontEncoding, String frontContentType, String localeFront, String localeAdmin, Integer controlNameMinlen, String company, String copyright, String recordCode, String email, String phone, String mobile, String resourcesPath)
/*     */   {
/*  86 */     setId(id);
/*  87 */     setGlobal(global);
/*  88 */     setDomain(domain);
/*  89 */     setName(name);
/*  90 */     setCurrentSystem(currentSystem);
/*  91 */     setSuffix(suffix);
/*  92 */     setLft(lft);
/*  93 */     setRgt(rgt);
/*  94 */     setCreateTime(createTime);
/*  95 */     setClose(close);
/*  96 */     setRelativePath(relativePath);
/*  97 */     setFrontEncoding(frontEncoding);
/*  98 */     setFrontContentType(frontContentType);
/*  99 */     setLocaleFront(localeFront);
/* 100 */     setLocaleAdmin(localeAdmin);
/* 101 */     setControlNameMinlen(controlNameMinlen);
/* 102 */     setCompany(company);
/* 103 */     setCopyright(copyright);
/* 104 */     setRecordCode(recordCode);
/* 105 */     setEmail(email);
/* 106 */     setPhone(phone);
/* 107 */     setMobile(mobile);
/* 108 */     setResourcesPath(resourcesPath);
/* 109 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Boolean getPageSync()
/*     */   {
/* 168 */     return this.pageSync;
/*     */   }
/*     */ 
/*     */   public void setPageSync(Boolean pageSync) {
/* 172 */     this.pageSync = pageSync;
/*     */   }
/*     */ 
/*     */   public Boolean getResouceSync() {
/* 176 */     return this.resouceSync;
/*     */   }
/*     */ 
/*     */   public void setResouceSync(Boolean resouceSync) {
/* 180 */     this.resouceSync = resouceSync;
/*     */   }
/*     */ 
/*     */   public Ftp getSyncPageFtp() {
/* 184 */     return this.syncPageFtp;
/*     */   }
/*     */ 
/*     */   public void setSyncPageFtp(Ftp syncPageFtp) {
/* 188 */     this.syncPageFtp = syncPageFtp;
/*     */   }
/*     */ 
/*     */   public String getResourcesPath()
/*     */   {
/* 193 */     return this.resourcesPath;
/*     */   }
/*     */ 
/*     */   public void setResourcesPath(String resourcesPath) {
/* 197 */     this.resourcesPath = resourcesPath;
/*     */   }
/*     */ 
/*     */   public Long getId() {
/* 201 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/* 205 */     this.id = id;
/* 206 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getDomain() {
/* 210 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(String domain) {
/* 214 */     this.domain = domain;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 218 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 222 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getAdditionalTitle() {
/* 226 */     return this.additionalTitle;
/*     */   }
/*     */ 
/*     */   public void setAdditionalTitle(String additionalTitle) {
/* 230 */     this.additionalTitle = additionalTitle;
/*     */   }
/*     */ 
/*     */   public String getCurrentSystem() {
/* 234 */     return this.currentSystem;
/*     */   }
/*     */ 
/*     */   public void setCurrentSystem(String currentSystem) {
/* 238 */     this.currentSystem = currentSystem;
/*     */   }
/*     */ 
/*     */   public String getSuffix() {
/* 242 */     return this.suffix;
/*     */   }
/*     */ 
/*     */   public void setSuffix(String suffix) {
/* 246 */     this.suffix = suffix;
/*     */   }
/*     */ 
/*     */   public Integer getLft() {
/* 250 */     return this.lft;
/*     */   }
/*     */ 
/*     */   public void setLft(Integer lft) {
/* 254 */     this.lft = lft;
/*     */   }
/*     */ 
/*     */   public Integer getRgt() {
/* 258 */     return this.rgt;
/*     */   }
/*     */ 
/*     */   public void setRgt(Integer rgt) {
/* 262 */     this.rgt = rgt;
/*     */   }
/*     */ 
/*     */   public Ftp getUploadFtp() {
/* 266 */     return this.uploadFtp;
/*     */   }
/*     */ 
/*     */   public void setUploadFtp(Ftp uploadFtp) {
/* 270 */     this.uploadFtp = uploadFtp;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime() {
/* 274 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/* 278 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public String getBaseDomain() {
/* 282 */     return this.baseDomain;
/*     */   }
/*     */ 
/*     */   public void setBaseDomain(String baseDomain) {
/* 286 */     this.baseDomain = baseDomain;
/*     */   }
/*     */ 
/*     */   public String getDomainAlias() {
/* 290 */     return this.domainAlias;
/*     */   }
/*     */ 
/*     */   public void setDomainAlias(String domainAlias) {
/* 294 */     this.domainAlias = domainAlias;
/*     */   }
/*     */ 
/*     */   public String getShortName() {
/* 298 */     return this.shortName;
/*     */   }
/*     */ 
/*     */   public void setShortName(String shortName) {
/* 302 */     this.shortName = shortName;
/*     */   }
/*     */ 
/*     */   public String getCloseReason() {
/* 306 */     return this.closeReason;
/*     */   }
/*     */ 
/*     */   public void setCloseReason(String closeReason) {
/* 310 */     this.closeReason = closeReason;
/*     */   }
/*     */ 
/*     */   public Boolean getClose() {
/* 314 */     return this.close;
/*     */   }
/*     */ 
/*     */   public void setClose(Boolean close) {
/* 318 */     this.close = close;
/*     */   }
/*     */ 
/*     */   public Boolean getRelativePath() {
/* 322 */     return this.relativePath;
/*     */   }
/*     */ 
/*     */   public void setRelativePath(Boolean relativePath) {
/* 326 */     this.relativePath = relativePath;
/*     */   }
/*     */ 
/*     */   public String getFrontEncoding() {
/* 330 */     return this.frontEncoding;
/*     */   }
/*     */ 
/*     */   public void setFrontEncoding(String frontEncoding) {
/* 334 */     this.frontEncoding = frontEncoding;
/*     */   }
/*     */ 
/*     */   public String getFrontContentType() {
/* 338 */     return this.frontContentType;
/*     */   }
/*     */ 
/*     */   public void setFrontContentType(String frontContentType) {
/* 342 */     this.frontContentType = frontContentType;
/*     */   }
/*     */ 
/*     */   public String getLocaleFront() {
/* 346 */     return this.localeFront;
/*     */   }
/*     */ 
/*     */   public void setLocaleFront(String localeFront) {
/* 350 */     this.localeFront = localeFront;
/*     */   }
/*     */ 
/*     */   public String getLocaleAdmin() {
/* 354 */     return this.localeAdmin;
/*     */   }
/*     */ 
/*     */   public void setLocaleAdmin(String localeAdmin) {
/* 358 */     this.localeAdmin = localeAdmin;
/*     */   }
/*     */ 
/*     */   public String getControlReserved() {
/* 362 */     return this.controlReserved;
/*     */   }
/*     */ 
/*     */   public void setControlReserved(String controlReserved) {
/* 366 */     this.controlReserved = controlReserved;
/*     */   }
/*     */ 
/*     */   public Integer getControlNameMinlen() {
/* 370 */     return this.controlNameMinlen;
/*     */   }
/*     */ 
/*     */   public void setControlNameMinlen(Integer controlNameMinlen) {
/* 374 */     this.controlNameMinlen = controlNameMinlen;
/*     */   }
/*     */ 
/*     */   public String getControlFrontIps() {
/* 378 */     return this.controlFrontIps;
/*     */   }
/*     */ 
/*     */   public void setControlFrontIps(String controlFrontIps) {
/* 382 */     this.controlFrontIps = controlFrontIps;
/*     */   }
/*     */ 
/*     */   public String getControlAdminIps() {
/* 386 */     return this.controlAdminIps;
/*     */   }
/*     */ 
/*     */   public void setControlAdminIps(String controlAdminIps) {
/* 390 */     this.controlAdminIps = controlAdminIps;
/*     */   }
/*     */ 
/*     */   public String getCompany() {
/* 394 */     return this.company;
/*     */   }
/*     */ 
/*     */   public void setCompany(String company) {
/* 398 */     this.company = company;
/*     */   }
/*     */ 
/*     */   public String getCopyright() {
/* 402 */     return this.copyright;
/*     */   }
/*     */ 
/*     */   public void setCopyright(String copyright) {
/* 406 */     this.copyright = copyright;
/*     */   }
/*     */ 
/*     */   public String getRecordCode() {
/* 410 */     return this.recordCode;
/*     */   }
/*     */ 
/*     */   public void setRecordCode(String recordCode) {
/* 414 */     this.recordCode = recordCode;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 418 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 422 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/* 426 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String phone) {
/* 430 */     this.phone = phone;
/*     */   }
/*     */ 
/*     */   public String getMobile() {
/* 434 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile) {
/* 438 */     this.mobile = mobile;
/*     */   }
/*     */ 
/*     */   public EmailSender getEmailSender() {
/* 442 */     return this.emailSender;
/*     */   }
/*     */ 
/*     */   public void setEmailSender(EmailSender emailSender) {
/* 446 */     this.emailSender = emailSender;
/*     */   }
/*     */ 
/*     */   public Admin getAdmin() {
/* 450 */     return this.admin;
/*     */   }
/*     */ 
/*     */   public void setAdmin(Admin admin) {
/* 454 */     this.admin = admin;
/*     */   }
/*     */ 
/*     */   public Website getParent() {
/* 458 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(Website parent) {
/* 462 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public Global getGlobal() {
/* 466 */     return this.global;
/*     */   }
/*     */ 
/*     */   public void setGlobal(Global global) {
/* 470 */     this.global = global;
/*     */   }
/*     */ 
/*     */   public Set<Website> getChild() {
/* 474 */     return this.child;
/*     */   }
/*     */ 
/*     */   public void setChild(Set<Website> child) {
/* 478 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr() {
/* 482 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr) {
/* 486 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public Map<String, MessageTemplate> getMessages() {
/* 490 */     return this.messages;
/*     */   }
/*     */ 
/*     */   public void setMessages(Map<String, MessageTemplate> messages) {
/* 494 */     this.messages = messages;
/*     */   }
/*     */ 
/*     */   public String getTplMobileSolution() {
/* 498 */     return this.tplMobileSolution;
/*     */   }
/*     */ 
/*     */   public void setTplMobileSolution(String tplMobileSolution) {
/* 502 */     this.tplMobileSolution = tplMobileSolution;
/*     */   }
/*     */ 
/*     */   public String getTplSolution() {
/* 506 */     return this.tplSolution;
/*     */   }
/*     */ 
/*     */   public void setTplSolution(String tplSolution) {
/* 510 */     this.tplSolution = tplSolution;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 515 */     if (obj == null) return false;
/* 516 */     if (!(obj instanceof Website)) return false;
/*     */ 
/* 518 */     Website website = (Website)obj;
/* 519 */     if ((getId() == null) || (website.getId() == null)) return false;
/* 520 */     return getId().equals(website.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 526 */     if (-2147483648 == this.hashCode) {
/* 527 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 529 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 530 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 533 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 538 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 542 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 546 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setRestart(Boolean restart) {
/* 550 */     this.restart = restart;
/*     */   }
/*     */ 
/*     */   public Boolean getRestart() {
/* 554 */     return this.restart;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseWebsite
 * JD-Core Version:    0.6.0
 */