/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.User;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseUser
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  25 */   public static String REF = "User";
/*  26 */   public static String PROP_LOGIN_COUNT = "loginCount";
/*  27 */   public static String PROP_LAST_LOGIN_IP = "lastLoginIp";
/*  28 */   public static String PROP_CREATE_TIME = "createTime";
/*  29 */   public static String PROP_RESET_KEY = "resetKey";
/*  30 */   public static String PROP_LAST_LOGIN_TIME = "lastLoginTime";
/*  31 */   public static String PROP_RESET_PWD = "resetPwd";
/*  32 */   public static String PROP_PASSWORD = "password";
/*  33 */   public static String PROP_EMAIL = "email";
/*  34 */   public static String PROP_CURRENT_LOGIN_TIME = "currentLoginTime";
/*  35 */   public static String PROP_CURRENT_LOGIN_IP = "currentLoginIp";
/*  36 */   public static String PROP_REGISTER_IP = "registerIp";
/*  37 */   public static String PROP_ID = "id";
/*  38 */   public static String PROP_USERNAME = "username";
/*  39 */   public static String PROP_ERR_TIME = "errTime";
/*  40 */   public static String PROP_ERR_COUNT = "errCount";
/*  41 */   public static String PROP_ERR_IP = "errIp";
/*     */ 
/*  78 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String username;
/*     */   private String email;
/*     */   private String password;
/*     */   private Date createTime;
/*     */   private Long loginCount;
/*     */   private String registerIp;
/*     */   private Date lastLoginTime;
/*     */   private String lastLoginIp;
/*     */   private Date currentLoginTime;
/*     */   private String currentLoginIp;
/*     */   private String resetKey;
/*     */   private String resetPwd;
/*     */   private String sessionId;
/*     */   private Date errTime;
/*     */   private Integer errCount;
/*     */   private String errIp;
/*     */ 
/*     */   public BaseUser()
/*     */   {
/*  46 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseUser(Long id)
/*     */   {
/*  53 */     setId(id);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseUser(Long id, String username, String password, Date createTime, Long loginCount, Integer errCount)
/*     */   {
/*  66 */     setId(id);
/*  67 */     setUsername(username);
/*  68 */     setPassword(password);
/*  69 */     setCreateTime(createTime);
/*  70 */     setLoginCount(loginCount);
/*  71 */     setErrCount(errCount);
/*     */ 
/*  73 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getErrCount()
/*     */   {
/* 105 */     return this.errCount;
/*     */   }
/*     */ 
/*     */   public void setErrCount(Integer errCount) {
/* 109 */     this.errCount = errCount;
/*     */   }
/*     */ 
/*     */   public Long getId() {
/* 113 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/* 117 */     this.id = id;
/* 118 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 122 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 126 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getErrIp() {
/* 130 */     return this.errIp;
/*     */   }
/*     */ 
/*     */   public void setErrIp(String errIp) {
/* 134 */     this.errIp = errIp;
/*     */   }
/*     */ 
/*     */   public Date getErrTime() {
/* 138 */     return this.errTime;
/*     */   }
/*     */ 
/*     */   public void setErrTime(Date errTime) {
/* 142 */     this.errTime = errTime;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 146 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 150 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/* 154 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/* 158 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime() {
/* 162 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/* 166 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Long getLoginCount() {
/* 170 */     return this.loginCount;
/*     */   }
/*     */ 
/*     */   public void setLoginCount(Long loginCount) {
/* 174 */     this.loginCount = loginCount;
/*     */   }
/*     */ 
/*     */   public String getRegisterIp() {
/* 178 */     return this.registerIp;
/*     */   }
/*     */ 
/*     */   public void setRegisterIp(String registerIp) {
/* 182 */     this.registerIp = registerIp;
/*     */   }
/*     */ 
/*     */   public Date getLastLoginTime() {
/* 186 */     return this.lastLoginTime;
/*     */   }
/*     */ 
/*     */   public void setLastLoginTime(Date lastLoginTime) {
/* 190 */     this.lastLoginTime = lastLoginTime;
/*     */   }
/*     */ 
/*     */   public String getLastLoginIp() {
/* 194 */     return this.lastLoginIp;
/*     */   }
/*     */ 
/*     */   public void setLastLoginIp(String lastLoginIp) {
/* 198 */     this.lastLoginIp = lastLoginIp;
/*     */   }
/*     */ 
/*     */   public Date getCurrentLoginTime() {
/* 202 */     return this.currentLoginTime;
/*     */   }
/*     */ 
/*     */   public void setCurrentLoginTime(Date currentLoginTime) {
/* 206 */     this.currentLoginTime = currentLoginTime;
/*     */   }
/*     */ 
/*     */   public String getCurrentLoginIp() {
/* 210 */     return this.currentLoginIp;
/*     */   }
/*     */ 
/*     */   public void setCurrentLoginIp(String currentLoginIp) {
/* 214 */     this.currentLoginIp = currentLoginIp;
/*     */   }
/*     */ 
/*     */   public String getResetKey() {
/* 218 */     return this.resetKey;
/*     */   }
/*     */ 
/*     */   public void setResetKey(String resetKey) {
/* 222 */     this.resetKey = resetKey;
/*     */   }
/*     */ 
/*     */   public String getResetPwd() {
/* 226 */     return this.resetPwd;
/*     */   }
/*     */ 
/*     */   public void setResetPwd(String resetPwd) {
/* 230 */     this.resetPwd = resetPwd;
/*     */   }
/*     */ 
/*     */   public String getSessionId() {
/* 234 */     return this.sessionId;
/*     */   }
/*     */ 
/*     */   public void setSessionId(String sessionId) {
/* 238 */     this.sessionId = sessionId;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 243 */     if (obj == null) return false;
/* 244 */     if (!(obj instanceof User)) return false;
/*     */ 
/* 246 */     User user = (User)obj;
/* 247 */     if ((getId() == null) || (user.getId() == null)) return false;
/* 248 */     return getId().equals(user.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 254 */     if (-2147483648 == this.hashCode) {
/* 255 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 257 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 258 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 261 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 266 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseUser
 * JD-Core Version:    0.6.0
 */