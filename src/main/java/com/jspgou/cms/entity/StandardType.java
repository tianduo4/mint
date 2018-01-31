/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseStandardType;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class StandardType extends BaseStandardType
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public static Long[] fetchIds(Collection<StandardType> standardTypes)
/*     */   {
/*  28 */     Long[] ids = new Long[standardTypes.size()];
/*  29 */     int i = 0;
/*  30 */     for (StandardType sdt : standardTypes) {
/*  31 */       ids[(i++)] = sdt.getId();
/*     */     }
/*  33 */     return ids;
/*     */   }
/*     */ 
/*     */   public void removeFromCategorys(Category category) {
/*  37 */     Set set = getCategorys();
/*  38 */     if (set != null)
/*  39 */       set.remove(category);
/*     */   }
/*     */ 
/*     */   public void addToCategory(Category category)
/*     */   {
/*  44 */     Set set = getCategorys();
/*  45 */     if (set == null) {
/*  46 */       set = new HashSet();
/*  47 */       setCategorys(set);
/*     */     }
/*  49 */     set.add(category);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson(Long[] standardTypeIds) throws JSONException {
/*  53 */     JSONObject json = new JSONObject();
/*  54 */     json.put("id", CommonUtils.parseId(getId()));
/*  55 */     json.put("name", CommonUtils.parseStr(getName()));
/*  56 */     json.put("remark", CommonUtils.parseStr(getRemark()));
/*  57 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/*  58 */     if (standardTypeIds != null) {
/*  59 */       json.put("standardTypeIds", standardTypeIds);
/*     */     }
/*  61 */     return json;
/*     */   }
/*     */   public JSONObject convertToJson1() throws JSONException {
/*  64 */     JSONObject json = new JSONObject();
/*  65 */     json.put("id", CommonUtils.parseId(getId()));
/*  66 */     json.put("name", CommonUtils.parseStr(getName()));
/*  67 */     json.put("remark", CommonUtils.parseStr(getRemark()));
/*  68 */     json.put("field", CommonUtils.parseStr(getField()));
/*  69 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/*  70 */     json.put("dataType", CommonUtils.parseBoolean(Boolean.valueOf(getDataType())));
/*     */ 
/*  72 */     if (getStandardSet() != null) {
/*  73 */       Set standards = getStandardSet();
/*  74 */       JSONArray jsonArray = new JSONArray();
/*  75 */       for (Standard standard : standards) {
/*  76 */         JSONObject obj = new JSONObject();
/*  77 */         obj.put("standardId", CommonUtils.parseId(standard.getId()));
/*  78 */         obj.put("standardName", CommonUtils.parseStr(standard.getName()));
/*  79 */         jsonArray.put(obj);
/*     */       }
/*  81 */       json.put("standard", jsonArray);
/*     */     }
/*     */ 
/*  84 */     return json;
/*     */   }
/*     */ 
/*     */   public StandardType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StandardType(Long id)
/*     */   {
/*  96 */     super(id);
/*     */   }
/*     */ 
/*     */   public StandardType(Long id, String name, String field, boolean dataType)
/*     */   {
/* 112 */     super(id, 
/* 110 */       name, 
/* 111 */       field, 
/* 112 */       dataType);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.StandardType
 * JD-Core Version:    0.6.0
 */