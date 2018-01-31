/*     */ package com.jspgou.common.hibernate4;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.type.Type;
/*     */ 
/*     */ public class Finder
/*     */ {
/*     */   private StringBuilder hqlBuilder;
/*     */   private List<String> params;
/*     */   private List<Object> values;
/*     */   private List<Type> types;
/*     */   private List<String> paramsList;
/*     */   private List<Collection<Object>> valuesList;
/*     */   private List<Type> typesList;
/*     */   private List<String> paramsArray;
/*     */   private List<Object[]> valuesArray;
/*     */   private List<Type> typesArray;
/* 379 */   private int firstResult = 0;
/*     */ 
/* 381 */   private int maxResults = 0;
/*     */ 
/* 383 */   private boolean cacheable = false;
/*     */   public static final String ROW_COUNT = "select count(*) ";
/*     */   public static final String FROM = "from";
/*     */   public static final String DISTINCT = "distinct";
/*     */   public static final String HQL_FETCH = "fetch";
/*     */   public static final String ORDER_BY = "order ";
/*     */   public static final String GROUP_BY = "group";
/*     */ 
/*     */   protected Finder()
/*     */   {
/*  20 */     this.hqlBuilder = new StringBuilder();
/*     */   }
/*     */ 
/*     */   protected Finder(String hql) {
/*  24 */     this.hqlBuilder = new StringBuilder(hql);
/*     */   }
/*     */ 
/*     */   public static Finder create() {
/*  28 */     return new Finder();
/*     */   }
/*     */ 
/*     */   public static Finder create(String hql) {
/*  32 */     return new Finder(hql);
/*     */   }
/*     */ 
/*     */   public Finder append(String hql) {
/*  36 */     this.hqlBuilder.append(hql);
/*  37 */     return this;
/*     */   }
/*     */ 
/*     */   public String getOrigHql()
/*     */   {
/*  46 */     return this.hqlBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getRowCountHql()
/*     */   {
/*  55 */     return getRowCountBaseHql("order ");
/*     */   }
/*     */ 
/*     */   public String getRowCountTotalHql(String selectSql) {
/*  59 */     return getRowCountTotalBaseHql("order ", selectSql);
/*     */   }
/*     */ 
/*     */   public String getRowCountHqlByGroup() {
/*  63 */     return getRowCountBaseHql("group");
/*     */   }
/*     */ 
/*     */   public int getFirstResult() {
/*  67 */     return this.firstResult;
/*     */   }
/*     */ 
/*     */   public void setFirstResult(int firstResult) {
/*  71 */     this.firstResult = firstResult;
/*     */   }
/*     */ 
/*     */   public int getMaxResults() {
/*  75 */     return this.maxResults;
/*     */   }
/*     */ 
/*     */   public void setMaxResults(int maxResults) {
/*  79 */     this.maxResults = maxResults;
/*     */   }
/*     */ 
/*     */   public boolean isCacheable()
/*     */   {
/*  88 */     return this.cacheable;
/*     */   }
/*     */ 
/*     */   public void setCacheable(boolean cacheable)
/*     */   {
/*  98 */     this.cacheable = cacheable;
/*     */   }
/*     */ 
/*     */   public Finder setParam(String param, Object value)
/*     */   {
/* 110 */     return setParam(param, value, null);
/*     */   }
/*     */ 
/*     */   public Finder setParam(String param, Object value, Type type)
/*     */   {
/* 123 */     getParams().add(param);
/* 124 */     getValues().add(value);
/* 125 */     getTypes().add(type);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParams(Map<String, Object> paramMap)
/*     */   {
/* 137 */     for (Map.Entry entry : paramMap.entrySet()) {
/* 138 */       setParam((String)entry.getKey(), entry.getValue());
/*     */     }
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Collection<Object> vals, Type type)
/*     */   {
/* 153 */     getParamsList().add(name);
/* 154 */     getValuesList().add(vals);
/* 155 */     getTypesList().add(type);
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Collection<Object> vals)
/*     */   {
/* 168 */     return setParamList(name, vals, null);
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Object[] vals, Type type)
/*     */   {
/* 181 */     getParamsArray().add(name);
/* 182 */     getValuesArray().add(vals);
/* 183 */     getTypesArray().add(type);
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Object[] vals)
/*     */   {
/* 196 */     return setParamList(name, vals, null);
/*     */   }
/*     */ 
/*     */   public Query setParamsToQuery(Query query)
/*     */   {
/* 205 */     if (this.params != null) {
/* 206 */       for (int i = 0; i < this.params.size(); i++) {
/* 207 */         if (this.types.get(i) == null)
/* 208 */           query.setParameter((String)this.params.get(i), this.values.get(i));
/*     */         else {
/* 210 */           query.setParameter((String)this.params.get(i), this.values.get(i), 
/* 211 */             (Type)this.types
/* 211 */             .get(i));
/*     */         }
/*     */       }
/*     */     }
/* 215 */     if (this.paramsList != null) {
/* 216 */       for (int i = 0; i < this.paramsList.size(); i++) {
/* 217 */         if (this.typesList.get(i) == null)
/* 218 */           query.setParameterList((String)this.paramsList.get(i), (Collection)this.valuesList.get(i));
/*     */         else {
/* 220 */           query.setParameterList((String)this.paramsList.get(i), (Collection)this.valuesList.get(i), (Type)this.typesList.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 224 */     if (this.paramsArray != null) {
/* 225 */       for (int i = 0; i < this.paramsArray.size(); i++) {
/* 226 */         if (this.typesArray.get(i) == null)
/* 227 */           query.setParameterList((String)this.paramsArray.get(i), 
/* 228 */             (Object[])this.valuesArray
/* 228 */             .get(i));
/*     */         else {
/* 230 */           query.setParameterList((String)this.paramsArray.get(i), 
/* 231 */             (Object[])this.valuesArray
/* 231 */             .get(i), (Type)this.typesArray.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 235 */     return query;
/*     */   }
/*     */ 
/*     */   public Query createQuery(Session s) {
/* 239 */     Query query = setParamsToQuery(s.createQuery(getOrigHql()));
/* 240 */     if (getFirstResult() > 0) {
/* 241 */       query.setFirstResult(getFirstResult());
/*     */     }
/* 243 */     if (getMaxResults() > 0) {
/* 244 */       query.setMaxResults(getMaxResults());
/*     */     }
/* 246 */     if (isCacheable()) {
/* 247 */       query.setCacheable(true);
/*     */     }
/* 249 */     return query;
/*     */   }
/*     */ 
/*     */   private String getRowCountBaseHql(String indexKey) {
/* 253 */     String hql = this.hqlBuilder.toString();
/*     */ 
/* 255 */     int fromIndex = hql.toLowerCase().indexOf("from");
/* 256 */     String projectionHql = hql.substring(0, fromIndex);
/*     */ 
/* 258 */     hql = hql.substring(fromIndex);
/* 259 */     String rowCountHql = hql.replace("fetch", "");
/*     */ 
/* 261 */     int index = rowCountHql.indexOf(indexKey);
/* 262 */     if (index > 0) {
/* 263 */       rowCountHql = rowCountHql.substring(0, index);
/*     */     }
/* 265 */     return wrapProjection(projectionHql) + rowCountHql;
/*     */   }
/*     */ 
/*     */   private String getRowCountTotalBaseHql(String indexKey, String selectSql) {
/* 269 */     String hql = this.hqlBuilder.toString();
/*     */ 
/* 271 */     int fromIndex = hql.toLowerCase().indexOf("from");
/* 272 */     String projectionHql = hql.substring(0, fromIndex);
/*     */ 
/* 274 */     hql = hql.substring(fromIndex);
/* 275 */     String rowCountHql = hql.replace("fetch", "");
/*     */ 
/* 277 */     int index = rowCountHql.indexOf(indexKey);
/* 278 */     if (index > 0) {
/* 279 */       rowCountHql = rowCountHql.substring(0, index);
/*     */     }
/*     */ 
/* 282 */     return "select count(bean) from ( " + projectionHql + rowCountHql + ") as a";
/*     */   }
/*     */ 
/*     */   private String wrapProjection(String projection) {
/* 286 */     if (projection.indexOf("select") == -1) {
/* 287 */       return "select count(*) ";
/*     */     }
/* 289 */     return projection.replace("select", "select count(") + ") ";
/*     */   }
/*     */ 
/*     */   private String wrapProjectionBeanId(String projection)
/*     */   {
/* 295 */     if (projection.indexOf("select") == -1) {
/* 296 */       return "select bean.id ";
/*     */     }
/* 298 */     return projection.replace("select bean", "select bean.id") + " ";
/*     */   }
/*     */ 
/*     */   private List<String> getParams()
/*     */   {
/* 303 */     if (this.params == null) {
/* 304 */       this.params = new ArrayList();
/*     */     }
/* 306 */     return this.params;
/*     */   }
/*     */ 
/*     */   private List<Object> getValues() {
/* 310 */     if (this.values == null) {
/* 311 */       this.values = new ArrayList();
/*     */     }
/* 313 */     return this.values;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypes() {
/* 317 */     if (this.types == null) {
/* 318 */       this.types = new ArrayList();
/*     */     }
/* 320 */     return this.types;
/*     */   }
/*     */ 
/*     */   private List<String> getParamsList() {
/* 324 */     if (this.paramsList == null) {
/* 325 */       this.paramsList = new ArrayList();
/*     */     }
/* 327 */     return this.paramsList;
/*     */   }
/*     */ 
/*     */   private List<Collection<Object>> getValuesList() {
/* 331 */     if (this.valuesList == null) {
/* 332 */       this.valuesList = new ArrayList();
/*     */     }
/* 334 */     return this.valuesList;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypesList() {
/* 338 */     if (this.typesList == null) {
/* 339 */       this.typesList = new ArrayList();
/*     */     }
/* 341 */     return this.typesList;
/*     */   }
/*     */ 
/*     */   private List<String> getParamsArray() {
/* 345 */     if (this.paramsArray == null) {
/* 346 */       this.paramsArray = new ArrayList();
/*     */     }
/* 348 */     return this.paramsArray;
/*     */   }
/*     */ 
/*     */   private List<Object[]> getValuesArray() {
/* 352 */     if (this.valuesArray == null) {
/* 353 */       this.valuesArray = new ArrayList();
/*     */     }
/* 355 */     return this.valuesArray;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypesArray() {
/* 359 */     if (this.typesArray == null) {
/* 360 */       this.typesArray = new ArrayList();
/*     */     }
/* 362 */     return this.typesArray;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 393 */     Finder find = 
/* 394 */       create("select distinct p FROM BookType join fetch p");
/* 395 */     System.out.println(find.getRowCountHql());
/* 396 */     System.out.println(find.getOrigHql());
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.Finder
 * JD-Core Version:    0.6.0
 */