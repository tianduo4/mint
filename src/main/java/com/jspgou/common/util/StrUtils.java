/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class StrUtils
/*     */ {
/*     */   private static final String API_PLACEHOLDER_VIDEO_BEGIN = "_video_begin";
/*     */   private static final String API_PLACEHOLDER_VIDEO_END = "_video_end";
/*     */   private static final String API_PLACEHOLDER_IMAGE_BEGIN = "_image_begin";
/*     */   private static final String API_PLACEHOLDER_IMAGE_END = "_image_end";
/*     */ 
/*     */   public static String handelUrl(String url)
/*     */   {
/*  42 */     if (url == null) {
/*  43 */       return null;
/*     */     }
/*  45 */     url = url.trim();
/*  46 */     if ((url.equals("")) || (url.startsWith("http://")) || 
/*  47 */       (url.startsWith("https://"))) {
/*  48 */       return url;
/*     */     }
/*  50 */     return "http://" + url.trim();
/*     */   }
/*     */ 
/*     */   public static String txt2htm(String txt)
/*     */   {
/*  61 */     if (StringUtils.isBlank(txt)) {
/*  62 */       return txt;
/*     */     }
/*  64 */     StringBuilder sb = new StringBuilder((int)(txt.length() * 1.2D));
/*     */ 
/*  66 */     boolean doub = false;
/*  67 */     for (int i = 0; i < txt.length(); i++) {
/*  68 */       char c = txt.charAt(i);
/*  69 */       if (c == ' ') {
/*  70 */         if (doub) {
/*  71 */           sb.append(' ');
/*  72 */           doub = false;
/*     */         } else {
/*  74 */           sb.append("&nbsp;");
/*  75 */           doub = true;
/*     */         }
/*     */       } else {
/*  78 */         doub = false;
/*  79 */         switch (c) {
/*     */         case '&':
/*  81 */           sb.append("&amp;");
/*  82 */           break;
/*     */         case '<':
/*  84 */           sb.append("&lt;");
/*  85 */           break;
/*     */         case '>':
/*  87 */           sb.append("&gt;");
/*  88 */           break;
/*     */         case '"':
/*  90 */           sb.append("&quot;");
/*  91 */           break;
/*     */         case '\n':
/*  93 */           sb.append("<br/>");
/*  94 */           break;
/*     */         default:
/*  96 */           sb.append(c);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 101 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String textCut(String s, int len, String append)
/*     */   {
/* 114 */     if (s == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     int slen = s.length();
/* 118 */     if (slen <= len) {
/* 119 */       return s;
/*     */     }
/*     */ 
/* 122 */     int maxCount = len * 2;
/* 123 */     int count = 0;
/* 124 */     int i = 0;
/* 125 */     for (; (count < maxCount) && (i < slen); i++) {
/* 126 */       if (s.codePointAt(i) < 256)
/* 127 */         count++;
/*     */       else {
/* 129 */         count += 2;
/*     */       }
/*     */     }
/* 132 */     if (i < slen) {
/* 133 */       if (count > maxCount) {
/* 134 */         i--;
/*     */       }
/* 136 */       if (!StringUtils.isBlank(append)) {
/* 137 */         if (s.codePointAt(i - 1) < 256)
/* 138 */           i -= 2;
/*     */         else {
/* 140 */           i--;
/*     */         }
/* 142 */         return s.substring(0, i) + append;
/*     */       }
/* 144 */       return s.substring(0, i);
/*     */     }
/*     */ 
/* 147 */     return s;
/*     */   }
/*     */ 
/*     */   public static String trimHtml2Txt(String html)
/*     */   {
/* 164 */     html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");
/* 165 */     html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");
/* 166 */     html = html.replaceAll("\\<![\\s\\S]*?>", "");
/* 167 */     html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");
/* 168 */     html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");
/* 169 */     html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");
/* 170 */     html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");
/* 171 */     html = html.replaceAll("\\<table>[\\s\\S]*?</table>(?i)", "");
/* 172 */     html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)", 
/* 173 */       "");
/* 174 */     html = html.replaceAll("\\\r\n|\n|\r", "");
/* 175 */     html = html.replaceAll("\\<br[^>]*>(?i)", "\r\n");
/* 176 */     html = html.replaceAll("\\</p>(?i)", "\r\n");
/* 177 */     html = html.replaceAll("\\<p>(?i)", "\r\n");
/*     */ 
/* 180 */     String regular = "<img(.*?)src=\"(.*?)/>";
/* 181 */     String img_pre = "<img(.*?)src=\"";
/* 182 */     String img_sub = "\"(.*?)/>";
/* 183 */     Pattern p = Pattern.compile(regular, 2);
/* 184 */     Matcher m = p.matcher(html);
/* 185 */     String src = null;
/* 186 */     String newSrc = null;
/* 187 */     while (m.find()) {
/* 188 */       src = m.group();
/* 189 */       newSrc = src.replaceAll(img_pre, "_image_begin")
/* 190 */         .replaceAll(img_sub, "_image_end")
/* 191 */         .trim();
/* 192 */       html = html.replace(src, newSrc);
/*     */     }
/*     */ 
/* 195 */     String videoregular = "<video(.*?)src=\"(.*?)\"(.*?)</video>";
/* 196 */     String video_pre = "<video(.*?)src=\"";
/* 197 */     String video_sub = "\"(.*?)</video>";
/* 198 */     Pattern videop = 
/* 199 */       Pattern.compile(videoregular, 2);
/* 200 */     Matcher videom = videop.matcher(html);
/* 201 */     String videosrc = null;
/* 202 */     String videonewSrc = null;
/* 203 */     while (videom.find()) {
/* 204 */       videosrc = videom.group();
/* 205 */       videonewSrc = videosrc
/* 206 */         .replaceAll(video_pre, "_video_begin")
/* 207 */         .replaceAll(video_sub, "_video_end")
/* 208 */         .trim();
/* 209 */       html = html.replace(videosrc, videonewSrc);
/*     */     }
/*     */ 
/* 212 */     html = html.replace("[NextPage][/NextPage]", "");
/* 213 */     html = html.replaceAll("\\<[^>]+>", "");
/*     */ 
/* 215 */     return html.trim();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.StrUtils
 * JD-Core Version:    0.6.0
 */