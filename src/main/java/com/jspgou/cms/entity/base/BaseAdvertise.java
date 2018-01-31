/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Adspace;
/*     */ import com.jspgou.cms.entity.Advertise;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class BaseAdvertise
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Advertise";
/*  24 */   public static String PROP_NAME = "name";
/*  25 */   public static String PROP_WEIGHT = "weight";
/*  26 */   public static String PROP_ADSPACE = "adspace";
/*  27 */   public static String PROP_ENABLE = "enable";
/*  28 */   public static String PROP_CLICK_COUNT = "clickCount";
/*  29 */   public static String PROP_DSIPLAY_COUNT = "displayCount";
/*  30 */   public static String PROP_ID = "id";
/*  31 */   public static String PROP_END_TIME = "endTime";
/*  32 */   public static String PROP_START_TIME = "startTime";
/*     */ 
/*  64 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private Integer weight;
/*     */   private Integer displayCount;
/*     */   private Integer clickCount;
/*     */   private Date startTime;
/*     */   private Date endTime;
/*     */   private Boolean enabled;
/*     */   private Adspace adspace;
/*     */   private Map<String, String> attr;
/*     */ 
/*     */   public BaseAdvertise()
/*     */   {
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAdvertise(Integer id)
/*     */   {
/*  44 */     setId(id);
/*  45 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAdvertise(Integer id, Adspace adspace)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setAdspace(adspace);
/*  57 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  93 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/* 101 */     this.id = id;
/* 102 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 112 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 120 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getWeight()
/*     */   {
/* 128 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(Integer weight)
/*     */   {
/* 136 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */   public Integer getDisplayCount()
/*     */   {
/* 144 */     return this.displayCount;
/*     */   }
/*     */ 
/*     */   public void setDisplayCount(Integer displayCount)
/*     */   {
/* 152 */     this.displayCount = displayCount;
/*     */   }
/*     */ 
/*     */   public Integer getClickCount()
/*     */   {
/* 160 */     return this.clickCount;
/*     */   }
/*     */ 
/*     */   public void setClickCount(Integer clickCount)
/*     */   {
/* 168 */     this.clickCount = clickCount;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 176 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date startTime)
/*     */   {
/* 184 */     this.startTime = startTime;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 192 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date endTime)
/*     */   {
/* 200 */     this.endTime = endTime;
/*     */   }
/*     */ 
/*     */   public Boolean getEnabled()
/*     */   {
/* 208 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   public void setEnabled(Boolean enabled)
/*     */   {
/* 216 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   public Adspace getAdspace()
/*     */   {
/* 224 */     return this.adspace;
/*     */   }
/*     */ 
/*     */   public void setAdspace(Adspace adspace)
/*     */   {
/* 232 */     this.adspace = adspace;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/* 240 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr)
/*     */   {
/* 248 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 255 */     if (obj == null) return false;
/* 256 */     if (!(obj instanceof Advertise)) return false;
/*     */ 
/* 258 */     Advertise advertise = (Advertise)obj;
/* 259 */     if ((getId() == null) || (advertise.getId() == null)) return false;
/* 260 */     return getId().equals(advertise.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 266 */     if (-2147483648 == this.hashCode) {
/* 267 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 269 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 270 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 273 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 279 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseAdvertise
 * JD-Core Version:    0.6.0
 */