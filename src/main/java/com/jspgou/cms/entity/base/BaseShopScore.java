/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseShopScore
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopScore";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_STATUS = "status";
/*  22 */   public static String PROP_MEMBER = "member";
/*  23 */   public static String PROP_ORDER_ITEM = "orderItem";
/*  24 */   public static String PROP_ORDER = "order";
/*  25 */   public static String PROP_ID = "id";
/*  26 */   public static String PROP_SCORE_TIME = "scoreTime";
/*  27 */   public static String PROP_REMARK = "remark";
/*  28 */   public static String PROP_SCORE_TYPE = "scoreType";
/*  29 */   public static String PROP_SCORE = "score";
/*  30 */   public static String PROP_USE_STATUS = "useStatus";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Integer score;
/*     */   private Date scoreTime;
/*     */   private Integer scoreType;
/*     */   private boolean useStatus;
/*     */   private boolean status;
/*     */   private String remark;
/*     */   private String code;
/*     */   private ShopMember member;
/*     */ 
/*     */   public BaseShopScore()
/*     */   {
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopScore(Long id)
/*     */   {
/*  42 */     setId(id);
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopScore(Long id, Integer scoreType, boolean useStatus, boolean status)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setScoreType(scoreType);
/*  57 */     setUseStatus(useStatus);
/*  58 */     setStatus(status);
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  93 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
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
/*     */   public Integer getScore()
/*     */   {
/* 128 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 136 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Date getScoreTime()
/*     */   {
/* 144 */     return this.scoreTime;
/*     */   }
/*     */ 
/*     */   public void setScoreTime(Date scoreTime)
/*     */   {
/* 152 */     this.scoreTime = scoreTime;
/*     */   }
/*     */ 
/*     */   public Integer getScoreType()
/*     */   {
/* 160 */     return this.scoreType;
/*     */   }
/*     */ 
/*     */   public void setScoreType(Integer scoreType)
/*     */   {
/* 168 */     this.scoreType = scoreType;
/*     */   }
/*     */ 
/*     */   public boolean getUseStatus()
/*     */   {
/* 176 */     return this.useStatus;
/*     */   }
/*     */ 
/*     */   public void setUseStatus(boolean useStatus)
/*     */   {
/* 184 */     this.useStatus = useStatus;
/*     */   }
/*     */ 
/*     */   public boolean getStatus()
/*     */   {
/* 192 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(boolean status)
/*     */   {
/* 200 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getRemark()
/*     */   {
/* 208 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark)
/*     */   {
/* 216 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 224 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 232 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 237 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 241 */     return this.code;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 246 */     if (obj == null) return false;
/* 247 */     if (!(obj instanceof ShopScore)) return false;
/*     */ 
/* 249 */     ShopScore shopScore = (ShopScore)obj;
/* 250 */     if ((getId() == null) || (shopScore.getId() == null)) return false;
/* 251 */     return getId().equals(shopScore.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 257 */     if (-2147483648 == this.hashCode) {
/* 258 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 260 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 261 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 264 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 270 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopScore
 * JD-Core Version:    0.6.0
 */