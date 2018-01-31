/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseFtp
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "Ftp";
/*  18 */   public static String PROP_TIMEOUT = "timeout";
/*  19 */   public static String PROP_PASSWORD = "password";
/*  20 */   public static String PROP_ENCODING = "encoding";
/*  21 */   public static String PROP_PATH = "path";
/*  22 */   public static String PROP_URL = "url";
/*  23 */   public static String PROP_IP = "ip";
/*  24 */   public static String PROP_PORT = "port";
/*  25 */   public static String PROP_NAME = "name";
/*  26 */   public static String PROP_ID = "id";
/*  27 */   public static String PROP_USERNAME = "username";
/*     */ 
/*  67 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private String ip;
/*     */   private Integer port;
/*     */   private String username;
/*     */   private String password;
/*     */   private String encoding;
/*     */   private Integer timeout;
/*     */   private String path;
/*     */   private String url;
/*     */ 
/*     */   public BaseFtp()
/*     */   {
/*  32 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseFtp(Integer id)
/*     */   {
/*  39 */     setId(id);
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseFtp(Integer id, String name, String ip, Integer port, String encoding, String url)
/*     */   {
/*  54 */     setId(id);
/*  55 */     setName(name);
/*  56 */     setIp(ip);
/*  57 */     setPort(port);
/*  58 */     setEncoding(encoding);
/*  59 */     setUrl(url);
/*  60 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/* 100 */     this.id = id;
/* 101 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 111 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 119 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/* 127 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip)
/*     */   {
/* 135 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public Integer getPort()
/*     */   {
/* 143 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(Integer port)
/*     */   {
/* 151 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 159 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 167 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 175 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password)
/*     */   {
/* 183 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 191 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(String encoding)
/*     */   {
/* 199 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   public Integer getTimeout()
/*     */   {
/* 207 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(Integer timeout)
/*     */   {
/* 215 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 223 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 231 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 239 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url)
/*     */   {
/* 247 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 253 */     if (obj == null) return false;
/* 254 */     if (!(obj instanceof Ftp)) return false;
/*     */ 
/* 256 */     Ftp ftp = (Ftp)obj;
/* 257 */     if ((getId() == null) || (ftp.getId() == null)) return false;
/* 258 */     return getId().equals(ftp.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 263 */     if (-2147483648 == this.hashCode) {
/* 264 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 266 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 267 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 270 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 275 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseFtp
 * JD-Core Version:    0.6.0
 */