/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.BeanFactory;
/*     */ import org.springframework.beans.factory.BeanFactoryAware;
/*     */ 
/*     */ public class PropertyUtils
/*     */   implements BeanFactoryAware
/*     */ {
/*     */   private BeanFactory beanFactory;
/*     */   private Properties properties;
/*     */ 
/*     */   public List<String> getList(String prefix)
/*     */   {
/*  29 */     if ((this.properties == null) || (prefix == null)) {
/*  30 */       return Collections.emptyList();
/*     */     }
/*  32 */     List list = new ArrayList();
/*  33 */     Enumeration en = this.properties.propertyNames();
/*     */ 
/*  35 */     while (en.hasMoreElements()) {
/*  36 */       String key = (String)en.nextElement();
/*  37 */       if (key.startsWith(prefix)) {
/*  38 */         list.add(this.properties.getProperty(key));
/*     */       }
/*     */     }
/*  41 */     return list;
/*     */   }
/*     */ 
/*     */   public Set<String> getSet(String prefix) {
/*  45 */     if ((this.properties == null) || (prefix == null)) {
/*  46 */       return Collections.emptySet();
/*     */     }
/*  48 */     Set set = new TreeSet();
/*  49 */     Enumeration en = this.properties.propertyNames();
/*     */ 
/*  51 */     while (en.hasMoreElements()) {
/*  52 */       String key = (String)en.nextElement();
/*  53 */       if (key.startsWith(prefix)) {
/*  54 */         set.add(this.properties.getProperty(key));
/*     */       }
/*     */     }
/*  57 */     return set;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getMap(String prefix)
/*     */   {
/*  62 */     if ((this.properties == null) || (prefix == null)) {
/*  63 */       return Collections.emptyMap();
/*     */     }
/*  65 */     Map map = new HashMap();
/*  66 */     Enumeration en = this.properties.propertyNames();
/*     */ 
/*  68 */     int len = prefix.length();
/*  69 */     while (en.hasMoreElements()) {
/*  70 */       String key = (String)en.nextElement();
/*  71 */       if (key.startsWith(prefix)) {
/*  72 */         map.put(key.substring(len), this.properties.getProperty(key));
/*     */       }
/*     */     }
/*  75 */     return map;
/*     */   }
/*     */ 
/*     */   public Properties getProperties(String prefix) {
/*  79 */     Properties props = new Properties();
/*  80 */     if ((this.properties == null) || (prefix == null)) {
/*  81 */       return props;
/*     */     }
/*  83 */     Enumeration en = this.properties.propertyNames();
/*     */ 
/*  85 */     int len = prefix.length();
/*  86 */     while (en.hasMoreElements()) {
/*  87 */       String key = (String)en.nextElement();
/*  88 */       if (key.startsWith(prefix)) {
/*  89 */         props.put(key.substring(len), this.properties.getProperty(key));
/*     */       }
/*     */     }
/*  92 */     return props;
/*     */   }
/*     */ 
/*     */   public String getPropertiesString(String prefix) {
/*  96 */     String property = "";
/*  97 */     if ((this.properties == null) || (prefix == null)) {
/*  98 */       return property;
/*     */     }
/* 100 */     Enumeration en = this.properties.propertyNames();
/*     */ 
/* 102 */     while (en.hasMoreElements()) {
/* 103 */       String key = (String)en.nextElement();
/* 104 */       if (key.equals(prefix)) {
/* 105 */         return this.properties.getProperty(key);
/*     */       }
/*     */     }
/* 108 */     return property;
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getBeanMap(String prefix) {
/* 112 */     Map keyMap = getMap(prefix);
/* 113 */     if (keyMap.isEmpty()) {
/* 114 */       return Collections.emptyMap();
/*     */     }
/* 116 */     Map resultMap = new HashMap(keyMap.size());
/*     */ 
/* 118 */     for (Map.Entry entry : keyMap.entrySet()) {
/* 119 */       String key = (String)entry.getKey();
/* 120 */       String value = (String)entry.getValue();
/* 121 */       resultMap.put(key, this.beanFactory.getBean(value, Object.class));
/*     */     }
/* 123 */     return resultMap;
/*     */   }
/*     */ 
/*     */   public static Properties getProperties(File file) {
/* 127 */     Properties props = new Properties();
/*     */     try
/*     */     {
/* 130 */       InputStream in = new FileInputStream(file);
/* 131 */       props.load(in);
/*     */     }
/*     */     catch (FileNotFoundException e) {
/* 134 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/* 137 */       e.printStackTrace();
/*     */     }
/* 139 */     return props;
/*     */   }
/*     */ 
/*     */   public static String getPropertyValue(File file, String key) {
/* 143 */     Properties props = getProperties(file);
/* 144 */     return (String)props.get(key);
/*     */   }
/*     */ 
/*     */   public void setProperties(Properties properties)
/*     */   {
/* 151 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
/* 155 */     this.beanFactory = beanFactory;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.PropertyUtils
 * JD-Core Version:    0.6.0
 */