/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseCategory;
/*     */ import com.jspgou.common.hibernate4.HibernateTree;
/*     */ import com.jspgou.common.hibernate4.PriorityComparator;
/*     */ import com.jspgou.common.hibernate4.PriorityInterface;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Category extends BaseCategory
/*     */   implements HibernateTree<Integer>, PriorityInterface
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int leval;
/*     */ 
/*     */   public int getLeval()
/*     */   {
/*  46 */     return this.leval;
/*     */   }
/*     */ 
/*     */   public void setLeval(int leval) {
/*  50 */     this.leval = leval;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/*  59 */     return "/" + getPath() + 
/*  60 */       "/" + "index" + getWebsite().getSuffix();
/*     */   }
/*     */ 
/*     */   public String getTplChannelRel(HttpServletRequest request)
/*     */   {
/*  68 */     String tpl = getTplChannel();
/*  69 */     if (StringUtils.isBlank(tpl)) {
/*  70 */       tpl = getType().getChannelPrefix();
/*  71 */       return getWebsite().getTemplate("category", tpl, request);
/*     */     }
/*  73 */     return getWebsite().getTemplateRel(tpl, request);
/*     */   }
/*     */ 
/*     */   public String getTplContentRel(HttpServletRequest request)
/*     */   {
/*  80 */     String tpl = getTplContent();
/*  81 */     if (StringUtils.isBlank(tpl)) {
/*  82 */       tpl = getType().getContentPrefix();
/*  83 */       return getWebsite().getTemplate("product", tpl, request);
/*     */     }
/*  85 */     return getWebsite().getTemplateRel(tpl, request);
/*     */   }
/*     */ 
/*     */   public Category getTopCategory()
/*     */   {
/*  94 */     Category ctg = this;
/*  95 */     Category parent = ctg.getParent();
/*  96 */     while (parent != null) {
/*  97 */       ctg = parent;
/*  98 */       parent = parent.getParent();
/*     */     }
/* 100 */     return ctg;
/*     */   }
/*     */ 
/*     */   public List<Category> getCategoryList()
/*     */   {
/* 109 */     List list = new LinkedList();
/* 110 */     Category ctg = this;
/* 111 */     while (ctg != null) {
/* 112 */       list.add(0, ctg);
/* 113 */       ctg = ctg.getParent();
/*     */     }
/* 115 */     return list;
/*     */   }
/*     */ 
/*     */   public int getDeep()
/*     */   {
/* 124 */     int deep = 0;
/* 125 */     Category parent = getParent();
/* 126 */     while (parent != null) {
/* 127 */       deep++;
/* 128 */       parent = parent.getParent();
/*     */     }
/* 130 */     return deep;
/*     */   }
/*     */ 
/*     */   public boolean isLeaf()
/*     */   {
/* 139 */     Set set = getChild();
/* 140 */     return (set != null) && (set.size() > 0);
/*     */   }
/*     */ 
/*     */   public void addToChild(Category category)
/*     */   {
/* 149 */     Set set = getChild();
/* 150 */     if (set == null) {
/* 151 */       set = new TreeSet(PriorityComparator.INSTANCE);
/* 152 */       setChild(set);
/*     */     }
/* 154 */     set.add(category);
/*     */   }
/*     */ 
/*     */   public void removeFromChild(Category category)
/*     */   {
/* 163 */     Set set = getChild();
/* 164 */     if (set != null)
/* 165 */       set.remove(category);
/*     */   }
/*     */ 
/*     */   public String getTreeCondition()
/*     */   {
/* 176 */     return "bean.website.id=" + getWebsite().getId();
/*     */   }
/*     */ 
/*     */   public Integer getParentId()
/*     */   {
/* 184 */     Category parent = getParent();
/* 185 */     if (parent != null) {
/* 186 */       return parent.getId();
/*     */     }
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */   public Long[] getBrandIds()
/*     */   {
/* 198 */     Set set = getBrands();
/* 199 */     if (set == null) {
/* 200 */       return null;
/*     */     }
/* 202 */     Long[] ids = new Long[set.size()];
/* 203 */     int i = 0;
/* 204 */     for (Brand brand : set) {
/* 205 */       ids[i] = brand.getId();
/* 206 */       i++;
/*     */     }
/* 208 */     return ids;
/*     */   }
/*     */ 
/*     */   public void addToBrands(Brand brand)
/*     */   {
/* 219 */     Set set = getBrands();
/* 220 */     if (set == null) {
/* 221 */       set = new HashSet();
/* 222 */       setBrands(set);
/*     */     }
/* 224 */     set.add(brand);
/* 225 */     brand.addToCategory(this);
/*     */   }
/*     */ 
/*     */   public void addToStandardTypes(StandardType sType)
/*     */   {
/* 236 */     Set set = getStandardType();
/* 237 */     if (set == null) {
/* 238 */       set = new HashSet();
/* 239 */       setStandardType(set);
/*     */     }
/* 241 */     set.add(sType);
/* 242 */     sType.addToCategory(this);
/*     */   }
/*     */ 
/*     */   public Long[] getStandardTypeIds()
/*     */   {
/* 251 */     Set set = getStandardType();
/* 252 */     if (set == null) {
/* 253 */       return null;
/*     */     }
/* 255 */     Long[] ids = new Long[set.size()];
/* 256 */     int i = 0;
/* 257 */     for (StandardType st : set) {
/* 258 */       ids[i] = st.getId();
/* 259 */       i++;
/*     */     }
/* 261 */     return ids;
/*     */   }
/*     */ 
/*     */   public String getLftName()
/*     */   {
/* 271 */     return "lft";
/*     */   }
/*     */ 
/*     */   public String getParentName()
/*     */   {
/* 279 */     return "parent";
/*     */   }
/*     */ 
/*     */   public String getRgtName()
/*     */   {
/* 287 */     return "rgt";
/*     */   }
/*     */ 
/*     */   public Category()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Category(Integer id)
/*     */   {
/* 299 */     super(id);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson() throws JSONException {
/* 303 */     JSONObject json = new JSONObject();
/* 304 */     json.put("id", CommonUtils.parseId(getId()));
/* 305 */     json.put("parentId", CommonUtils.parseInteger(getParentId()));
/* 306 */     json.put("categoryName", CommonUtils.parseStr(getName()));
/* 307 */     json.put("leval", CommonUtils.parseInteger(Integer.valueOf(getLeval())));
/* 308 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson1() throws JSONException {
/* 312 */     JSONObject json = new JSONObject();
/* 313 */     json.put("id", CommonUtils.parseId(getId()));
/* 314 */     json.put("categoryName", CommonUtils.parseStr(getName()));
/* 315 */     json.put("path", CommonUtils.parseStr(getPath()));
/* 316 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/* 317 */     json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
/* 318 */     json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
/* 319 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson2(boolean isRoot) throws JSONException {
/* 323 */     JSONObject json = new JSONObject();
/* 324 */     json.put("id", CommonUtils.parseId(getId()));
/* 325 */     json.put("name", CommonUtils.parseStr(getName()));
/* 326 */     json.put("isRoot", CommonUtils.parseBoolean(Boolean.valueOf(isRoot)));
/* 327 */     json.put("isChild", getChild().size() > 0);
/* 328 */     json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
/* 329 */     json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
/* 330 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson3() throws JSONException {
/* 334 */     JSONObject json = new JSONObject();
/* 335 */     json.put("id", CommonUtils.parseId(getId()));
/* 336 */     json.put("name", CommonUtils.parseStr(getName()));
/* 337 */     json.put("parentId", CommonUtils.parseInteger(getParentId()));
/* 338 */     json.put("path", CommonUtils.parseStr(getPath()));
/* 339 */     json.put("typeName", getType() == null ? "" : CommonUtils.parseStr(getType().getName()));
/* 340 */     json.put("typeId", getType() == null ? "" : CommonUtils.parseLong(getType().getId()));
/* 341 */     json.put("title", CommonUtils.parseStr(getTitle()));
/* 342 */     json.put("keywords", CommonUtils.parseStr(getKeywords()));
/* 343 */     json.put("description", CommonUtils.parseStr(getDescription()));
/* 344 */     json.put("tplChannel", CommonUtils.parseStr(getTplChannel()));
/* 345 */     json.put("tplContent", CommonUtils.parseStr(getTplContent()));
/* 346 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/* 347 */     json.put("imagePath", CommonUtils.parseStr(getImagePath()));
/* 348 */     json.put("colorsize", CommonUtils.parseBoolean(getColorsize()));
/*     */ 
/* 350 */     return json;
/*     */   }
/*     */ 
/*     */   public Category(Integer id, ProductType type, Website website, String name, String path, Integer lft, Integer rgt, Integer priority)
/*     */   {
/* 375 */     super(id, 
/* 369 */       type, 
/* 370 */       website, 
/* 371 */       name, 
/* 372 */       path, 
/* 373 */       lft, 
/* 374 */       rgt, 
/* 375 */       priority);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Category
 * JD-Core Version:    0.6.0
 */