package com.jspgou.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PayUtil {
    public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
    public static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String buildAliPayRequest(Map<String, String> sParaTemp, String key, String strMethod, String strButtonName) {
        Map sPara = buildAliPayRequestPara(sParaTemp, key);
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

    public static Map<String, String> buildAliPayRequestPara(Map<String, String> sParaTemp, String key) {
        Map sPara = paraFilter(sParaTemp);

        String mysign = buildRequestMysign(sPara, key);

        sPara.put("sign", mysign);
        sPara.put("sign_type", "MD5");
        return sPara;
    }

    public static boolean verifyAliPay(Map<String, String> params, String partner, String key) {
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = (String) params.get("notify_id");
            responseTxt = verifyAliPayResponse(notify_id, partner);
        }
        String sign = "";
        if (params.get("sign") != null) sign = (String) params.get("sign");
        boolean isSign = getSignVeryfy(params, sign, key);

        return (isSign) && (responseTxt.equals("true"));
    }

    public static String verifyAliPayResponse(String notify_id, String partner) {
        String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
        return checkAliPayUrl(veryfy_url);
    }

    public static String checkAliPayUrl(String urlvalue) {
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

    public static boolean getSignVeryfy(Map<String, String> Params, String sign, String key) {
        Map sParaNew = paraFilter(Params);

        String preSignStr = createLinkString(sParaNew);

        boolean isSign = false;
        isSign = verify(preSignStr, sign, key, "utf-8");
        return isSign;
    }

    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));

        return mysign.equals(sign);
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

    public static String buildRequestMysign(Map<String, String> sPara, String key) {
        String prestr = createLinkString(sPara);
        String mysign = "";
        mysign = sign(prestr, key, "utf-8");
        return mysign;
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

    public static byte[] getContentBytes(String content, String charset) {
        if ((charset == null) || ("".equals(charset)))
            return content.getBytes();
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
        }
        throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
    }

    public static String assembParamToXml(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.keySet();
        List list = new ArrayList(es);
        Object[] ary = list.toArray();
        Arrays.sort(ary);
        list = Arrays.asList(ary);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String val = (String) parameters.get(key);
            if (("attach".equalsIgnoreCase(key)) || ("body".equalsIgnoreCase(key)) || ("sign".equalsIgnoreCase(key)))
                sb.append("<" + key + ">" + "<![CDATA[" + val + "]]></" + key + ">");
            else {
                sb.append("<" + key + ">" + val + "</" + key + ">");
            }
        }
        sb.append("</xml>");
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

    public static String getWeiXinResponse(HttpServletRequest request) {
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

    public static Map parseXMLToMap(String strxml)
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

        str = MD5Encode(str, "utf-8").toUpperCase();
        return str;
    }

    public static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isBlank(charsetName))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
        } catch (Exception localException) {
        }
        return resultString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
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
}

