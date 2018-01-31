/*     */ package com.jspgou.common.developer;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class ModuleGenerator
/*     */ {
/*  16 */   private static final Logger log = LoggerFactory.getLogger(ModuleGenerator.class);
/*  17 */   public static final String SPT = File.separator;
/*     */   public static final String ENCODING = "UTF-8";
/*  21 */   private Properties prop = new Properties();
/*     */   private String packName;
/*     */   private String fileName;
/*     */   private File daoImplFile;
/*     */   private File daoFile;
/*     */   private File managerFile;
/*     */   private File managerImplFile;
/*     */   private File actionFile;
/*     */   private File pageListFile;
/*     */   private File pageEditFile;
/*     */   private File pageAddFile;
/*     */   private File daoImplTpl;
/*     */   private File daoTpl;
/*     */   private File managerTpl;
/*     */   private File managerImplTpl;
/*     */   private File actionTpl;
/*     */   private File pageListTpl;
/*     */   private File pageEditTpl;
/*     */   private File pageAddTpl;
/*     */ 
/*     */   public ModuleGenerator(String s, String s1)
/*     */   {
/*  42 */     this.prop = new Properties();
/*  43 */     this.packName = s;
/*  44 */     this.fileName = s1;
/*     */   }
/*     */ 
/*     */   private void loadProperties() {
/*     */     try {
/*  49 */       log.debug("packName=" + this.packName);
/*  50 */       log.debug("fileName=" + this.fileName);
/*  51 */       FileInputStream fileinput = new FileInputStream(getFilePath(this.packName, this.fileName));
/*  52 */       this.prop.load(fileinput);
/*  53 */       String entityUp = this.prop.getProperty("Entity");
/*  54 */       log.debug("entityUp:" + entityUp);
/*  55 */       if ((entityUp == null) || (entityUp.trim().equals(""))) {
/*  56 */         log.warn("Entity not specified, exit!");
/*  57 */         return;
/*     */       }
/*  59 */       String entityLow = entityUp.substring(0, 1).toLowerCase() + 
/*  60 */         entityUp.substring(1);
/*  61 */       log.debug("entityLow:" + entityLow);
/*  62 */       this.prop.put("entity", entityLow);
/*  63 */       if (log.isDebugEnabled()) {
/*  64 */         Set ps = this.prop.keySet();
/*  65 */         for (Iterator localIterator = ps.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
/*  66 */           log.debug(o + "=" + this.prop.get(o)); }
/*     */       }
/*     */     }
/*     */     catch (FileNotFoundException filenotfoundexception) {
/*  70 */       filenotfoundexception.printStackTrace();
/*     */     } catch (IOException ioexception) {
/*  72 */       ioexception.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void prepareFile()
/*     */   {
/*  78 */     String daoImplFilePath = getFilePath(this.prop.getProperty("dao_impl_p"), 
/*  79 */       this.prop.getProperty("Entity") + "DaoImpl.java");
/*  80 */     this.daoImplFile = new File(daoImplFilePath);
/*  81 */     log.debug("daoImplFile:" + this.daoImplFile.getAbsolutePath());
/*     */ 
/*  83 */     String daoFilePath = getFilePath(this.prop.getProperty("dao_p"), this.prop
/*  84 */       .getProperty("Entity") + 
/*  85 */       "Dao.java");
/*  86 */     this.daoFile = new File(daoFilePath);
/*  87 */     log.debug("daoFile:" + this.daoFile.getAbsolutePath());
/*     */ 
/*  89 */     String managerFilePath = getFilePath(this.prop.getProperty("manager_p"), 
/*  90 */       this.prop.getProperty("Entity") + "Mng.java");
/*  91 */     this.managerFile = new File(managerFilePath);
/*  92 */     log.debug("managerFile:" + this.managerFile.getAbsolutePath());
/*     */ 
/*  94 */     String managerImplFilePath = getFilePath(this.prop
/*  95 */       .getProperty("manager_impl_p"), this.prop.getProperty("Entity") + 
/*  96 */       "MngImpl.java");
/*  97 */     this.managerImplFile = new File(managerImplFilePath);
/*  98 */     log.debug("managerImplFile:" + this.managerImplFile.getAbsolutePath());
/*     */ 
/* 100 */     String actionFilePath = getFilePath(this.prop.getProperty("action_p"), this.prop
/* 101 */       .getProperty("Entity") + 
/* 102 */       "Act.java");
/* 103 */     this.actionFile = new File(actionFilePath);
/* 104 */     log.debug("actionFile:" + this.actionFile.getAbsolutePath());
/*     */ 
/* 106 */     String pagePath = "WebContent/WEB-INF/" + 
/* 107 */       this.prop.getProperty("config_sys") + "/" + 
/* 108 */       this.prop.getProperty("config_entity") + "/";
/* 109 */     this.pageListFile = new File(pagePath + "list.html");
/* 110 */     log.debug("pageListFile:" + this.pageListFile.getAbsolutePath());
/* 111 */     this.pageEditFile = new File(pagePath + "edit.html");
/* 112 */     log.debug("pageEditFile:" + this.pageEditFile.getAbsolutePath());
/* 113 */     this.pageAddFile = new File(pagePath + "add.html");
/* 114 */     log.debug("pageAddFile:" + this.pageAddFile.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   private void prepareTemplate() {
/* 118 */     String tplPack = this.prop.getProperty("template_dir");
/* 119 */     log.debug("tplPack:" + tplPack);
/* 120 */     this.daoImplTpl = new File(getFilePath(tplPack, "dao_impl.txt"));
/* 121 */     this.daoTpl = new File(getFilePath(tplPack, "dao.txt"));
/* 122 */     this.managerImplTpl = new File(getFilePath(tplPack, "manager_impl.txt"));
/* 123 */     this.managerTpl = new File(getFilePath(tplPack, "manager.txt"));
/* 124 */     this.actionTpl = new File(getFilePath(tplPack, "action.txt"));
/* 125 */     this.pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
/* 126 */     this.pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
/* 127 */     this.pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
/*     */   }
/*     */ 
/*     */   private static void stringToFile(File file, String s) throws IOException {
/* 131 */     FileUtils.writeStringToFile(file, s, "UTF-8");
/*     */   }
/*     */ 
/*     */   private void writeFile() {
/*     */     try {
/* 136 */       if ("true".equals(this.prop.getProperty("is_dao"))) {
/* 137 */         stringToFile(this.daoImplFile, readTpl(this.daoImplTpl));
/* 138 */         stringToFile(this.daoFile, readTpl(this.daoTpl));
/*     */       }
/* 140 */       if ("true".equals(this.prop.getProperty("is_manager"))) {
/* 141 */         stringToFile(this.managerImplFile, readTpl(this.managerImplTpl));
/* 142 */         stringToFile(this.managerFile, readTpl(this.managerTpl));
/*     */       }
/* 144 */       if ("true".equals(this.prop.getProperty("is_action"))) {
/* 145 */         stringToFile(this.actionFile, readTpl(this.actionTpl));
/*     */       }
/* 147 */       if ("true".equals(this.prop.getProperty("is_page"))) {
/* 148 */         stringToFile(this.pageListFile, readTpl(this.pageListTpl));
/* 149 */         stringToFile(this.pageAddFile, readTpl(this.pageAddTpl));
/* 150 */         stringToFile(this.pageEditFile, readTpl(this.pageEditTpl));
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 154 */       log.warn("write file faild! " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private String readTpl(File file) {
/* 159 */     String content = null;
/*     */     try {
/* 161 */       content = FileUtils.readFileToString(file, "UTF-8");
/* 162 */       Set ps = this.prop.keySet();
/* 163 */       for (Iterator localIterator = ps.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
/* 164 */         String key = (String)o;
/* 165 */         String value = this.prop.getProperty(key);
/* 166 */         content = content.replaceAll("\\#\\{" + key + "\\}", value); }
/*     */     }
/*     */     catch (IOException e) {
/* 169 */       log.warn("read file faild. " + e.getMessage());
/*     */     }
/* 171 */     return content;
/*     */   }
/*     */ 
/*     */   private String getFilePath(String packageName, String name) {
/* 175 */     log.debug("replace:" + packageName);
/* 176 */     String path = packageName.replaceAll("\\.", "/");
/* 177 */     log.debug("after relpace:" + path);
/* 178 */     return "src/" + path + "/" + name;
/*     */   }
/*     */ 
/*     */   public void generate() {
/* 182 */     loadProperties();
/* 183 */     prepareFile();
/* 184 */     prepareTemplate();
/* 185 */     writeFile();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 189 */     String packName = "com.jeecms.common.developer.template";
/* 190 */     String fileName = "template.properties";
/* 191 */     new ModuleGenerator(packName, fileName).generate();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.developer.ModuleGenerator
 * JD-Core Version:    0.6.0
 */