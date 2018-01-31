/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class PayUtil
/*     */ {
/*     */   public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
/*     */   public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
/* 458 */   public static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
/* 459 */     "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/*     */ 
/*     */   public static String buildAliPayRequest(Map<String, String> sParaTemp, String key, String strMethod, String strButtonName)
/*     */   {
/*  54 */     Map sPara = buildAliPayRequestPara(sParaTemp, key);
/*  55 */     List keys = new ArrayList(sPara.keySet());
/*  56 */     StringBuffer sbHtml = new StringBuffer();
/*  57 */     sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=utf-8\" method=\"" + strMethod + "\">");
/*  58 */     for (int i = 0; i < keys.size(); i++) {
/*  59 */       String name = (String)keys.get(i);
/*  60 */       String value = (String)sPara.get(name);
/*  61 */       sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
/*     */     }
/*     */ 
/*  64 */     sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
/*  65 */     sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
/*  66 */     return sbHtml.toString();
/*     */   }
/*     */ 
/*     */   public static Map<String, String> buildAliPayRequestPara(Map<String, String> sParaTemp, String key)
/*     */   {
/*  76 */     Map sPara = paraFilter(sParaTemp);
/*     */ 
/*  78 */     String mysign = buildRequestMysign(sPara, key);
/*     */ 
/*  80 */     sPara.put("sign", mysign);
/*  81 */     sPara.put("sign_type", "MD5");
/*  82 */     return sPara;
/*     */   }
/*     */ 
/*     */   public static boolean verifyAliPay(Map<String, String> params, String partner, String key)
/*     */   {
/*  94 */     String responseTxt = "true";
/*  95 */     if (params.get("notify_id") != null) {
/*  96 */       String notify_id = (String)params.get("notify_id");
/*  97 */       responseTxt = verifyAliPayResponse(notify_id, partner);
/*     */     }
/*  99 */     String sign = "";
/* 100 */     if (params.get("sign") != null) sign = (String)params.get("sign");
/* 101 */     boolean isSign = getSignVeryfy(params, sign, key);
/*     */ 
/* 106 */     return (isSign) && (responseTxt.equals("true"));
/*     */   }
/*     */ 
/*     */   public static String verifyAliPayResponse(String notify_id, String partner)
/*     */   {
/* 123 */     String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
/* 124 */     return checkAliPayUrl(veryfy_url);
/*     */   }
/*     */ 
/*     */   public static String checkAliPayUrl(String urlvalue)
/*     */   {
/* 137 */     String inputLine = "";
/*     */     try {
/* 139 */       URL url = new URL(urlvalue);
/* 140 */       HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/* 141 */       BufferedReader in = new BufferedReader(
/* 142 */         new InputStreamReader(urlConnection
/* 142 */         .getInputStream()));
/* 143 */       inputLine = in.readLine().toString();
/*     */     } catch (Exception e) {
/* 145 */       e.printStackTrace();
/* 146 */       inputLine = "";
/*     */     }
/* 148 */     return inputLine;
/*     */   }
/*     */ 
/*     */   public static boolean getSignVeryfy(Map<String, String> Params, String sign, String key)
/*     */   {
/* 159 */     Map sParaNew = paraFilter(Params);
/*     */ 
/* 161 */     String preSignStr = createLinkString(sParaNew);
/*     */ 
/* 163 */     boolean isSign = false;
/* 164 */     isSign = verify(preSignStr, sign, key, "utf-8");
/* 165 */     return isSign;
/*     */   }
/*     */ 
/*     */   public static boolean verify(String text, String sign, String key, String input_charset)
/*     */   {
/* 177 */     text = text + key;
/* 178 */     String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
/*     */ 
/* 180 */     return mysign.equals(sign);
/*     */   }
/*     */ 
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/* 193 */     Map result = new HashMap();
/* 194 */     if ((sArray == null) || (sArray.size() <= 0)) {
/* 195 */       return result;
/*     */     }
/* 197 */     for (String key : sArray.keySet()) {
/* 198 */       String value = (String)sArray.get(key);
/* 199 */       if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) || 
/* 200 */         (key.equalsIgnoreCase("sign_type"))) {
/*     */         continue;
/*     */       }
/* 203 */       result.put(key, value);
/*     */     }
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   public static String buildRequestMysign(Map<String, String> sPara, String key)
/*     */   {
/* 214 */     String prestr = createLinkString(sPara);
/* 215 */     String mysign = "";
/* 216 */     mysign = sign(prestr, key, "utf-8");
/* 217 */     return mysign;
/*     */   }
/*     */ 
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/* 226 */     List keys = new ArrayList(params.keySet());
/* 227 */     Collections.sort(keys);
/* 228 */     String prestr = "";
/* 229 */     for (int i = 0; i < keys.size(); i++) {
/* 230 */       String key = (String)keys.get(i);
/* 231 */       String value = (String)params.get(key);
/* 232 */       if (i == keys.size() - 1)
/* 233 */         prestr = prestr + key + "=" + value;
/*     */       else {
/* 235 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/* 238 */     return prestr;
/*     */   }
/*     */ 
/*     */   public static String sign(String text, String key, String input_charset)
/*     */   {
/* 249 */     text = text + key;
/* 250 */     return DigestUtils.md5Hex(getContentBytes(text, input_charset));
/*     */   }
/*     */ 
/*     */   public static byte[] getContentBytes(String content, String charset) {
/* 254 */     if ((charset == null) || ("".equals(charset)))
/* 255 */       return content.getBytes();
/*     */     try
/*     */     {
/* 258 */       return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
/*     */     }
/* 260 */     throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
/*     */   }
/*     */ 
/*     */   public static String assembParamToXml(Map<String, String> parameters)
/*     */   {
/* 270 */     StringBuffer sb = new StringBuffer();
/* 271 */     sb.append("<xml>");
/* 272 */     Set es = parameters.keySet();
/* 273 */     List list = new ArrayList(es);
/* 274 */     Object[] ary = list.toArray();
/* 275 */     Arrays.sort(ary);
/* 276 */     list = Arrays.asList(ary);
/* 277 */     Iterator it = list.iterator();
/* 278 */     while (it.hasNext()) {
/* 279 */       String key = (String)it.next();
/* 280 */       String val = (String)parameters.get(key);
/* 281 */       if (("attach".equalsIgnoreCase(key)) || ("body".equalsIgnoreCase(key)) || ("sign".equalsIgnoreCase(key)))
/* 282 */         sb.append("<" + key + ">" + "<![CDATA[" + val + "]]></" + key + ">");
/*     */       else {
/* 284 */         sb.append("<" + key + ">" + val + "</" + key + ">");
/*     */       }
/*     */     }
/* 287 */     sb.append("</xml>");
/* 288 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getRequestXml(Map<String, String> parameters) {
/* 292 */     StringBuffer sb = new StringBuffer();
/* 293 */     sb.append("<xml>");
/* 294 */     Set es = parameters.entrySet();
/* 295 */     Iterator it = es.iterator();
/* 296 */     while (it.hasNext()) {
/* 297 */       Map.Entry entry = (Map.Entry)it.next();
/* 298 */       String k = (String)entry.getKey();
/* 299 */       String v = (String)entry.getValue();
/* 300 */       if (("attach".equalsIgnoreCase(k)) || ("body".equalsIgnoreCase(k)) || ("sign".equalsIgnoreCase(k)))
/* 301 */         sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
/*     */       else {
/* 303 */         sb.append("<" + k + ">" + v + "</" + k + ">");
/*     */       }
/*     */     }
/* 306 */     sb.append("</xml>");
/* 307 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getWeiXinResponse(HttpServletRequest request)
/*     */   {
/* 316 */     BufferedReader bis = null;
/* 317 */     String result = "";
/*     */     try {
/* 319 */       bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
/* 320 */       String line = null;
/* 321 */       while ((line = bis.readLine()) != null)
/* 322 */         result = result + line;
/*     */     }
/*     */     catch (Exception e) {
/* 325 */       e.printStackTrace();
/*     */ 
/* 327 */       if (bis != null)
/*     */         try {
/* 329 */           bis.close();
/*     */         } catch (IOException e) {
/* 331 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */     finally
/*     */     {
/* 327 */       if (bis != null) {
/*     */         try {
/* 329 */           bis.close();
/*     */         } catch (IOException e) {
/* 331 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 335 */     return result;
/*     */   }
/*     */ 
/*     */   public static Map parseXMLToMap(String strxml)
/*     */     throws JDOMException, IOException
/*     */   {
/* 346 */     strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
/* 347 */     if ((strxml == null) || ("".equals(strxml))) {
/* 348 */       return null;
/*     */     }
/* 350 */     Map m = new HashMap();
/* 351 */     InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
/* 352 */     SAXBuilder builder = new SAXBuilder();
/* 353 */     Document doc = builder.build(in);
/* 354 */     Element root = doc.getRootElement();
/* 355 */     List list = root.getChildren();
/* 356 */     Iterator it = list.iterator();
/* 357 */     while (it.hasNext()) {
/* 358 */       Element e = (Element)it.next();
/* 359 */       String k = e.getName();
/* 360 */       String v = "";
/* 361 */       List children = e.getChildren();
/* 362 */       if (children.isEmpty())
/* 363 */         v = e.getTextNormalize();
/*     */       else {
/* 365 */         v = getChildrenText(children);
/*     */       }
/* 367 */       m.put(k, v);
/*     */     }
/*     */ 
/* 370 */     in.close();
/* 371 */     return m;
/*     */   }
/*     */ 
/*     */   public static String getChildrenText(List children)
/*     */   {
/* 380 */     StringBuffer sb = new StringBuffer();
/* 381 */     if (!children.isEmpty()) {
/* 382 */       Iterator it = children.iterator();
/* 383 */       while (it.hasNext()) {
/* 384 */         Element e = (Element)it.next();
/* 385 */         String name = e.getName();
/* 386 */         String value = e.getTextNormalize();
/* 387 */         List list = e.getChildren();
/* 388 */         sb.append("<" + name + ">");
/* 389 */         if (!list.isEmpty()) {
/* 390 */           sb.append(getChildrenText(list));
/*     */         }
/* 392 */         sb.append(value);
/* 393 */         sb.append("</" + name + ">");
/*     */       }
/*     */     }
/* 396 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String createSign(Map<String, String> param, String key)
/*     */   {
/* 409 */     List list = new ArrayList(param.keySet());
/* 410 */     Object[] ary = list.toArray();
/* 411 */     Arrays.sort(ary);
/* 412 */     list = Arrays.asList(ary);
/* 413 */     String str = "";
/* 414 */     for (int i = 0; i < list.size(); i++) {
/* 415 */       str = str + list.get(i) + "=" + (String)param.get(new StringBuilder().append(list.get(i)).toString()) + "&";
/*     */     }
/*     */ 
/* 418 */     str = str + "key=" + key;
/*     */ 
/* 420 */     str = MD5Encode(str, "utf-8").toUpperCase();
/* 421 */     return str;
/*     */   }
/*     */ 
/*     */   public static String MD5Encode(String origin, String charsetName) {
/* 425 */     String resultString = null;
/*     */     try {
/* 427 */       resultString = new String(origin);
/* 428 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 429 */       if (StringUtils.isBlank(charsetName))
/* 430 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
/*     */       else
/* 432 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 437 */     return resultString;
/*     */   }
/*     */ 
/*     */   public static String byteArrayToHexString(byte[] b) {
/* 441 */     StringBuffer resultSb = new StringBuffer();
/* 442 */     for (int i = 0; i < b.length; i++) {
/* 443 */       resultSb.append(byteToHexString(b[i]));
/*     */     }
/* 445 */     return resultSb.toString();
/*     */   }
/*     */ 
/*     */   public static String byteToHexString(byte b) {
/* 449 */     int n = b;
/* 450 */     if (n < 0) {
/* 451 */       n += 256;
/*     */     }
/* 453 */     int d1 = n / 16;
/* 454 */     int d2 = n % 16;
/* 455 */     return hexDigits[d1] + hexDigits[d2];
/*     */   }
/*     */ 
/*     */   public static String changeY2F(Double amount)
/*     */   {
/* 467 */     String currency = amount.toString();
/* 468 */     int index = currency.indexOf(".");
/* 469 */     int length = currency.length();
/* 470 */     Long amLong = Long.valueOf(0L);
/* 471 */     if (index == -1)
/* 472 */       amLong = Long.valueOf(currency + "00");
/* 473 */     else if (length - index >= 3)
/* 474 */       amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
/* 475 */     else if (length - index == 2)
/* 476 */       amLong = Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0);
/*     */     else {
/* 478 */       amLong = Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00");
/*     */     }
/* 480 */     return amLong.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.PayUtil
 * JD-Core Version:    0.6.0
 */