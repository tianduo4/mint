package com.mint.cms;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mint.common.httpClient.HttpProtocolHandler;
import com.mint.common.httpClient.HttpRequest;
import com.mint.common.httpClient.HttpResponse;
import com.mint.common.httpClient.HttpResultType;
import com.mint.common.util.Num62;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class Alipay {
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;
    public static final String characterEncodingUTF8 = "UTF-8";
    public static final String characterEncodingGBK = "GBK";
    public static final String characterEncodingISO = "ISO-8859-1";
    public static final String UNIFORM_SINGLE_INTERFACE = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String WECHATPAYMENT = "tpl.weChatPayment";
    public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String buildRequestMysign(Map<String, String> sPara, String key) {
        String prestr = createLinkString(sPara);
        String mysign = "";
        mysign = sign(prestr, key, "utf-8");
        return mysign;
    }

    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String key) {
        Map sPara = paraFilter(sParaTemp);

        String mysign = buildRequestMysign(sPara, key);

        sPara.put("sign", mysign);
        sPara.put("sign_type", "MD5");
        return sPara;
    }

    public static String buildRequest(Map<String, String> sParaTemp, String key, String strMethod, String strButtonName) {
        Map sPara = buildRequestPara(sParaTemp, key);
        List keys = new ArrayList(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=utf-8\" method=\"" + strMethod + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    public static String buildRequest(String strParaFileName, String strFilePath, Map<String, String> sParaTemp, String key)
            throws Exception {
        Map sPara = buildRequestPara(sParaTemp, key);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);

        request.setCharset("utf-8");
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl("https://mapi.alipay.com/gateway.do?_input_charset=utf-8");
        HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry entry : properties.entrySet()) {
            nameValuePair[(i++)] = new NameValuePair((String) entry.getKey(), (String) entry.getValue());
        }
        return nameValuePair;
    }

    public static boolean verify(Map<String, String> params, String partner, String key) {
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = (String) params.get("notify_id");
            responseTxt = verifyResponse(notify_id, partner);
        }
        String sign = "";
        if (params.get("sign") != null) sign = (String) params.get("sign");
        boolean isSign = getSignVeryfy(params, sign, key);

        return (isSign) && (responseTxt.equals("true"));
    }

    private static boolean getSignVeryfy(Map<String, String> Params, String sign, String key) {
        Map sParaNew = paraFilter(Params);

        String preSignStr = createLinkString(sParaNew);

        boolean isSign = false;
        isSign = verify(preSignStr, sign, key, "utf-8");
        return isSign;
    }

    private static String verifyResponse(String notify_id, String partner) {
        String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
        return checkUrl(veryfy_url);
    }

    private static String checkUrl(String urlvalue) {
        String inputLine = "";
        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection
                            .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }
        return inputLine;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map result = new HashMap();
        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = (String) sArray.get(key);
            if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) ||
                    (key.equalsIgnoreCase("sign_type"))) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    public static String createLinkString(Map<String, String> params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            if (i == keys.size() - 1)
                prestr = prestr + key + "=" + value;
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));

        return mysign.equals(sign);
    }

    private static byte[] getContentBytes(String content, String charset) {
        if ((charset == null) || ("".equals(charset)))
            return content.getBytes();
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
        }
        throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
    }

    public static String getWCRandomNumber(Integer num) {
        return nameDf.format(new Date()) + RandomStringUtils.random(num.intValue(), Num62.N62_CHARS);
    }

    public static String createSign(Map<String, String> param, String key) {
        List list = new ArrayList(param.keySet());
        Object[] ary = list.toArray();
        Arrays.sort(ary);
        list = Arrays.asList(ary);
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = str + list.get(i) + "=" + (String) param.get(new StringBuilder().append(list.get(i)).toString()) + "&";
        }

        str = str + "key=" + key;

        str = MD5Encode(str, "UTF-8").toUpperCase();
        return str;
    }

    public static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if ((charsetName == null) || ("".equals(charsetName)))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
        } catch (Exception localException) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String testPost(String urlStr, String xmlInfo) {
        String line1 = "";
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);

            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            out.write(new String(xmlInfo.getBytes("UTF-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con
                            .getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                line1 = line1 + line;
            }

            return new String(line1.getBytes(), "UTF-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map doXMLParse(String strxml)
            throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if ((strxml == null) || ("".equals(strxml))) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty())
                v = e.getTextNormalize();
            else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }

        in.close();
        return m;
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

    public static String getRequestXml(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (("attach".equalsIgnoreCase(k)) || ("body".equalsIgnoreCase(k)) || ("sign".equalsIgnoreCase(k)))
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static void noticeWeChatSuccess() {
        Map parames = new HashMap();
        parames.put("return_code", "SUCCESS");
        parames.put("return_msg", "OK");

        String xmlWeChat = getRequestXml(parames);
        try {
            testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
            System.out.println("告诉微信不要再回调了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String changeY2F(Double amount) {
        String currency = amount.toString();
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = Long.valueOf(0L);
        if (index == -1)
            amLong = Long.valueOf(currency + "00");
        else if (length - index >= 3)
            amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
        else if (length - index == 2)
            amLong = Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0);
        else {
            amLong = Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    public static void main(String[] args) {
        System.out.println(changeY2F(Double.valueOf(0.01D)));
    }

    public static String CharacterTranscodingGbkToUtf8(String name) {
        String str = null;
        try {
            str = new String(name.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String CharacterTranscodingIsoToUtf8(String name) {
        String str = null;
        try {
            str = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping({"qr_code.jspx"})
    @ResponseBody
    public static void getQRCode(String code_url, HttpServletResponse response) {
        encodeQrcode(code_url, response);
    }

    public static void encodeQrcode(String content, HttpServletResponse response) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            try {
                ImageIO.write(image, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                1);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? -16777216 : -1);
            }
        }
        return image;
    }

    public static boolean isWeiXin(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
            Matcher m = p.matcher(userAgent);
            String version = null;
            if (m.find()) {
                version = m.group(1);
            }
            return (version != null) && (NumberUtils.toInt(version) >= 5);
        }
        return false;
    }

    public static String getXmlRequest(HttpServletRequest request) {
        BufferedReader bis = null;
        String result = "";
        try {
            bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null)
                result = result + line;
        } catch (Exception e) {
            e.printStackTrace();

            if (bis != null)
                try {
                    bis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}

