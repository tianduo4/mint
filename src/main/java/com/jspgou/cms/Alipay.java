/*     */ package com.jspgou.cms;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.jspgou.common.httpClient.HttpProtocolHandler;
/*     */ import com.jspgou.common.httpClient.HttpRequest;
/*     */ import com.jspgou.common.httpClient.HttpResponse;
/*     */ import com.jspgou.common.httpClient.HttpResultType;
/*     */ import com.jspgou.common.util.Num62;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.MessageDigest;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ public class Alipay
/*     */ {
/*     */   private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
/*     */   private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
/*     */   private static final int BLACK = -16777216;
/*     */   private static final int WHITE = -1;
/*     */   public static final String characterEncodingUTF8 = "UTF-8";
/*     */   public static final String characterEncodingGBK = "GBK";
/*     */   public static final String characterEncodingISO = "ISO-8859-1";
/*     */   public static final String UNIFORM_SINGLE_INTERFACE = "https://api.mch.weixin.qq.com/pay/unifiedorder";
/*     */   public static final String WECHATPAYMENT = "tpl.weChatPayment";
/* 364 */   public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");
/*     */ 
/* 432 */   private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
/* 433 */     "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/*     */ 
/*     */   public static String buildRequestMysign(Map<String, String> sPara, String key)
/*     */   {
/*  83 */     String prestr = createLinkString(sPara);
/*  84 */     String mysign = "";
/*  85 */     mysign = sign(prestr, key, "utf-8");
/*  86 */     return mysign;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String key)
/*     */   {
/*  96 */     Map sPara = paraFilter(sParaTemp);
/*     */ 
/*  98 */     String mysign = buildRequestMysign(sPara, key);
/*     */ 
/* 100 */     sPara.put("sign", mysign);
/* 101 */     sPara.put("sign_type", "MD5");
/* 102 */     return sPara;
/*     */   }
/*     */ 
/*     */   public static String buildRequest(Map<String, String> sParaTemp, String key, String strMethod, String strButtonName)
/*     */   {
/* 114 */     Map sPara = buildRequestPara(sParaTemp, key);
/* 115 */     List keys = new ArrayList(sPara.keySet());
/* 116 */     StringBuffer sbHtml = new StringBuffer();
/* 117 */     sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=utf-8\" method=\"" + strMethod + "\">");
/* 118 */     for (int i = 0; i < keys.size(); i++) {
/* 119 */       String name = (String)keys.get(i);
/* 120 */       String value = (String)sPara.get(name);
/* 121 */       sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
/*     */     }
/*     */ 
/* 124 */     sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
/* 125 */     sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
/* 126 */     return sbHtml.toString();
/*     */   }
/*     */ 
/*     */   public static String buildRequest(String strParaFileName, String strFilePath, Map<String, String> sParaTemp, String key)
/*     */     throws Exception
/*     */   {
/* 141 */     Map sPara = buildRequestPara(sParaTemp, key);
/* 142 */     HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
/* 143 */     HttpRequest request = new HttpRequest(HttpResultType.BYTES);
/*     */ 
/* 145 */     request.setCharset("utf-8");
/* 146 */     request.setParameters(generatNameValuePair(sPara));
/* 147 */     request.setUrl("https://mapi.alipay.com/gateway.do?_input_charset=utf-8");
/* 148 */     HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
/* 149 */     if (response == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     String strResult = response.getStringResult();
/* 153 */     return strResult;
/*     */   }
/*     */ 
/*     */   private static NameValuePair[] generatNameValuePair(Map<String, String> properties)
/*     */   {
/* 163 */     NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
/* 164 */     int i = 0;
/* 165 */     for (Map.Entry entry : properties.entrySet()) {
/* 166 */       nameValuePair[(i++)] = new NameValuePair((String)entry.getKey(), (String)entry.getValue());
/*     */     }
/* 168 */     return nameValuePair;
/*     */   }
/*     */ 
/*     */   public static boolean verify(Map<String, String> params, String partner, String key)
/*     */   {
/* 181 */     String responseTxt = "true";
/* 182 */     if (params.get("notify_id") != null) {
/* 183 */       String notify_id = (String)params.get("notify_id");
/* 184 */       responseTxt = verifyResponse(notify_id, partner);
/*     */     }
/* 186 */     String sign = "";
/* 187 */     if (params.get("sign") != null) sign = (String)params.get("sign");
/* 188 */     boolean isSign = getSignVeryfy(params, sign, key);
/*     */ 
/* 193 */     return (isSign) && (responseTxt.equals("true"));
/*     */   }
/*     */ 
/*     */   private static boolean getSignVeryfy(Map<String, String> Params, String sign, String key)
/*     */   {
/* 207 */     Map sParaNew = paraFilter(Params);
/*     */ 
/* 209 */     String preSignStr = createLinkString(sParaNew);
/*     */ 
/* 211 */     boolean isSign = false;
/* 212 */     isSign = verify(preSignStr, sign, key, "utf-8");
/* 213 */     return isSign;
/*     */   }
/*     */ 
/*     */   private static String verifyResponse(String notify_id, String partner)
/*     */   {
/* 227 */     String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
/* 228 */     return checkUrl(veryfy_url);
/*     */   }
/*     */ 
/*     */   private static String checkUrl(String urlvalue)
/*     */   {
/* 241 */     String inputLine = "";
/*     */     try {
/* 243 */       URL url = new URL(urlvalue);
/* 244 */       HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/* 245 */       BufferedReader in = new BufferedReader(
/* 246 */         new InputStreamReader(urlConnection
/* 246 */         .getInputStream()));
/* 247 */       inputLine = in.readLine().toString();
/*     */     } catch (Exception e) {
/* 249 */       e.printStackTrace();
/* 250 */       inputLine = "";
/*     */     }
/* 252 */     return inputLine;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/* 261 */     Map result = new HashMap();
/* 262 */     if ((sArray == null) || (sArray.size() <= 0)) {
/* 263 */       return result;
/*     */     }
/* 265 */     for (String key : sArray.keySet()) {
/* 266 */       String value = (String)sArray.get(key);
/* 267 */       if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) || 
/* 268 */         (key.equalsIgnoreCase("sign_type"))) {
/*     */         continue;
/*     */       }
/* 271 */       result.put(key, value);
/*     */     }
/* 273 */     return result;
/*     */   }
/*     */ 
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/* 282 */     List keys = new ArrayList(params.keySet());
/* 283 */     Collections.sort(keys);
/* 284 */     String prestr = "";
/* 285 */     for (int i = 0; i < keys.size(); i++) {
/* 286 */       String key = (String)keys.get(i);
/* 287 */       String value = (String)params.get(key);
/* 288 */       if (i == keys.size() - 1)
/* 289 */         prestr = prestr + key + "=" + value;
/*     */       else {
/* 291 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/* 294 */     return prestr;
/*     */   }
/*     */ 
/*     */   public static String sign(String text, String key, String input_charset)
/*     */   {
/* 305 */     text = text + key;
/* 306 */     return DigestUtils.md5Hex(getContentBytes(text, input_charset));
/*     */   }
/*     */ 
/*     */   public static boolean verify(String text, String sign, String key, String input_charset)
/*     */   {
/* 318 */     text = text + key;
/* 319 */     String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
/*     */ 
/* 321 */     return mysign.equals(sign);
/*     */   }
/*     */ 
/*     */   private static byte[] getContentBytes(String content, String charset)
/*     */   {
/* 336 */     if ((charset == null) || ("".equals(charset)))
/* 337 */       return content.getBytes();
/*     */     try
/*     */     {
/* 340 */       return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
/*     */     }
/* 342 */     throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
/*     */   }
/*     */ 
/*     */   public static String getWCRandomNumber(Integer num)
/*     */   {
/* 370 */     return nameDf.format(new Date()) + RandomStringUtils.random(num.intValue(), Num62.N62_CHARS);
/*     */   }
/*     */ 
/*     */   public static String createSign(Map<String, String> param, String key)
/*     */   {
/* 383 */     List list = new ArrayList(param.keySet());
/* 384 */     Object[] ary = list.toArray();
/* 385 */     Arrays.sort(ary);
/* 386 */     list = Arrays.asList(ary);
/* 387 */     String str = "";
/* 388 */     for (int i = 0; i < list.size(); i++) {
/* 389 */       str = str + list.get(i) + "=" + (String)param.get(new StringBuilder().append(list.get(i)).toString()) + "&";
/*     */     }
/*     */ 
/* 392 */     str = str + "key=" + key;
/*     */ 
/* 394 */     str = MD5Encode(str, "UTF-8").toUpperCase();
/* 395 */     return str;
/*     */   }
/*     */ 
/*     */   public static String MD5Encode(String origin, String charsetName) {
/* 399 */     String resultString = null;
/*     */     try {
/* 401 */       resultString = new String(origin);
/* 402 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 403 */       if ((charsetName == null) || ("".equals(charsetName)))
/* 404 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
/*     */       else
/* 406 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 411 */     return resultString;
/*     */   }
/*     */ 
/*     */   private static String byteArrayToHexString(byte[] b) {
/* 415 */     StringBuffer resultSb = new StringBuffer();
/* 416 */     for (int i = 0; i < b.length; i++) {
/* 417 */       resultSb.append(byteToHexString(b[i]));
/*     */     }
/* 419 */     return resultSb.toString();
/*     */   }
/*     */ 
/*     */   private static String byteToHexString(byte b) {
/* 423 */     int n = b;
/* 424 */     if (n < 0) {
/* 425 */       n += 256;
/*     */     }
/* 427 */     int d1 = n / 16;
/* 428 */     int d2 = n % 16;
/* 429 */     return hexDigits[d1] + hexDigits[d2];
/*     */   }
/*     */ 
/*     */   public static String testPost(String urlStr, String xmlInfo)
/*     */   {
/* 442 */     String line1 = "";
/*     */     try {
/* 444 */       URL url = new URL(urlStr);
/* 445 */       URLConnection con = url.openConnection();
/* 446 */       con.setDoOutput(true);
/*     */ 
/* 448 */       con.setRequestProperty("Cache-Control", "no-cache");
/* 449 */       con.setRequestProperty("Content-Type", "text/xml");
/*     */ 
/* 451 */       OutputStreamWriter out = new OutputStreamWriter(con
/* 452 */         .getOutputStream());
/* 453 */       out.write(new String(xmlInfo.getBytes("UTF-8")));
/* 454 */       out.flush();
/* 455 */       out.close();
/* 456 */       BufferedReader br = new BufferedReader(
/* 457 */         new InputStreamReader(con
/* 457 */         .getInputStream()));
/* 458 */       String line = "";
/* 459 */       for (line = br.readLine(); line != null; line = br.readLine()) {
/* 460 */         line1 = line1 + line;
/*     */       }
/*     */ 
/* 463 */       return new String(line1.getBytes(), "UTF-8");
/*     */     }
/*     */     catch (MalformedURLException e) {
/* 466 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 468 */       e.printStackTrace();
/*     */     }
/* 470 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map doXMLParse(String strxml)
/*     */     throws JDOMException, IOException
/*     */   {
/* 481 */     strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
/* 482 */     if ((strxml == null) || ("".equals(strxml))) {
/* 483 */       return null;
/*     */     }
/* 485 */     Map m = new HashMap();
/* 486 */     InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
/* 487 */     SAXBuilder builder = new SAXBuilder();
/* 488 */     Document doc = builder.build(in);
/* 489 */     Element root = doc.getRootElement();
/* 490 */     List list = root.getChildren();
/* 491 */     Iterator it = list.iterator();
/* 492 */     while (it.hasNext()) {
/* 493 */       Element e = (Element)it.next();
/* 494 */       String k = e.getName();
/* 495 */       String v = "";
/* 496 */       List children = e.getChildren();
/* 497 */       if (children.isEmpty())
/* 498 */         v = e.getTextNormalize();
/*     */       else {
/* 500 */         v = getChildrenText(children);
/*     */       }
/* 502 */       m.put(k, v);
/*     */     }
/*     */ 
/* 505 */     in.close();
/* 506 */     return m;
/*     */   }
/*     */ 
/*     */   public static String getChildrenText(List children)
/*     */   {
/* 515 */     StringBuffer sb = new StringBuffer();
/* 516 */     if (!children.isEmpty()) {
/* 517 */       Iterator it = children.iterator();
/* 518 */       while (it.hasNext()) {
/* 519 */         Element e = (Element)it.next();
/* 520 */         String name = e.getName();
/* 521 */         String value = e.getTextNormalize();
/* 522 */         List list = e.getChildren();
/* 523 */         sb.append("<" + name + ">");
/* 524 */         if (!list.isEmpty()) {
/* 525 */           sb.append(getChildrenText(list));
/*     */         }
/* 527 */         sb.append(value);
/* 528 */         sb.append("</" + name + ">");
/*     */       }
/*     */     }
/* 531 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getRequestXml(Map<String, String> parameters)
/*     */   {
/* 540 */     StringBuffer sb = new StringBuffer();
/* 541 */     sb.append("<xml>");
/* 542 */     Set es = parameters.entrySet();
/* 543 */     Iterator it = es.iterator();
/* 544 */     while (it.hasNext()) {
/* 545 */       Map.Entry entry = (Map.Entry)it.next();
/* 546 */       String k = (String)entry.getKey();
/* 547 */       String v = (String)entry.getValue();
/* 548 */       if (("attach".equalsIgnoreCase(k)) || ("body".equalsIgnoreCase(k)) || ("sign".equalsIgnoreCase(k)))
/* 549 */         sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
/*     */       else {
/* 551 */         sb.append("<" + k + ">" + v + "</" + k + ">");
/*     */       }
/*     */     }
/* 554 */     sb.append("</xml>");
/* 555 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void noticeWeChatSuccess() {
/* 559 */     Map parames = new HashMap();
/* 560 */     parames.put("return_code", "SUCCESS");
/* 561 */     parames.put("return_msg", "OK");
/*     */ 
/* 563 */     String xmlWeChat = getRequestXml(parames);
/*     */     try {
/* 565 */       testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
/* 566 */       System.out.println("告诉微信不要再回调了");
/*     */     } catch (Exception e) {
/* 568 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String changeY2F(Double amount)
/*     */   {
/* 579 */     String currency = amount.toString();
/* 580 */     int index = currency.indexOf(".");
/* 581 */     int length = currency.length();
/* 582 */     Long amLong = Long.valueOf(0L);
/* 583 */     if (index == -1)
/* 584 */       amLong = Long.valueOf(currency + "00");
/* 585 */     else if (length - index >= 3)
/* 586 */       amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
/* 587 */     else if (length - index == 2)
/* 588 */       amLong = Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0);
/*     */     else {
/* 590 */       amLong = Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00");
/*     */     }
/* 592 */     return amLong.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 596 */     System.out.println(changeY2F(Double.valueOf(0.01D)));
/*     */   }
/*     */ 
/*     */   public static String CharacterTranscodingGbkToUtf8(String name)
/*     */   {
/* 609 */     String str = null;
/*     */     try {
/* 611 */       str = new String(name.getBytes("GBK"), "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 614 */       e.printStackTrace();
/*     */     }
/* 616 */     return str;
/*     */   }
/*     */ 
/*     */   public static String CharacterTranscodingIsoToUtf8(String name)
/*     */   {
/* 625 */     String str = null;
/*     */     try {
/* 627 */       str = new String(name.getBytes("ISO-8859-1"), "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 630 */       e.printStackTrace();
/*     */     }
/* 632 */     return str;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"qr_code.jspx"})
/*     */   @ResponseBody
/*     */   public static void getQRCode(String code_url, HttpServletResponse response)
/*     */   {
/* 643 */     encodeQrcode(code_url, response);
/*     */   }
/*     */ 
/*     */   public static void encodeQrcode(String content, HttpServletResponse response)
/*     */   {
/* 653 */     if (StringUtils.isBlank(content)) {
/* 654 */       return;
/*     */     }
/* 656 */     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
/* 657 */     Map hints = new HashMap();
/* 658 */     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
/* 659 */     BitMatrix bitMatrix = null;
/*     */     try {
/* 661 */       bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
/* 662 */       BufferedImage image = toBufferedImage(bitMatrix);
/*     */       try
/*     */       {
/* 665 */         ImageIO.write(image, "png", response.getOutputStream());
/*     */       }
/*     */       catch (IOException e) {
/* 668 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (WriterException e1) {
/* 672 */       e1.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static BufferedImage toBufferedImage(BitMatrix matrix) {
/* 677 */     int width = matrix.getWidth();
/* 678 */     int height = matrix.getHeight();
/* 679 */     BufferedImage image = new BufferedImage(width, height, 
/* 680 */       1);
/* 681 */     for (int x = 0; x < width; x++) {
/* 682 */       for (int y = 0; y < height; y++) {
/* 683 */         image.setRGB(x, y, matrix.get(x, y) ? -16777216 : -1);
/*     */       }
/*     */     }
/* 686 */     return image;
/*     */   }
/*     */ 
/*     */   public static boolean isWeiXin(HttpServletRequest request)
/*     */   {
/* 696 */     String userAgent = request.getHeader("User-Agent");
/* 697 */     if (StringUtils.isNotBlank(userAgent)) {
/* 698 */       Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
/* 699 */       Matcher m = p.matcher(userAgent);
/* 700 */       String version = null;
/* 701 */       if (m.find()) {
/* 702 */         version = m.group(1);
/*     */       }
/* 704 */       return (version != null) && (NumberUtils.toInt(version) >= 5);
/*     */     }
/* 706 */     return false;
/*     */   }
/*     */ 
/*     */   public static String getXmlRequest(HttpServletRequest request)
/*     */   {
/* 715 */     BufferedReader bis = null;
/* 716 */     String result = "";
/*     */     try {
/* 718 */       bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
/* 719 */       String line = null;
/* 720 */       while ((line = bis.readLine()) != null)
/* 721 */         result = result + line;
/*     */     }
/*     */     catch (Exception e) {
/* 724 */       e.printStackTrace();
/*     */ 
/* 726 */       if (bis != null)
/*     */         try {
/* 728 */           bis.close();
/*     */         } catch (IOException ex) {
/* 730 */           ex.printStackTrace();
/*     */         }
/*     */     }
/*     */     finally
/*     */     {
/* 726 */       if (bis != null) {
/*     */         try {
/* 728 */           bis.close();
/*     */         } catch (IOException e) {
/* 730 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 734 */     return result;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.Alipay
 * JD-Core Version:    0.6.0
 */