/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ConvertMapToJson
/*     */ {
/*     */   private static final String QUOTE = "\"";
/*     */ 
/*     */   public static String buildJsonBody(Map<String, Object> body, int tabCount, boolean addComma)
/*     */   {
/*  12 */     StringBuilder sbJsonBody = new StringBuilder();
/*  13 */     sbJsonBody.append("{");
/*  14 */     Set<String> keySet = body.keySet();
/*  15 */     int count = 0;
/*  16 */     int size = keySet.size();
/*  17 */     for (String key : keySet) {
/*  18 */       count++;
/*  19 */       sbJsonBody.append(buildJsonField(key, body.get(key), tabCount + 1, count != size));
/*     */     }
/*  21 */     sbJsonBody.append("}");
/*  22 */     return sbJsonBody.toString();
/*     */   }
/*     */ 
/*     */   private static String buildJsonField(String key, Object value, int tabCount, boolean addComma) {
/*  26 */     StringBuilder sbJsonField = new StringBuilder();
/*  27 */     sbJsonField.append("\"").append(key).append("\"").append(": ");
/*  28 */     sbJsonField.append(buildJsonValue(value, tabCount, addComma));
/*  29 */     return sbJsonField.toString();
/*     */   }
/*     */ 
/*     */   private static String buildJsonValue(Object value, int tabCount, boolean addComma)
/*     */   {
/*  41 */     StringBuilder sbJsonValue = new StringBuilder();
/*  42 */     if ((value instanceof String))
/*  43 */       sbJsonValue.append("\"").append(value).append("\"");
/*  44 */     else if (((value instanceof Integer)) || ((value instanceof Long)) || ((value instanceof Double)))
/*  45 */       sbJsonValue.append(value);
/*  46 */     else if ((value instanceof Date))
/*  47 */       sbJsonValue.append("\"").append(formatDate((Date)value)).append("\"");
/*  48 */     else if ((value.getClass().isArray()) || ((value instanceof Collection)))
/*  49 */       sbJsonValue.append(buildJsonArray(value, tabCount, addComma));
/*  50 */     else if ((value instanceof Map)) {
/*  51 */       sbJsonValue.append(buildJsonBody((Map)value, tabCount, addComma));
/*     */     }
/*  53 */     sbJsonValue.append(buildJsonTail(addComma));
/*  54 */     return sbJsonValue.toString();
/*     */   }
/*     */ 
/*     */   private static String buildJsonArray(Object value, int tabCount, boolean addComma)
/*     */   {
/*  67 */     StringBuilder sbJsonArray = new StringBuilder();
/*  68 */     sbJsonArray.append("[\n");
/*  69 */     Object[] objArray = null;
/*  70 */     if (value.getClass().isArray())
/*  71 */       objArray = (Object[])value;
/*  72 */     else if ((value instanceof Collection))
/*     */     {
/*  74 */       objArray = ((Collection)value).toArray();
/*     */     }
/*  76 */     int size = objArray.length;
/*  77 */     int count = 0;
/*     */ 
/*  79 */     for (Object obj : objArray)
/*     */     {
/*  82 */       count++; sbJsonArray.append(buildJsonValue(obj, tabCount + 1, count != size));
/*     */     }
/*  84 */     sbJsonArray.append("]");
/*  85 */     return sbJsonArray.toString();
/*     */   }
/*     */ 
/*     */   private static String buildJsonTail(boolean addComma)
/*     */   {
/*  95 */     return addComma ? "," : "";
/*     */   }
/*     */ 
/*     */   private static String formatDate(Date date)
/*     */   {
/* 105 */     return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.ConvertMapToJson
 * JD-Core Version:    0.6.0
 */