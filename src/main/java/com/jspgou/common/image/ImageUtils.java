package com.jspgou.common.image;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ImageUtils {
    public static final String[] IMAGE_EXT = {"jpg", "jpeg",
            "gif", "png", "bmp"};

    public static boolean isValidImageExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : IMAGE_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImage(InputStream in) {
        ImageInfo ii = new ImageInfo();
        ii.setInput(in);
        return ii.check();
    }

    public static Position markPosition(int width, int height, int p, int offsetx, int offsety) {
        if ((p < 1) || (p > 5))
            p = (int) (Math.random() * 5.0D) + 1;
        int y;
        int x;
        switch (p) {
            case 1:
                x = offsetx;
                y = offsety;
                break;
            case 2:
                x = width + offsetx;
                y = offsety;
                break;
            case 3:
                x = offsetx;
                y = height + offsety;
                break;
            case 4:
                x = width + offsetx;
                y = height + offsety;
                break;
            case 5:
                x = width / 2 + offsetx;
                y = height / 2 + offsety;
                break;
            default:
                throw new RuntimeException("never reach ...");
        }
        return new Position(x, y);
    }

    public static List<String> getImageSrc(String htmlCode) {
        List imageSrcList = new ArrayList();
        String regular = "<img(.*?)src=\"(.*?)\"";
        String img_pre = "(?i)<img(.*?)src=\"";
        String img_sub = "\"";
        Pattern p = Pattern.compile(regular, 2);
        Matcher m = p.matcher(htmlCode);
        String src = null;
        while (m.find()) {
            src = m.group();
            src = src.replaceAll(img_pre, "").replaceAll(img_sub, "").trim();
            imageSrcList.add(src);
        }
        return imageSrcList;
    }

    public static boolean isImage(String s) {
        s = s.toLowerCase();
        String[] as = IMAGE_EXT;
        int i = as.length;
        for (int j = 0; j < i; j++) {
            String s1 = as[j];
            if (s1.equals(s))
                return true;
        }
        return false;
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return this.y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}

