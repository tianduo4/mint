/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gift;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseGift
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Gift";
/*  20 */   public static String PROP_GIFT_STOCK = "giftStock";
/*  21 */   public static String PROP_GIFT_NAME = "giftName";
/*  22 */   public static String PROP_GIFT_PICTURE = "giftPicture";
/*  23 */   public static String PROP_GIFT_SCORE = "giftScore";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_ISGIFT = "isgift";
/*     */ 
/*  45 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String giftName;
/*     */   private Integer giftScore;
/*     */   private Integer giftStock;
/*     */   private String giftPicture;
/*     */   private Boolean isgift;
/*     */   private String introduced;
/*     */ 
/*     */   public BaseGift()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGift(Long id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  65 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  73 */     this.id = id;
/*  74 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getGiftName()
/*     */   {
/*  84 */     return this.giftName;
/*     */   }
/*     */ 
/*     */   public void setGiftName(String giftName)
/*     */   {
/*  92 */     this.giftName = giftName;
/*     */   }
/*     */ 
/*     */   public Integer getGiftScore()
/*     */   {
/* 100 */     return this.giftScore;
/*     */   }
/*     */ 
/*     */   public void setGiftScore(Integer giftScore)
/*     */   {
/* 108 */     this.giftScore = giftScore;
/*     */   }
/*     */ 
/*     */   public Integer getGiftStock()
/*     */   {
/* 116 */     return this.giftStock;
/*     */   }
/*     */ 
/*     */   public void setGiftStock(Integer giftStock)
/*     */   {
/* 124 */     this.giftStock = giftStock;
/*     */   }
/*     */ 
/*     */   public String getGiftPicture()
/*     */   {
/* 132 */     return this.giftPicture;
/*     */   }
/*     */ 
/*     */   public void setGiftPicture(String giftPicture)
/*     */   {
/* 140 */     this.giftPicture = giftPicture;
/*     */   }
/*     */ 
/*     */   public Boolean getIsgift()
/*     */   {
/* 148 */     return this.isgift;
/*     */   }
/*     */ 
/*     */   public void setIsgift(Boolean isgift)
/*     */   {
/* 156 */     this.isgift = isgift;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 163 */     if (obj == null) return false;
/* 164 */     if (!(obj instanceof Gift)) return false;
/*     */ 
/* 166 */     Gift gift = (Gift)obj;
/* 167 */     if ((getId() == null) || (gift.getId() == null)) return false;
/* 168 */     return getId().equals(gift.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 174 */     if (-2147483648 == this.hashCode) {
/* 175 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 177 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 178 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 181 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 187 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setIntroduced(String introduced) {
/* 191 */     this.introduced = introduced;
/*     */   }
/*     */ 
/*     */   public String getIntroduced() {
/* 195 */     return this.introduced;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseGift
 * JD-Core Version:    0.6.0
 */