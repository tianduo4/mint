/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.common.upload.UploadUtils;
/*     */ import com.jspgou.common.util.MyFileUtils;
/*     */ import com.jspgou.core.entity.base.BaseFtp;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.SocketException;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.net.PrintCommandListener;
/*     */ import org.apache.commons.net.ftp.FTPClient;
/*     */ import org.apache.commons.net.ftp.FTPReply;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class Ftp extends BaseFtp
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   private static final Logger log = LoggerFactory.getLogger(Ftp.class);
/*     */ 
/*     */   public String storeByExt(String path, String ext, InputStream in)
/*     */     throws IOException
/*     */   {
/*  39 */     String fileName = UploadUtils.generateRamdonFilename(ext);
/*  40 */     String fileUrl = path + fileName;
/*  41 */     store(fileUrl, in);
/*  42 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   public String storeByExts(String path, String ext, InputStream in) {
/*  46 */     String fileName = UploadUtils.generateRamdonFilename(ext);
/*  47 */     String fileUrl = path + fileName;
/*  48 */     if (store(fileUrl, in) == 0) {
/*  49 */       return getUrl() + "/" + fileUrl;
/*     */     }
/*  51 */     return "";
/*     */   }
/*     */ 
/*     */   public void storeByExt(String path, InputStream in) throws IOException
/*     */   {
/*  56 */     store(path, in);
/*     */   }
/*     */ 
/*     */   public String storeByFilename(String filename, InputStream in) throws IOException
/*     */   {
/*  61 */     store(filename, in);
/*  62 */     return filename;
/*     */   }
/*     */ 
/*     */   public File retrieve(String name, String fileName) throws IOException {
/*  66 */     String path = System.getProperty("java.io.tmpdir");
/*  67 */     File file = new File(path, fileName);
/*  68 */     file = UploadUtils.getUniqueFile(file);
/*  69 */     FTPClient ftp = getClient();
/*  70 */     OutputStream output = new FileOutputStream(file);
/*  71 */     ftp.retrieveFile(getPath() + name, output);
/*  72 */     output.close();
/*  73 */     ftp.logout();
/*  74 */     ftp.disconnect();
/*  75 */     return file;
/*     */   }
/*     */ 
/*     */   public boolean restore(String name, File file) throws IOException {
/*  79 */     store(name, FileUtils.openInputStream(file));
/*  80 */     file.deleteOnExit();
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   public int storeByFloder(String folder, String rootPath)
/*     */   {
/*     */     try
/*     */     {
/*  88 */       FTPClient ftp = getClient();
/*  89 */       if (ftp != null) {
/*  90 */         List files = MyFileUtils.getFiles(new File(folder));
/*  91 */         for (File file : files) {
/*  92 */           String filename = getPath() + file.getName();
/*  93 */           String name = FilenameUtils.getName(filename);
/*  94 */           String path = FilenameUtils.getFullPath(filename);
/*  95 */           String fileAbsolutePath = file.getAbsolutePath();
/*  96 */           String fileRelativePath = fileAbsolutePath.substring(rootPath.length() + 1, fileAbsolutePath.indexOf(name));
/*  97 */           path = path + fileRelativePath.replace("\\", "/");
/*  98 */           if (!ftp.changeWorkingDirectory(path)) {
/*  99 */             String[] ps = StringUtils.split(path, '/');
/* 100 */             String p = "/";
/* 101 */             ftp.changeWorkingDirectory(p);
/* 102 */             for (String s : ps) {
/* 103 */               p = p + s + "/";
/* 104 */               if (!ftp.changeWorkingDirectory(p)) {
/* 105 */                 ftp.makeDirectory(s);
/* 106 */                 ftp.changeWorkingDirectory(p);
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 111 */           name = new String(name.getBytes(getEncoding()), "iso-8859-1");
/* 112 */           if (!file.isFile()) {
/* 113 */             ftp.makeDirectory(name);
/*     */           } else {
/* 115 */             InputStream in = new FileInputStream(file.getAbsolutePath());
/* 116 */             ftp.storeFile(name, in);
/* 117 */             in.close();
/*     */           }
/*     */         }
/* 120 */         ftp.logout();
/* 121 */         ftp.disconnect();
/*     */       }
/* 123 */       return 0;
/*     */     } catch (SocketException e) {
/* 125 */       log.error("ftp store error", e);
/* 126 */       return 3;
/*     */     } catch (IOException e) {
/* 128 */       log.error("ftp store error", e);
/* 129 */     }return 4;
/*     */   }
/*     */ 
/*     */   private int store(String remote, InputStream in)
/*     */   {
/*     */     try {
/* 135 */       FTPClient ftp = getClient();
/* 136 */       if (ftp != null) {
/* 137 */         String filename = getPath() + remote;
/* 138 */         String name = FilenameUtils.getName(filename);
/* 139 */         String path = FilenameUtils.getFullPath(filename);
/* 140 */         if (!ftp.changeWorkingDirectory(path)) {
/* 141 */           String[] ps = StringUtils.split(path, '/');
/* 142 */           String p = "/";
/* 143 */           ftp.changeWorkingDirectory(p);
/* 144 */           for (String s : ps) {
/* 145 */             p = p + s + "/";
/* 146 */             if (!ftp.changeWorkingDirectory(p)) {
/* 147 */               ftp.makeDirectory(s);
/* 148 */               ftp.changeWorkingDirectory(p);
/*     */             }
/*     */           }
/*     */         }
/* 152 */         ftp.storeFile(name, in);
/* 153 */         ftp.logout();
/* 154 */         ftp.disconnect();
/*     */       }
/* 156 */       in.close();
/* 157 */       return 0;
/*     */     } catch (SocketException e) {
/* 159 */       log.error("ftp store error", e);
/* 160 */       return 3;
/*     */     } catch (IOException e) {
/* 162 */       log.error("ftp store error", e);
/* 163 */     }return 4;
/*     */   }
/*     */ 
/*     */   private FTPClient getClient() throws SocketException, IOException
/*     */   {
/* 168 */     FTPClient ftp = new FTPClient();
/* 169 */     ftp.addProtocolCommandListener(
/* 170 */       new PrintCommandListener(new PrintWriter(System.out)));
/* 171 */     ftp.setDefaultPort(getPort().intValue());
/* 172 */     ftp.connect(getIp());
/* 173 */     int reply = ftp.getReplyCode();
/* 174 */     if (!FTPReply.isPositiveCompletion(reply)) {
/* 175 */       log.warn("FTP server refused connection: {}", getIp());
/* 176 */       ftp.disconnect();
/* 177 */       return null;
/*     */     }
/* 179 */     if (!ftp.login(getUsername(), getPassword())) {
/* 180 */       log.warn("FTP server refused login: {}, user: {}", getIp(), 
/* 181 */         getUsername());
/* 182 */       ftp.logout();
/* 183 */       ftp.disconnect();
/* 184 */       return null;
/*     */     }
/* 186 */     ftp.setControlEncoding(getEncoding());
/* 187 */     ftp.setFileType(2);
/* 188 */     ftp.enterLocalPassiveMode();
/* 189 */     return ftp;
/*     */   }
/*     */ 
/*     */   public Ftp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Ftp(Integer id)
/*     */   {
/* 201 */     super(id);
/*     */   }
/*     */ 
/*     */   public Ftp(Integer id, String name, String ip, Integer port, String encoding, String url)
/*     */   {
/* 221 */     super(id, 
/* 217 */       name, 
/* 218 */       ip, 
/* 219 */       port, 
/* 220 */       encoding, 
/* 221 */       url);
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() {
/* 225 */     JSONObject json = new JSONObject();
/* 226 */     json.put("id", CommonUtils.parseId(getId()));
/* 227 */     json.put("name", CommonUtils.parseStr(getName()));
/* 228 */     json.put("ip", CommonUtils.parseStr(getIp()));
/* 229 */     json.put("port", Integer.valueOf(CommonUtils.parseInteger(getPort())));
/* 230 */     json.put("username", CommonUtils.parseStr(getUsername()));
/* 231 */     json.put("password", "");
/* 232 */     json.put("encoding", CommonUtils.parseStr(getEncoding()));
/* 233 */     json.put("timeout", Integer.valueOf(CommonUtils.parseInteger(getTimeout())));
/* 234 */     json.put("path", CommonUtils.parseStr(getPath()));
/* 235 */     json.put("url", CommonUtils.parseStr(getUrl()));
/*     */ 
/* 237 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Ftp
 * JD-Core Version:    0.6.0
 */