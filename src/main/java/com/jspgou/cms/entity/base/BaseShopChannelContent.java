/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.entity.ShopChannelContent;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopChannelContent
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopChannelContent";
/*  20 */   public static String PROP_CHANNEL = "channel";
/*  21 */   public static String PROP_CONTENT = "content";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  42 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String content;
/*     */   private ShopChannel channel;
/*     */ 
/*     */   public BaseShopChannelContent()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopChannelContent(Integer id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  62 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  70 */     this.id = id;
/*  71 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  81 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/*  89 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public ShopChannel getChannel()
/*     */   {
/*  97 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public void setChannel(ShopChannel channel)
/*     */   {
/* 105 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 112 */     if (obj == null) return false;
/* 113 */     if (!(obj instanceof ShopChannelContent)) return false;
/*     */ 
/* 115 */     ShopChannelContent shopChannelContent = (ShopChannelContent)obj;
/* 116 */     if ((getId() == null) || (shopChannelContent.getId() == null)) return false;
/* 117 */     return getId().equals(shopChannelContent.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 123 */     if (-2147483648 == this.hashCode) {
/* 124 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 126 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 127 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 130 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 136 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopChannelContent
 * JD-Core Version:    0.6.0
 */