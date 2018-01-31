/*     */ package com.jspgou.plug.weixin.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ public abstract class BaseWeixinMenu
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "WeixinMenu";
/*  18 */   public static String PROP_NAME = "name";
/*  19 */   public static String PROP_PARENT = "parent";
/*  20 */   public static String PROP_SITE = "site";
/*  21 */   public static String PROP_KEY = "key";
/*  22 */   public static String PROP_URL = "url";
/*  23 */   public static String PROP_TYPE = "type";
/*  24 */   public static String PROP_ID = "id";
/*     */ 
/*  44 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private String type;
/*     */   private String url;
/*     */   private String key;
/*     */   private WeixinMenu parent;
/*     */   private Website site;
/*     */   private Set<WeixinMenu> child;
/*     */ 
/*     */   public BaseWeixinMenu()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWeixinMenu(Integer id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  71 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  79 */     this.id = id;
/*  80 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  90 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  98 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 107 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 115 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 124 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url)
/*     */   {
/* 132 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 141 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/* 149 */     this.key = key;
/*     */   }
/*     */ 
/*     */   public WeixinMenu getParent()
/*     */   {
/* 158 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(WeixinMenu parent)
/*     */   {
/* 166 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public Website getSite()
/*     */   {
/* 175 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(Website site)
/*     */   {
/* 183 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public Set<WeixinMenu> getChild()
/*     */   {
/* 192 */     return this.child;
/*     */   }
/*     */ 
/*     */   public void setChild(Set<WeixinMenu> child)
/*     */   {
/* 200 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public void addTochild(WeixinMenu weixinMenu) {
/* 204 */     if (getChild() == null) setChild(new TreeSet());
/* 205 */     getChild().add(weixinMenu);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 212 */     if (obj == null) return false;
/* 213 */     if (!(obj instanceof WeixinMenu)) return false;
/*     */ 
/* 215 */     WeixinMenu weixinMenu = (WeixinMenu)obj;
/* 216 */     if ((getId() == null) || (weixinMenu.getId() == null)) return false;
/* 217 */     return getId().equals(weixinMenu.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 222 */     if (-2147483648 == this.hashCode) {
/* 223 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 225 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 226 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 229 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 234 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.base.BaseWeixinMenu
 * JD-Core Version:    0.6.0
 */