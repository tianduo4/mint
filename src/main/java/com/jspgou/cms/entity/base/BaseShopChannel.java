/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.entity.ShopChannelContent;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseShopChannel
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopChannel";
/*  24 */   public static String PROP_TYPE = "type";
/*  25 */   public static String PROP_CHANNEL_CONTENT = "channelContent";
/*  26 */   public static String PROP_PARAM1 = "param1";
/*  27 */   public static String PROP_RGT = "rgt";
/*  28 */   public static String PROP_WEBSITE = "website";
/*  29 */   public static String PROP_TPL_CHANNEL = "tplChannel";
/*  30 */   public static String PROP_PRIORITY = "priority";
/*  31 */   public static String PROP_TPL_CONTENT = "tplContent";
/*  32 */   public static String PROP_OUTER_URL = "outerUrl";
/*  33 */   public static String PROP_BLANK = "blank";
/*  34 */   public static String PROP_PARAM3 = "param3";
/*  35 */   public static String PROP_LFT = "lft";
/*  36 */   public static String PROP_PARENT = "parent";
/*  37 */   public static String PROP_PATH = "path";
/*  38 */   public static String PROP_DISPLAY = "display";
/*  39 */   public static String PROP_NAME = "name";
/*  40 */   public static String PROP_PARAM2 = "param2";
/*  41 */   public static String PROP_ID = "id";
/*     */ 
/*  83 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private Integer lft;
/*     */   private Integer rgt;
/*     */   private Integer type;
/*     */   private String name;
/*     */   private String path;
/*     */   private String outerUrl;
/*     */   private String tplChannel;
/*     */   private String tplContent;
/*     */   private Integer priority;
/*     */   private Boolean blank;
/*     */   private Boolean display;
/*     */   private String param1;
/*     */   private String param2;
/*     */   private String param3;
/*     */   private ShopChannelContent channelContent;
/*     */   private ShopChannel parent;
/*     */   private Website website;
/*     */   private Set<ShopChannel> child;
/*     */ 
/*     */   public BaseShopChannel()
/*     */   {
/*  46 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopChannel(Integer id)
/*     */   {
/*  53 */     setId(id);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopChannel(Integer id, Website website, Integer lft, Integer rgt, Integer type, String name, Integer priority)
/*     */   {
/*  69 */     setId(id);
/*  70 */     setWebsite(website);
/*  71 */     setLft(lft);
/*  72 */     setRgt(rgt);
/*  73 */     setType(type);
/*  74 */     setName(name);
/*  75 */     setPriority(priority);
/*  76 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/* 123 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/* 131 */     this.id = id;
/* 132 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getLft()
/*     */   {
/* 142 */     return this.lft;
/*     */   }
/*     */ 
/*     */   public void setLft(Integer lft)
/*     */   {
/* 150 */     this.lft = lft;
/*     */   }
/*     */ 
/*     */   public Integer getRgt()
/*     */   {
/* 158 */     return this.rgt;
/*     */   }
/*     */ 
/*     */   public void setRgt(Integer rgt)
/*     */   {
/* 166 */     this.rgt = rgt;
/*     */   }
/*     */ 
/*     */   public Integer getType()
/*     */   {
/* 174 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(Integer type)
/*     */   {
/* 182 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 190 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 198 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 206 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 214 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getOuterUrl()
/*     */   {
/* 222 */     return this.outerUrl;
/*     */   }
/*     */ 
/*     */   public void setOuterUrl(String outerUrl)
/*     */   {
/* 230 */     this.outerUrl = outerUrl;
/*     */   }
/*     */ 
/*     */   public String getTplChannel()
/*     */   {
/* 238 */     return this.tplChannel;
/*     */   }
/*     */ 
/*     */   public void setTplChannel(String tplChannel)
/*     */   {
/* 246 */     this.tplChannel = tplChannel;
/*     */   }
/*     */ 
/*     */   public String getTplContent()
/*     */   {
/* 254 */     return this.tplContent;
/*     */   }
/*     */ 
/*     */   public void setTplContent(String tplContent)
/*     */   {
/* 262 */     this.tplContent = tplContent;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 270 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 278 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getBlank()
/*     */   {
/* 286 */     return this.blank;
/*     */   }
/*     */ 
/*     */   public void setBlank(Boolean blank)
/*     */   {
/* 294 */     this.blank = blank;
/*     */   }
/*     */ 
/*     */   public Boolean getDisplay()
/*     */   {
/* 302 */     return this.display;
/*     */   }
/*     */ 
/*     */   public void setDisplay(Boolean display)
/*     */   {
/* 310 */     this.display = display;
/*     */   }
/*     */ 
/*     */   public String getParam1()
/*     */   {
/* 318 */     return this.param1;
/*     */   }
/*     */ 
/*     */   public void setParam1(String param1)
/*     */   {
/* 326 */     this.param1 = param1;
/*     */   }
/*     */ 
/*     */   public String getParam2()
/*     */   {
/* 334 */     return this.param2;
/*     */   }
/*     */ 
/*     */   public void setParam2(String param2)
/*     */   {
/* 342 */     this.param2 = param2;
/*     */   }
/*     */ 
/*     */   public String getParam3()
/*     */   {
/* 350 */     return this.param3;
/*     */   }
/*     */ 
/*     */   public void setParam3(String param3)
/*     */   {
/* 358 */     this.param3 = param3;
/*     */   }
/*     */ 
/*     */   public ShopChannelContent getChannelContent()
/*     */   {
/* 366 */     return this.channelContent;
/*     */   }
/*     */ 
/*     */   public void setChannelContent(ShopChannelContent channelContent)
/*     */   {
/* 374 */     this.channelContent = channelContent;
/*     */   }
/*     */ 
/*     */   public ShopChannel getParent()
/*     */   {
/* 382 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(ShopChannel parent)
/*     */   {
/* 390 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 398 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 406 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<ShopChannel> getChild()
/*     */   {
/* 414 */     return this.child;
/*     */   }
/*     */ 
/*     */   public void setChild(Set<ShopChannel> child)
/*     */   {
/* 422 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 429 */     if (obj == null) return false;
/* 430 */     if (!(obj instanceof ShopChannel)) return false;
/*     */ 
/* 432 */     ShopChannel shopChannel = (ShopChannel)obj;
/* 433 */     if ((getId() == null) || (shopChannel.getId() == null)) return false;
/* 434 */     return getId().equals(shopChannel.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 440 */     if (-2147483648 == this.hashCode) {
/* 441 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 443 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 444 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 447 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 453 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopChannel
 * JD-Core Version:    0.6.0
 */