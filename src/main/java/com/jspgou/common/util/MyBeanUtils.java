/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Locale;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class MyBeanUtils
/*     */ {
/*     */   public static Object getFieldValue(Object object, String fieldName)
/*     */   {
/*  16 */     Field field = getDeclaredField(object, fieldName);
/*     */ 
/*  18 */     if (field == null) {
/*  19 */       throw new IllegalArgumentException("Could not find field [" + 
/*  20 */         fieldName + "] on target [" + object + "]");
/*     */     }
/*     */ 
/*  23 */     makeAccessible(field);
/*     */ 
/*  25 */     Object result = null;
/*     */     try {
/*  27 */       result = field.get(object);
/*     */     } catch (IllegalAccessException e) {
/*  29 */       throw new RuntimeException("never happend exception!", e);
/*     */     }
/*  31 */     return result;
/*     */   }
/*     */ 
/*     */   public static void setFieldValue(Object object, String fieldName, Object value)
/*     */   {
/*  39 */     Field field = getDeclaredField(object, fieldName);
/*     */ 
/*  41 */     if (field == null) {
/*  42 */       throw new IllegalArgumentException("Could not find field [" + 
/*  43 */         fieldName + "] on target [" + object + "]");
/*     */     }
/*     */ 
/*  46 */     makeAccessible(field);
/*     */     try
/*     */     {
/*  49 */       field.set(object, value);
/*     */     } catch (IllegalAccessException e) {
/*  51 */       throw new RuntimeException("never happend exception!", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static Field getDeclaredField(Object object, String fieldName)
/*     */   {
/*  61 */     Assert.notNull(object);
/*  62 */     return getDeclaredField(object.getClass(), fieldName);
/*     */   }
/*     */ 
/*     */   protected static Field getDeclaredField(Class clazz, String fieldName)
/*     */   {
/*  71 */     Assert.notNull(clazz);
/*  72 */     Assert.hasText(fieldName);
/*  73 */     for (Class superClass = clazz; superClass != Object.class; ) {
/*     */       try
/*     */       {
/*  76 */         return superClass.getDeclaredField(fieldName);
/*     */       }
/*     */       catch (NoSuchFieldException localNoSuchFieldException)
/*     */       {
/*  73 */         superClass = superClass
/*  74 */           .getSuperclass();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   protected static void makeAccessible(Field field)
/*     */   {
/*  88 */     if ((!Modifier.isPublic(field.getModifiers())) || 
/*  89 */       (!Modifier.isPublic(field.getDeclaringClass().getModifiers())))
/*  90 */       field.setAccessible(true);
/*     */   }
/*     */ 
/*     */   public static Object getSimpleProperty(Object bean, String propName)
/*     */     throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
/*     */   {
/*  98 */     return bean.getClass().getMethod(getReadMethod(propName), new Class[0]).invoke(bean, new Object[0]);
/*     */   }
/*     */ 
/*     */   private static String getReadMethod(String name) {
/* 102 */     return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + 
/* 103 */       name.substring(1);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.MyBeanUtils
 * JD-Core Version:    0.6.0
 */