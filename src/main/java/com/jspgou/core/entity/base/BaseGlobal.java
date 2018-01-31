/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Global;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class BaseGlobal
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Global";
/*  24 */   public static String PROP_CONTEXT_PATH = "contextPath";
/*  25 */   public static String PROP_PORT = "port";
/*  26 */   public static String PROP_ID = "id";
/*     */ 
/*  42 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String contextPath;
/*     */   private Integer port;
/*     */   private String defImg;
/*     */   private String treaty;
/*     */   private Integer activeScore;
/*     */   private Integer stockWarning;
/*     */   private String processUrl;
/*     */   private String password;
/*     */   private Map<String, String> attr;
/*     */ 
/*     */   public BaseGlobal()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGlobal(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  59 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/*  63 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public Long getId() {
/*  67 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/*  71 */     this.id = id;
/*  72 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getContextPath() {
/*  76 */     return this.contextPath;
/*     */   }
/*     */ 
/*     */   public void setContextPath(String contextPath) {
/*  80 */     this.contextPath = contextPath;
/*     */   }
/*     */ 
/*     */   public Integer getPort() {
/*  84 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(Integer port) {
/*  88 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public String getDefImg() {
/*  92 */     return this.defImg;
/*     */   }
/*     */ 
/*     */   public void setDefImg(String defImg) {
/*  96 */     this.defImg = defImg;
/*     */   }
/*     */ 
/*     */   public String getTreaty() {
/* 100 */     return this.treaty;
/*     */   }
/*     */ 
/*     */   public void setTreaty(String treaty) {
/* 104 */     this.treaty = treaty;
/*     */   }
/*     */ 
/*     */   public Integer getActiveScore() {
/* 108 */     return this.activeScore;
/*     */   }
/*     */ 
/*     */   public void setActiveScore(Integer activeScore) {
/* 112 */     this.activeScore = activeScore;
/*     */   }
/*     */ 
/*     */   public Integer getStockWarning() {
/* 116 */     return this.stockWarning;
/*     */   }
/*     */ 
/*     */   public void setStockWarning(Integer stockWarning) {
/* 120 */     this.stockWarning = stockWarning;
/*     */   }
/*     */ 
/*     */   public String getProcessUrl() {
/* 124 */     return this.processUrl;
/*     */   }
/*     */ 
/*     */   public void setProcessUrl(String processUrl) {
/* 128 */     this.processUrl = processUrl;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/* 134 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr)
/*     */   {
/* 139 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 144 */     if (obj == null) return false;
/* 145 */     if (!(obj instanceof Global)) return false;
/*     */ 
/* 147 */     Global global = (Global)obj;
/* 148 */     if ((getId() == null) || (global.getId() == null)) return false;
/* 149 */     return getId().equals(global.getId());
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
/* 167 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseGlobal
 * JD-Core Version:    0.6.0
 */