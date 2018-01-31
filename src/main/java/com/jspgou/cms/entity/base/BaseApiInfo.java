/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiInfo;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseApiInfo
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ApiInfo";
/*  18 */   public static String PROP_LAST_CALL_TIME = "lastCallTime";
/*  19 */   public static String PROP_API_URL = "apiUrl";
/*  20 */   public static String PROP_API_NAME = "apiName";
/*  21 */   public static String PROP_API_CODE = "apiCode";
/*  22 */   public static String PROP_LIMIT_CALL_DAY = "limitCallDay";
/*  23 */   public static String PROP_DISABLED = "disabled";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_CALL_MONTH_COUNT = "callMonthCount";
/*  26 */   public static String PROP_CALL_DAY_COUNT = "callDayCount";
/*  27 */   public static String PROP_CALL_WEEK_COUNT = "callWeekCount";
/*  28 */   public static String PROP_CALL_TOTAL_COUNT = "callTotalCount";
/*     */ 
/*  76 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String apiName;
/*     */   private String apiUrl;
/*     */   private String apiCode;
/*     */   private Boolean disabled;
/*     */   private Integer limitCallDay;
/*     */   private Integer callTotalCount;
/*     */   private Integer callMonthCount;
/*     */   private Integer callWeekCount;
/*     */   private Integer callDayCount;
/*     */   private Date lastCallTime;
/*     */ 
/*     */   public BaseApiInfo()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiInfo(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiInfo(Long id, String apiName, String apiUrl, String apiCode, Boolean disabled, Integer limitCallDay, Integer callTotalCount, Integer callMonthCount, Integer callWeekCount, Integer callDayCount)
/*     */   {
/*  59 */     setId(id);
/*  60 */     setApiName(apiName);
/*  61 */     setApiUrl(apiUrl);
/*  62 */     setApiCode(apiCode);
/*  63 */     setDisabled(disabled);
/*  64 */     setLimitCallDay(limitCallDay);
/*  65 */     setCallTotalCount(callTotalCount);
/*  66 */     setCallMonthCount(callMonthCount);
/*  67 */     setCallWeekCount(callWeekCount);
/*  68 */     setCallDayCount(callDayCount);
/*  69 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 110 */     this.id = id;
/* 111 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getApiName()
/*     */   {
/* 121 */     return this.apiName;
/*     */   }
/*     */ 
/*     */   public void setApiName(String apiName)
/*     */   {
/* 129 */     this.apiName = apiName;
/*     */   }
/*     */ 
/*     */   public String getApiUrl()
/*     */   {
/* 137 */     return this.apiUrl;
/*     */   }
/*     */ 
/*     */   public void setApiUrl(String apiUrl)
/*     */   {
/* 145 */     this.apiUrl = apiUrl;
/*     */   }
/*     */ 
/*     */   public String getApiCode()
/*     */   {
/* 153 */     return this.apiCode;
/*     */   }
/*     */ 
/*     */   public void setApiCode(String apiCode)
/*     */   {
/* 161 */     this.apiCode = apiCode;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 168 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled) {
/* 172 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Integer getLimitCallDay()
/*     */   {
/* 179 */     return this.limitCallDay;
/*     */   }
/*     */ 
/*     */   public void setLimitCallDay(Integer limitCallDay)
/*     */   {
/* 187 */     this.limitCallDay = limitCallDay;
/*     */   }
/*     */ 
/*     */   public Integer getCallTotalCount()
/*     */   {
/* 195 */     return this.callTotalCount;
/*     */   }
/*     */ 
/*     */   public void setCallTotalCount(Integer callTotalCount)
/*     */   {
/* 203 */     this.callTotalCount = callTotalCount;
/*     */   }
/*     */ 
/*     */   public Integer getCallMonthCount()
/*     */   {
/* 211 */     return this.callMonthCount;
/*     */   }
/*     */ 
/*     */   public void setCallMonthCount(Integer callMonthCount)
/*     */   {
/* 219 */     this.callMonthCount = callMonthCount;
/*     */   }
/*     */ 
/*     */   public Integer getCallWeekCount()
/*     */   {
/* 227 */     return this.callWeekCount;
/*     */   }
/*     */ 
/*     */   public void setCallWeekCount(Integer callWeekCount)
/*     */   {
/* 235 */     this.callWeekCount = callWeekCount;
/*     */   }
/*     */ 
/*     */   public Integer getCallDayCount()
/*     */   {
/* 243 */     return this.callDayCount;
/*     */   }
/*     */ 
/*     */   public void setCallDayCount(Integer callDayCount)
/*     */   {
/* 251 */     this.callDayCount = callDayCount;
/*     */   }
/*     */ 
/*     */   public Date getLastCallTime()
/*     */   {
/* 259 */     return this.lastCallTime;
/*     */   }
/*     */ 
/*     */   public void setLastCallTime(Date lastCallTime)
/*     */   {
/* 267 */     this.lastCallTime = lastCallTime;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 273 */     if (obj == null) return false;
/* 274 */     if (!(obj instanceof ApiInfo)) return false;
/*     */ 
/* 276 */     ApiInfo apiInfo = (ApiInfo)obj;
/* 277 */     if ((getId() == null) || (apiInfo.getId() == null)) return false;
/* 278 */     return getId().equals(apiInfo.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 283 */     if (-2147483648 == this.hashCode) {
/* 284 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 286 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 287 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 290 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 295 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseApiInfo
 * JD-Core Version:    0.6.0
 */