/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.WebserviceAuth;
/*     */ import com.jspgou.cms.entity.WebserviceCallRecord;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseWebserviceCallRecord
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "WebserviceCallRecord";
/*  18 */   public static String PROP_SERVICE_CODE = "serviceCode";
/*  19 */   public static String PROP_ID = "id";
/*  20 */   public static String PROP_RECORD_TIME = "recordTime";
/*  21 */   public static String PROP_AUTH = "auth";
/*     */ 
/*  57 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String serviceCode;
/*     */   private Date recordTime;
/*     */   private WebserviceAuth auth;
/*     */ 
/*     */   public BaseWebserviceCallRecord()
/*     */   {
/*  26 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebserviceCallRecord(Integer id)
/*     */   {
/*  33 */     setId(id);
/*  34 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebserviceCallRecord(Integer id, WebserviceAuth auth, String serviceCode, Date recordTime)
/*     */   {
/*  46 */     setId(id);
/*  47 */     setAuth(auth);
/*  48 */     setServiceCode(serviceCode);
/*  49 */     setRecordTime(recordTime);
/*  50 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  78 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  86 */     this.id = id;
/*  87 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getServiceCode()
/*     */   {
/*  97 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode)
/*     */   {
/* 105 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public Date getRecordTime()
/*     */   {
/* 113 */     return this.recordTime;
/*     */   }
/*     */ 
/*     */   public void setRecordTime(Date recordTime)
/*     */   {
/* 121 */     this.recordTime = recordTime;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth getAuth()
/*     */   {
/* 129 */     return this.auth;
/*     */   }
/*     */ 
/*     */   public void setAuth(WebserviceAuth auth)
/*     */   {
/* 137 */     this.auth = auth;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 144 */     if (obj == null) return false;
/* 145 */     if (!(obj instanceof WebserviceCallRecord)) return false;
/*     */ 
/* 147 */     WebserviceCallRecord cmsWebserviceCallRecord = (WebserviceCallRecord)obj;
/* 148 */     if ((getId() == null) || (cmsWebserviceCallRecord.getId() == null)) return false;
/* 149 */     return getId().equals(cmsWebserviceCallRecord.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 155 */     if (-2147483648 == this.hashCode) {
/* 156 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 158 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 159 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 162 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 168 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseWebserviceCallRecord
 * JD-Core Version:    0.6.0
 */