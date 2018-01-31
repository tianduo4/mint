/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiInfo;
/*     */ import com.jspgou.cms.entity.ApiRecord;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseApiRecord
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ApiRecord";
/*  18 */   public static String PROP_API_ACCOUNT = "apiAccount";
/*  19 */   public static String PROP_CALL_TIME_STAMP = "callTimeStamp";
/*  20 */   public static String PROP_SIGN = "sign";
/*  21 */   public static String PROP_API_INFO = "apiInfo";
/*  22 */   public static String PROP_ID = "id";
/*  23 */   public static String PROP_API_IP = "apiIp";
/*  24 */   public static String PROP_API_CALL_TIME = "apiCallTime";
/*     */ 
/*  62 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String apiIp;
/*     */   private Date apiCallTime;
/*     */   private Long callTimeStamp;
/*     */   private String sign;
/*     */   private ApiAccount apiAccount;
/*     */   private ApiInfo apiInfo;
/*     */ 
/*     */   public BaseApiRecord()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiRecord(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiRecord(Long id, ApiAccount apiAccount, Date apiCallTime, Long callTimeStamp, String sign)
/*     */   {
/*  50 */     setId(id);
/*  51 */     setApiAccount(apiAccount);
/*  52 */     setApiCallTime(apiCallTime);
/*  53 */     setCallTimeStamp(callTimeStamp);
/*  54 */     setSign(sign);
/*  55 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  86 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  94 */     this.id = id;
/*  95 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getApiIp()
/*     */   {
/* 105 */     return this.apiIp;
/*     */   }
/*     */ 
/*     */   public void setApiIp(String apiIp)
/*     */   {
/* 113 */     this.apiIp = apiIp;
/*     */   }
/*     */ 
/*     */   public Date getApiCallTime()
/*     */   {
/* 121 */     return this.apiCallTime;
/*     */   }
/*     */ 
/*     */   public void setApiCallTime(Date apiCallTime)
/*     */   {
/* 129 */     this.apiCallTime = apiCallTime;
/*     */   }
/*     */ 
/*     */   public Long getCallTimeStamp()
/*     */   {
/* 137 */     return this.callTimeStamp;
/*     */   }
/*     */ 
/*     */   public void setCallTimeStamp(Long callTimeStamp)
/*     */   {
/* 145 */     this.callTimeStamp = callTimeStamp;
/*     */   }
/*     */ 
/*     */   public String getSign()
/*     */   {
/* 153 */     return this.sign;
/*     */   }
/*     */ 
/*     */   public void setSign(String sign)
/*     */   {
/* 161 */     this.sign = sign;
/*     */   }
/*     */ 
/*     */   public ApiAccount getApiAccount()
/*     */   {
/* 169 */     return this.apiAccount;
/*     */   }
/*     */ 
/*     */   public void setApiAccount(ApiAccount apiAccount)
/*     */   {
/* 177 */     this.apiAccount = apiAccount;
/*     */   }
/*     */ 
/*     */   public ApiInfo getApiInfo()
/*     */   {
/* 185 */     return this.apiInfo;
/*     */   }
/*     */ 
/*     */   public void setApiInfo(ApiInfo apiInfo)
/*     */   {
/* 193 */     this.apiInfo = apiInfo;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 199 */     if (obj == null) return false;
/* 200 */     if (!(obj instanceof ApiRecord)) return false;
/*     */ 
/* 202 */     ApiRecord apiRecord = (ApiRecord)obj;
/* 203 */     if ((getId() == null) || (apiRecord.getId() == null)) return false;
/* 204 */     return getId().equals(apiRecord.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 209 */     if (-2147483648 == this.hashCode) {
/* 210 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 212 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 213 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 216 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 221 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseApiRecord
 * JD-Core Version:    0.6.0
 */