/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gathering;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseGathering
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Gathering";
/*  20 */   public static String PROP_DRAWEE = "drawee";
/*  21 */   public static String PROP_ACCOUNTS = "accounts";
/*  22 */   public static String PROP_MONEY = "money";
/*  23 */   public static String PROP_BANK = "bank";
/*  24 */   public static String PROP_COMMENT = "comment";
/*  25 */   public static String PROP_ID = "id";
/*  26 */   public static String PROP_SHOP_ADMIN = "shopAdmin";
/*  27 */   public static String PROP_INDENT = "indent";
/*     */ 
/*  69 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String bank;
/*     */   private String accounts;
/*     */   private double money;
/*     */   private String drawee;
/*     */   private String comment;
/*     */   private Order indent;
/*     */   private ShopAdmin shopAdmin;
/*     */ 
/*     */   public BaseGathering()
/*     */   {
/*  32 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGathering(Long id)
/*     */   {
/*  39 */     setId(id);
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseGathering(Long id, Order indent, ShopAdmin shopAdmin, String bank, String accounts, String drawee, String comment)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setIndent(indent);
/*  57 */     setShopAdmin(shopAdmin);
/*  58 */     setBank(bank);
/*  59 */     setAccounts(accounts);
/*  60 */     setDrawee(drawee);
/*  61 */     setComment(comment);
/*  62 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  94 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 102 */     this.id = id;
/* 103 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getBank()
/*     */   {
/* 113 */     return this.bank;
/*     */   }
/*     */ 
/*     */   public void setBank(String bank)
/*     */   {
/* 121 */     this.bank = bank;
/*     */   }
/*     */ 
/*     */   public String getAccounts()
/*     */   {
/* 129 */     return this.accounts;
/*     */   }
/*     */ 
/*     */   public void setAccounts(String accounts)
/*     */   {
/* 137 */     this.accounts = accounts;
/*     */   }
/*     */ 
/*     */   public double getMoney()
/*     */   {
/* 145 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(double money)
/*     */   {
/* 153 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public String getDrawee()
/*     */   {
/* 161 */     return this.drawee;
/*     */   }
/*     */ 
/*     */   public void setDrawee(String drawee)
/*     */   {
/* 169 */     this.drawee = drawee;
/*     */   }
/*     */ 
/*     */   public String getComment()
/*     */   {
/* 177 */     return this.comment;
/*     */   }
/*     */ 
/*     */   public void setComment(String comment)
/*     */   {
/* 185 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */   public Order getIndent()
/*     */   {
/* 193 */     return this.indent;
/*     */   }
/*     */ 
/*     */   public void setIndent(Order indent)
/*     */   {
/* 201 */     this.indent = indent;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getShopAdmin()
/*     */   {
/* 209 */     return this.shopAdmin;
/*     */   }
/*     */ 
/*     */   public void setShopAdmin(ShopAdmin shopAdmin)
/*     */   {
/* 217 */     this.shopAdmin = shopAdmin;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 224 */     if (obj == null) return false;
/* 225 */     if (!(obj instanceof Gathering)) return false;
/*     */ 
/* 227 */     Gathering gathering = (Gathering)obj;
/* 228 */     if ((getId() == null) || (gathering.getId() == null)) return false;
/* 229 */     return getId().equals(gathering.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 235 */     if (-2147483648 == this.hashCode) {
/* 236 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 238 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 239 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 242 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 248 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseGathering
 * JD-Core Version:    0.6.0
 */