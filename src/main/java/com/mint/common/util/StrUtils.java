package com.mint.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StrUtils {
    private static final String API_PLACEHOLDER_VIDEO_BEGIN = "_video_begin";
    private static final String API_PLACEHOLDER_VIDEO_END = "_video_end";
    private static final String API_PLACEHOLDER_IMAGE_BEGIN = "_image_begin";
    private static final String API_PLACEHOLDER_IMAGE_END = "_image_end";

    public static String handelUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if ((url.equals("")) || (url.startsWith("http://")) ||
                (url.startsWith("https://"))) {
            return url;
        }
        return "http://" + url.trim();
    }

    public static String txt2htm(String txt) {
        if (StringUtils.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2D));

        boolean doub = false;
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if (c == ' ') {
                if (doub) {
                    sb.append(' ');
                    doub = false;
                } else {
                    sb.append("&nbsp;");
                    doub = true;
                }
            } else {
                doub = false;
                switch (c) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '\n':
                        sb.append("<br/>");
                        break;
                    default:
                        sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }

        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for (; (count < maxCount) && (i < slen); i++) {
            if (s.codePointAt(i) < 256)
                count++;
            else {
                count += 2;
            }
        }
        if (i < slen) {
            if (count > maxCount) {
                i--;
            }
            if (!StringUtils.isBlank(append)) {
                if (s.codePointAt(i - 1) < 256)
                    i -= 2;
                else {
                    i--;
                }
                return s.substring(0, i) + append;
            }
            return s.substring(0, i);
        }

        return s;
    }

    public static String trimHtml2Txt(String html) {
        html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");
        html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");
        html = html.replaceAll("\\<![\\s\\S]*?>", "");
        html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");
        html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");
        html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");
        html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");
        html = html.replaceAll("\\<table>[\\s\\S]*?</table>(?i)", "");
        html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)",
                "");
        html = html.replaceAll("\\\r\n|\n|\r", "");
        html = html.replaceAll("\\<br[^>]*>(?i)", "\r\n");
        html = html.replaceAll("\\</p>(?i)", "\r\n");
        html = html.replaceAll("\\<p>(?i)", "\r\n");

        String regular = "<img(.*?)src=\"(.*?)/>";
        String img_pre = "<img(.*?)src=\"";
        String img_sub = "\"(.*?)/>";
        Pattern p = Pattern.compile(regular, 2);
        Matcher m = p.matcher(html);
        String src = null;
        String newSrc = null;
        while (m.find()) {
            src = m.group();
            newSrc = src.replaceAll(img_pre, "_image_begin")
                    .replaceAll(img_sub, "_image_end")
                    .trim();
            html = html.replace(src, newSrc);
        }

        String videoregular = "<video(.*?)src=\"(.*?)\"(.*?)</video>";
        String video_pre = "<video(.*?)src=\"";
        String video_sub = "\"(.*?)</video>";
        Pattern videop =
                Pattern.compile(videoregular, 2);
        Matcher videom = videop.matcher(html);
        String videosrc = null;
        String videonewSrc = null;
        while (videom.find()) {
            videosrc = videom.group();
            videonewSrc = videosrc
                    .replaceAll(video_pre, "_video_begin")
                    .replaceAll(video_sub, "_video_end")
                    .trim();
            html = html.replace(videosrc, videonewSrc);
        }

        html = html.replace("[NextPage][/NextPage]", "");
        html = html.replaceAll("\\<[^>]+>", "");

        return html.trim();
    }
}

