/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Webservice;
/*     */ import com.jspgou.cms.entity.WebserviceParam;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class BaseWebservice
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "Webservice";
/*  18 */   public static String PROP_OPERATE = "operate";
/*  19 */   public static String PROP_TYPE = "type";
/*  20 */   public static String PROP_ADDRESS = "address";
/*  21 */   public static String PROP_TARGET_NAMESPACE = "targetNamespace";
/*  22 */   public static String PROP_ID = "id";
/*  23 */   public static String PROP_SUCCESS_RESULT = "successResult";
/*     */ 
/*  55 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String address;
/*     */   private String targetNamespace;
/*     */   private String successResult;
/*     */   private String type;
/*     */   private String operate;
/*     */   private List<WebserviceParam> params;
/*     */ 
/*     */   public BaseWebservice()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebservice(Integer id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebservice(Integer id, String address)
/*     */   {
/*  46 */     setId(id);
/*  47 */     setAddress(address);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  80 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  88 */     this.id = id;
/*  89 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/*  99 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address)
/*     */   {
/* 107 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getTargetNamespace()
/*     */   {
/* 115 */     return this.targetNamespace;
/*     */   }
/*     */ 
/*     */   public void setTargetNamespace(String targetNamespace)
/*     */   {
/* 123 */     this.targetNamespace = targetNamespace;
/*     */   }
/*     */ 
/*     */   public String getSuccessResult()
/*     */   {
/* 131 */     return this.successResult;
/*     */   }
/*     */ 
/*     */   public void setSuccessResult(String successResult)
/*     */   {
/* 139 */     this.successResult = successResult;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 147 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 155 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getOperate()
/*     */   {
/* 163 */     return this.operate;
/*     */   }
/*     */ 
/*     */   public void setOperate(String operate)
/*     */   {
/* 171 */     this.operate = operate;
/*     */   }
/*     */ 
/*     */   public List<WebserviceParam> getParams()
/*     */   {
/* 179 */     return this.params;
/*     */   }
/*     */ 
/*     */   public void setParams(List<WebserviceParam> params)
/*     */   {
/* 187 */     this.params = params;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 192 */     if (obj == null) return false;
/* 193 */     if (!(obj instanceof Webservice)) return false;
/*     */ 
/* 195 */     Webservice cmsWebservice = (Webservice)obj;
/* 196 */     if ((getId() == null) || (cmsWebservice.getId() == null)) return false;
/* 197 */     return getId().equals(cmsWebservice.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 203 */     if (-2147483648 == this.hashCode) {
/* 204 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 206 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 207 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 210 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 216 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseWebservice
 * JD-Core Version:    0.6.0
 */