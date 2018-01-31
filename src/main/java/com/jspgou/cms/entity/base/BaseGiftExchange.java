/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import com.jspgou.cms.entity.GiftExchange;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseGiftExchange
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "GiftExchange";
/*  18 */   public static String PROP_STATUS = "status";
/*  19 */   public static String PROP_MEMBER = "member";
/*  20 */   public static String PROP_AMOUNT = "amount";
/*  21 */   public static String PROP_DETAILADDRESS = "detailaddress";
/*  22 */   public static String PROP_GIFT = "gift";
/*  23 */   public static String PROP_CREATE_TIME = "createTime";
/*  24 */   public static String PROP_WAYBILL = "waybill";
/*  25 */   public static String PROP_TOTAL_SCORE = "totalScore";
/*  26 */   public static String PROP_ID = "id";
/*  27 */   public static String PROP_SCORE = "score";
/*     */ 
/*  65 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer score;
/*     */   private Integer amount;
/*     */   private Date createTime;
/*     */   private Integer totalScore;
/*     */   private String detailaddress;
/*     */   private Integer status;
/*     */   private String waybill;
/*     */   private ShopMember member;
/*     */   private Gift gift;
/*     */ 
/*     */   public BaseGiftExchange()
/*     */   {
/*  32 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGiftExchange(Long id)
/*     */   {
/*  39 */     setId(id);
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGiftExchange(Long id, ShopMember member, Gift gift, Date createTime, Integer status)
/*     */   {
/*  53 */     setId(id);
/*  54 */     setMember(member);
/*  55 */     setGift(gift);
/*  56 */     setCreateTime(createTime);
/*  57 */     setStatus(status);
/*  58 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 100 */     this.id = id;
/* 101 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getScore()
/*     */   {
/* 111 */     return this.score;
/*     */   }
/*     */ 
/*     */   public void setScore(Integer score)
/*     */   {
/* 119 */     this.score = score;
/*     */   }
/*     */ 
/*     */   public Integer getAmount()
/*     */   {
/* 127 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(Integer amount)
/*     */   {
/* 135 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 143 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 151 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Integer getTotalScore()
/*     */   {
/* 159 */     return this.totalScore;
/*     */   }
/*     */ 
/*     */   public void setTotalScore(Integer totalScore)
/*     */   {
/* 167 */     this.totalScore = totalScore;
/*     */   }
/*     */ 
/*     */   public String getDetailaddress()
/*     */   {
/* 175 */     return this.detailaddress;
/*     */   }
/*     */ 
/*     */   public void setDetailaddress(String detailaddress)
/*     */   {
/* 183 */     this.detailaddress = detailaddress;
/*     */   }
/*     */ 
/*     */   public Integer getStatus()
/*     */   {
/* 191 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status)
/*     */   {
/* 199 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getWaybill()
/*     */   {
/* 207 */     return this.waybill;
/*     */   }
/*     */ 
/*     */   public void setWaybill(String waybill)
/*     */   {
/* 215 */     this.waybill = waybill;
/*     */   }
/*     */ 
/*     */   public ShopMember getMember()
/*     */   {
/* 223 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(ShopMember member)
/*     */   {
/* 231 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Gift getGift()
/*     */   {
/* 239 */     return this.gift;
/*     */   }
/*     */ 
/*     */   public void setGift(Gift gift)
/*     */   {
/* 247 */     this.gift = gift;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 254 */     if (obj == null) return false;
/* 255 */     if (!(obj instanceof GiftExchange)) return false;
/*     */ 
/* 257 */     GiftExchange giftExchange = (GiftExchange)obj;
/* 258 */     if ((getId() == null) || (giftExchange.getId() == null)) return false;
/* 259 */     return getId().equals(giftExchange.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 265 */     if (-2147483648 == this.hashCode) {
/* 266 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 268 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 269 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 272 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 278 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseGiftExchange
 * JD-Core Version:    0.6.0
 */