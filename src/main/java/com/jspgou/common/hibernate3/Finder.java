/*     */ package com.jspgou.common.hibernate3;
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
/* 345 */   private int firstResult = 0;
/*     */ 
/* 347 */   private int maxResults = 0;
/*     */ 
/* 349 */   private boolean cacheable = false;
/*     */   public static final String ROW_COUNT = "select count(*) ";
/*     */   public static final String FROM = "from";
/*     */   public static final String DISTINCT = "distinct";
/*     */   public static final String HQL_FETCH = "fetch";
/*     */   public static final String ORDER_BY = "order";
/*     */ 
/*     */   protected Finder()
/*     */   {
/*  21 */     this.hqlBuilder = new StringBuilder();
/*     */   }
/*     */ 
/*     */   protected Finder(String hql) {
/*  25 */     this.hqlBuilder = new StringBuilder(hql);
/*     */   }
/*     */ 
/*     */   public static Finder create() {
/*  29 */     return new Finder();
/*     */   }
/*     */ 
/*     */   public static Finder create(String hql) {
/*  33 */     return new Finder(hql);
/*     */   }
/*     */ 
/*     */   public Finder append(String hql) {
/*  37 */     this.hqlBuilder.append(hql);
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public String getOrigHql()
/*     */   {
/*  47 */     return this.hqlBuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getRowCountHql()
/*     */   {
/*  56 */     String hql = this.hqlBuilder.toString();
/*     */ 
/*  58 */     int fromIndex = hql.toLowerCase().indexOf("from");
/*  59 */     String projectionHql = hql.substring(0, fromIndex);
/*     */ 
/*  61 */     hql = hql.substring(fromIndex);
/*  62 */     String rowCountHql = hql.replace("fetch", "");
/*     */ 
/*  64 */     int index = rowCountHql.indexOf("order");
/*  65 */     if (index > 0) {
/*  66 */       rowCountHql = rowCountHql.substring(0, index);
/*     */     }
/*  68 */     return wrapProjection(projectionHql) + rowCountHql;
/*     */   }
/*     */ 
/*     */   public int getFirstResult() {
/*  72 */     return this.firstResult;
/*     */   }
/*     */ 
/*     */   public void setFirstResult(int firstResult) {
/*  76 */     this.firstResult = firstResult;
/*     */   }
/*     */ 
/*     */   public int getMaxResults() {
/*  80 */     return this.maxResults;
/*     */   }
/*     */ 
/*     */   public void setMaxResults(int maxResults) {
/*  84 */     this.maxResults = maxResults;
/*     */   }
/*     */ 
/*     */   public boolean isCacheable()
/*     */   {
/*  93 */     return this.cacheable;
/*     */   }
/*     */ 
/*     */   public void setCacheable(boolean cacheable)
/*     */   {
/* 103 */     this.cacheable = cacheable;
/*     */   }
/*     */ 
/*     */   public Finder setParam(String param, Object value)
/*     */   {
/* 115 */     return setParam(param, value, null);
/*     */   }
/*     */ 
/*     */   public Finder setParam(String param, Object value, Type type)
/*     */   {
/* 128 */     getParams().add(param);
/* 129 */     getValues().add(value);
/* 130 */     getTypes().add(type);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParams(Map<String, Object> paramMap)
/*     */   {
/* 142 */     for (Map.Entry entry : paramMap.entrySet()) {
/* 143 */       setParam((String)entry.getKey(), entry.getValue());
/*     */     }
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Collection<Object> vals, Type type)
/*     */   {
/* 158 */     getParamsList().add(name);
/* 159 */     getValuesList().add(vals);
/* 160 */     getTypesList().add(type);
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Collection<Object> vals)
/*     */   {
/* 173 */     return setParamList(name, vals, null);
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Object[] vals, Type type)
/*     */   {
/* 186 */     getParamsArray().add(name);
/* 187 */     getValuesArray().add(vals);
/* 188 */     getTypesArray().add(type);
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   public Finder setParamList(String name, Object[] vals)
/*     */   {
/* 201 */     return setParamList(name, vals, null);
/*     */   }
/*     */ 
/*     */   public Query setParamsToQuery(Query query)
/*     */   {
/* 210 */     if (this.params != null) {
/* 211 */       for (int i = 0; i < this.params.size(); i++) {
/* 212 */         if (this.types.get(i) == null)
/* 213 */           query.setParameter((String)this.params.get(i), this.values.get(i));
/*     */         else {
/* 215 */           query.setParameter((String)this.params.get(i), this.values.get(i), 
/* 216 */             (Type)this.types.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 220 */     if (this.paramsList != null) {
/* 221 */       for (int i = 0; i < this.paramsList.size(); i++) {
/* 222 */         if (this.typesList.get(i) == null)
/* 223 */           query
/* 224 */             .setParameterList((String)this.paramsList.get(i), 
/* 225 */             (Collection)this.valuesList.get(i));
/*     */         else {
/* 227 */           query.setParameterList((String)this.paramsList.get(i), 
/* 228 */             (Collection)this.valuesList.get(i), (Type)this.typesList.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 232 */     if (this.paramsArray != null) {
/* 233 */       for (int i = 0; i < this.paramsArray.size(); i++) {
/* 234 */         if (this.typesArray.get(i) == null)
/* 235 */           query.setParameterList((String)this.paramsArray.get(i), 
/* 236 */             (Object[])this.valuesArray.get(i));
/*     */         else {
/* 238 */           query.setParameterList((String)this.paramsArray.get(i), 
/* 239 */             (Object[])this.valuesArray.get(i), (Type)this.typesArray.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 243 */     return query;
/*     */   }
/*     */ 
/*     */   public Query createQuery(Session s) {
/* 247 */     Query query = setParamsToQuery(s.createQuery(getOrigHql()));
/* 248 */     if (getFirstResult() > 0) {
/* 249 */       query.setFirstResult(getFirstResult());
/*     */     }
/* 251 */     if (getMaxResults() > 0) {
/* 252 */       query.setMaxResults(getMaxResults());
/*     */     }
/* 254 */     if (isCacheable()) {
/* 255 */       query.setCacheable(true);
/*     */     }
/* 257 */     return query;
/*     */   }
/*     */ 
/*     */   private String wrapProjection(String projection) {
/* 261 */     if (projection.indexOf("select") == -1) {
/* 262 */       return "select count(*) ";
/*     */     }
/* 264 */     return projection.replace("select", "select count(") + ") ";
/*     */   }
/*     */ 
/*     */   private List<String> getParams()
/*     */   {
/* 269 */     if (this.params == null) {
/* 270 */       this.params = new ArrayList();
/*     */     }
/* 272 */     return this.params;
/*     */   }
/*     */ 
/*     */   private List<Object> getValues() {
/* 276 */     if (this.values == null) {
/* 277 */       this.values = new ArrayList();
/*     */     }
/* 279 */     return this.values;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypes() {
/* 283 */     if (this.types == null) {
/* 284 */       this.types = new ArrayList();
/*     */     }
/* 286 */     return this.types;
/*     */   }
/*     */ 
/*     */   private List<String> getParamsList() {
/* 290 */     if (this.paramsList == null) {
/* 291 */       this.paramsList = new ArrayList();
/*     */     }
/* 293 */     return this.paramsList;
/*     */   }
/*     */ 
/*     */   private List<Collection<Object>> getValuesList() {
/* 297 */     if (this.valuesList == null) {
/* 298 */       this.valuesList = new ArrayList();
/*     */     }
/* 300 */     return this.valuesList;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypesList() {
/* 304 */     if (this.typesList == null) {
/* 305 */       this.typesList = new ArrayList();
/*     */     }
/* 307 */     return this.typesList;
/*     */   }
/*     */ 
/*     */   private List<String> getParamsArray() {
/* 311 */     if (this.paramsArray == null) {
/* 312 */       this.paramsArray = new ArrayList();
/*     */     }
/* 314 */     return this.paramsArray;
/*     */   }
/*     */ 
/*     */   private List<Object[]> getValuesArray() {
/* 318 */     if (this.valuesArray == null) {
/* 319 */       this.valuesArray = new ArrayList();
/*     */     }
/* 321 */     return this.valuesArray;
/*     */   }
/*     */ 
/*     */   private List<Type> getTypesArray() {
/* 325 */     if (this.typesArray == null) {
/* 326 */       this.typesArray = new ArrayList();
/*     */     }
/* 328 */     return this.typesArray;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 358 */     Finder find = 
/* 359 */       create("select distinct p FROM BookType join fetch p");
/* 360 */     System.out.println(find.getRowCountHql());
/* 361 */     System.out.println(find.getOrigHql());
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate3.Finder
 * JD-Core Version:    0.6.0
 */