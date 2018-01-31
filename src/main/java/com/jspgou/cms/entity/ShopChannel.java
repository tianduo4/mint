/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseShopChannel;
/*     */ import com.jspgou.common.hibernate4.HibernateTree;
/*     */ import com.jspgou.common.hibernate4.PriorityComparator;
/*     */ import com.jspgou.common.hibernate4.PriorityInterface;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ShopChannel extends BaseShopChannel
/*     */   implements HibernateTree<Integer>, PriorityInterface
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int ALONE = 1;
/*     */   public static final int ARTICLE = 2;
/*     */   public static final int OUTER_URL = 3;
/*     */   public static final String CHANNEL_SUFFIX = "栏目";
/*     */   public static final String CONTENT_SUFFIX = "内容";
/*     */   public static final String ALONE_SUFFIX = "单页";
/*     */ 
/*     */   public static String getChannelTplDirRel(Website web)
/*     */   {
/*  61 */     return "/" + "channel";
/*     */   }
/*     */ 
/*     */   public static String getContentTplDirRel(Website web)
/*     */   {
/*  70 */     return "/" + "article";
/*     */   }
/*     */ 
/*     */   public static String[] getChannelTpls(Integer type, String realChannelDir, String relChannelPath)
/*     */   {
/*  82 */     String prefix = getPrefix(type, true);
/*  83 */     if (prefix != null) {
/*  84 */       return ProductType.getTpls(realChannelDir, relChannelPath, prefix);
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   public static String[] getContentTpls(Integer type, String realContentDir, String relContentPath)
/*     */   {
/*  99 */     String prefix = getPrefix(type, false);
/* 100 */     if (prefix != null) {
/* 101 */       return ProductType.getTpls(realContentDir, relContentPath, prefix);
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getPrefix(Integer type, boolean isChannel)
/*     */   {
/* 115 */     if (type == null) {
/* 116 */       throw new IllegalStateException("ShopChannle type connot be null");
/*     */     }
/* 118 */     if (type.intValue() == 1)
/* 119 */       return "单页";
/* 120 */     if (type.intValue() == 2) {
/* 121 */       return isChannel ? "栏目" : "内容";
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   public String getTplChannelRel(HttpServletRequest request)
/*     */   {
/* 133 */     String tpl = getTplChannel();
/* 134 */     if (StringUtils.isBlank(tpl)) {
/* 135 */       String suffix = getPrefix(getType(), true);
/* 136 */       if (suffix != null) {
/* 137 */         return getWebsite().getTemplate("channel", "栏目", request);
/*     */       }
/* 139 */       return null;
/*     */     }
/*     */ 
/* 142 */     return getWebsite().getTemplateRel(tpl, request);
/*     */   }
/*     */ 
/*     */   public String getTplContentRel(HttpServletRequest request)
/*     */   {
/* 149 */     String tpl = getTplContent();
/* 150 */     if (StringUtils.isBlank(tpl)) {
/* 151 */       String suffix = getPrefix(getType(), false);
/*     */ 
/* 153 */       if (suffix != null) {
/* 154 */         return getWebsite().getTemplate("article", "内容", request);
/*     */       }
/* 156 */       return null;
/*     */     }
/*     */ 
/* 159 */     return getWebsite().getTemplateRel(tpl, request);
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 168 */     int type = getType().intValue();
/* 169 */     if (type == 3) {
/* 170 */       String url = getOuterUrl();
/* 171 */       if (StringUtils.isBlank(url)) {
/* 172 */         throw new IllegalStateException(
/* 173 */           "ShopChannel outerUrl cannot be blank while type is OUTER_URL, ID=" + 
/* 174 */           getId());
/*     */       }
/* 176 */       if (url.startsWith("/"))
/* 177 */         return url;
/* 178 */       if (url.startsWith("http")) {
/* 179 */         return url;
/*     */       }
/* 181 */       return "http://" + url;
/*     */     }
/* 183 */     if (type == 1)
/* 184 */       return "/" + getPath() + 
/* 185 */         getWebsite().getSuffix();
/* 186 */     if (type == 2) {
/* 187 */       return "/" + getPath() + 
/* 188 */         "/" + "index" + getWebsite().getSuffix();
/*     */     }
/*     */ 
/* 191 */     throw new IllegalStateException(
/* 192 */       "ShopChannel type not supported: id=" + getId() + " type=" + 
/* 193 */       type);
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 203 */     ShopChannelContent content = getChannelContent();
/* 204 */     if (content != null) {
/* 205 */       return content.getContent();
/*     */     }
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   public int getDeep()
/*     */   {
/* 217 */     int deep = 0;
/* 218 */     ShopChannel parent = getParent();
/* 219 */     while (parent != null) {
/* 220 */       deep++;
/* 221 */       parent = parent.getParent();
/*     */     }
/* 223 */     return deep;
/*     */   }
/*     */ 
/*     */   public void addToChild(ShopChannel shopChannel)
/*     */   {
/* 232 */     Set set = getChild();
/* 233 */     if (set == null) {
/* 234 */       set = new TreeSet(PriorityComparator.INSTANCE);
/* 235 */       setChild(set);
/*     */     }
/* 237 */     set.add(shopChannel);
/*     */   }
/*     */ 
/*     */   public String getTreeCondition()
/*     */   {
/* 247 */     return "bean.website.id=" + getWebsite().getId();
/*     */   }
/*     */ 
/*     */   public Integer getParentId()
/*     */   {
/* 255 */     ShopChannel parent = getParent();
/* 256 */     if (parent != null) {
/* 257 */       return parent.getId();
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLftName()
/*     */   {
/* 268 */     return "lft";
/*     */   }
/*     */ 
/*     */   public String getParentName()
/*     */   {
/* 276 */     return "parent";
/*     */   }
/*     */ 
/*     */   public String getRgtName()
/*     */   {
/* 284 */     return "rgt";
/*     */   }
/*     */ 
/*     */   public ShopChannel()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ShopChannel(Integer id)
/*     */   {
/* 296 */     super(id);
/*     */   }
/*     */ 
/*     */   public ShopChannel(Integer id, Website website, Integer lft, Integer rgt, Integer type, String name, Integer priority)
/*     */   {
/* 307 */     super(id, website, lft, rgt, type, name, priority);
/*     */   }
/*     */ 
/*     */   public JSONObject converToTreeJson()
/*     */   {
/* 312 */     JSONObject json = new JSONObject();
/* 313 */     json.put("id", CommonUtils.parseId(getId()));
/* 314 */     json.put("pid", Integer.valueOf(getParent() != null ? CommonUtils.parseInteger(getParent().getId()) : 0));
/* 315 */     json.put("name", CommonUtils.parseStr(getName()));
/* 316 */     json.put("leval", Integer.valueOf(CommonUtils.parseInteger(Integer.valueOf(getDeep()))));
/* 317 */     if ((getChild() != null) && (getChild().size() > 0))
/* 318 */       json.put("isChild", Boolean.valueOf(true));
/*     */     else {
/* 320 */       json.put("isChild", Boolean.valueOf(false));
/*     */     }
/* 322 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() {
/* 326 */     JSONObject json = new JSONObject();
/* 327 */     json.put("id", CommonUtils.parseId(getId()));
/* 328 */     json.put("pid", Integer.valueOf(getParent() != null ? CommonUtils.parseInteger(getParent().getId()) : 0));
/* 329 */     json.put("name", CommonUtils.parseStr(getName()));
/* 330 */     if ((getChild() != null) && (getChild().size() > 0))
/* 331 */       json.put("isChild", Boolean.valueOf(true));
/*     */     else {
/* 333 */       json.put("isChild", Boolean.valueOf(false));
/*     */     }
/* 335 */     json.put("type", Integer.valueOf(CommonUtils.parseInteger(getType())));
/* 336 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 337 */     json.put("path", CommonUtils.parseStr(getPath()));
/* 338 */     json.put("tplChannel", CommonUtils.parseStr(getTplChannel()));
/* 339 */     json.put("tplContent", CommonUtils.parseStr(getTplContent()));
/* 340 */     json.put("display", CommonUtils.parseBoolean(getDisplay()));
/* 341 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 342 */     json.put("outerUrl", CommonUtils.parseStr(getOuterUrl()));
/* 343 */     json.put("blank", CommonUtils.parseBoolean(getBlank()));
/* 344 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopChannel
 * JD-Core Version:    0.6.0
 */