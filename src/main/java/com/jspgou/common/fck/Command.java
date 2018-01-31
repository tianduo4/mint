/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Command
/*     */ {
/*     */   private String name;
/*  30 */   private static final Map<String, Command> getCommands = new HashMap(
/*  31 */     3);
/*     */ 
/*  32 */   private static final Map<String, Command> postCommands = new HashMap(
/*  33 */     2);
/*     */ 
/*  35 */   public static final Command GET_FOLDERS = new Command("GetFolders");
/*     */ 
/*  37 */   public static final Command GET_FOLDERS_AND_FILES = new Command(
/*  38 */     "GetFoldersAndFiles");
/*     */ 
/*  40 */   public static final Command CREATE_FOLDER = new Command("CreateFolder");
/*     */ 
/*  42 */   public static final Command FILE_UPLOAD = new Command("FileUpload");
/*     */ 
/*  44 */   public static final Command QUICK_UPLOAD = new Command("QuickUpload");
/*     */ 
/*     */   static
/*     */   {
/*  48 */     getCommands.put(GET_FOLDERS.getName(), GET_FOLDERS);
/*  49 */     getCommands.put(GET_FOLDERS_AND_FILES.getName(), GET_FOLDERS_AND_FILES);
/*  50 */     getCommands.put(CREATE_FOLDER.getName(), CREATE_FOLDER);
/*     */ 
/*  53 */     postCommands.put(FILE_UPLOAD.getName(), FILE_UPLOAD);
/*  54 */     postCommands.put(QUICK_UPLOAD.getName(), QUICK_UPLOAD);
/*     */   }
/*     */ 
/*     */   private Command(String name)
/*     */   {
/*  64 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  73 */     return this.name;
/*     */   }
/*     */ 
/*     */   public static Command valueOf(String name)
/*     */   {
/*  88 */     if (Utils.isEmpty(name)) {
/*  89 */       throw new NullPointerException("Name is null or empty");
/*     */     }
/*  91 */     Command command = (Command)getCommands.get(name);
/*  92 */     if (command == null)
/*  93 */       command = (Command)postCommands.get(name);
/*  94 */     if (command == null) {
/*  95 */       throw new IllegalArgumentException("No command const " + name);
/*     */     }
/*  97 */     return command;
/*     */   }
/*     */ 
/*     */   public static boolean isValidForGet(String name)
/*     */   {
/* 110 */     return getCommands.containsKey(name);
/*     */   }
/*     */ 
/*     */   public static boolean isValidForPost(String name)
/*     */   {
/* 123 */     return postCommands.containsKey(name);
/*     */   }
/*     */ 
/*     */   public static Command getCommand(String name)
/*     */   {
/*     */     try
/*     */     {
/* 138 */       return valueOf(name); } catch (Exception e) {
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 155 */     if (this == obj) {
/* 156 */       return true;
/*     */     }
/* 158 */     if ((obj == null) || (getClass() != obj.getClass())) {
/* 159 */       return false;
/*     */     }
/* 161 */     Command command = (Command)obj;
/* 162 */     return this.name.equals(command.getName());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 173 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 184 */     return this.name;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.Command
 * JD-Core Version:    0.6.0
 */